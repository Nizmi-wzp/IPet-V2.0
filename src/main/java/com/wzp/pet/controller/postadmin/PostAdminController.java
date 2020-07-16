package com.wzp.pet.controller.postadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/postadmin")
public class PostAdminController {
    @RequestMapping(value = "/postlist")
    public String postList(){
        return "front/pet-post";
    }

    @RequestMapping(value = "/mypost")
    public String Mypost(){
        return "frontend/mypost";
    }



}
