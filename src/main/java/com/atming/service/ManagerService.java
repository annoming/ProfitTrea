package com.atming.service;

import com.atming.entity.User;

import java.util.List;

/**
 * @author annoming
 * @date 2021/3/28 5:19 下午
 */

public interface ManagerService {

    User findOneByEmail(User user);

    String findOrganizeByEmail(String email);

    int insertUser(User user);

    List<User> findAllUser(String organization);

    List<User> findAll(String organization);

    List<User> findAll();

    int deleteManagerById(String userId);

    User getUserById(String userId);
}
