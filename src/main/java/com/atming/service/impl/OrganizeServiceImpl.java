package com.atming.service.impl;

import com.atming.entity.OrganizationManger;
import com.atming.mapper.OrganizeMapper;
import com.atming.service.OrganizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author annoming
 * @date 2021/3/24 8:46 下午
 */

@Service
public class OrganizeServiceImpl implements OrganizeService {

    @Autowired
    private OrganizeMapper organizeMapper;

    @Override
    public OrganizationManger isExists(OrganizationManger organize) {
        String organizeEmail = organize.getEmail();
        return organizeMapper.selectByEmail(organizeEmail);
    }

    @Override
    public OrganizationManger findOrganizeById(String organizationId) {
        return organizeMapper.selectByID(organizationId);
    }

    @Override
    public List<OrganizationManger> getAll() {
        return organizeMapper.selectAll();
    }

    @Override
    public List<OrganizationManger> getOrganizeByName(String organizeName) {
        return organizeMapper.selectOrganizeByName(organizeName);
    }

    @Override
    public List<OrganizationManger> getSelectOrganizeName(String organizeName) {
        return organizeMapper.selectOneOrganize(organizeName);
    }

    @Override
    public List<OrganizationManger> getByNameAndDate(String organizeName, Date startTime, Date endTime) {
        return organizeMapper.selectOrganizeByDate(organizeName,startTime,endTime);
    }

    @Override
    public List<OrganizationManger> getByDate(Date startTime, Date endTime) {
        return organizeMapper.selectByDate(startTime, endTime);
    }

    @Override
    public int addOneOrganize(OrganizationManger organize) {
        return organizeMapper.insertOrganize(organize);
    }

    @Override
    public int deleteById(String organizeId) {
        return organizeMapper.deleteOne(organizeId);
    }

    @Override
    public int deleteUserByOrganizeId(String organizeId) {
        return organizeMapper.deleteUser(organizeId);
    }

    @Override
    public int updateOrganizeById(String organizeName, String email, String address, String type,Date updateTime,String organizeId) {
        return organizeMapper.updateOrganizeById(organizeName, email, address, type, updateTime, organizeId);
    }

    @Override
    public String getOrganizeId() {
        return organizeMapper.selectId();
    }

    @Override
    public List selectCityList() {
        return organizeMapper.getCityList();
    }

    @Override
    public List selectCountryByName(String cityName) {
        return organizeMapper.getCountryList(cityName);
    }
}
