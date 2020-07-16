package com.wzp.pet.mapper;

import com.wzp.pet.po.News;
import com.wzp.pet.util.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class NewsMapperTest extends BaseTest {
    @Autowired
    @Qualifier("NewsMapper")
    private NewsMapper newsMapper;
    @Test
    public void queryNews() {
        List<News> newsList = newsMapper.queryNews();
        System.out.println(newsList.size());
        System.out.println(newsList.get(0).getNewsTitle());

    }

    @Test
    public void queryNewsListPage(){
        News news=new News();
        news.setNewsTitle("狗狗");
        List<News> newsList=newsMapper.queryNewsListPage(news,0,3);
        System.out.println(newsList.size());
    }
    @Test
public void getRecordCount(){
    News news=new News();
    news.setNewsTitle("狗狗");
    int num=newsMapper.getRecordCount(news);
    System.out.println(num);
}
    @Test
    public void queryNewsList() {
    }

    @Test
    public void insertNews() {
        int effect=0;
        News news=new News();
        news.setNewsTitle("狗狗");
        news.setNewsImage("6.png");
        news.setNewsContents("666");
        Date date = new Date();
        news.setNewsTime(""+date);
        effect=newsMapper.insertNews(news);
        assertEquals(1,effect);
    }

    @Test
    public void batchInsertNews() {
    }

    @Test
    public void deleteNews() {
    }

    @Test
    public void batchDeleteNews() {
    }

    @Test
    public void updateNews() {
    }
}