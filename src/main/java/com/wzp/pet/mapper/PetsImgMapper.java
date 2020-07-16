package com.wzp.pet.mapper;

import com.wzp.pet.po.PetsImg;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("PetsImgMapper")
public interface PetsImgMapper {
    /**
     * 列出某个商品的详情图列表
     *
     * @param
     * @return
     */
    List<PetsImg> queryImgList();

    /**
     * 批量添加商品详情图片
     *
     * @param ImgList
     * @return
     */
    int batchInsertImg(List<PetsImg> ImgList);

    /**
     * 删除指定商品下的所有详情图
     *
     * @param imagesId
     * @return
     */
    int deleteImgById(Integer imagesId);

}
