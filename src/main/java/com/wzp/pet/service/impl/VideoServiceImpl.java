package com.wzp.pet.service.impl;

import com.wzp.pet.mapper.VideoMapper;
import com.wzp.pet.po.Video;
import com.wzp.pet.po.view.Video_click;
import com.wzp.pet.service.VideoService;
import com.wzp.pet.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("VideoServiceImpl")
public class VideoServiceImpl implements VideoService {
    @Autowired
    @Qualifier("VideoMapper")
    private VideoMapper videoMapper;
    @Override
    public List<Video> getVideoList() {
        return videoMapper.queryVideo();
    }

    @Override
    public List<Video_click> videoClick() {
        return videoMapper.videoClick();
    }

    @Override
    public List<Video> queryVideoPage(Video video, int pageIndex, int pageSize) {
        //查询起始行
        int rowIndex= PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Video> videoList=videoMapper.queryVideoList(video,rowIndex,pageSize);
        return videoList ;
    }

    @Override
    public int querycount(Video video) {
        int num=videoMapper.querynumber(video);
        return num;
    }


    @Override
    public int updateVideoClick(Integer videoId){
        return videoMapper.updateVideoClick(videoId);
    }
    @Override
    public Video queryVideoById(Integer videoId){
        return  videoMapper.queryVideoById(videoId);

    }
    public int delVideo(int videoId){
        return videoMapper.delVideo(videoId);
    }
}
