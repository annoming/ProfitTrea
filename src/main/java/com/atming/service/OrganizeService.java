package com.atming.service;

import com.atming.entity.OrganizationManger;

import java.util.List;

/**
 * @author annoming
 * @date 2021/3/24 8:44 下午
 */

public interface OrganizeService {

    /***
     * 判断组织是否存在
     * @param organize
     * @return
     */
    OrganizationManger isExists(OrganizationManger organize);

    OrganizationManger findOrganizeById(String organizationId);

    /**
     * 查询组织信息
     * @return
     */
    List<OrganizationManger> getAll();
    /***
     * 添加组织
     * @param organize
     * @return
     */
    int addOneOrganize(OrganizationManger organize);

    int deleteById(String organizeId);

    int deleteUserByOrganizeId(String organizeId);
    /***
     * 获取组织id
     * @return
     */
    String getOrganizeId();

    List selectCityList();

    List selectCountryByName(String cityName);
}
