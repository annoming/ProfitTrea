package com.atming.service.impl;

import com.atming.entity.Role;
import com.atming.mapper.RoleMapper;
import com.atming.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author annoming
 * @date 2021/4/7 5:03 下午
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role findById(Integer roleId) {
        return roleMapper.selectRoleById(roleId);
    }
}
