package com.cavin.culture.controller;

import com.cavin.culture.bean.History;
import com.cavin.culture.bean.JsonMessage;
import com.cavin.culture.service.HistoryService;
import com.cavin.culture.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @RequestMapping(value = "/getHistoriesByType", method = RequestMethod.GET)
    @ResponseBody
    public JsonMessage getHistoriesByType(@RequestParam(value = "type")String type, HttpServletRequest request) {
        String token = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("access_token")) {
                token = cookie.getValue();
            }
        }
        Claims claims = JWTUtil.parseToken(token);
        String username = claims.getAudience();

        List<History> histories = historyService.getHistoriesByNameAndType(username, type);
        return JsonMessage.success().addData("histories", histories);
    }

    @RequestMapping(value = "/addHistory", method = RequestMethod.POST)
    @ResponseBody
    public JsonMessage addHistory(@RequestBody History history, HttpServletRequest request) {
        String token = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("access_token")) {
                token = cookie.getValue();
            }
        }
        Claims claims = JWTUtil.parseToken(token);
        String username = claims.getAudience();

        history.setUserName(username);
        Integer insertNum = historyService.insertHistory(history);
        return JsonMessage.success().addData("insertNum", insertNum);
    }

    @RequestMapping(value = "/deleteHistoryById/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonMessage deleteHistoryById(@PathVariable Integer id) {
        Integer deleteNum = historyService.deleteHistoryById(id);
        return JsonMessage.success().addData("deleteNum", deleteNum);
    }

}
