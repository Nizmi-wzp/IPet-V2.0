package com.wzp.pet.mapper;

import com.wzp.pet.po.Pets;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PetsMapper {
    /*1》输出新闻列表*/
    List<Pets> queryPet();
    /*2》根据条件查询新闻信息*/
    List<Pets> queryPetList(@Param("PetCondition") Pets PetCondition,@Param("rowIndex")int rowIndex,@Param("pageSize") int pageSize);
    /*3》插入单条记录*/
    int insertPet(Pets Pets);
    /* 4》批量插入*/
    int batchInsertPet(List<Pets> PetsList);
    /*5》根据新闻Id删除单条记录*/
    int deletePet(long PetsId);
    /*6》根据Id列表批量删除*/
    int batchDeletePet(List<Long> PetsIdList);
    /*7》根据新闻ID号修改*/
    int updatePet(Pets Pets);
}
