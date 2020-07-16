package com.wzp.pet.mapper;

import com.wzp.pet.po.Video;
import com.wzp.pet.po.view.Video_click;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("VideoMapper")
public interface VideoMapper {
    /*1》输出视频列表*/
    List<Video> queryVideo();

    int querynumber(@Param("videoCondition") Video videoCondition);

    /*2》根据条件查询视频信息*/
    List<Video> queryVideoList(@Param("videoCondition") Video videoCondition,@Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);
    /*3》插入单条记录*/
    int insertvideo(Video video);
    /* 4》批量插入*/
    int batchInsertVideo(List<Video> videoList);
    /*5》根据视频Id删除单条记录*/
    int delVideo(int videoId);
    /*6》根据Id列表批量删除*/
    int batchDeleteVideo(List<Long> videoIdList);
    /*7》根据视频ID号修改*/
    int updateVideo(Video video);
    //8》视频点击量
    List<Video_click> videoClick();
    int updateVideoClick(@Param("videoId") Integer videoId);
    Video queryVideoById(@Param("videoId") Integer videoId);
}
