package com.atming.service;

import com.atming.entity.Permission;

/**
 * @author annoming
 * @date 2021/4/7 4:45 下午
 */

public interface PermissionService {

    Permission selectOneByPermissionId(Integer permissionId);
}
