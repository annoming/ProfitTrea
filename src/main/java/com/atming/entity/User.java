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
public class User {
    /**用户编号*/
    private String userId;

    /**用户名*/
    private String userName;

    /**密码*/
    private String password;

    /**密码盐*/
    private String salt;

    /**真实姓名*/
    private String realName;

    /**组织编号*/
    private String organization;

    /**学号*/
    private String studentNumber;

    /**专业*/
    private String major;

    /**班级*/
    private String grade;

    /**头像路径*/
    private String avatar;

    /**邮箱*/
    private String email;

    /**用户状态*/
    private Integer status;
    private Date createTime;
    private Date updateTime;

    /**角色类型 100 超级管理员    101  组织管理员   102   个体管理员     103  平台管理员   104  普通管理员*/
    private int roleType;

    public  User(){

    }

    public User(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", realName='" + realName + '\'' +
                ", organization='" + organization + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", major='" + major + '\'' +
                ", grade='" + grade + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", roleType=" + roleType +
                '}';
    }
}
