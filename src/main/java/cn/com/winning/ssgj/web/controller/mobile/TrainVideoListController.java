package cn.com.winning.ssgj.web.controller.mobile;

import cn.com.winning.ssgj.base.annoation.ILog;
import cn.com.winning.ssgj.base.helper.SSGJHelper;
import cn.com.winning.ssgj.base.util.Base64Utils;
import cn.com.winning.ssgj.base.util.MD5;
import cn.com.winning.ssgj.base.util.StringUtil;
import cn.com.winning.ssgj.domain.EtTrainVideoList;
import cn.com.winning.ssgj.domain.SysTrainVideoRepo;
import cn.com.winning.ssgj.domain.SysUserInfo;
import cn.com.winning.ssgj.web.controller.common.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 服务号视频学习
 *
 * @author ChenKuai
 * @create 2018-02-27 下午 3:53
 **/
@Controller
@CrossOrigin
@RequestMapping("/mobile/trainVideoList")
public class TrainVideoListController extends BaseController {
    //参数：OPENID:公众的唯一ID   HOSPCODE:医院代码  WORKNUM:用户工号
    /**
     * @author: Chen, Kuai
     * @Description: 获取视频分类
     */
    @Autowired
    private SSGJHelper ssgjHelper;

    @RequestMapping(value = "/list.do")
    public String TrainVideoTypeList(Model model, String userId,String serialNo,String openId ) {

        try {
            //获取全部视频
            SysTrainVideoRepo repo = new SysTrainVideoRepo();
            //repo.getMap().put("video_droit_list", info_old.getVideoDroit());
            List<SysTrainVideoRepo> repoTypeList = super.getFacade().getSysTrainVideoRepoService().getSysTrainVideoRepoTypeList(repo);
            model.addAttribute("repoTypeList", repoTypeList);
            model.addAttribute("OPENID", openId);
            model.addAttribute("userId", userId);
            model.addAttribute("serialNo", serialNo);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "/mobile/bridge";
    }


    /**
     * @param video_type
     * @author: Chen, Kuai
     * @Description: 获取全部的视频
     */
    @RequestMapping("/video.do")
    public String TrainVideoList(Model model, String video_type, String OPENID) {
        DecimalFormat df = new DecimalFormat("0.00");
        SysTrainVideoRepo repo = new SysTrainVideoRepo();
        repo.setVideoType(video_type); //根据分类
        repo.getMap().put("OPENID", OPENID);
        List<SysTrainVideoRepo> SysTrainVideoWithRecord = super.getFacade().getSysTrainVideoRepoService()
                .getSysTrainVideoWithRecoedList(repo);
        //计算时长
        Long timeNum = 0L;
        int study_num = 0;
        List<SysTrainVideoRepo> SysTrainVideoStudied = new ArrayList<SysTrainVideoRepo>();
        List<SysTrainVideoRepo> SysTrainVideoUnStudy = new ArrayList<SysTrainVideoRepo>();
        if (SysTrainVideoWithRecord != null) {
            for (int i = 0; i < SysTrainVideoWithRecord.size(); i++) {
                timeNum = timeNum + (long) SysTrainVideoWithRecord.get(i).getVideoTime();
                String mapNum = SysTrainVideoWithRecord.get(i).getMap().get("num") + "";
                if (mapNum != null && !mapNum.equals("null")) {
                    study_num += 1;
                    //已经学习
                    SysTrainVideoStudied.add(SysTrainVideoWithRecord.get(i));
                } else {
                    //未学习
                    SysTrainVideoUnStudy.add(SysTrainVideoWithRecord.get(i));
                }
            }
        }

        model.addAttribute("videoWithRecoed", SysTrainVideoWithRecord);
        model.addAttribute("sysTrainVideoStudied", SysTrainVideoStudied);
        model.addAttribute("sysTrainVideoUnStudy", SysTrainVideoUnStudy);
        model.addAttribute("OPENID", OPENID);
        model.addAttribute("timeNum", timeNum);
        model.addAttribute("size", SysTrainVideoWithRecord.size());
        model.addAttribute("study_num", study_num);
        return "mobile/service/course-study";
    }


    /**
     * @author: Chen, Kuai
     * @Description: 视频播放记录信息
     */
    @RequestMapping(value = "/videoPlay.do")
    @ILog
    public String clickVideoListRecord(Model model, String OPENID, Long id) {
        //点击播放  存储播放信息
        EtTrainVideoList trainVideo = new EtTrainVideoList();
        trainVideo.setOpenId(OPENID);
        trainVideo.setVideoId(id);
        trainVideo = super.getFacade().getEtTrainVideoListService().getEtTrainVideoList(trainVideo);
        if (trainVideo == null) {
            EtTrainVideoList trainVideo_new = new EtTrainVideoList();
            Long id2 = ssgjHelper.createVideoIdService();
            trainVideo_new.setId(ssgjHelper.createVideoIdService());
            trainVideo_new.setCId((long) -2);//微信游客类型
            trainVideo_new.setPmId((long) -2);
            trainVideo_new.setPdId((long) -2);
            trainVideo_new.setUserId("yk0001");
            trainVideo_new.setOpenId(OPENID);           //微信登陆唯一标识
            trainVideo_new.setVideoId(id);
            trainVideo_new.setNum(1);
            //trainVideo.setVideoTime(new Date());
            super.getFacade().getEtTrainVideoListService().createEtTrainVideoList(trainVideo_new);
        } else {
            trainVideo.setNum(trainVideo.getNum() + 1);
            super.getFacade().getEtTrainVideoListService().modifyEtTrainVideoList(trainVideo);
        }

        //EtTrainVideoList trainVideo = new EtTrainVideoList();

        //获取链接地址
        SysTrainVideoRepo repo = new SysTrainVideoRepo();
        repo.setId((long) id);
        repo.setStatus(1);
        repo = super.getFacade().getSysTrainVideoRepoService().getSysTrainVideoRepo(repo);
        model.addAttribute("repo", repo);
        if (trainVideo != null) {
            model.addAttribute("num", trainVideo.getNum());
        } else {
            model.addAttribute("num", 0);

        }

        SysTrainVideoRepo repo_new = new SysTrainVideoRepo();
        repo_new.setVideoType(repo.getVideoType()); //根据分类
        repo_new.getMap().put("not_id", repo.getId());
        repo_new.getMap().put("OPENID", OPENID);
        List<SysTrainVideoRepo> SysTrainVideoWithRecord = super.getFacade().getSysTrainVideoRepoService()
                .getSysTrainVideoWithRecoedList(repo_new);
        model.addAttribute("videoWithRecoed", SysTrainVideoWithRecord);
        model.addAttribute("OPENID", OPENID);

        return "mobile/service/course-detail";
    }


}