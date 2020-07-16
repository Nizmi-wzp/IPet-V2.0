package com.wzp.pet.mapper;

import com.wzp.pet.po.Video;
import com.wzp.pet.po.view.Video_click;
import com.wzp.pet.util.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

import static org.junit.Assert.*;

public class VideoMapperTest extends BaseTest {
    @Autowired
    @Qualifier("VideoMapper")
    private VideoMapper videoMapper;
    @Test
    public void queryVedio(){
        List<Video> vedioList=videoMapper.queryVideo();
        System.out.println(vedioList.size());
        System.out.println(vedioList.get(0).getVideo());
    }
    @Test
    public void vedioClick(){
        List<Video_click> video_clickList =videoMapper.videoClick();
        assertEquals(6, video_clickList.size());
        System.out.println(video_clickList.get(0).getClicks());
    }
    @Test
    public void queryVideoById(){
        Integer videoId=1;
        Video video=videoMapper.queryVideoById(videoId);
        System.out.println(video.getVideo());
    }
    @Test
    public void queryVideoList(){
        Video video=new Video();
        video.setVideoType("狗");
        List<Video> videoList=videoMapper.queryVideoList(video,0,5);
        System.out.println(videoList.size());
        System.out.println(videoList.get(0).getVideo());
    }
    @Test
    public void querynumber(){
        Video video=new Video();
        video.setVideoType("狗");
        int num=videoMapper.querynumber(video);
        System.out.println(num);

    }
    @Test
    public void delVideo(){
        int num=videoMapper.delVideo(8);
        System.out.println(num);

    }
}