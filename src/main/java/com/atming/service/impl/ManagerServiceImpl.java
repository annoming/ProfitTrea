package com.atming.service.impl;

import com.atming.entity.User;
import com.atming.mapper.ManagerMapper;
import com.atming.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author annoming
 * @date 2021/3/28 5:21 下午
 */
@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerMapper managerMapper;

    @Override
    public User findOneByEmail(User user) {
        String email = user.getEmail();
        return managerMapper.selectManagerByEmail(email);
    }

    @Override
    public String findOrganizeByEmail(String email) {
        return managerMapper.selectOrganizeByEmail(email);
    }

    @Override
    public int insertUser(User user) {
        return managerMapper.insertUser(user);
    }

    @Override
    public List<User> findAllUser(String organization) {
        return managerMapper.selectUsers(organization);
    }

    @Override
    public List<User> findAll(String organization) {
        return managerMapper.selectManagers(organization);
    }

    @Override
    public List<User> findAll() {
        return managerMapper.selectAll();
    }

    @Override
    public List findManagerAsName(String organization,String userName) {
        return managerMapper.selectManagerAsName(organization,userName);
    }

    @Override
    public List<User> findAdminAsName(String userName) {
        return managerMapper.selectAdminAsName(userName);
    }

    @Override
    public List<User> findOneByUserName(String userName) {
        return managerMapper.findByUserName(userName);
    }

    @Override
    public List<User> findByDate(String organization,Date startTime, Date endTime) {
        return managerMapper.findByDate(organization,startTime,endTime);
    }

    @Override
    public List<User> findAllDate(Date startTime, Date endTime) {
        return managerMapper.findAllDate(startTime,endTime);
    }

    @Override
    public List<User> findByNameAndDate(String userName, Date startTime, Date endTime) {
        return managerMapper.findByNameAndDate(userName,startTime,endTime);
    }

    @Override
    public int deleteManagerById(String userId) {
        return managerMapper.deleteManager(userId);
    }

    @Override
    public User getUserById(String userId) {
        return managerMapper.findById(userId);
    }

    @Override
    public int updateInfoById(String userId, String realName, String email, Date time) {
        return managerMapper.updateById(userId,realName,email,time);
    }

    @Override
    public int updateUserById(String userId, String realName, String email, String studentNumber, String major, String grade,Date updateTime) {
        return managerMapper.updateUserById(userId, realName, email, studentNumber, major, grade, updateTime);
    }


}
