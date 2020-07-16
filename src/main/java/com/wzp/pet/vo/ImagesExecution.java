package com.wzp.pet.vo;

import com.wzp.pet.enums.ImagesStateEnum;
import com.wzp.pet.po.Images;

import java.util.List;

public class ImagesExecution {
    private int state;
    private String stateInfo;
    private int count;
    private Images images;
    private List<Images> imagesList;

    //失败的构造器
    public ImagesExecution(ImagesStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateiInfo();
    }
    //成功的构造器

    public ImagesExecution(ImagesStateEnum stateEnum, Images images) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateiInfo();
        this.images= images;
    }

    //成功的构造器

    public ImagesExecution(ImagesStateEnum stateEnum, List<Images> imagesList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateiInfo();
        this.imagesList = imagesList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public List<Images> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<Images> imagesList) {
        this.imagesList = imagesList;
    }
}
