package com.atming.service.impl;

import com.atming.entity.User;
import com.atming.mapper.ManagerMapper;
import com.atming.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public int deleteManagerById(String userId) {
        return managerMapper.deleteManager(userId);
    }

    @Override
    public User getUserById(String userId) {
        return managerMapper.findById(userId);
    }


}
