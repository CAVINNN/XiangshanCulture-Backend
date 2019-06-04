package com.cavin.culture.mapper;

import com.cavin.culture.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select user_name, email, register_date from user where user_name=#{userName}")
    User getUserByName(String userName);

    @Select("select user_password from user where user_name=#{userName}")
    String getPasswordByName(String userName);

    @Options(keyProperty = "user_name")
    @Insert("insert into user(user_name, user_password, email, register_date) values(#{userName}, #{userPassword}, #{email}, #{registerDate})")
    int insertUser(User user);

}
