package com.wzp.pet.interceptor.admin;

import com.wzp.pet.po.Admin;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminPerssionInterceptor extends HandlerInterceptorAdapter {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 从session中获取当前选择的店铺
        Admin currentadmin=(Admin)request.getSession().getAttribute("loginname");




        return false;
    }
}
