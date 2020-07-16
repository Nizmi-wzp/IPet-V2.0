package com.wzp.pet.controller.backstageadmin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/*springMVC路由管理*/
@Controller
@RequestMapping(value="back",method = {RequestMethod.GET})
public class RouterController {
    /* 跳转至后台页面*/
    @RequestMapping(value = "index")
    public String index() {
        return "back/index";
    }
    /* 跳转至后台登录页面*/
    @RequestMapping(value = "login")
    public String login() {
        return "back/login";
    }
    /* 跳转至后台管理员界面页面*/
    @RequestMapping(value = "adminmanager")
    public String adminmanager() {
        return "back/adminmanager";
    }
    /* 跳转至后台登录成功页面*/
    @RequestMapping(value = "loginok")
    public String loginok() {
        return "back/loginok";
    }
    /* 跳转至后台修改密码页面*/
    @RequestMapping(value = "changepwd")
    public String changepwd() {
        return "back/changepwd";
    }
    /* 跳转至后台绑定页面*/
    @RequestMapping(value = "bindacount")
    public String bindacount() {
        return "back/bindacount";
    }
    /* 跳转至后台图片管理页面*/
    @RequestMapping(value = "imagesmanager")
    public String imagesmaneger() {
        return "back/imagesmanager";
    }
    /* 跳转至后台新闻管理页面*/
    @RequestMapping(value = "newsmanager")
    public String newsmaneger() {
        return "back/newsmanager";
    }
    /* 跳转至后台贴吧管理页面*/
    @RequestMapping(value = "postmanager")
    public String postmaneger() {
        return "back/postmanager";
    }
    /* 跳转至后台回帖管理页面*/
    @RequestMapping(value = "replymanager")
    public String replymaneger() {
        return "back/replymanager";
    }
    /* 跳转至后台百科管理页面*/
    @RequestMapping(value = "baikekonwledgemanager")
    public String baikekonwledgemaneger() {
        return "back/baikekonwledgemanager";
    }
    /* 跳转至后台视频管理页面*/
    @RequestMapping(value = "videomanager")
    public String vediomaneger() {
        return "back/videomanager";
    }
    /* 跳转至后台图表管理页面*/
    @RequestMapping(value = "echartmanager")
    public String echartmaneger() { return "back/echartmaneger"; }
    /* 跳转至后台领养管理页面*/
    @RequestMapping(value = "adoptmanager")
    public String adoptmaneger() { return "back/adoptmanager"; }
    /* 跳转至后台领养管理页面*/
    @RequestMapping(value = "shoppingmanager")
    public String shoppingmaneger() {
        return "back/shoppingmanager";
    }
    /* 跳转至后台领养管理页面*/
    @RequestMapping(value = "usermanager")
    public String usermaneger() {
        return "back/usermanager";
    }
    @RequestMapping(value = "location")
    public String location() {
        return "back/location";
    }
    @RequestMapping(value = "myinfo")
    public String myinfo() {
        return "back/myinfo";
    }
    @RequestMapping(value = "reg")
    public String reg() {
        return "back/reg";
    }
    @RequestMapping(value = "message")
    public String message() {return "back/message"; }
    @RequestMapping(value = "404")
    public String error() {
        return "back/404";
    }
    @RequestMapping(value = "baidu")
    public String baidu() {
        return "back/baidu";
    }
    @RequestMapping(value = "layim")
    public String layim() {
        return "back/layim";
    }
}
