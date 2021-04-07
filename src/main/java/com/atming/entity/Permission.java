package com.atming.entity;

import java.util.Date;

/**
 * @author annoming
 * @date 2021/4/7 4:29 下午
 */

public class Permission {

    /**权限编号*/
    private Integer permissionId;

    /**权限名*/
    private String permissionName;

    /**权限地址*/
    private String permissionUrl;

    /**权限允许访问*/
    private Integer permissionStatus;

    /**创建时间*/
    private Date createTime;

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionUrl() {
        return permissionUrl;
    }

    public void setPermissionUrl(String permissionUrl) {
        this.permissionUrl = permissionUrl;
    }

    public Integer getPermissionStatus() {
        return permissionStatus;
    }

    public void setPermissionStatus(Integer permissionStatus) {
        this.permissionStatus = permissionStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "permissionId=" + permissionId +
                ", permissionName='" + permissionName + '\'' +
                ", permissionUrl='" + permissionUrl + '\'' +
                ", permissionStatus=" + permissionStatus +
                ", createTime=" + createTime +
                '}';
    }
}
