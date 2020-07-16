package com.wzp.pet.service.impl;

import com.wzp.pet.mapper.UserMapper;
import com.wzp.pet.po.User;
import com.wzp.pet.service.UserService;
import com.wzp.pet.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {
    @Autowired
    @Qualifier("UserMapper")
    private UserMapper userMapper;

    @Override
    public List<User> getUserList() {
        return userMapper.queryUser();
    }

    @Override
    public List<User> queryUserPage(User user, int pageIndex, int pageSize) {
        //查询起始行
        int rowIndex= PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<User> userList=userMapper.queryUserPage(user,rowIndex,pageSize);
        return userList ;



    }

    @Override
    public int querycount(User user) {
        int num=userMapper.querynumber(user);
        return num;
    }


    @Override
    public int deleteUser(int userId){
        return userMapper.deleteUser(userId);
    }
    @Override
    public int addUser(User user){

        if (user==null || user.getUserName()==null || user.getUserPassword()==null )
            return -1;

        User user1=userMapper.findUserByName(user.getUserName());
        if (user1!=null)   return -1001;
        return userMapper.addUser(user);

    }
    @Override
    public int updatePassword(String userPassword,int userId){

        return userMapper.updateUserPassword(userPassword,userId);

    }
    @Override
    public int userLogin(User user){
        if (user==null || user.getUserName()==null || user.getUserPassword()==null)
            return -1;
        User user1=userMapper.findUserByName(user.getUserName());
        if(user1==null) return -1001;
        else if (user.getUserPassword().equals(user1.getUserPassword()))
            return 1;
        else return 0;
    }

    @Override
    public User findUserByName(String userName) {
        return userMapper.findUserByName(userName);
    }


}
