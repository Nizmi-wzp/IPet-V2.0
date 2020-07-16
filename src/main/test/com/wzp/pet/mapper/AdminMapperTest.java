package com.wzp.pet.mapper;

import com.wzp.pet.po.Admin;
import com.wzp.pet.util.BaseTest;
import com.wzp.pet.util.MD5;
import com.wzp.pet.util.RandomPhone;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class AdminMapperTest extends BaseTest {
@Autowired
@Qualifier("AdminMapper")
private AdminMapper adminMapper;

    private static final String adminName = "ipet_601";
    private static final String adminPassword = MD5.getMd5("admin");
    @Test
    public void queryAdmin() {
        List<Admin> adminList = adminMapper.queryAdmin();
        System.out.println(adminList.size());
        System.out.println(adminList.get(0).getAdminName());
    }
    @Test
   //条件查询分页
    public void queryAdminList() {
        Admin adminCondition =new Admin();
        //adminCondition.setAdminId(10010L);
        //adminCondition.setNickname("心");//有效
        adminCondition.setRole("超级管理员");
        List<Admin> adminList = adminMapper.queryAdminList(adminCondition,0,3);
        System.out.println(adminList.size());
        System.out.println(adminList.get(0).getAdminName());
    }
    //分页总数
    @Test
    public void queryAdmincount(){
        Admin admin=new Admin();
        admin.setAdminName("汪");
        int num=adminMapper.queryAdmincount(admin);
        System.out.println(num);

    }

    @Test
    public void queryAdminById(){
        Long adminId=10010L;
        Admin admin=null;
        admin=adminMapper.queryAdminById(adminId);
        System.out.println(admin.getAdminName());
    }

    @Test

    public void insertAdmin(){
       // 批量测试数据
       String password="admin";
        Random rand = new Random();
        for(int i=0;i<300;i++){
            Admin admin=new Admin();
            RandomPhone randomPhone=new RandomPhone();
            admin.setAdminName("ipet_600"+i);
           admin.setAdminPassword(MD5.getMd5(password));
           admin.setRole("编辑人员");
           admin.setPhone(randomPhone.getTel());
            int effectedNum = adminMapper.insertAdmin(admin);
            assertEquals(1, effectedNum);

        }
        Admin admin=new Admin();
        admin.setAdminName("ipet_606");
        admin.setAdminPassword("test");
        admin.setRole("编辑人员");
        admin.setPhone("15874692513");
        int effectedNum = adminMapper.insertAdmin(admin);
        System.out.println("插入后主键为："+admin.getAdminId());
        assertEquals(1, effectedNum);

    }
@Test

    public void batchInsertAdmin(){
        Admin admin=new Admin();
        admin.setAdminName("ipet_607");
        admin.setAdminPassword("1589623");
        admin.setRole("编辑人员");
        admin.setPhone("13692561002");
        Admin admin1=new Admin();
        admin1.setAdminName("ipet_608");
        admin1.setAdminPassword("8596321");
        admin1.setRole("编辑人员");
        admin1.setPhone("16325896025");
        List<Admin> adminList=new ArrayList<Admin>();
        adminList.add(admin);
        adminList.add(admin1);
        int effectnum=adminMapper.batchInsertAdmin(adminList);
        assertEquals(2,effectnum);
    }

    @Test
    public void deleteAdmin(){
        Long adminId = 10019L;
        int effectedNum =adminMapper.deleteAdmin(adminId);
        assertEquals(1, effectedNum);
    }
    @Test
    public  void batchDeleteAdmin(){
        List<Long> adminIdList=new ArrayList<>();
         adminIdList.add(10312L);
         adminIdList.add(10313L);
         int num=adminMapper.batchDeleteAdmin(adminIdList);
         System.out.println(num);
    }
    @Test
    public void updateAdmin(){
        Admin admin=new Admin();

        Long adminId=10018L;
        admin.setAdminName("");
        admin.setAdminPassword("");
        int effectedNum = adminMapper.updateAdmin(admin);
        assertEquals(1, effectedNum);
    }

    @Test

    public void queryLocalByAdminNameAndPwd(){
        // 按照帐号和密码查询用户信息
        Admin admin = adminMapper.queryLocalByAdminNameAndPwd(adminName,adminPassword);
        assertEquals("ipet_601", admin.getAdminName());
    }
    @Test
    public void queryAdminByName(){
        Admin admin=new Admin();
        admin=adminMapper.queryAdminByName("aaa");
        System.out.println(admin.getAdminId());
    }
    @Test
    public void updateAdminCondition(){
        int effectedNum = adminMapper.updateAdminCondition("aaa","778899","555");
        assertEquals(1, effectedNum);
        // 查询出该条平台帐号的最新信息

    }
    @Test
    public void updateAdminPwd(){
        int effect=adminMapper.updateAdminPwd(10015L,"55555");
        assertEquals(1,effect);
    }
}