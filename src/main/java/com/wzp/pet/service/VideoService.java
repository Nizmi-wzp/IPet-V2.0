package com.wzp.pet.service;

import com.wzp.pet.po.Video;
import com.wzp.pet.po.view.Video_click;

import java.util.List;

public interface VideoService {
    List<Video> getVideoList();
    List<Video_click> videoClick();
    //分页
    public List<Video> queryVideoPage(Video video,int pageIndex, int pageSize);
    public int querycount(Video video);

    int updateVideoClick(Integer videoId);

    Video queryVideoById(Integer videoId);
    int delVideo(int videoId);
}
