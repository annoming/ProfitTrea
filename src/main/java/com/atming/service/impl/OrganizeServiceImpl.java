package com.atming.service.impl;

import com.atming.entity.OrganizationManger;
import com.atming.mapper.OrganizeMapper;
import com.atming.service.OrganizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<OrganizationManger> getAll() {
        return organizeMapper.selectAll();
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
    public String getOrganizeId() {
        return organizeMapper.selectId();
    }
}
