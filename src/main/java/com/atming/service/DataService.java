package com.atming.service;

import com.atming.entity.Stock;

import java.util.List;

/**
 * @author annoming
 * @date 2021/4/8 3:43 下午
 */

public interface DataService {

    List<Stock> findStock(String stockType);

}
