package com.wzp.pet.controller.backstageadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/*springMVC路由管理*/
@Controller
@RequestMapping(value="backstage",method = {RequestMethod.GET})
public class BackstageadminController {
   /* 跳转至后台登录页面*/
    @RequestMapping(value = "Login")
    public String login() {
        return "backstage/Login";
    }
    /* 跳转至后台登录成功页面*/
    @RequestMapping(value = "loginok")
    public String loginok() {
        return "backstage/loginok";
    }
    /* 跳转至后台修改密码页面*/
    @RequestMapping(value = "changepwd")
    public String changepwd() {
        return "backstage/changepwd";
    }
    /* 跳转至后台绑定页面*/
    @RequestMapping(value = "bindacount")
    public String bindacount() {
        return "backstage/bindacount";
    }
    /* 跳转至后台主页页面*/
    @RequestMapping(value = "backmain")
    public String backmain() {
        return "backstage/backmain";
    }
    /* 跳转至后台管理员管理页面*/
    @RequestMapping(value = "adminmaneger")
    public String adminmaneger() {
        return "backstage/adminmaneger";
    }
    /* 跳转至后台图片管理页面*/
    @RequestMapping(value = "imagesmaneger")
    public String imagesmaneger() {
        return "backstage/imagesmaneger";
    }
    /* 跳转至后台新闻管理页面*/
    @RequestMapping(value = "newsmaneger")
    public String newsmaneger() {
        return "backstage/newsmaneger";
    }
    /* 跳转至后台贴吧管理页面*/
    @RequestMapping(value = "postmaneger")
    public String postmaneger() {
        return "backstage/postmaneger";
    }
    /* 跳转至后台百科管理页面*/
    @RequestMapping(value = "baikekonwledgemaneger")
    public String baikekonwledgemaneger() {
        return "backstage/baikekonwledgemaneger";
    }
    /* 跳转至后台视频管理页面*/
    @RequestMapping(value = "vediomaneger")
    public String vediomaneger() {
        return "backstage/vediomaneger";
    }
    /* 跳转至后台图表管理页面*/
    @RequestMapping(value = "echartmaneger")
    public String echartmaneger() { return "backstage/echartmaneger"; }
    /* 跳转至后台领养管理页面*/
    @RequestMapping(value = "adoptmaneger")
    public String adoptmaneger() { return "backstage/adoptmaneger"; }
    /* 跳转至后台领养管理页面*/
    @RequestMapping(value = "shoppingmaneger")
    public String shoppingmaneger() {
        return "backstage/shoppingmaneger";
    }
    /* 跳转至后台领养管理页面*/
    @RequestMapping(value = "usermaneger")
    public String usermaneger() {
        return "backstage/usermaneger";
    }
    @RequestMapping(value = "petmap")
    public String petmap() {
        return "backstage/petmap";
    }
}
