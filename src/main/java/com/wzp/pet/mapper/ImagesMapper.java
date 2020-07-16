package com.wzp.pet.mapper;

import com.wzp.pet.po.Images;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("ImagesMapper")
public interface ImagesMapper {
    /*查询所有图片列表*/
    List<Images> listImages();
    /*根据查询条件动态查询图片分页*/
    List<Images> queryImages(@Param("imagesCondition") Images imagesCondition,@Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);
    //分页条数
    int queryImagecount(@Param("imagesCondition") Images imagesCondition);

    int insertImages(Images images);
    int batchInsertpetImg(List<Images> imagesList);

    int updateImages(Images images);

    int deleteImages(Integer imagesId);
    Images queryImgById(Integer imagesId);

}
