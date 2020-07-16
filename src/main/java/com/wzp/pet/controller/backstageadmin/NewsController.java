package com.wzp.pet.controller.backstageadmin;

import com.wzp.pet.po.News;
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
@RequestMapping("/newsadmin")
public class NewsController {
    Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    @Qualifier("NewsServiceImpl")
    private com.wzp.pet.service.NewsService newsService;

    @RequestMapping(value = "/listnews", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listAdmin() {
        logger.info("===start===");
        long startTime = System.currentTimeMillis();

        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<News> newsList = new ArrayList<News>();
        try {
            newsList = newsService.getNewsList();
            modelMap.put("newsList", newsList);
            modelMap.put("success", true);
            modelMap.put("total", newsList.size());
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

    @RequestMapping(value = "/getNewsListByPage", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getImagesListBypage(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        News news=new News();
        // 获取前台传过来的页码
        int pageIndex = HttpServletRequestUtil.getInt(request, "page");
        int pageSize = HttpServletRequestUtil.getInt(request, "limit");
        // 空值判断
        if ((pageIndex > -1) && (pageSize > -1) ) {
            String queryKey= HttpServletRequestUtil.getString(request, "queryKey");
            logger.info("输出获取传入的值："+queryKey);
            if(queryKey!=null && !"".equalsIgnoreCase(queryKey))
                news.setNewsTitle(queryKey);
            // 传入查询条件以及分页信息进行查询，返回相应商品列表以及总数
            List<News> newsList = newsService.getNewsListByPage(news,pageIndex,pageSize);
            int recordCount=newsService.getRecordCount(news);

            modelMap.put("count",recordCount);
            modelMap.put("newsList",newsList);
            modelMap.put("success", true);

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex");
        }
        return modelMap;
    }

}
