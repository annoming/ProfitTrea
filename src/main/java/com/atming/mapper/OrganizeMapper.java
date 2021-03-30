package com.atming.mapper;

import com.atming.entity.OrganizationManger;

import java.util.List;

/**
 * @author annoming
 * @date 2021/3/24 8:47 下午
 */

public interface OrganizeMapper {

    /**
     * 根据邮箱查询组织
     * @param email  邮箱
     * @return  OrganizationManger
     */
    OrganizationManger selectByEmail(String email);

    List selectAll();
    /**
     * 插入组织信息
     * @param organize
     * @return
     */
    int insertOrganize(OrganizationManger organize);

    /***
     * 查询组织id
     * @return
     */
    String selectId();

    int deleteOne(String organizeId);
}
