package com.wzp.pet.service;

import com.wzp.pet.exceptions.ImagesOperationException;
import com.wzp.pet.po.Images;
import com.wzp.pet.vo.ImageHolder;
import com.wzp.pet.vo.ImagesExecution;


import java.util.List;

public interface ImagesService {
    List<Images> getImagesList();

    //分页总数
    public int getRecordCount(Images images);
    //记录总数
    ImagesExecution getImagesListByCondition(Images imagesCondition, int pageIndex, int pageSize);


    ImagesExecution addImages(Images images, ImageHolder thumbnail)
            throws ImagesOperationException;
    //根据爬虫批量插入数据库
    ImagesExecution batchaddImages(List<Images> imagesList)
          throws ImagesOperationException;

    int delimage(Integer imageId);
}
