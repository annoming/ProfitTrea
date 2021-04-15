package com.atming.service.impl;

import com.atming.entity.Permission;
import com.atming.mapper.PermissionMapper;
import com.atming.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author annoming
 * @date 2021/4/7 4:47 下午
 */

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Permission selectOneByPermissionId(Integer permissionId) {
        return permissionMapper.selectByPermissionId(permissionId);
    }
}
