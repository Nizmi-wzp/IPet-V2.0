package com.wzp.pet.service;

import com.wzp.pet.po.Baike;

import java.util.List;

public interface BaikeService {
    List<Baike> getBaikeList(String queryKey, int rowIndex, int pagesize);
    int queryBaikeNum(String queryKey);
}
