package com.wzp.pet.controller.backstageadmin;

import com.wzp.pet.po.Video;
import com.wzp.pet.po.view.Video_click;
import com.wzp.pet.service.VideoService;
import com.wzp.pet.util.HttpServletRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/videoadmin")
public class VideoController {
    Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    @Qualifier("VideoServiceImpl")
    private VideoService videoService;

    @RequestMapping(value = "/listvideo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listVedio() {
        logger.info("===start===");
        long startTime = System.currentTimeMillis();

        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<Video> videoList = new ArrayList<Video>();
        try {
            videoList = videoService.getVideoList();
            modelMap.put("videoList", videoList);
            modelMap.put("success", true);
            modelMap.put("total",videoList.size());
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        logger.error("test error!");
        long endTime = System.currentTimeMillis();
        logger.debug("costTime:[{}ms]", endTime - startTime);
        logger.info("===end===");

        return modelMap;
    }

    @RequestMapping(value = "/videoclick", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> vedioClick() {
        logger.info("===start===");
        long startTime = System.currentTimeMillis();

        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<Video_click> videoClickList = new ArrayList<Video_click>();
        try {
            videoClickList = videoService.videoClick();
            modelMap.put("videoClickList", videoClickList);
            modelMap.put("success", true);
            modelMap.put("total", videoClickList.size());
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        logger.error("test error!");
        long endTime = System.currentTimeMillis();
        logger.debug("costTime:[{}ms]", endTime - startTime);
        logger.info("===end===");

        return modelMap;
    }



    @RequestMapping(value = "/watchVideo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> watchVedio(HttpServletRequest request) {
        logger.info("===start===");
        long startTime = System.currentTimeMillis();

        Map<String, Object> modelMap = new HashMap<String, Object>();
        int videoId = HttpServletRequestUtil.getInt(request,"videoID");
     ;
        try {
            int flag= videoService.updateVideoClick(videoId);
            Video v= videoService.queryVideoById(videoId);
            modelMap.put("success", true);
            modelMap.put("video", v);
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        logger.error("test error!");
        long endTime = System.currentTimeMillis();
        logger.debug("costTime:[{}ms]", endTime - startTime);
        logger.info("===end===");

        return modelMap;

    }

    @RequestMapping(value = "/listvideopage", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listVideoPage(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String queryKey= HttpServletRequestUtil.getString(request,"queryKey");
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        //前端查询关键字为宠物类型
         Video video=new Video();
          video.setVideoType(queryKey);
        List<Video> videoList=videoService.queryVideoPage(video,pageIndex,pageSize);

        modelMap.put("success",true);
        modelMap.put("videoList",videoList);
        return modelMap;
    }

    @RequestMapping(value = "/videocount", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> userCount(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String queryKey= HttpServletRequestUtil.getString(request,"queryKey");
        Video video=new Video();
        video.setVideoType(queryKey);
        int num=videoService.querycount(video);
        modelMap.put("count",num);
        modelMap.put("success",true);
        return modelMap;
    }
    @RequestMapping(value = "/delVideo", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> delVideo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int videoId= HttpServletRequestUtil.getInt(request,"videoId");
        int num=videoService.delVideo(videoId);
        if(num>0){
            modelMap.put("success",true);
        }else{
            modelMap.put("success",false);
        }
        return modelMap;
    }

}
