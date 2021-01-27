package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户Controller
 *
 * @author jerome
 * @date 2017/3/22 14:36
 */
@RestController
@RequestMapping("user")
public class UserController {

    private Logger LOGGER =  LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    /**
     * 获取用户信息
     * @param userName
     * @return
     */
    @RequestMapping("info/{userName}")
    User info(@PathVariable String userName) {
        User user = new User();
        user.setLogin_name(userName);
        user.setPwd("123");
        return user;
    }

}
