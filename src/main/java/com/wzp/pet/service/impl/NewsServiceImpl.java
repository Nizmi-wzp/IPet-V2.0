package com.wzp.pet.service.impl;

import com.wzp.pet.mapper.NewsMapper;
import com.wzp.pet.po.News;
import com.wzp.pet.service.NewsService;
import com.wzp.pet.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("NewsServiceImpl")
public class NewsServiceImpl implements NewsService {
    @Autowired
    @Qualifier("NewsMapper")
    private NewsMapper newsMapper;

    @Override
    public List<News> getNewsList() {
        return newsMapper.queryNews();
    }

    @Override
    public List<News> getNewsListByPage(News news, int pageIndex, int pageSize) {
        //查询起始行
        int rowIndex= PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<News > newsList=newsMapper.queryNewsListPage(news,rowIndex,pageSize);

        return newsList;
    }
    @Override
    public int getRecordCount(News news){
        int num=newsMapper.getRecordCount(news);
        return num;
    }
}
