package com.example.service;

import com.example.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User findUserByLoginName(String loginName) {

        // 数据库查询

        // 模拟数据
        User user = new User();
        user.setLogin_name(loginName);
        user.setPwd("E99A18C428CB38D5F260853678922E03");
        user.setOpenid("E99A18C428CB38D5F260853678922E04");
        return user;
    }
}
