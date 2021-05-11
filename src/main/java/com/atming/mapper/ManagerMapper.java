package com.atming.mapper;

import com.atming.entity.User;

import java.util.Date;
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

    String selectOrganizeByEmail(String email);

    int insertUser(User user);

    List selectUsers(String organization);

    List selectManagers(String organization);

    List selectAll();

    List selectManagerAsName(String organization,String userName);

    List selectAdminAsName(String userName);

    List findByUserName(String userName);

    List findByDate(String organization,Date startTime, Date endTime);

    List findAllDate(Date startTime, Date endTime);

    List findByNameAndDate(String userName, Date startTime, Date endTime);

    int deleteManager(String userId);

    User findById(String userId);

    int updateById(String userId, String realName, String email, Date time);

    int updateUserById(String userId, String realName, String email, String studentNumber, String major, String grade,Date updateTime);
}
