package com.wzp.pet.service;

import com.wzp.pet.enums.ImagesStateEnum;
import com.wzp.pet.exceptions.ImagesOperationException;
import com.wzp.pet.po.Images;
import com.wzp.pet.util.BaseTest;
import com.wzp.pet.util.ReadImagesFiles;
import com.wzp.pet.vo.ImageHolder;
import com.wzp.pet.vo.ImagesExecution;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

public class ImagesServiceTest extends BaseTest {
    @Autowired
    private ImagesService imagesService;
    @Test
    public void getImagesList() {
        List<Images> imagesList=imagesService.getImagesList();
        System.out.println("图片数量"+imagesList.size());
        System.out.println(imagesList.get(0).getImages());
    }

    @Test
    public void getImagesListByCondition() {
        Images imagesCondition=new Images();
        imagesCondition.setPetType("喵");
        //imagesCondition.setImagesId(9);
        ImagesExecution imagesEXecution=imagesService.getImagesListByCondition(imagesCondition,0,9);
        //期待返回状态码与枚举状态码一致为1
        System.out.println(imagesEXecution.getImagesList().size());
        System.out.println(imagesEXecution.getImagesList().get(0).getImages());
        System.out.println(imagesEXecution.getState());
        assertEquals(ImagesStateEnum.SUCCESS.getState(), imagesEXecution.getState());

    }
    @Test
    public void getRecordCount(){
        Images images=new Images();
        images.setPetType("汪");
        int num=imagesService.getRecordCount(images);
        System.out.println(num);
        //assertEquals(8,num);
    }
    @Test
    public void batchaddImages() throws ImagesOperationException, FileNotFoundException {
        ReadImagesFiles readImagesFiles=new ReadImagesFiles();
        List<Images> imagesList=new ArrayList<Images>();
        imagesList=readImagesFiles.getimgAddr();
        ImagesExecution imagesExecution=imagesService.batchaddImages(imagesList);
        assertEquals(ImagesStateEnum.SUCCESS.getState(),imagesExecution.getState());
    }
@Test
    public void addImages() throws ImagesOperationException,FileNotFoundException {
        Images images=new Images();
        images.setImages("UU.jpg");
        // 创建缩略图文件流
        File thumbnailFile = new File("D:/img/1.jpg");
        System.out.println(thumbnailFile.getName());
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);
        ImagesExecution ie =imagesService.addImages(images,thumbnail);
       System.out.println(ie.getStateInfo());
        assertEquals(ImagesStateEnum.SUCCESS.getState(), ie.getState());
    }
}