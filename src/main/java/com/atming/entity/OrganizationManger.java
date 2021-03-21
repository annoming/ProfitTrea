package com.atming.entity;

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

    /**组织性质：101 学校 102 企业 103 个人*/
    private int organizeType;

    /**用户名*/
    private String userName;

    /**密码*/
    private String password;

    /**密码盐*/
    private String salt;

    /**头像路径*/
    private String avatar;

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;

    /**账户状态*/
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

    public int getOrganizeType() {
        return organizeType;
    }

    public void setOrganizeType(int organizeType) {
        this.organizeType = organizeType;
    }

    @Override
    public String toString() {
        return "OrganizationManger{" +
                "organizeId='" + organizeId + '\'' +
                ", organizeName='" + organizeName + '\'' +
                ", organizeEmail='" + organizeEmail + '\'' +
                ", locationAddress='" + locationAddress + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", organizeType='" + organizeType + '\'' +
                '}';
    }
}
