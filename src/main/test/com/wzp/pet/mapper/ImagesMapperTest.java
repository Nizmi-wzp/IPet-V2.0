package com.wzp.pet.mapper;

import com.wzp.pet.po.Admin;
import com.wzp.pet.po.Images;
import com.wzp.pet.util.BaseTest;
import com.wzp.pet.util.ReadImagesFiles;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ImagesMapperTest extends BaseTest {
    @Autowired
    @Qualifier("ImagesMapper")
    private  ImagesMapper imagesMapper;
    @Test
    public void listImages(){
        List<Images> imagesList=imagesMapper.listImages();
        System.out.println(imagesList.size());
        System.out.println(imagesList.get(0).getImages());
    }

    @Test
    public void queryImages() {
        Images imagesCondition=new Images();
        imagesCondition.setPetType("喵");
        //imagesCondition.setImagesId(9);
        List<Images> imagesList=imagesMapper.queryImages(imagesCondition,1,9);
        System.out.println(imagesList.size());
        System.out.println(imagesList.get(0).getImages());
    }
    @Test
    public void queryImagecount(){
        Images images=new Images();
        images.setPetType("汪");
        int num=imagesMapper.queryImagecount(images);
        assertEquals(8,num);
    }
   @Test
    public void insertImages(){
        int flag=0;
       Images images=new Images();
       images.setImages("images/666.jpg");
       flag=imagesMapper.insertImages(images);
       assertEquals(1,flag);

    }
    @Test
    public void batchInsertpetImg(){
/*        Images images=new Images();
        images.setImages("11.jpg");
        images.setPetType("喵星人");
        Images images1=new Images();
        images1.setImages("22.jpg");
        images1.setPetType("汪星人");
        List<Images> imagesList=new ArrayList<Images>();
        imagesList.add(images);
        imagesList.add(images1);
        int effectnum=imagesMapper.batchInsertpetImg(imagesList);
        assertEquals(2,effectnum);*/
        List<Images> imagesList=new ArrayList<Images>();

        List<String> addrList=new ArrayList<String>();
        ReadImagesFiles readImagesFiles=new ReadImagesFiles();
        addrList=readImagesFiles.getimgAddr();
        for(int i=0;i<addrList.size();i++)
        {
            Images images=new Images();
            images.setImages(addrList.get(i));
            imagesList.add(images);
        }
        int flag=0;
        System.out.println(imagesList.get(1).getImages());
        System.out.println(imagesList.get(2).getImages());
        flag=imagesMapper.batchInsertpetImg(imagesList);
        if(flag>0){System.out.println("插入成功");}

    }
    @Test
    public void updateImages(){
        Images images=new Images();
        images.setImagesId(22);
        images.setImages("pig.jpg");
        images.setPetType("猪");
        int effectedNum = imagesMapper.updateImages(images);
        assertEquals(1, effectedNum);

    }
    @Test
    public void deleteImages(){
        Integer imagesId = 20;
        int effectedNum =imagesMapper.deleteImages(imagesId);
        assertEquals(1, effectedNum);
    }
    @Test
    public void queryImgById(){
        Integer imagesId=5;
        Images images=imagesMapper.queryImgById(imagesId);
        System.out.println(images.getImages());
    }
}