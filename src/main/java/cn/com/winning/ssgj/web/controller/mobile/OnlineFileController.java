package cn.com.winning.ssgj.web.controller.mobile;

import cn.com.winning.ssgj.base.Constants;
import cn.com.winning.ssgj.base.annoation.ILog;
import cn.com.winning.ssgj.base.helper.SSGJHelper;
import cn.com.winning.ssgj.base.util.*;
import cn.com.winning.ssgj.domain.*;
import cn.com.winning.ssgj.web.controller.common.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.*;

/**
 * 实施资料汇总上线可行性方案表
 *
 * @author ChenKuai
 * @create 2018-03-10 上午 11:37
 **/
@Controller
@CrossOrigin
@RequestMapping("mobile/implementData")
public class OnlineFileController extends BaseController {

    @Autowired
    private SSGJHelper ssgjHelper;

    private static int port = Integer.valueOf(FtpPropertiesLoader.getProperty("ftp.port")).intValue();


    @RequestMapping(value = "/list.do")
    public String floorQuestionList(Model model,String userId,String serialNo) {

        EtOnlineFile onlineFile = new  EtOnlineFile();
        //parameter = "eyJXT1JLTlVNIjoiNTgyMyIsIkhPU1BDT0RFIjoiMTE5ODAifQ==";
        try{
            //获取用户的信息
            SysUserInfo info = new SysUserInfo();
            info.setId(Long.valueOf(userId));
            info.setStatus(1);
            info.setUserType("1");  //0医院1公司员工
            info = super.getFacade().getSysUserInfoService().getSysUserInfo(info);
            if(info !=null){
                //根据客户编号 找出对应的全部 1.业务流程调研 = 调研报告
                EtBusinessProcess businessProcess = new EtBusinessProcess();
                businessProcess.setSerialNo(serialNo);
                businessProcess.setIsScope(1);
                List<EtBusinessProcess> businessProcessesList = super.getFacade().getEtBusinessProcessService().getEtBusinessProcessList(businessProcess);
                //根据客户编号 找出对应的全部 2.单据报表开发 = 单据
                EtReport report=new EtReport();
                report.setSerialNo(serialNo);
                List<EtReport> reportList = super.getFacade().getEtReportService().getEtReportList(report);
                //根据客户编号 找出对应的全部 3.上线可行性评估 = 上线联调报告 4.上线切换方案
                onlineFile.setSerialNo(serialNo);
                onlineFile.setStatus(1);
                onlineFile.setFileType("3");
                List<EtOnlineFile> onlineFileList_three = super.getFacade().getEtOnlineFileService().getEtOnlineFileList(onlineFile);
                onlineFile.setFileType("4");
                List<EtOnlineFile> onlineFileList_four = super.getFacade().getEtOnlineFileService().getEtOnlineFileList(onlineFile);
                model.addAttribute("onlineFileList_one",businessProcessesList);
                model.addAttribute("onlineFileList_two",reportList);
                model.addAttribute("onlineFileList_three",onlineFileList_three);
                model.addAttribute("onlineFileList_four",onlineFileList_four);
            }else{

            }
            model.addAttribute("userId",userId);
            model.addAttribute("serialNo",serialNo);
            model.addAttribute("serialName", getSerialName(Long.parseLong(serialNo)));
        }catch (Exception e){
            e.printStackTrace();
        }


        return "/mobile/enterprise/data-upload";
    }

