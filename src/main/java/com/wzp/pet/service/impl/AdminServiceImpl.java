package com.wzp.pet.service.impl;

import com.wzp.pet.enums.AdminOperationEnum;
import com.wzp.pet.enums.ImagesStateEnum;
import com.wzp.pet.exceptions.AdminOperationException;
import com.wzp.pet.exceptions.ImagesOperationException;
import com.wzp.pet.mapper.AdminMapper;
import com.wzp.pet.po.Admin;
import com.wzp.pet.service.AdminService;
import com.wzp.pet.util.ImageUtil;
import com.wzp.pet.util.PageCalculator;
import com.wzp.pet.util.PathUtil;
import com.wzp.pet.vo.AdminExecution;
import com.wzp.pet.vo.ImageHolder;
import com.wzp.pet.vo.ImagesExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import com.wzp.pet.util.MD5;

@Service("AdminServiceImpl")
public class AdminServiceImpl implements AdminService {
    @Autowired
    @Qualifier("AdminMapper")
    private AdminMapper adminMapper;
    @Override
    public List<Admin> getAdminList() {
        return adminMapper.queryAdmin();
    }

    @Override
    public Admin getAdminById(Long adminId) throws AdminOperationException {
        return adminMapper.queryAdminById(adminId);
    }
    @Override
    public AdminExecution addAdmin(Admin admin)throws AdminOperationException{
        if (admin != null) {
            try {
                int effectedNum = adminMapper.insertAdmin(admin);
                if (effectedNum <= 0) {
                    throw new AdminOperationException("新增管理员失败");
                } else {
                    return new AdminExecution(AdminOperationEnum.SUCCESS);
                }

            } catch (Exception e) {
                throw new AdminOperationException("insert error: " + e.getMessage());
            }
        } else {
            return new AdminExecution(AdminOperationEnum.EMPTY_LIST);
        }
    }

    @Override
    public AdminExecution queryAdminPage(Admin admin, int pageIndex, int pageSize) throws AdminOperationException {

        //查询起始行
        int rowIndex= PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Admin> adminList=adminMapper.queryAdminList(admin,rowIndex,pageSize);

        //int count=imagesMapper.queryImagecount(imagesCondition);
        AdminExecution adminExecution=new AdminExecution(AdminOperationEnum.SUCCESS,adminList);
        //  imagesEXecution.setCount(count);
        return adminExecution;
    }
    @Override
    public int queryAdmincount(Admin admin){
        if(admin!=null){
            try {
                int num=adminMapper.queryAdmincount(admin);
                return num;
            }catch (Exception e){return 0;}
        }
        else {
            return 0;
        }

    }

    @Override
    @Transactional//事务注解
    public AdminExecution batchAddAdmin(List<Admin> adminList) throws AdminOperationException {
        if (adminList != null && adminList.size() > 0) {
            try {
                int effectedNum = adminMapper.batchInsertAdmin(adminList);
                if (effectedNum <= 0) {
                    throw new AdminOperationException("新增管理员失败");
                } else {
                    return new AdminExecution(AdminOperationEnum.SUCCESS);
                }

            } catch (Exception e) {
                throw new AdminOperationException("batchAddAdmin error: " + e.getMessage());
            }
        } else {
            return new AdminExecution(AdminOperationEnum.EMPTY_LIST);
        }
    }

    @Override
    @Transactional//事务注解
    public AdminExecution deleteAdmin(long adminId) throws AdminOperationException {
       /* List<Admin> adminList=adminMapper.queryAdmin();
        int len=adminList.size();
        // if(0<adminId&&adminId<len)
        return null;*/

        // 删除该ImagesCategory
        try {
            int effectedNum = adminMapper.deleteAdmin(adminId);
            if (effectedNum <= 0) {
                throw new AdminOperationException("管理员删除失败");
            } else {
                return new AdminExecution(AdminOperationEnum.SUCCESS);
            }
        } catch (Exception e) {
            throw new AdminOperationException("deleteAdmin error:" + e.getMessage());
        }

    }

    @Override
    public int batchdeleteAdmin(List<Long> adminIdList) {
        int num=adminMapper.batchDeleteAdmin(adminIdList);
        return num;
    }

