package com.atming.mapper;

import com.atming.entity.OrganizationManger;
import com.atming.entity.User;

import java.util.List;

/**
 * @author annoming
 * @date 2021/3/28 5:15 下午
 */

public interface ManagerMapper {

    /**
     * 根据邮箱查询管理员
     * @param email  邮箱
     * @return  User
     */
    User selectManagerByEmail(String email);

    int insertUser(User user);

    List selectManagers();

    int deleteManager(String userId);
}
