package com.wzp.pet.mapper;

import com.wzp.pet.po.Baike;
import com.wzp.pet.util.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class BaikeMapperTest extends BaseTest {
    @Autowired
    private BaikeMapper baikeMapper;
    @Test
    public void queryaikeNum(){
        int num=baikeMapper.querybaikeNum(null);
        System.out.println(num);

    }
    @Test
    public void queryBaikebypage(){
        List<Baike> baikeList = baikeMapper.queryBaikebypage(null,3,4);
        System.out.println(baikeList.size());
        System.out.println(baikeList.get(0).getBaikeTheme());
    }

}