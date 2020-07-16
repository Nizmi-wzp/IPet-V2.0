package com.wzp.pet.service;

import com.wzp.pet.po.Admin;
import com.wzp.pet.util.BaseTest;
import com.wzp.pet.vo.AdminExecution;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AdminServiceTest extends BaseTest {
@Autowired
private AdminService adminService;
    @Test
    public void getAdminList(){
        List<Admin> adminList=adminService.getAdminList();
        System.out.println("管理员数量"+adminList.size());
        System.out.println(adminList.get(0).getAdminPassword());
    }
    @Test
    public void getAdminById(){
        Long adminId=10018L;
        Admin admin=null;
        admin=adminService.getAdminById(adminId);
        assertEquals("aaa",admin.getAdminName());
    }
    @Test
    public void deleteAdmin() throws Exception{
        long adminId = 9L;
        List<Admin> adminList = adminService.getAdminList();
        for (Admin a : adminList) {
            if ("3434".equals(a.getAdminName())) {
                AdminExecution  adminExecution= adminService.deleteAdmin(adminId);
                assertEquals(1,adminExecution.getState());
            }
        }
    }


    @Test
    public void batchAddAdmin()throws Exception{
        Admin admin=new Admin();
        admin.setAdminName("111111");
        admin.setAdminPassword("1111");
        Admin admin1=new Admin();
        admin1.setAdminName("222112");
        admin1.setAdminPassword("2222");
        List<Admin> adminList=new ArrayList<Admin>();
        adminList.add(admin);
        adminList.add(admin1);
        AdminExecution adminExecution=adminService.batchAddAdmin(adminList);
        assertEquals(1,adminExecution.getState());

    }
    @Test
    public void getLocalByAdminNameAndPwd(){
        Admin admin=new Admin();
        admin=adminService.getLocalByAdminNameAndPwd("汪志鹏","admin");
        assertEquals("汪志鹏",admin.getAdminName());
    }
    @Test
    public void modifyAdmin(){
        AdminExecution adminExecution=adminService.modifyAdmin("b","admin","555");
        assertEquals(1,adminExecution.getState());
    }
    @Test
    public void bindAdmin(){
// 新增一条平台帐号
        Admin admin=new Admin();
        String username = "testusername23";
        String password = "testpassword232";
        // 给平台帐号设置上用户信息
        // 给用户设置上用户Id,标明是某个用户创建的帐号

        // 设置帐号
        admin.setAdminName(username);
        // 设置密码
        admin.setAdminPassword(password);
        // 绑定帐号
        AdminExecution ae = adminService.bindAdmin(admin);

        // 打印用户名字和帐号密码看看跟预期是否相符
        System.out.println("用户昵称：" + admin.getAdminName());
        System.out.println("平台帐号密码：" + admin.getAdminPassword());
    }
    @Test
    public void queryAdminPage(){
        Admin admin=new Admin();
        admin.setRole("超级管理员");
        AdminExecution ae=adminService.queryAdminPage(admin,0,3);
        System.out.println(ae.getAdminList().size());
        System.out.println(ae.getAdminList().get(0).getAdminName());

    }
    @Test
    public void queryAdmincount(){
        Admin admin=new Admin();
        admin.setAdminName("ipet_60");
         int num=adminService.queryAdmincount(admin);
         System.out.println(num);
    }
    @Test
    public void addAdmin(){
        Admin admin=new Admin();
        admin.setAdminName("ipet_2");
        admin.setAdminPassword("admin");
        admin.setRole("普通管理员");
        admin.setPhone("");
        admin.setNickname("dada");
        AdminExecution ae=adminService.addAdmin(admin);
        System.out.println(ae.getStateInfo());
        assertEquals(1,ae.getState());
    }


}