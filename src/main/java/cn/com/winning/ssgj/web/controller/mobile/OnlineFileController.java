package cn.com.winning.ssgj.web.controller.mobile;

import cn.com.winning.ssgj.base.annoation.ILog;
import cn.com.winning.ssgj.base.helper.SSGJHelper;
import cn.com.winning.ssgj.base.util.*;
import cn.com.winning.ssgj.domain.EtOnlineFile;
import cn.com.winning.ssgj.domain.SysDataInfo;
import cn.com.winning.ssgj.domain.SysDictInfo;
import cn.com.winning.ssgj.domain.SysUserInfo;
import cn.com.winning.ssgj.web.controller.common.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
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
    @ILog
    public String floorQuestionList(Model model, String parameter) {

        EtOnlineFile onlineFile = new  EtOnlineFile();
        String parameter2 = "eyJXT1JLTlVNIjoiMTQyMCJ9";
        String hospcode ="11980";

        try{
            byte[] byteArray = Base64Utils.decryptBASE64(parameter2);
            String userJsonStr = "[" + new String(Base64Utils.decryptBASE64(parameter2), "UTF-8") + "]";
            ArrayList<JSONObject> userList = JSON.parseObject(userJsonStr, ArrayList.class);
            String worknum="5823"; //(String) userList.get(0).get("WORKNUM");
            //获取用户的信息
            SysUserInfo info = new SysUserInfo();
            info.setUserid(worknum);
            info.setStatus(1);
            info.setUserType("1");  //0医院1公司员工
            info = super.getFacade().getSysUserInfoService().getSysUserInfo(info);
            if(info !=null){
                //根据客户编号 找出对应的全部
                onlineFile.setSerialNo(hospcode);
                onlineFile.setFileType("1");
                List<EtOnlineFile> onlineFileList_one = super.getFacade().getEtOnlineFileService().getEtOnlineFileList(onlineFile);
                onlineFile.setFileType("2");
                List<EtOnlineFile> onlineFileList_two = super.getFacade().getEtOnlineFileService().getEtOnlineFileList(onlineFile);
                onlineFile.setFileType("3");
                List<EtOnlineFile> onlineFileList_three = super.getFacade().getEtOnlineFileService().getEtOnlineFileList(onlineFile);
                onlineFile.setFileType("4");
                List<EtOnlineFile> onlineFileList_four = super.getFacade().getEtOnlineFileService().getEtOnlineFileList(onlineFile);
                model.addAttribute("onlineFileList_one",onlineFileList_one);
                model.addAttribute("onlineFileList_two",onlineFileList_two);
                model.addAttribute("onlineFileList_three",onlineFileList_three);
                model.addAttribute("onlineFileList_four",onlineFileList_four);
            }else{

            }
            model.addAttribute("userId",worknum);
            model.addAttribute("serialNo",hospcode);

        }catch (Exception e){
            e.printStackTrace();
        }


        return "/mobile/enterprise/data-upload";
    }

    @RequestMapping("/details.do")
    @ILog
    public String fileDetails(Model model, String fileType,String serialNo,String userId) {
        EtOnlineFile info = new EtOnlineFile();
        info.setFileType(fileType);
        info.setSerialNo(serialNo);
        List<EtOnlineFile> onlineFiles =super.getFacade().getEtOnlineFileService().getEtOnlineFileList(info);
        //获取文件的类型
        SysDictInfo info1 = new SysDictInfo();
        info1.setDictCode("FileType");
        List<SysDictInfo> dictInfos =super.getFacade().getSysDictInfoService().getSysDictInfoList(info1);
        model.addAttribute("dictInfos",dictInfos);
        model.addAttribute("onlineFiles",onlineFiles);
        model.addAttribute("serialNo",serialNo);
        model.addAttribute("userId",userId);
        model.addAttribute("fileType",fileType);
        return "/mobile/enterprise/data-upload-report";
    }


    @RequestMapping("/uploadImg.do")
    @ILog
    public String uploadImg (HttpServletRequest request,EtOnlineFile info,String serialNo,String userId, String fileType, MultipartFile uploadFile){
        Map<String,Object> map = new HashMap<String,Object>();
        //如果文件不为空，写入上传路径
        try{

            if(!uploadFile.isEmpty()) {
                //上传文件路径
                String path = request.getServletContext().getRealPath("/onlineFile/");
                System.out.println(path);

                //上传文件名
                String filename = uploadFile.getOriginalFilename();
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
                String remotePath = "/onlineFile/"+ filename;
                String remoteDir ="/onlineFile/" ;
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
                    SysUserInfo userInfo = new SysUserInfo();
                    userInfo.setUserid(userId+"");//员工编码
                    userInfo.setStatus(1);
                    userInfo.setUserType("0");//0医院
                    List<SysUserInfo> userInfoList = super.getFacade().getSysUserInfoService().getSysUserInfoList(userInfo);
                    //上传资料 1.生成一条记录
                    //2.修改原来图片路径
                    info.setId(ssgjHelper.createOnlineFileIdService());
                    info.setCId((long)-2);    //11980游客
                    info.setPmId((long)-2);
                    info.setSerialNo(serialNo);//客户编码
                    info.setImgPath(remotePath);//图片路径
                    info.setCreator((long)100193);
                    info.setCreateTime(new Timestamp(new Date().getTime()));
                    info.setFileType(fileType);

                    super.getFacade().getEtOnlineFileService().createEtOnlineFile(info);


                }else if(!StringUtil.isEmptyOrNull(msg)){

                }
            } else {

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return "/mobile/enterprise/data-upload-report";
    }



}
