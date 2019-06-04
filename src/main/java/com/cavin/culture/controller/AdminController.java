package com.cavin.culture.controller;

import com.cavin.culture.bean.JsonMessage;
import com.cavin.culture.bean.User;
import com.cavin.culture.service.AdminService;
import com.cavin.culture.utils.JWTUtil;
import com.cavin.culture.utils.SHAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/admin/isLogin", method = RequestMethod.GET)
    @ResponseBody
    public JsonMessage isLogin(HttpServletRequest request) {
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
                // 有access_token
                return JsonMessage.success();
            } else {
                // 无access_token
                return JsonMessage.error(401, "请先登录！");
            }
        } else {
            // 无access_token
            return JsonMessage.error(401, "请先登录！");
        }
    }

    @RequestMapping(value = "/admin/register", method = RequestMethod.POST)
    @ResponseBody
    public JsonMessage login(@RequestBody User user) {
        User checkUser = adminService.getUserByName(user.getUserName());
        if (checkUser == null) {
            Integer insertNum = adminService.insertUser(user);
            return JsonMessage.success().addData("insertNum", insertNum);
        } else {
            return JsonMessage.error(400, "该用户名已注册");
        }
    }

    @RequestMapping(value = "/admin/login", method = RequestMethod.POST)
    @ResponseBody
    public JsonMessage login(HttpServletResponse response, @RequestBody User user) {
        String username = user.getUserName();
        String password = user.getUserPassword();
        User checkUser = adminService.getUserByName(username);
        if (checkUser != null) {
            String checkPassword = SHAUtil.getSHA256(password);
            String storedPassword = adminService.getPasswordByName(username);
            if (checkPassword.equals(storedPassword)) {
                // 登陆成功
                String token = JWTUtil.getJwtToken(checkUser.getUserName());
                Cookie cookie = new Cookie("access_token", token);
                cookie.setDomain("localhost");
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                cookie.setMaxAge(3*24*60*60);
                response.addCookie(cookie);
                return JsonMessage.success();
            } else {
                return JsonMessage.error(400, "密码错误！");
            }
        } else {
            return JsonMessage.error(400, "用户名不存在！");
        }
    }

    @RequestMapping(value = "/admin/logout", method = RequestMethod.POST)
    @ResponseBody
    public JsonMessage logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("access_token")) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
            return JsonMessage.success();
        } else {
            return JsonMessage.error(401, "请先登录！");
        }
    }

}
