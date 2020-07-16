package com.wzp.pet.controller.backstageadmin;

import com.wzp.pet.po.Baike;
import com.wzp.pet.service.BaikeService;
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
@RequestMapping("/baikeadmin")
public class BaikeController {
    Logger logger = LoggerFactory.getLogger(BaikeController.class);
    @Autowired
    private BaikeService baikeService;

    @RequestMapping(value = "/baikeCount", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listBaikeCount(HttpServletRequest request) {

        Map<String, Object> modelMap = new HashMap<String, Object>();
        String key= HttpServletRequestUtil.getString(request,"queryKey");

        int count;
        try{

            count=baikeService.queryBaikeNum(key);

            modelMap.put("success", true);
            modelMap.put("total", count);

        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;

    }


    @RequestMapping(value = "/baikebypage", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listBaikebypage(HttpServletRequest request) {
        logger.info("===start===");
        long startTime = System.currentTimeMillis();

        String key= HttpServletRequestUtil.getString(request,"queryKey");
        int pageIndex =HttpServletRequestUtil.getInt(request,"pageIndex");

        int rowIndex=(pageIndex<=0)?0:(pageIndex-1)*4;

        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<Baike> baikeList = new ArrayList<Baike>();
        try {
            baikeList = baikeService.getBaikeList(key,rowIndex,4);
            int count=baikeService.queryBaikeNum(key);
            modelMap.put("baikeList", baikeList);
            modelMap.put("success",true);
            modelMap.put("total", count);
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

}
