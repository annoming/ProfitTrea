package com.atming.service;

import com.atming.entity.User;

import java.util.Date;
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

    List findManagerAsName(String organization,String userName);

    List<User> findAdminAsName(String userName);

    List<User> findOneByUserName(String userName);

    List<User> findByDate(String organization,Date startTime, Date endTime);

    List<User> findAllDate(Date startTime, Date endTime);

    List<User> findByNameAndDate(String userName, Date startTime, Date endTime);

    int deleteManagerById(String userId);

    User getUserById(String userId);

    int updateInfoById(String userId, String realName, String email, Date time);

    int updateUserById(String userId, String realName, String email, String studentNumber, String major, String grade,Date updateTime);

}
