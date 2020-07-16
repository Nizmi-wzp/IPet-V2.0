package com.wzp.pet.service;

import  com.wzp.pet.exceptions.AdminOperationException;
import com.wzp.pet.po.Admin;
import com.wzp.pet.po.Images;
import com.wzp.pet.vo.AdminExecution;
import com.wzp.pet.vo.ImageHolder;
import com.wzp.pet.vo.ImagesExecution;

import java.util.List;

public interface AdminService {
    List<Admin> getAdminList();


   Admin getAdminById(Long adminId)throws AdminOperationException;

    AdminExecution addAdmin(Admin admin)throws AdminOperationException;
//分页
    AdminExecution queryAdminPage(Admin admin, int pageIndex, int pageSize) throws AdminOperationException;
    int queryAdmincount(Admin admin);
    /**
     *
     * @param adminList
     * @return
     * @throws AdminOperationException
     */


    AdminExecution batchAddAdmin(List<Admin> adminList)
            throws AdminOperationException;

    /**
     * 将此类别下的商品里的类别id置为空，再删除掉该商品类别
     * @param adminId
     * @return
     * @throws RuntimeException
     */
    AdminExecution deleteAdmin(long adminId)
            throws AdminOperationException;

    int batchdeleteAdmin(List<Long> adminIdList);

    public Admin getLocalByAdminNameAndPwd(String adminName, String adminPassword)
            throws AdminOperationException;;

     public Admin queryAdminByName(String adminName);
    /**
     * 修改平台帐号的登录密码
     *

     * @param adminName
     * @param adminPassword
     * @param newPassword
     * @return
     */
   AdminExecution modifyAdmin( String adminName, String adminPassword, String newPassword)
            throws AdminOperationException;

   //账号绑定
    AdminExecution bindAdmin(Admin admin ) throws AdminOperationException;

    AdminExecution resetPwd(Long adminId,String newpassword);

    AdminExecution addImages(ImageHolder thumbnail);
}