    @RequestMapping("/details.do")
    @ILog
    public String fileDetails(Model model, String fileType,String serialNo,String userId,Long id) {
        if(fileType.equals("1")){
            EtBusinessProcess process = new EtBusinessProcess();
            process.setId(id);
            process = super.getFacade().getEtBusinessProcessService().getEtBusinessProcess(process);
            if(StringUtils.isNotBlank(process.getImgPath())){
                String[] imgs=process.getImgPath().split(";");
                List<String> lists= Arrays.asList(imgs);
                process.setImgs(lists);
            }
            model.addAttribute("onlineFiles",process);
            model.addAttribute("dataName",process.getFlowName());

        }else if(fileType.equals("2")){
            EtReport report = new EtReport();
            report.setId(id);
            report = super.getFacade().getEtReportService().getEtReport(report);
            if(StringUtils.isNotBlank(report.getImgPath())){
                String[] imgs=report.getImgPath().split(";");
                List<String> lists= Arrays.asList(imgs);
                report.setImgs(lists);
            }
            SysDictInfo info1 = new SysDictInfo();
            info1.setDictCode("paperType");
            List<SysDictInfo> dictInfos =super.getFacade().getSysDictInfoService().getSysDictInfoList(info1);
            model.addAttribute("dictInfos",dictInfos);
            model.addAttribute("onlineFiles",report);
            model.addAttribute("dataName",report.getReportName());
            model.addAttribute("fileType",2);

        }else{
            EtOnlineFile info = new EtOnlineFile();
            info.setSerialNo(serialNo);
            if(fileType.equals("3")){
                info.setFileType("3"); //上线可行性报告
            }else{
                info.setFileType("4");
            }
            info.setStatus(1);
            //info.setId(id);
            List<EtOnlineFile> onlineFiles =super.getFacade().getEtOnlineFileService().getEtOnlineFileList(info);
            model.addAttribute("onlineFiles",onlineFiles);
            model.addAttribute("dataName","上线可行性报告");
        }
        model.addAttribute("serialNo",serialNo);
        model.addAttribute("userId",userId);
        model.addAttribute("fileType",fileType);

        return "/mobile/enterprise/data-upload-report";
    }

    @RequestMapping("/addDetails.do")
    @ILog
    public String addDetails(Model model, String fileType,String serialNo,String userId,Long id) {
        if(fileType.equals("2")){
            SysDictInfo info1 = new SysDictInfo();
            info1.setDictCode("paperType");
            List<SysDictInfo> dictInfos =super.getFacade().getSysDictInfoService().getSysDictInfoList(info1);
            model.addAttribute("dictInfos",dictInfos);
        }
        model.addAttribute("serialNo",serialNo);
        model.addAttribute("userId",userId);
        model.addAttribute("fileType",fileType);

        return "/mobile/enterprise/data-upload-add";
    }


