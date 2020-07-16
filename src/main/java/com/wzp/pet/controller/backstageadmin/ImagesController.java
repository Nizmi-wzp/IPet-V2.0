package com.wzp.pet.controller.backstageadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wzp.pet.enums.ImagesStateEnum;
import com.wzp.pet.exceptions.ImagesOperationException;
import com.wzp.pet.po.Images;
import com.wzp.pet.service.ImagesService;
import com.wzp.pet.util.HttpServletRequestUtil;
import com.wzp.pet.vo.ImageHolder;
import com.wzp.pet.vo.ImagesExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/imagesadmin")
public class ImagesController {
    @Autowired
    private ImagesService imagesService;

    Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    @Qualifier("AdminServiceImpl")
    private com.wzp.pet.service.AdminService adminService;
    // 支持上传商品详情图的最大数量
    private static final int IMAGEMAXCOUNT = 6;

//显示所有图片
    @RequestMapping(value = "/listimages", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listImages() {
        logger.info("===start===");
        long startTime = System.currentTimeMillis();

        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<Images> imagesList = new ArrayList<Images>();
        try {
            imagesList = imagesService.getImagesList();
            modelMap.put("imagesList", imagesList);
            modelMap.put("success", true);
            modelMap.put("total", imagesList.size());
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

    //分页显示图片信息
    /**
     * 获取该图片列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getimageslistbypage", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getImagesListBypage(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Images images=new Images();
        // 获取前台传过来的页码
        int pageIndex = HttpServletRequestUtil.getInt(request, "page");
        System.out.println(pageIndex);
        // 获取前台传过来的每页要求返回的商品数上限
        int pageSize = HttpServletRequestUtil.getInt(request, "limit");
        System.out.println(pageSize);
        // 空值判断
        if ((pageIndex > -1) && (pageSize > -1) ) {
            // 获取传入的需要检索的条件，包括是否需要从某个商品类别以及模糊查找商品名去筛选某个店铺下的商品列表
            // 筛选的条件可以进行排列组合
            String queryKey= HttpServletRequestUtil.getString(request, "queryKey");
            logger.info("输出获取传入的值："+queryKey);
            Images imagesCondition=new Images();
            if(queryKey!=null && !"".equalsIgnoreCase(queryKey))
            imagesCondition.setPetType(queryKey);
            // 传入查询条件以及分页信息进行查询，返回相应商品列表以及总数
            ImagesExecution ie = imagesService.getImagesListByCondition(imagesCondition, pageIndex, pageSize);
            int recordCount=imagesService.getRecordCount(imagesCondition);

            modelMap.put("count",recordCount);
            modelMap.put("imagesList",ie.getImagesList());
            modelMap.put("success", true);

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }
    //获取记录条数
    @RequestMapping(value = "/getrecordCount",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object>  getPageCount(HttpServletRequest request) {
        HashMap<String,Object> map = new HashMap<String,Object>();
        String queryKey= HttpServletRequestUtil.getString(request,"queryKey");
        Images images=new Images();
        images.setPetType(queryKey);
        int recordCount=imagesService.getRecordCount(images);
        map.put("success",true);
        map.put("recordCount",recordCount);
        return map;
    }
//封装多条件查询
    private Images compactProductCondition(Integer imagesId, String imagestype) {
        Images imagesCondition = new Images();
        // 若有指定类别的要求则添加进去
        if (imagesId != -1L) {

            imagesCondition.setImagesId(imagesId);
        }
        // 若有商品名模糊查询的要求则添加进去
        if (imagestype != null) {
            imagesCondition.setPetType(imagestype);
        }
        return imagesCondition;
    }
    //图片上传
    @RequestMapping(value = "/addimages", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addImages(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 接收前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
        ObjectMapper mapper = new ObjectMapper();
        ImageHolder thumbnail = null;
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        try {
            // 若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage(request, thumbnail);
                logger.info("返回缩略图："+thumbnail.getImageName());
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        if (thumbnail != null  && thumbnail.getImage()!=null ) {
            try {
                // 从session中获取当前店铺的Id并赋值给product，减少对前端数据的依赖
                 logger.info("传入缩略图："+thumbnail.getImageName());
                Images images=new Images();
             //   images.setImagesId(33);
                images.setImages(thumbnail.getImageName());
                // 执行添加操作
                ImagesExecution ie=imagesService.addImages(images,thumbnail);
                logger.info("信息"+ie.getStateInfo());

                if (ie.getState() == ImagesStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", ie.getStateInfo());
                }
            } catch (ImagesOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入图片信息");
        }
        return modelMap;
    }

//从前端取出文件流
    private ImageHolder handleImage(HttpServletRequest request, ImageHolder thumbnail)
            throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 取出缩略图并构建ImageHolder对象
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("img");
        logger.info("图片信息："+thumbnailFile.getName());
        if (thumbnailFile != null) {
            thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
            logger.info(thumbnail.getImageName());
        }
        return thumbnail;

    }
    //删除图片
    @RequestMapping(value = "/delimages", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> delImages(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Integer imagesId=HttpServletRequestUtil.getInt(request,"imagesId");
        int num=imagesService.delimage(imagesId);
        if(num>0)
            modelMap.put("success",true);
        else{
            modelMap.put("success",false);
        }
        return modelMap;
    }
}
