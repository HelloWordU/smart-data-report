package com.rz.smartDataReport.common;


import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 登录拦截器
 *
 * @author pan_junbiao
 **/
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        //判断当前请求地址是否登录地址
        if (uri.contains("index")
                || uri.contains("toLoginPage") || uri.contains("favicon")
                || uri.contains("doc.html")) {
            //登录请求，直接放行
            return true;
        } else {
            //判断用户是否登录
            if (request.getSession().getAttribute("userId") != null || request.getHeader("x-userId") != null) {
                //说明已经登录，放行
                return true;
            } else {
                //没有登录，重定向到登录界面
                response.sendRedirect("/noAuthError");
            }
        }

        //默认拦截
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}