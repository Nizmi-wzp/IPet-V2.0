package com.wzp.pet.service;

import com.wzp.pet.po.User;
import com.wzp.pet.util.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserServiceTest extends BaseTest {
    @Autowired
    private UserService userService;

    @Test
    public void getUserList() {
        List<User> userList=new ArrayList<>();
        userList=userService.getUserList();
        System.out.println(userList.size());


    }
    @Test
    public void queryUserPage(){
        User user=new User();
       // user.setUserId(1);
        List<User> userList=userService.queryUserPage(user,0,3);
        System.out.println(userList.get(0).getUserName());
        System.out.println(userList.size());
    }
}