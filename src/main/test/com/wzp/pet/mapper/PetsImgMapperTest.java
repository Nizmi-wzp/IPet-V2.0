package com.wzp.pet.mapper;

import com.wzp.pet.po.PetsImg;
import com.wzp.pet.util.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

import static org.junit.Assert.*;

public class PetsImgMapperTest extends BaseTest {
    @Autowired
    @Qualifier("PetsImgMapper")
    private PetsImgMapper petsImgMapper;
    @Test
    public void queryImgList() {
        List<PetsImg> userList = petsImgMapper.queryImgList();
        System.out.println(userList.size());

        System.out.println(userList.get(0).getImages());
    }

    @Test
    public void batchInsertImg() {
    }

    @Test
    public void deleteImgById() {
    }
}