package com.wzp.pet.mapper;

import com.wzp.pet.po.News;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("NewsMapper")
public interface NewsMapper {
    /*1》输出新闻列表*/
    List<News> queryNews();

    //分页总数
    public int getRecordCount(@Param("newsCondition") News newsCondition);

    /*2》根据条件查询新闻信息分页*/
    List<News> queryNewsListPage(@Param("newsCondition") News newsCondition,@Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);
    /*3》插入单条记录*/
    int insertNews(News news);
    /* 4》批量插入*/
    int batchInsertNews(List<News> NewsList);
    /*5》根据新闻Id删除单条记录*/
    int deleteNews(long NewsId);
    /*6》根据Id列表批量删除*/
    int batchDeleteNews(List<Long> NewsIdList);
    /*7》根据新闻ID号修改*/
    int updateNews(News news);
}
