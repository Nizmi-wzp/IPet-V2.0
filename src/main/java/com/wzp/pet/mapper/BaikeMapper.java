package com.wzp.pet.mapper;

import com.wzp.pet.po.Baike;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("BaikeMapper")
public interface BaikeMapper {
    int querybaikeNum(@Param("queryKey") String queryKey);
    List<Baike> queryBaikebypage(@Param("queryKey") String queryKey, @Param("rowIndex")int rowIndex, @Param("pagesize")int pagesize );

}
