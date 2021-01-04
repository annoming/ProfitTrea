package com.atming.service;

import com.atming.entity.User;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: Annoming
 * @Date: 2021/1/3
 * @Time: 23:50
 * @Description
 */

public interface UserService{
    /**
     * 判断用户是否存在
     * @param user 用户信息
     * @return User
     */
    User getLogin(User user);
}
