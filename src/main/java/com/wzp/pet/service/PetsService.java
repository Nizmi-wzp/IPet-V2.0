package com.wzp.pet.service;

import com.wzp.pet.exceptions.PetsOperationException;
import com.wzp.pet.po.Pets;
import com.wzp.pet.vo.ImageHolder;
import com.wzp.pet.vo.PetsExecution;

import java.util.List;

public interface PetsService {

    PetsExecution addPets(Pets pets , ImageHolder thumbnail, List<ImageHolder> productImgList)
            throws PetsOperationException;
}
