package com.atming.mapper;

import com.atming.entity.Permission;

/**
 * @author annoming
 * @date 2021/4/7 4:47 下午
 */

public interface PermissionMapper {

    Permission selectByPermissionId(Integer permissionId);
}
