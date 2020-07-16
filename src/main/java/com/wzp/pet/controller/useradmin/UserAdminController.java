package com.wzp.pet.controller.useradmin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/useradmin")
public class UserAdminController {
    @RequestMapping(value = "/userlist")
    public String userList(){
        return "user/userlist";
    }

    @RequestMapping(value = "/userlogin")
    public String userlogin(){
        return "user/login";
    }

    @RequestMapping(value = "/userreg")
    public String userreg(){
        return "user/register";
    }

    @RequestMapping(value = "/home")
    public String jumphome(){
        return "frontend/homepage";
    }

    @RequestMapping(value = "/post")
    public String jumppost(){
        return "frontend/pet-post";
    }






}
