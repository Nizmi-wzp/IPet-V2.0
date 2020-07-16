package com.wzp.pet.service.impl;

import com.wzp.pet.enums.ImagesStateEnum;
import com.wzp.pet.exceptions.ImagesOperationException;
import com.wzp.pet.mapper.ImagesMapper;
import com.wzp.pet.po.Images;
import com.wzp.pet.service.ImagesService;
import com.wzp.pet.util.ImageUtil;
import com.wzp.pet.util.PageCalculator;
import com.wzp.pet.util.PathUtil;
import com.wzp.pet.vo.ImageHolder;
import com.wzp.pet.vo.ImagesExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.wzp.pet.util.ReadImagesFiles;

@Service("ImagesServiceImpl")
public class ImagesServiceImpl implements ImagesService {
@Autowired
@Qualifier("ImagesMapper")
private ImagesMapper imagesMapper;
    @Override
    public List<Images> getImagesList() {
        return imagesMapper.listImages();
    }




    @Override
    public ImagesExecution getImagesListByCondition(Images imagesCondition, int pageIndex, int pageSize) {
        //查询起始行
        int rowIndex= PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Images> imagesList=imagesMapper.queryImages(imagesCondition,rowIndex,pageSize);

        //int count=imagesMapper.queryImagecount(imagesCondition);
        ImagesExecution imagesEXecution=new ImagesExecution(ImagesStateEnum.SUCCESS,imagesList);
      //  imagesEXecution.setCount(count);
        return imagesEXecution;
    }
    @Override
    public int getRecordCount(Images images){
        int num=imagesMapper.queryImagecount(images);
        return num;
    }

    @Override
    public ImagesExecution addImages(Images images, ImageHolder thumbnail) throws ImagesOperationException {
        if (images != null ){
            //添加缩略图,上传到指定目录
            if (thumbnail != null) {
                addThumbnail(images, thumbnail);
            }
            try {
                int effectedNum = imagesMapper.insertImages(images);
                if (effectedNum <= 0) {
                    throw new ImagesOperationException("添加图片失败");
                }
            } catch (Exception e) {
                throw new ImagesOperationException("插入失败:" + e.toString());
            }
            return new ImagesExecution(ImagesStateEnum.SUCCESS, images);

        }
        else {
            // 传参为空则返回空值错误信息
            return new ImagesExecution(ImagesStateEnum.EMPTY);
        }
    }

    @Override
    public ImagesExecution batchaddImages(List<Images> imagesList) throws ImagesOperationException {
        ReadImagesFiles readImagesFiles=new ReadImagesFiles();
        imagesList=readImagesFiles.getimgAddr();
        int flag=0;
        flag=imagesMapper.batchInsertpetImg(imagesList);
        if(flag>0){
            return new ImagesExecution(ImagesStateEnum.SUCCESS);
        }
        return new ImagesExecution(ImagesStateEnum.INNER_ERROR);
    }

    @Override
    //删除一张图片
    public int delimage(Integer imageId) {
        Images images=imagesMapper.queryImgById(imageId);
        String addr=images.getImages();
        //删除本地服务器
        ImageUtil.deleteFileOrPath(addr);
        //删除数据库
        int num=imagesMapper.deleteImages(imageId);
        return num;
    }

    //添加缩略图,上传到指定目录
    private void addThumbnail(Images images, ImageHolder thumbnail) {
        //创建路径
        String dest= PathUtil.getPetImagePath();
        //存入目的地址
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        images.setImages(PathUtil.getPetImagePathURL(thumbnailAddr));

    }
    //批量添加
    private void addImagesList(Images images, List<ImageHolder> ImgHolderList) {
        // 获取图片存储路径，这里直接存放到相应店铺的文件夹底下
        String dest=PathUtil.getPetImagePath();
        List<Images> imagesList= new ArrayList<Images>();
        // 遍历图片一次去处理，并添加进productImg实体类里
        for (ImageHolder ImgHolder : ImgHolderList) {
            String imgAddr = ImageUtil.generateNormalImg(ImgHolder, dest);
           images.setImages(imgAddr);
           imagesList.add(images);

        }
        // 如果确实是有图片需要添加的，就执行批量添加操作
        if (imagesList.size() > 0) {

            try {
                int effectedNum = imagesMapper.batchInsertpetImg(imagesList);
                if (effectedNum <= 0) {
                    throw new ImagesOperationException("创建详情图片失败");
                }
            } catch (Exception e) {
                throw new ImagesOperationException("创建详情图片失败:" + e.toString());
            }

        }
    }
    /**
     * 删除所有详情图
     *
     * @param imagesId
     */
    private void deleteImgList(Integer imagesId) {
        // 根据productId获取原来的图片
       List<Images> imagesList = imagesMapper.listImages();
        // 干掉原来的图片
        for (Images images: imagesList) {
            ImageUtil.deleteFileOrPath(images.getImages());
        }
        // 删除数据库里原有图片的信息
        imagesMapper.deleteImages(imagesId);

    }



}
