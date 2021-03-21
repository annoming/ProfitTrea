package com.atming.entity;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: Annoming
 * @Date: 2021/1/3
 * @Time: 23:45
 * @Description
 */
public class User extends OrganizationManger{
    /**用户编号*/
    private String userId;

    /**用户真实姓名*/
    private String realName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String getOrganizeName() {
        return super.getOrganizeName();
    }

    @Override
    public void setOrganizeName(String organizeName) {
        super.setOrganizeName(organizeName);
    }

    @Override
    public String getLocationAddress() {
        return super.getLocationAddress();
    }

    @Override
    public void setLocationAddress(String locationAddress) {
        super.setLocationAddress(locationAddress);
    }

    @Override
    public String getDetailAddress() {
        return super.getDetailAddress();
    }

    @Override
    public void setDetailAddress(String detailAddress) {
        super.setDetailAddress(detailAddress);
    }

    @Override
    public String getOrganizeId() {
        return super.getOrganizeId();
    }

    @Override
    public void setOrganizeId(String organizeId) {
        super.setOrganizeId(organizeId);
    }

    @Override
    public String getOrganizeEmail() {
        return super.getOrganizeEmail();
    }

    @Override
    public void setOrganizeEmail(String organizeEmail) {
        super.setOrganizeEmail(organizeEmail);
    }

    @Override
    public int getOrganizeType() {
        return super.getOrganizeType();
    }

    @Override
    public void setOrganizeType(int organizeType) {
        super.setOrganizeType(organizeType);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", realName='" + realName + '\'' +
                '}';
    }
}
