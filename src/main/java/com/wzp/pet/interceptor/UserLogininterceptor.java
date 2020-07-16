package com.wzp.pet.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

//用户管理系统登录拦截器
public class UserLogininterceptor extends HandlerInterceptorAdapter {
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handle) throws Exception {
       // 从session中取出用户信息来
       Object adminObj = request.getSession().getAttribute("admin");

       // 若不满足登录验证，则直接跳转到帐号登录页面
       PrintWriter out = response.getWriter();
       out.println("<html>");
       out.println("<script>");
       //登陆成功返回店家管理的页面
       out.println("window.open ('" + request.getContextPath() + "/local/login?usertype=2','_self')");
       out.println("</script>");
       out.println("</html>");
       return false;
   }

}
