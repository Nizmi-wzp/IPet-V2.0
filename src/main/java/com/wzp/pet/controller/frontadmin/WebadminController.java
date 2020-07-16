package com.wzp.pet.controller.frontadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/front",method = {RequestMethod.GET})
public class WebadminController {
    @RequestMapping(value = "userlist")
    public String user() {
        return "pet/userlist";
    }
    @RequestMapping(value = "pagelist")
    public String pagelist() {
        return "pet/pagelist";
    }
    @RequestMapping(value = "pet-images")
    public String petimages() {
        return "front/pet-images";
    }
    @RequestMapping(value = "login")
    public String login() {
        return "front/login";
    }
    @RequestMapping(value = "register")
    public String register() {
        return "front/register";
    }
    @RequestMapping(value = "pet-post")
    public String petpost() { return "front/pet-post"; }
    @RequestMapping(value = "pet-adopt")
    public String petadopt() { return "front/pet-adopt"; }
    @RequestMapping(value = "index")
    public String index() { return "front/index"; }
    @RequestMapping(value = "pet-video")
    public String petvideo() { return "front/pet-video"; }
    @RequestMapping(value = "player")
    public String player() { return "front/player"; }
    @RequestMapping(value = "pet-baike")
    public String baike() { return "front/baike"; }
    @RequestMapping(value = "mypost")
    public String mypost() { return "front/mypost"; }
    @RequestMapping(value = "pet-news")
    public String petnews() { return "front/pet-news"; }
    @RequestMapping(value = "pet-shopping")
    public String petshopping() { return "front/pet-shopping"; }

}