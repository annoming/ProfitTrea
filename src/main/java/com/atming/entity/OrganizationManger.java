package com.atming.entity;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: Annoming
 * @Date: 2021/1/5
 * @Time: 8:50
 * @Description
 */
public class OrganizationManger{
    /**组织编号*/
    private String organizationId;

    /**组织名称*/
    private String organizeName;

    /**组织邮箱*/
    private String email;

    /**所在地区*/
    private String location;

    /**详细地址*/
    private String address;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;

    /**组织状态 100 审核通过 103 待审核 104审核不通过 110禁用*/
    private Integer status;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizeName() {
        return organizeName;
    }

    public void setOrganizeName(String organizeName) {
        this.organizeName = organizeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrganizationManger{" +
                "organizationId='" + organizationId + '\'' +
                ", organizeName='" + organizeName + '\'' +
                ", email='" + email + '\'' +
                ", location='" + location + '\'' +
                ", address='" + address + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", status=" + status +
                '}';
    }
}
