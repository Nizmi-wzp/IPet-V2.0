package com.wzp.pet.service;

import com.wzp.pet.po.News;

import java.util.List;

public interface NewsService {
    List<News> getNewsList();
    public List<News> getNewsListByPage(News news, int pageIndex, int pageSize);
    public int getRecordCount(News news);
}
