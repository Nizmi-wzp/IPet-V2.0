package com.wzp.pet.mapper;

import com.wzp.pet.po.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("UserMapper")
public interface UserMapper {
    /*1》输出新闻列表*/
    List<User> queryUser();
    /*2》根据条件查询用户信息*/
    List<User> queryUserList(@Param("UserCondition") User UserCondition);


    int querynumber(@Param("UserCondition") User UserCondition);
    List<User> queryUserPage(@Param("UserCondition")User userCondition,@Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize );
    /*3》插入单条记录*/
    int insertUser(User User);
    /* 4》批量插入*/
    int batchInsertUser(List<User> UserList);
    /*5》根据新闻Id删除单条记录*/
    int deleteUser(Integer UserId);
    /*6》根据Id列表批量删除*/
    int batchDeleteUser(List<Long> UserIdList);
    /*7》根据新闻ID号修改*/
    int updateUser(User User);


    public  User findUserByName (@Param("userName")String userName);
    public int addUser(@Param("user") User user);
    public int deleteUser(@Param("userId") int userId);
    public int updateUserPassword(@Param("userPassword") String userPassword,@Param("userId") int userId);
}
