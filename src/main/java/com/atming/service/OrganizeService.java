package com.atming.service;

import com.atming.entity.OrganizationManger;

import java.util.Date;
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

    List<OrganizationManger> getOrganizeByName(String organizeName);

    List<OrganizationManger> getSelectOrganizeName(String organizeName);

    List<OrganizationManger> getByNameAndDate(String organizeName, Date startTime, Date endTime);

    List<OrganizationManger> getByDate(Date startTime, Date endTime);
    /***
     * 添加组织
     * @param organize
     * @return
     */
    int addOneOrganize(OrganizationManger organize);

    int deleteById(String organizeId);

    int deleteUserByOrganizeId(String organizeId);

    int updateOrganizeById(String organizeName, String email, String address, String type,Date updateTime,String organizeId);
    /***
     * 获取组织id
     * @return
     */
    String getOrganizeId();

    List selectCityList();

    List selectCountryByName(String cityName);
}
