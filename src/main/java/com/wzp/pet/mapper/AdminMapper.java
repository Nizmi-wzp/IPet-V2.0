package com.wzp.pet.mapper;

import com.wzp.pet.po.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("AdminMapper")
public interface AdminMapper {
    /*1》输出管理员列表*/
    List<Admin> queryAdmin();
    Admin queryAdminById(@Param("adminId") Long adminId);

    /*2》根据条件查询管理员信息分页*/
    List<Admin> queryAdminList(@Param("adminCondition") Admin adminCondition,@Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);
    //分页总条数
    int queryAdmincount(@Param("adminCondition") Admin adminCondition);
    /*3》插入单条记录*/
    int insertAdmin(Admin admin);
   /* 4》批量插入*/
    int batchInsertAdmin(List<Admin> adminList);
    /*5》根据管理员Id删除单条记录*/
    int deleteAdmin(Long adminId);
    /*6》根据Id列表批量删除*/
    int batchDeleteAdmin(List<Long> adminIdList);
    /*7》根据管理员ID号修改管理员名和密码*/
    int updateAdmin(Admin admin);
    /**
     * 通过帐号和密码查询对应信息，登录用
     *
     * @param adminName
     * @param adminPassword
     * @return
     */
    Admin queryLocalByAdminNameAndPwd(@Param("adminName") String adminName, @Param("adminPassword") String adminPassword);

    //检测登陆管理员是否可用
    Admin queryAdminByName(@Param("adminName") String adminName);

    int updateAdminCondition(@Param("adminName") String adminName,
                      @Param("adminPassword") String adminPassword, @Param("newPassword") String newPassword);
    //修改密码
    int updateAdminPwd(@Param("adminId") Long adminId,@Param("newPassword") String newPassword);

}
