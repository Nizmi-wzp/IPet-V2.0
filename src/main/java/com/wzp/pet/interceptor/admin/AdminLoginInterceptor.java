package com.wzp.pet.interceptor.admin;

import com.wzp.pet.po.Admin;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminLoginInterceptor extends HandlerInterceptorAdapter {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Object adminObj = request.getSession().getAttribute("admin");
        if (adminObj != null) {
            // 若用户信息不为空则将session里的用户信息转换成PersonInfo实体类对象
            Admin admin = (Admin) adminObj;
            // 做空值判断，确保userId不为空并且该帐号的可用状态为1，并且用户类型为店家
            if (admin != null && admin.getAdminId()!= 0 && admin.getAdminId() > 0)
                // 若通过验证则返回true,拦截器返回true之后，用户接下来的操作得以正常执行
                return true;
        }
        return false;
    }

}
