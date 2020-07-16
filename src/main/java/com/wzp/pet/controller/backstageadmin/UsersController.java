package com.wzp.pet.controller.backstageadmin;


import com.wzp.pet.po.User;
import com.wzp.pet.service.UserService;
import com.wzp.pet.util.HttpServletRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/useradmin")
public class UsersController {
    Logger logger = LoggerFactory.getLogger(UsersController.class);
    @Autowired
    @Qualifier("UserServiceImpl")
    private UserService userService;

    @RequestMapping(value = "/listuser", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listUser() {
        logger.info("===start===");
        long startTime = System.currentTimeMillis();

        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<User> userList = new ArrayList<User>();
        try {
            userList = userService.getUserList();
            modelMap.put("userList", userList);
            modelMap.put("success", true);
            modelMap.put("total", userList.size());
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

    @RequestMapping(value = "/listuserpage", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listUserPage(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String queryKey= HttpServletRequestUtil.getString(request,"queryKey");
        // 获取前台传过来的页码
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");

        User user=new User();
  /*      user.setUserId( Integer. parseInt(queryKey));
        user.setTelephone(queryKey);*/
        user.setUserName(queryKey);


        List<User> userList=userService.queryUserPage(user,pageIndex,pageSize);

        modelMap.put("success",true);
        modelMap.put("userList",userList);
        return modelMap;
    }

    @RequestMapping(value = "/usercount", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> userCount(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String queryKey= HttpServletRequestUtil.getString(request,"queryKey");
        User user=new User();
        user.setUserName(queryKey);
        int num=userService.querycount(user);
        modelMap.put("count",num);
        modelMap.put("success",true);
        return modelMap;
    }

    @RequestMapping(value = "/deleteuser", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> deleteUser(HttpServletRequest request){
        int userId= HttpServletRequestUtil.getInt(request,"userId");
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (userId==-1){
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户编号为空！");
        }else{
            int flag;
            flag=userService.deleteUser(userId);
            if (flag<=0) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "删除失败！");
            }else{
                modelMap.put("success", true);
            }

        }
        return modelMap;
    }


    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addUser(HttpServletRequest request){
        String userName= HttpServletRequestUtil.getString(request,"userName");
        String userPassword= HttpServletRequestUtil.getString(request,"userPwd");
        String userPassword2= HttpServletRequestUtil.getString(request,"userPwd2");
        User user=new User();
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int flag;
        try {

            if (userName == null || userPassword == null||userPassword2==null) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "用户名或者密码为空！");
            }else if(!userPassword.equals(userPassword2)){
                modelMap.put("success", false);
                modelMap.put("errMsg", "两次输入密码不一致！");
            }else {
                flag = userService.addUser(user);
                if (flag==-1001){
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "该用户名已经被注册！");
                }
                else if (flag <= 0) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "系统错误！");
                } else {
                    modelMap.put("success", true);
                }

            }

        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", "注册失败！");
        }
        return modelMap;
    }


    @RequestMapping(value = "/updateuser", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> updateUser(HttpServletRequest request){

        String userPassword= HttpServletRequestUtil.getString(request,"userPassword");
        int userId= HttpServletRequestUtil.getInt(request,"userId");
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (userId==-1){
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户编号为空！");
        }else{
            int flag;
            flag=userService.updatePassword(userPassword,userId);
            if (flag<=0) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "修改失败！");
            }else{
                modelMap.put("success", true);
            }
        }
        return modelMap;
    }


    @RequestMapping(value = "/loginuser", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> loginUser(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String userName= HttpServletRequestUtil.getString(request,"userName");
        String userPassword= HttpServletRequestUtil.getString(request,"userPwd");
        User user=new User();
        user.setUserName(userName);
        user.setUserPassword(userPassword);

        int flag= userService.userLogin(user);
        if(flag==-1)
        {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名或密码为空！");
        }
        else if(flag==-1001){
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名不存在！");
        }
        else if(flag==0){
            modelMap.put("success", false);
            modelMap.put("errMsg", "密码错误！");
        }

        if(flag==1)
        {
            modelMap.put("success", true);
            modelMap.put("errMsg", "登陆成功！");
            user=userService.findUserByName(user.getUserName());
            request.getSession().setAttribute("user",user);
        }
        return modelMap;
    }

}

