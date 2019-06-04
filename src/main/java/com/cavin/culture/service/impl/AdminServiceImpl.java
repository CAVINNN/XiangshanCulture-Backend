package com.cavin.culture.service.impl;

import com.cavin.culture.bean.User;
import com.cavin.culture.mapper.UserMapper;
import com.cavin.culture.service.AdminService;
import com.cavin.culture.utils.SHAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public User getUserByName(String userName) {
        return userMapper.getUserByName(userName);
    }

    @Override
    public String getPasswordByName(String userName) {
        return userMapper.getPasswordByName(userName);
    }

    @Override
    public Integer insertUser(User user) {
        String username = user.getUserName();
        String password = user.getUserPassword();
        String email = user.getEmail();
        Date registerDate = user.getRegisterDate();
        String sha256Password = SHAUtil.getSHA256(password);
        User encodeUser = new User(username, sha256Password, email, registerDate);

        Integer insertNum = userMapper.insertUser(encodeUser);
        return insertNum;
    }

}
