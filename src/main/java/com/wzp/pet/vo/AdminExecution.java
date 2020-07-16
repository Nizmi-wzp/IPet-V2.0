package com.wzp.pet.vo;

import com.wzp.pet.enums.AdminOperationEnum;
import com.wzp.pet.po.Admin;

import java.util.List;

public class AdminExecution {


    private int state;
    private String stateInfo;
    private int count;

    private Admin admin;
    private List<Admin> adminList;

    public AdminExecution(){

    }
//操作失败时使用的构造器
    public AdminExecution(AdminOperationEnum stateEnum){
        this.state=stateEnum.getState();
        this.stateInfo=stateEnum.getStateInfo();

    }

    // 成功的构造器
    public AdminExecution(AdminOperationEnum stateEnum, Admin admin) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.admin= admin;
    }

    //操作成功时使用的构造器
    public AdminExecution(AdminOperationEnum stateEnum,List<Admin> adminList){
        this.state=stateEnum.getState();
        this.stateInfo=stateEnum.getStateInfo();
        this.adminList=adminList;


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

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public List<Admin> getAdminList() {
        return adminList;
    }

    public void setAdminList(List<Admin> adminList) {
        this.adminList = adminList;
    }


}
