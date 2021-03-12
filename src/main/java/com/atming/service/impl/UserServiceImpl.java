package com.atming.service.impl;

import com.atming.entity.User;
import com.atming.mapper.UserMapper;
import com.atming.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: Annoming
 * @Date: 2021/1/3
 * @Time: 23:50
 * @Description
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getLogin(User user) {
        String userName = user.getUserName();
        return userMapper.findOne(userName);
    }
}
