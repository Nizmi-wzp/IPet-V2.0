package com.wzp.pet.vo;

import com.wzp.pet.enums.PetsStateEnum;
import com.wzp.pet.po.Pets;

import java.util.List;

public class PetsExecution {
    // 结果状态
    private int state;

    // 状态标识
    private String stateInfo;

    // 数量
    private int count;

    // 操作的product（增删改商品的时候用）
    private Pets pets;

    // 获取的product列表(查询商品列表的时候用)
    private List<Pets> petsList;

    public PetsExecution() {
    }

    // 失败的构造器
    public PetsExecution(PetsStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    // 成功的构造器
    public PetsExecution(PetsStateEnum stateEnum, Pets pets ) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.pets = pets;
    }

    // 成功的构造器
    public PetsExecution(PetsStateEnum stateEnum, List<Pets> petsList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.petsList = petsList;
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

    public Pets getProduct() {
        return pets;
    }

    public void setProduct(Pets pets) {
        this.pets= pets;
    }

    public List<Pets> getProductList() {
        return petsList;
    }

    public void setProductList(List<Pets> petsList) {
        this.petsList =petsList;
    }

}
