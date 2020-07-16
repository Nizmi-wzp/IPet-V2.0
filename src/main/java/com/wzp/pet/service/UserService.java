package com.wzp.pet.service;

import com.wzp.pet.po.User;

import java.util.List;

public interface UserService {
    List<User> getUserList();
    public List<User> queryUserPage(User user,int pageIndex, int pageSize);
    public int querycount(User user);


    public int deleteUser(int userId);
    public int addUser(User user);
    public int updatePassword(String userPassword,int userId);
    public int userLogin(User user);
    public User findUserByName(String userName);
}
