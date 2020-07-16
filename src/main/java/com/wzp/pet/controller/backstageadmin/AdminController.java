package com.wzp.pet.controller.backstageadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wzp.pet.enums.ImagesStateEnum;
import com.wzp.pet.exceptions.AdminOperationException;
import com.wzp.pet.enums.AdminOperationEnum;
import com.wzp.pet.exceptions.ImagesOperationException;
import com.wzp.pet.po.Admin;
import com.wzp.pet.po.Images;
import com.wzp.pet.util.CodeUtil;
import com.wzp.pet.util.HttpServletRequestUtil;
import com.wzp.pet.util.MD5;
import com.wzp.pet.util.SendMessage.PhoneCode;
import com.wzp.pet.util.SendMessage.ShortMessage;
import com.wzp.pet.vo.AdminExecution;
import com.wzp.pet.vo.ImageHolder;
import com.wzp.pet.vo.ImageUpload;
import com.wzp.pet.vo.ImagesExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wzp.pet.util.MD5.getMd5;

@Controller
@RequestMapping("/admin")
public class AdminController {
    Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    @Qualifier("AdminServiceImpl")
    private com.wzp.pet.service.AdminService adminService;

    @RequestMapping(value = "/listadmin", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listAdmin() {
        logger.info("===start===");
        long startTime = System.currentTimeMillis();

        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<Admin> adminList = new ArrayList<Admin>();
        try {
            adminList = adminService.getAdminList();
            modelMap.put("adminList", adminList);
            modelMap.put("success", true);
            modelMap.put("total", adminList.size());
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        logger.error("test error!");
        long endTime = System.currentTimeMillis();
        logger.debug("costTime:[{}ms]", endTime - startTime);
        logger.info("===end===");

        return modelMap;
    }
    @RequestMapping(value = "/listadminById", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listAdminById(HttpServletRequest request) {
        logger.info("===start===");
        long startTime = System.currentTimeMillis();
        Admin admin=new Admin();
        Long adminId=HttpServletRequestUtil.getLong(request,"adminId");
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            admin = adminService.getAdminById(adminId);
            modelMap.put("admin", admin);
            modelMap.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        logger.error("test error!");
        long endTime = System.currentTimeMillis();
        logger.debug("costTime:[{}ms]", endTime - startTime);
        logger.info("===end===");

        return modelMap;

    }
   //分页
    @RequestMapping(value = "/listadminByPage", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listAdminByPage(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        // 获取前台传过来的页码
        int pageIndex=HttpServletRequestUtil.getInt(request, "page");
        int pageSize = HttpServletRequestUtil.getInt(request, "limit");

        // 空值判断
        if ((pageIndex > -1) && (pageSize > -1) ) {
            // 获取传入的需要检索的条件，包括是否需要从某个商品类别以及模糊查找商品名去筛选某个店铺下的商品列表
            // 筛选的条件可以进行排列组合
            String queryKey= HttpServletRequestUtil.getString(request, "queryKey");
            logger.info("输出获取传入的值："+queryKey);
            Admin admin=new Admin();
            if(queryKey!=null && !"".equalsIgnoreCase(queryKey))
                admin.setAdminName(queryKey);
            // 传入查询条件以及分页信息进行查询，返回相应商品列表以及总数
            AdminExecution ae= adminService.queryAdminPage(admin, pageIndex, pageSize);
            int recordCount=adminService.queryAdmincount(admin);

            modelMap.put("count",recordCount);
            modelMap.put("adminList",ae.getAdminList());
            modelMap.put("success", true);

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex ");
        }
        return modelMap;

    }

    @RequestMapping(value = "/removeadmin", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> removeAdmin(Long adminId, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (adminId != null &&adminId > 0) {
            try {
               // Admin currentadmin = (Admin) request.getSession().getAttribute("currentadmin");

                AdminExecution adminExecution = adminService.deleteAdmin(adminId);
                if (adminExecution.getState() == AdminOperationEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", adminExecution.getStateInfo());
                }
            }catch (AdminOperationException e) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", e.toString());
                    return modelMap;
                }
        } else {
            modelMap.put("success", false);
            modelMap.put("adminId",adminId);
            modelMap.put("errMsg", "请至少选择一个删除");
        }
        return modelMap;
    }

//批量删除管理员
    @RequestMapping(value = "/batchremoveadmin", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> batchremoveadmin(@RequestBody List<Long> adminIdList, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (adminIdList != null &&adminIdList.size() > 0) {
            try {
                // Admin currentadmin = (Admin) request.getSession().getAttribute("currentadmin");

                int num = adminService.batchdeleteAdmin(adminIdList);
                if (num>0) {
                    modelMap.put("success", true);
                }else {
                    modelMap.put("success", false);
                }
            }catch (AdminOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少选择一个删除");
        }
        return modelMap;
    }
    @RequestMapping(value = "/addAdmin", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addAdmin(HttpServletRequest request) {

        //获取管理员名称
        String adminName=HttpServletRequestUtil.getString(request,"adminName");
        //获取管理员密码
        String adminPassword=getMd5(HttpServletRequestUtil.getString(request,"password"));
        //获取管理员身份
        String role=HttpServletRequestUtil.getString(request,"role");
        //获取管理员性别
        String sex=HttpServletRequestUtil.getString(request,"sex");
        //获取管理员昵称
        String nickName=HttpServletRequestUtil.getString(request,"nickName");
        //获取管理员头像
        String photo=HttpServletRequestUtil.getString(request,"img");
        //获取管理员手机
        String phone=HttpServletRequestUtil.getString(request,"phone");
        //获取管理员邮箱
        String email=HttpServletRequestUtil.getString(request,"email");
        //获取管理员签名
        String contents=HttpServletRequestUtil.getString(request,"contents");
        Admin admin=new Admin();
        admin.setPhone(phone);
        admin.setRole(role);
        admin.setNickname(nickName);
        admin.setContents(contents);
        admin.setAdminPassword(adminPassword);
        admin.setAdminName(adminName);
        admin.setEmail(email);
        admin.setPhoto(photo);
        admin.setSex(sex);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if(admin!=null){
            try {
                AdminExecution ae = adminService.addAdmin(admin);
                if (ae.getState() == AdminOperationEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", ae.getStateInfo());
                }
            } catch (AdminOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "管理员为空！");
        }
        return modelMap;
    }
    //批量添加
    @RequestMapping(value = "/batchAddAdmin", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> batchAddAdmin(@RequestBody List<Admin> adminList,
                                                    HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        if (adminList != null && adminList.size() > 0) {
            try {
                AdminExecution ae = adminService.batchAddAdmin(adminList);
                if (ae.getState() == AdminOperationEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", ae.getStateInfo());
                }
            } catch (AdminOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少输入一个管理员");
        }
        return modelMap;
    }

    //登陆名称检查
    @RequestMapping(value = "/loginNamecheck", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> loginNamecheck(HttpServletRequest request,HttpServletResponse response) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String adminName=HttpServletRequestUtil.getString(request,"adminName");
        Admin admin=new Admin();
        admin=adminService.queryAdminByName(adminName);
        if(admin!=null){
            modelMap.put("success",true);
            modelMap.put("admin",admin);
        }else{
            modelMap.put("success",false);
        }
        return modelMap;
    }
    //登陆检查
    @RequestMapping(value = "/logincheck", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> logincheck(HttpServletRequest request,HttpServletResponse response) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        // 获取输入的帐号
        String adminName = HttpServletRequestUtil.getString(request, "adminName");
        // 获取输入的密码
        String adminPassword = HttpServletRequestUtil.getString(request, "adminPassword");
        // 非空校验
        if (adminName != null && adminPassword != null) {
            // 传入帐号和密码去获取平台帐号信息
            Admin admin = adminService.getLocalByAdminNameAndPwd(adminName,adminPassword);
            if (admin != null) {
                //创建两个cookie
                Cookie cookie_name=new Cookie("cookie_name",adminName);
                Cookie cookie_password=new Cookie("cookie_password",adminPassword);
                //设置时间为1天
                cookie_name.setMaxAge(60*60*24);
                cookie_password.setMaxAge(60*60*24);
                //设置路径
                cookie_name.setPath(request.getContextPath());
                cookie_password.setPath(request.getContextPath());
                response.addCookie(cookie_name);
                response.addCookie(cookie_password);
                // 若能取到帐号信息则登录成功
                modelMap.put("success", true);
                modelMap.put("admin",admin);
                // 同时在session里设置用户信息
               request.getSession().setAttribute("loginId",admin.getAdminId());
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "用户名或密码错误");
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名和密码均不能为空");

        }
        return modelMap;
    }
    //登出
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    /*
     * 当用户点击登出按钮的时候注销session
     *
     * @param request
     * @return
     * @throws IOException
     */
    private Map<String, Object> logout(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 将用户session置为空
        request.getSession().setAttribute("loginId", null);
        modelMap.put("success", true);
        return modelMap;
    }

    @RequestMapping(value = "/changeadminpwd", method = RequestMethod.POST)
    @ResponseBody
    /*
     * 修改密码
     *
     * @param request
     * @return
     */
    private Map<String, Object> changeAdminPwd(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        // 获取登录的管理员信息
        Long adminId=(Long)request.getSession().getAttribute("loginId");
        // 获取新密码
        String newPassword = HttpServletRequestUtil.getString(request, "newPassword");

        if (adminId != null && newPassword != null ){
            try {
                // 修改平台帐号的用户密码
               AdminExecution ae = adminService.resetPwd(adminId,
                        newPassword);
                if (ae.getState() == AdminOperationEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", ae.getStateInfo());
                }
            } catch (AdminOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入用户名和密码");
        }
        return modelMap;
    }

    @RequestMapping(value = "/bindacount", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> bindacount(HttpServletRequest request,HttpServletResponse response) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
        if (needVerify && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        // 获取输入的帐号
        String adminName = HttpServletRequestUtil.getString(request, "adminName");
        // 获取输入的密码
        String adminPassword = HttpServletRequestUtil.getString(request, "password");
        // 非空校验
        if (adminName != null && adminPassword != null) {
            // 传入帐号和密码去获取平台帐号信息
            Admin admin=new Admin();
            admin.setAdminName(adminName);
            admin.setAdminPassword(adminPassword);
            AdminExecution ae = adminService.bindAdmin(admin);
            if (ae.getState()==AdminOperationEnum.SUCCESS.getState()) {
                // 若能取到帐号信息则成功
                modelMap.put("success", true);
                //  同时在session里设置用户信息
                request.getSession().setAttribute("loginname", admin.getAdminName());

            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "用户名或密码错误");
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名和密码均不能为空");
        }
        return modelMap;
    }
    @RequestMapping(value = "/adminrole", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> adminRole(HttpServletRequest request,HttpServletResponse response) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Long adminId=(Long) request.getSession().getAttribute("loginId");
        System.out.println(adminId);

        Admin admin=adminService.getAdminById(adminId);
        if(admin!=null){
            System.out.println(admin.getRole());
            modelMap.put("success",true);
            modelMap.put("admin",admin);
        }
        else {System.out.println("空异常");
            modelMap.put("success",false);}
        return modelMap;
    }


    @RequestMapping(value = "/uploadheadimage", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addImages(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 接收前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
        ImageHolder thumbnail = null;
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        try {
            // 若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage(request, thumbnail);
                logger.info("返回缩略图："+thumbnail.getImageName());
                AdminExecution ae=adminService.addImages(thumbnail);
                modelMap.put("code", 0);
                ImageUpload imageUpload=new ImageUpload();
                imageUpload.setSrc(ae.getAdmin().getPhoto());
                modelMap.put("data",imageUpload);

            } else {
                modelMap.put("code", 1);
                modelMap.put("msg", "上传图片不能为空");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("code", 1);
            modelMap.put("msg", e.toString());
            return modelMap;
        }

        return modelMap;
    }

    //从前端取出文件流
    private ImageHolder handleImage(HttpServletRequest request, ImageHolder thumbnail)
            throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 取出缩略图并构建ImageHolder对象
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("file");
        logger.info("图片信息："+thumbnailFile.getName());
        if (thumbnailFile != null) {
            thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
            logger.info(thumbnail.getImageName());
        }
        return thumbnail;
    }
    //发送短信验证码
    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> sendMessage(HttpServletRequest request,HttpServletResponse response) {
        //检测验证码，检测手机号唯一
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String phone = HttpServletRequestUtil.getString(request, "phone");
        String inputcode=HttpServletRequestUtil.getString(request, "code");
        if (phone == null) {
            System.out.println(phone);//前台用户手机号
            modelMap.put("success", false);
        }
        else if(phone != null){
            System.out.println(phone);
            //该手机号已经注册
            //验证码是否正确正确
            ShortMessage shortMessage=new ShortMessage();
            String checkcode=shortMessage.code;//获取验证码
            request.getSession().setAttribute("code",checkcode);
            String testcode=(String) request.getSession().getAttribute("code");//后端生成的验证码
            System.out.println(testcode);
            String msg=shortMessage.Sms(phone);
            //调用短信工具包
            System.out.println(msg);
            if(msg!=null)
            {modelMap.put("success", true);}
            else{modelMap.put("success", false);}
        }
        return modelMap;
    }
    //注册
    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> reg(HttpServletRequest request,HttpServletResponse response) {
        //检测验证码，检测手机号唯一
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //获取前端账户，手机号，密码，昵称
        String phone = HttpServletRequestUtil.getString(request, "phone");
        String adminName = HttpServletRequestUtil.getString(request, "adminName");
        String password = HttpServletRequestUtil.getString(request, "password");
        String nickName = HttpServletRequestUtil.getString(request, "nickName");
        String role = HttpServletRequestUtil.getString(request, "role");
        String checkCode=(String) request.getSession().getAttribute("code");//后端生成的验证码
        String inputCode=HttpServletRequestUtil.getString(request, "inputCode");//用户输入的验证码
        //获取管理员性别
        String sex="";
        //获取管理员头像
        String photo="";
        //获取管理员邮箱
        String email="";
        //获取管理员签名
        String contents="";
        //检验验证码
        if(checkCode.contentEquals(inputCode)){//字符串的相等不能用==
            //可以注册，验证码正确
            Admin admin=new Admin();
            admin.setAdminName(adminName);
            admin.setAdminPassword(MD5.getMd5(password));
            admin.setRole(role);
            admin.setPhone(phone);
            admin.setNickname(nickName);
            if(admin!=null){
                try {
                    AdminExecution ae = adminService.addAdmin(admin);
                    if (ae.getState() == AdminOperationEnum.SUCCESS.getState()) {
                        modelMap.put("success", true);
                    } else {
                        modelMap.put("success", false);
                        modelMap.put("note","该手机号已注册！");
                        modelMap.put("errMsg", ae.getStateInfo());
                    }
                } catch (AdminOperationException e) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", e.toString());
                    //return modelMap;
                }
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "管理员为空！");
            }
        }
        else{
            modelMap.put("success",false);
            modelMap.put("errMsg", "验证码错误！");
            //不可以注册，验证码错误
        }
        return modelMap;
    }



}
