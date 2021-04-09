package com.atming.entity;

/**
 * @author annoming
 * @date 2021/4/7 4:58 下午
 */

public class Role {

    /**角色编号*/
    private Integer roleId;

    /**角色名字*/
    private String roleName;

    /**角色描述*/
    private String description;

    /**权限类型*/
    private Integer permissionType;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(Integer permissionType) {
        this.permissionType = permissionType;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", description='" + description + '\'' +
                ", permissionType=" + permissionType +
                '}';
    }
}
