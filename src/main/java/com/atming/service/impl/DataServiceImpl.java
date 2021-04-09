package com.atming.service.impl;

import com.atming.entity.Stock;
import com.atming.mapper.DataMapper;
import com.atming.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * @author annoming
 * @date 2021/4/8 3:44 下午
 */

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private DataMapper dataMapper;

    @Override
    public List<Stock> findStock(String stockType) {
        return dataMapper.getStockData(stockType);
    }

}