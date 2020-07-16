package com.wzp.pet.mapper;

import com.wzp.pet.po.User;
import com.wzp.pet.util.RandomPhone;
import org.junit.Test;
import com.wzp.pet.util.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

import static org.junit.Assert.*;

public class UserMapperTest extends BaseTest{
    @Autowired
    @Qualifier("UserMapper")
    private UserMapper userMapper;
    @Test
    public void queryUser() {
        List<User> userList = userMapper.queryUser();
        System.out.println(userList.size());

        System.out.println(userList.get(0).getUserName());
    }
    @Test
    public void insertUser(){
/*       for(int i=0;i<300;i++){
            User user=new User();
            RandomPhone randomPhone=new RandomPhone();
            String username= randomPhone.getChineseName();
            String tel=randomPhone.getTel();
            String password=String.valueOf((int)Math.random()*999999+1);
            user.setUserName(username);
            user.setUserPassword(password);
            user.setTelephone(tel);
            user.setUserImage(i+".jpg");
            int flag=userMapper.insertUser(user);
            assertEquals(1,flag);
        }*/
       User user=new User();
        user.setUserName("AAA");
        user.setUserPassword("85671945");
        int flag=userMapper.insertUser(user);
        assertEquals(1,flag);
    }
@Test
    public void queryUserPage(){
        User user=new User();
        user.setUserName("李");
        List<User> userList=userMapper.queryUserPage(user,0,5);
        System.out.println(userList.size());
        System.out.println(userList.get(0).getUserName());
    }
    @Test
    public void querynumber(){
        User user=new User();
        user.setUserName("李");
        int num=userMapper.querynumber(user);
        System.out.println(num);


    }


    @Test
    public void deleteUser(){
        int userId=17;
        int cnt;
        cnt=userMapper.deleteUser(userId);

        System.out.println(cnt);
    }



    @Test
    public void findUserByName(){

        User user=userMapper.findUserByName("李平");
        System.out.println(user);
        assertNotNull(user);


    }

    @Test
    public void updateUser(){

        Integer userId=1;
        String userPassword=("12223344");
        int flag;
        flag=userMapper.updateUserPassword(userPassword,userId);
        assertEquals(1,flag);

    }

}