    @Override
    public Admin getLocalByAdminNameAndPwd(String adminName, String adminPassword) throws AdminOperationException {
        return adminMapper.queryLocalByAdminNameAndPwd(adminName,MD5.getMd5(adminPassword));
    }
    public Admin queryAdminByName(String adminName){
        return adminMapper.queryAdminByName(adminName);
    }
    @Override
    public AdminExecution modifyAdmin( String adminName, String adminPassword, String newPassword) throws AdminOperationException {
        // 非空判断，判断传入的用户Id,帐号,新旧密码是否为空，新旧密码是否相同，若不满足条件则返回错误信息
        if ( adminName != null && adminPassword != null && newPassword != null
                && !adminPassword.equals(newPassword)) {
            try {
                // 更新密码，并对新密码进行MD5加密
                int effectedNum = adminMapper.updateAdminCondition( adminName, MD5.getMd5(adminPassword),
                        MD5.getMd5(newPassword));
                // 判断更新是否成功
                if (effectedNum <= 0) {
                    throw new AdminOperationException("更新密码失败");
                }
                return new AdminExecution(AdminOperationEnum.SUCCESS);
            } catch (Exception e) {
                throw new AdminOperationException("更新密码失败:" + e.toString());
            }
        } else {
            return new AdminExecution(AdminOperationEnum.EMPTY_LIST);
        }
    }
   //插入操作
    @Override
    public AdminExecution bindAdmin(Admin admin) throws AdminOperationException {
        if(admin.getAdminName()==null||admin.getAdminPassword()==null){
            return new AdminExecution(AdminOperationEnum.EMPTY_LIST);
        }
        Admin admin2=adminMapper.queryAdminById(admin.getAdminId());
        if (admin2 != null) {
            // 如果绑定过则直接退出，以保证平台帐号的唯一性
            return new AdminExecution(AdminOperationEnum.ONLY_ONE_ACCOUNT);
        }
        try {
            // 如果之前没有绑定过平台帐号，则创建一个平台帐号与该用户绑定
            // 对密码进行MD5加密
            admin.setAdminPassword(MD5.getMd5(admin.getAdminPassword()));
            int effectedNum = adminMapper.insertAdmin(admin);
            // 判断创建是否成功
            if (effectedNum <= 0) {
                throw new AdminOperationException("帐号绑定失败");
            } else {
                return new AdminExecution(AdminOperationEnum.SUCCESS,admin);
            }
        } catch (Exception e) {
            throw new AdminOperationException("insertAdmin error: " + e.getMessage());
        }

    }

    @Override
    public AdminExecution resetPwd(Long adminId, String newpassword) {
        // 非空判断，判断传入的用户Id,帐号,新旧密码是否为空，新旧密码是否相同，若不满足条件则返回错误信息
        if ( adminId != null && newpassword != null) {
            try {
                // 更新密码，并对新密码进行MD5加密
                int effectedNum = adminMapper.updateAdminPwd( adminId, MD5.getMd5(newpassword));
                // 判断更新是否成功
                if (effectedNum <= 0) {
                    throw new AdminOperationException("更新密码失败");
                }
                return new AdminExecution(AdminOperationEnum.SUCCESS);
            } catch (Exception e) {
                throw new AdminOperationException("更新密码失败:" + e.toString());
            }
        } else {
            return new AdminExecution(AdminOperationEnum.EMPTY_LIST);
        }
    }

    @Override
    public AdminExecution addImages(ImageHolder thumbnail) throws ImagesOperationException {

            //添加缩略图,上传到指定目录
            if (thumbnail != null) {
                String dest=addThumbnail(thumbnail);
            Admin admin=new Admin();
            admin.setPhoto(dest);
            return new AdminExecution(AdminOperationEnum.SUCCESS, admin);

        }
        else {
            // 传参为空则返回空值错误信息
            return new AdminExecution(AdminOperationEnum.EMPTY_LIST);
        }
    }
    //添加缩略图,上传到指定目录
    private String addThumbnail( ImageHolder thumbnail) {
        //创建路径
        String dest= PathUtil.getPetImagePath();
        //存入目的地址
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        return PathUtil.getPetImagePathURL(thumbnailAddr);

    }

}
