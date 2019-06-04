package com.cavin.culture.controller.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        Cookie[] cookies = null;
        cookies = request.getCookies();
        if (cookies != null) {
            boolean isLogin = false;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("access_token")) {
                    isLogin = true;
                }
            }
            if (isLogin) {
                return true;
            } else {
                response.setStatus(401);
                return false;
            }
        } else {
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
