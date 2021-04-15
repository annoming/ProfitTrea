package com.atming.service.impl;

import com.atming.entity.Announce;
import com.atming.entity.News;
import com.atming.mapper.QuotationMapper;
import com.atming.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author annoming
 * @date 2021/4/8 5:24 下午
 */

@Service
public class QuotationServiceImpl implements QuotationService {

    @Autowired
    private QuotationMapper quotationMapper;

    @Override
    public List<News> selectAllNews() {
        return quotationMapper.getNews();
    }

    @Override
    public List<Announce> selectAllAnnounce() {
        return quotationMapper.getAnnounce();
    }
}
