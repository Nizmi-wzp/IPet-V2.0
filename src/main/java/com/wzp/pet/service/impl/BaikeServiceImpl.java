package com.wzp.pet.service.impl;

import com.wzp.pet.mapper.BaikeMapper;
import com.wzp.pet.po.Baike;
import com.wzp.pet.service.BaikeService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("BaikeServiceImpl")
public class BaikeServiceImpl implements BaikeService {
    @Autowired
    @Qualifier("BaikeMapper")
    private BaikeMapper baikeMapper;
    @Override
    public List<Baike> getBaikeList(String queryKey, int rowIndex, int pagesize) {
        return baikeMapper.queryBaikebypage(queryKey,rowIndex, pagesize);
    }

    @Override
    public int queryBaikeNum(String queryKey) {
        return baikeMapper.querybaikeNum(queryKey);
    }
}
