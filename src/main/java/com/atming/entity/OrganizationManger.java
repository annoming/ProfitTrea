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
    private String organizeId;

    /**组织名称*/
    private String organizeName;

    /**组织邮箱*/
    private String organizeEmail;

    /**所在地区*/
    private String locationAddress;

    /**详细地址*/
    private String detailAddress;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;

    /**组织状态 100 审核通过 103 待审核 104审核不通过 110禁用*/
    private Integer status;


    public String getOrganizeName() {
        return organizeName;
    }

    public void setOrganizeName(String organizeName) {
        this.organizeName = organizeName;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getOrganizeId() {
        return organizeId;
    }

    public void setOrganizeId(String organizeId) {
        this.organizeId = organizeId;
    }

    public String getOrganizeEmail() {
        return organizeEmail;
    }

    public void setOrganizeEmail(String organizeEmail) {
        this.organizeEmail = organizeEmail;
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
                "organizeId='" + organizeId + '\'' +
                ", organizeName='" + organizeName + '\'' +
                ", organizeEmail='" + organizeEmail + '\'' +
                ", locationAddress='" + locationAddress + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", status=" + status +
                '}';
    }
}
