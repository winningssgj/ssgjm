package cn.com.winning.ssgj.web.controller.admin;

import cn.com.winning.ssgj.base.Constants;
import cn.com.winning.ssgj.base.annoation.ILog;
import cn.com.winning.ssgj.base.helper.SSGJHelper;
import cn.com.winning.ssgj.base.util.CommonFtpUtils;
import cn.com.winning.ssgj.base.util.ConnectionUtil;
import cn.com.winning.ssgj.base.util.DateUtil;
import cn.com.winning.ssgj.domain.*;
import cn.com.winning.ssgj.domain.support.Row;
import cn.com.winning.ssgj.web.controller.common.BaseController;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenshijie
 * @title
 * @email chensj@winning.com.cm
 * @package cn.com.winning.ssgj.web.controller
 * @date 2018-03-09 16:39
 */
@Controller
@RequestMapping(value = "/admin/script")
public class SysDataScriptController extends BaseController {

    @Autowired
    private SSGJHelper ssgjHelper;

    @RequestMapping(value = "/pageInfo.do")
    public String getPageInfo(HttpServletRequest request, Model model) {
        return "online/scriptPage";
    }


    @RequestMapping(value = "/list.do")
    @ResponseBody
    public Map<String, Object> queryInfoByPage(SysDataCheckScript script, Row row) {
        script.setRow(row);
        script.setStatus(Constants.STATUS_USE);
        List<SysDataCheckScript> dataCheckScriptList = super.getFacade().getSysDataCheckScriptService().getSysDataCheckScriptPageListFuzzy(script);
        int total = super.getFacade().getSysDataCheckScriptService().getSysDataCheckScriptCountFuzzy(script);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", total);
        result.put("status", Constants.SUCCESS);
        result.put("rows", dataCheckScriptList);
        return result;
    }

    @RequestMapping(value = "/add.do")
    @ResponseBody
    @Transactional
    @ILog
    public Map<String, Object> addDataCheckScript(SysDataCheckScript script) {
        SysUserInfo userInfo = (SysUserInfo) SecurityUtils.getSubject().getPrincipal();
        script.setId(ssgjHelper.createDataCheckScriptIdService());
        script.setLastUpdateTime(DateUtil.getCurrentTimestamp());
        script.setLastUpdator(userInfo.getId());
        super.getFacade().getSysDataCheckScriptService().createSysDataCheckScript(script);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", Constants.SUCCESS);
        result.put("data", script.getId());
        return result;
    }

    @RequestMapping(value = "/update.do")
    @ResponseBody
    @Transactional
    @ILog
    public Map<String, Object> updateDataCheckScript(SysDataCheckScript script) throws ClassNotFoundException, SQLException {
        SysUserInfo userInfo = (SysUserInfo) SecurityUtils.getSubject().getPrincipal();
        script.setLastUpdateTime(DateUtil.getCurrentTimestamp());
        script.setLastUpdator(userInfo.getId());
//        String content = script.getsDesc();
//        System.out.println(content);
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        Connection connection
//                = DriverManager.getConnection("jdbc:sqlserver://172.16.0.200:1433;DatabaseName=THIS4","sa","zyc@8468");
//        PreparedStatement ps = connection.prepareStatement(content);
//        ps.execute();
//        ps = connection.prepareStatement("exec usp_yy_jcsj_his '0'");
//        ResultSet rs = ps.executeQuery();
//        while (rs.next()){
//            System.out.println(rs.getString(1)+"--"+rs.getString(2)+"--"+rs.getString(3));
//        }
        super.getFacade().getSysDataCheckScriptService().modifySysDataCheckScript(script);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", Constants.SUCCESS);
        result.put("data", script.getId());
        return result;
    }


    @RequestMapping(value = "/getById.do")
    @ResponseBody
    public Map<String, Object> getSysDataCheckScriptById(SysDataCheckScript script) {
        script = super.getFacade().getSysDataCheckScriptService().getSysDataCheckScript(script);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", Constants.SUCCESS);
        result.put("data", script);
        return result;
    }

    /**
     * 文件删除
     * @param script
     * @return
     */
    @RequestMapping(value = "/deleteFile.do")
    @ResponseBody
    @Transactional
    @ILog
    public Map<String, Object> deleteFile(SysDataCheckScript script) {
        script = super.getFacade().getSysDataCheckScriptService().getSysDataCheckScript(script);
        CommonFtpUtils.removeUploadFile(script.getRemotePath());
        script.setRemotePath("");
        super.getFacade().getSysDataCheckScriptService().modifySysDataCheckScript(script);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", Constants.SUCCESS);
        return result;

    }

    @RequestMapping(value = "/modifyById.do")
    @ResponseBody
    @Transactional
    @ILog
    public Map<String, Object> modifySysDataCheckScriptById(SysDataCheckScript script) {
        script = super.getFacade().getSysDataCheckScriptService().getSysDataCheckScript(script);
        if (script.getStatus() == Constants.STATUS_UNUSE) {
            script.setStatus(Constants.STATUS_USE);
        } else {
            script.setStatus(Constants.STATUS_UNUSE);
        }
        script.setLastUpdateTime(DateUtil.getCurrentTimestamp());
        script.setLastUpdator(((SysUserInfo) SecurityUtils.getSubject().getPrincipal()).getId());
        super.getFacade().getSysDataCheckScriptService().modifySysDataCheckScript(script);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", Constants.SUCCESS);
        return result;
    }

    @RequestMapping(value = "/existName.do")
    @ResponseBody
    public Map<String, Object> existDataCheckScriptName(SysDataCheckScript script) {
        boolean exists = super.getFacade().getSysDataCheckScriptService().existDataCheckScriptName(script);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("valid", exists);
        result.put("status", Constants.SUCCESS);
        return result;
    }

    @RequestMapping(value = "/existScript.do")
    @ResponseBody
    public Map<String, Object> existDataCheckScript(SysDataCheckScript script) {
        boolean exists = super.getFacade().getSysDataCheckScriptService().existDataCheckScript(script);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("valid", exists);
        result.put("status", Constants.SUCCESS);
        return result;
    }


    @RequestMapping(value = "/queryAppName.do")
    @ResponseBody
    public Map<String, Object> queryAppName(String name, int matchCount) {
        PmisProductLineInfo lineInfo = new PmisProductLineInfo();
        lineInfo.setName(name);
        lineInfo.setZt(Constants.PMIS_STATUS_USE);
        Row row = new Row(0, matchCount);
        lineInfo.setRow(row);
        List<PmisProductLineInfo> lineInfos = super.getFacade().getPmisProductLineInfoService().getPmisProductLineInfoPaginatedListByName(lineInfo);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", matchCount);
        result.put("status", Constants.SUCCESS);
        result.put("data", lineInfos);
        return result;
    }

}