    /**
     * Chen,Kuai 上传图片
     */
    @RequestMapping(value="/uploadImgAjax.do", method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Boolean> uploadImgAjax (HttpServletRequest request,@RequestParam MultipartFile uploadFile){
        Map<String,Boolean> map = new HashMap<String,Boolean>();
        String serialNo = request.getParameter("serialNo");
        String userId = request.getParameter("userId");
        String fileType = request.getParameter("fileType");
        String dataName = request.getParameter("dataName");
        String dataType = request.getParameter("dataType");
        String id =request.getParameter("id");
        try{
            if(!uploadFile.isEmpty()) {
                //上传文件路径
                String pathLu= Constants.UPLOAD_MOBILE_PREFIX+serialNo+"/onlineFile/";
                String path = request.getServletContext().getRealPath(pathLu);

                //上传文件名
                String filename = uploadFile.getOriginalFilename();
                filename = System.currentTimeMillis()+"."+StringUtils.substringAfterLast(filename,".");
                File filepath = new File(path,filename);
                //判断路径是否存在，如果不存在就创建一个
                if (!filepath.getParentFile().exists()) {
                    filepath.getParentFile().mkdirs();
                }
                //将上传文件保存到一个目标文件当中
                File newFile = new File(path + File.separator + filename);
                if(newFile.exists()){
                    newFile.delete();
                }
                uploadFile.transferTo(newFile);
                String remotePath = pathLu+ filename;
                String remoteDir =pathLu ;
                boolean ftpStatus = false;
                String msg = "";
                if (port == 21){
                    ftpStatus = FtpUtils.uploadFile(remotePath, newFile);
                }else if(port == 22){
                    try {
                        SFtpUtils.uploadFile(newFile.getPath(),remoteDir,filename);
                        ftpStatus = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        ftpStatus = false;
                        msg = e.getMessage();
                    }
                }
                if(ftpStatus){
                    EtUserLookProject lastProject= super.getFacade().getEtUserLookProjectService().getLastUserLookProject(super.user_id(userId,"1"));
                    if(fileType.equals("1")){  //调研报告
                        EtBusinessProcess process = new EtBusinessProcess();
                        process.setId(Long.valueOf(id));
                        process = super.getFacade().getEtBusinessProcessService().getEtBusinessProcess(process);
                        if(StringUtils.isNotBlank(process.getImgPath())){
                            process.setImgPath(process.getImgPath()+";"+ remotePath);//拼接图片路径
                        }else{
                            process.setImgPath(remotePath);
                        }
                        process.setFlowName(dataName);
                        //process.setOperator(super.user_id(userId,"1"));
                        process.setOperator(Long.parseLong(userId));
                        process.setOperatorTime(new Timestamp(new Date().getTime()));
                        super.getFacade().getEtBusinessProcessService().modifyEtBusinessProcess(process);

                    }else if(fileType.equals("2")){//单据报表
                        EtReport report = new EtReport();
                        report.setId(Long.valueOf(id));
                        report = super.getFacade().getEtReportService().getEtReport(report);
                        if(StringUtils.isNotBlank(report.getImgPath())){
                            report.setImgPath(report.getImgPath()+ remotePath+";");//拼接图片路径
                        }else{
                            report.setImgPath(remotePath+";");
                        }
                        report.setReportName(dataName);
                        report.setReportType(Integer.valueOf(dataType));
                        report.setOperator(Long.parseLong(userId));
                        report.setOperatorTime(new Timestamp(new Date().getTime()));
                        super.getFacade().getEtReportService().modifyEtReport(report);

                    }else{ //上线/切花审批
                        EtOnlineFile info = new EtOnlineFile();
//                        info.setId(Long.valueOf(id));
//                        info = super.getFacade().getEtOnlineFileService().getEtOnlineFile(info);
//                        if(StringUtils.isNotBlank(info.getImgPath())){
//                            info.setImgPath(info.getImgPath()+";"+ remotePath);//拼接图片路径
//                        }else{
//                            info.setImgPath(remotePath);
//                        }
//                        info.setOperator(super.user_id(userId,"1"));
//                        info.setOperatorTime(new Timestamp(new Date().getTime()));
//                        super.getFacade().getEtOnlineFileService().modifyEtOnlineFile(info);
//                        EtOnlineFile info = new EtOnlineFile();
//                        SysUserInfo userInfo = new SysUserInfo();
//                        userInfo.setUserid(userId+"");//员工编码
//                        userInfo.setStatus(1);
//                        userInfo.setUserType("0");//0医院
//                        List<SysUserInfo> userInfoList = super.getFacade().getSysUserInfoService().getSysUserInfoList(userInfo);
//                        //上传资料 1.生成一条记录//2.修改原来图片路径
                        info.setId(ssgjHelper.createEtOnlineFileIdService());
                        info.setSerialNo(serialNo);
                        if(lastProject !=null && !"".equals(lastProject)){
                            info.setcId(lastProject.getCId());
                            info.setPmId(lastProject.getPmId());
                        }else{
                            info.setcId((long)-2);
                            info.setPmId((long)-2);
                        }
                        info.setImgPath(remotePath);//图片路径
                        info.setFileType(fileType);
                        info.setCreator((long)super.user_id(userId,"1"));
                        info.setCreateTime(new Timestamp(new Date().getTime()));
                        info.setOperator(Long.parseLong(userId));
                        info.setOperatorTime(new Timestamp(new Date().getTime()));
                        super.getFacade().getEtOnlineFileService().createEtOnlineFile(info);
                    }

                    map.put("status",true);

                }else if(!StringUtil.isEmptyOrNull(msg)){
                    map.put("status",false);

                }
            } else {
                map.put("status",false);
            }

        }catch (Exception e){
            e.printStackTrace();
            map.put("status",false);
        }


        return map;

    }


    /**
     * Chen,Kuai 上传图片
     */
    @RequestMapping(value="/uploadImgAjaxAdd.do", method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> uploadImgAjaxAdd (HttpServletRequest request,@RequestParam MultipartFile uploadFile){
        Map<String,Object> map = new HashMap<String,Object>();
        String serialNo = request.getParameter("serialNo");
        String userId = request.getParameter("userId");
        String fileType = request.getParameter("fileType");
        String dataName = request.getParameter("dataName");
        String dataType = request.getParameter("dataType");
        String id = request.getParameter("id");
        try{
            if(!uploadFile.isEmpty()) {
                //上传文件路径
                String pathLu= Constants.UPLOAD_MOBILE_PREFIX+serialNo+"/onlineFile/";
                String path = request.getServletContext().getRealPath(pathLu);

                //上传文件名
                String filename = uploadFile.getOriginalFilename();
                filename = System.currentTimeMillis()+"."+StringUtils.substringAfterLast(filename,".");
                File filepath = new File(path,filename);
                //判断路径是否存在，如果不存在就创建一个
                if (!filepath.getParentFile().exists()) {
                    filepath.getParentFile().mkdirs();
                }
                //将上传文件保存到一个目标文件当中
                File newFile = new File(path + File.separator + filename);
                if(newFile.exists()){
                    newFile.delete();
                }
                uploadFile.transferTo(newFile);
                String remotePath = pathLu+ filename;
                String remoteDir =pathLu ;
                boolean ftpStatus = false;
                String msg = "";
                if (port == 21){
                    ftpStatus = FtpUtils.uploadFile(remotePath, newFile);
                }else if(port == 22){
                    try {
                        SFtpUtils.uploadFile(newFile.getPath(),remoteDir,filename);
                        ftpStatus = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        ftpStatus = false;
                        msg = e.getMessage();
                    }
                }
                EtUserLookProject lastProject= super.getFacade().getEtUserLookProjectService().getLastUserLookProject(Long.parseLong(userId));
                if(ftpStatus){
                    if(fileType.equals("1")){  //调研报告
                        EtBusinessProcess process = new EtBusinessProcess();
                        if(StringUtils.isBlank(id)){  //id为空新建一个流程项目
                            Long onlineId =ssgjHelper.createEtBusinessProcessIdService();
                            process.setId(onlineId);//业务流程ID
                            process.setImgPath(remotePath);
                            if(lastProject !=null && !"".equals(lastProject)){
                                process.setcId(lastProject.getCId());
                                process.setPmId(lastProject.getPmId());
                            }else{
                                process.setcId((long)-2);
                                process.setPmId((long)-2);
                            }
                            process.setFlowName(dataName);
                            process.setSerialNo(serialNo);
                            process.setFlowCode(ssgjHelper.createCustomerFlowCode());
                            process.setSourceType(Constants.STATUS_USE);
                            process.setStatus(Constants.STATUS_USE);
                            process.setIsScope(1);
                            //process.setCreator(super.user_id(userId,"1"));
                            process.setCreator(Long.parseLong(userId));
                            process.setCreateTime(new Timestamp(new Date().getTime()));
                            super.getFacade().getEtBusinessProcessService().createEtBusinessProcess(process);
                            map.put("onlineId",String.valueOf(onlineId));
                        }else{
                            process.setId(Long.valueOf(id));
                            process = super.getFacade().getEtBusinessProcessService().getEtBusinessProcess(process);
                            if(StringUtils.isNotBlank(process.getImgPath())){
                                process.setImgPath(process.getImgPath()+";"+ remotePath);//拼接图片路径
                            }else{
                                process.setImgPath(remotePath);
                            }
                            process.setOperator(Long.parseLong(userId));
                            process.setOperatorTime(new Timestamp(new Date().getTime()));
                            super.getFacade().getEtBusinessProcessService().modifyEtBusinessProcess(process);
                            map.put("onlineId",String.valueOf(id));

                        }

                    }else if(fileType.equals("2")){//单据报表 新增
                        EtReport report = new EtReport();
                        if(StringUtils.isBlank(id)){
                            report.setId(ssgjHelper.createEtReportIdService());
                            report.setSerialNo(serialNo);
                            if(lastProject !=null && !"".equals(lastProject)){
                                report.setcId(lastProject.getCId());
                                report.setPmId(lastProject.getPmId());
                            }else{
                                report.setcId((long)-2);
                                report.setPmId((long)-2);
                            }
                            report.setImgPath(remotePath+";");
                            report.setReportName(dataName);
                            report.setReportType(Integer.parseInt(dataType));
                            report.setStatus(1);//未审核
                            report.setSourceType(0);//自定义
                            report.setCreator(Long.parseLong(userId));
                            report.setCreateTime(new Timestamp(new Date().getTime()));
                            report.setOperator(Long.parseLong(userId));
                            report.setOperatorTime(new Timestamp(new Date().getTime()));
                            super.getFacade().getEtReportService().createEtReport(report);
                            map.put("onlineId",String.valueOf(report.getId()));
                        }else{
                            report.setId(Long.valueOf(id));
                            report = super.getFacade().getEtReportService().getEtReport(report);
                            if(StringUtils.isNotBlank(report.getImgPath())){
                                report.setImgPath(report.getImgPath()+ remotePath+";");//拼接图片路径
                            }else{
                                report.setImgPath(remotePath+";");
                            }
                            report.setOperator(Long.parseLong(userId));
                            report.setOperatorTime(new Timestamp(new Date().getTime()));
                            super.getFacade().getEtReportService().modifyEtReport(report);
                            map.put("onlineId",String.valueOf(id));
                        }

                    }else{ //上线/切花审批
                        EtOnlineFile info = new EtOnlineFile();
                        //info.setId(Long.valueOf(id));
                        info = super.getFacade().getEtOnlineFileService().getEtOnlineFile(info);
                        if(StringUtils.isNotBlank(info.getImgPath())){
                            info.setImgPath(info.getImgPath()+";"+ remotePath);//拼接图片路径
                        }else{
                            info.setImgPath(remotePath);
                        }
                        info.setOperator(Long.parseLong(userId));
                        info.setOperatorTime(new Timestamp(new Date().getTime()));
                        super.getFacade().getEtOnlineFileService().modifyEtOnlineFile(info);
                    }

                    map.put("status","true");

                }else if(!StringUtil.isEmptyOrNull(msg)){
                    map.put("status","false");

                }
                map.put("path",remotePath);
            } else {
                map.put("status","false");
            }

        }catch (Exception e){
            e.printStackTrace();
            map.put("status","false");
        }


        return map;

    }


    /**
     * Chen,Kuai 删除图片
     * @param id
     * @return
     */
    @RequestMapping("/deleteImg.do")
    @ResponseBody
    @ILog
    public Map<String,Boolean> deleteImg(Long id,String fileType,String imgPath){
        Map<String,Boolean> map = new HashMap<String,Boolean>();
        try{
            if(fileType.equals("1")){
                EtBusinessProcess process = new EtBusinessProcess();
                process.setId(id);
                process = super.getFacade().getEtBusinessProcessService().getEtBusinessProcess(process);
                String[] imgs=process.getImgPath().split(";");
                String str="";
                if(imgs.length >1){
                    for(int i = 0; i < imgs.length; i++) {
                        if(imgPath.equals(imgs[i])){

                        }else{
                            str +=imgs[i]+";";
                        }
                    }
                    //process.setImgPath(str.substring(0,str.length()-1));
                }else{
                    process.setImgPath("");
                }
                super.getFacade().getEtBusinessProcessService().modifyEtBusinessProcess(process);


            }else if(fileType.equals("2")){
                EtReport report = new EtReport();
                report.setId(id);
                report=super.getFacade().getEtReportService().getEtReport(report);
                String[] imgs=report.getImgPath().split(";");
                String str="";
                if(imgs.length >1){
                    for(int i = 0; i < imgs.length; i++) {
                        if(imgPath.equals(imgs[i])){

                        }else{
                            str +=imgs[i]+";";
                        }
                    }
                    report.setImgPath(str.substring(0,str.length()-1));
                }else{
                    report.setImgPath("");
                }
                super.getFacade().getEtReportService().modifyEtReport(report);

            }else{
                EtOnlineFile info = new EtOnlineFile();
                info.setId(id);
                info = super.getFacade().getEtOnlineFileService().getEtOnlineFile(info);
                String[] imgs=info.getImgPath().split(";");
                String str="";
                if(imgs.length >1){
                    for(int i = 0; i < imgs.length; i++) {
                        if(imgPath.equals(imgs[i])){

                        }else{
                            str +=imgs[i]+";";
                        }
                    }
                    info.setImgPath(str.substring(0,str.length()-1));
                }else{
                    info.setImgPath("");
                }

                super.getFacade().getEtOnlineFileService().modifyEtOnlineFile(info);
            }
            if (port == 21) {
                FtpUtils.deleteFtpFile(imgPath);
            } else if (port == 22) {
                SFtpUtils.rmFile(imgPath);
            }
            map.put("status",true);
        }catch (Exception e){
            map.put("status",false);
        }
        return map;
    }


}

