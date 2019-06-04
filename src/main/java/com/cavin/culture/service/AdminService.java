package com.cavin.culture.service;

import com.cavin.culture.bean.User;

public interface AdminService {

    User getUserByName(String userName);

    String getPasswordByName(String userName);

    Integer insertUser(User user);

}
