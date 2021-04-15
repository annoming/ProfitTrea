package com.atming.service;

import com.atming.entity.Announce;
import com.atming.entity.News;

import java.util.List;

/**
 * @author annoming
 * @date 2021/4/8 5:23 下午
 */

public interface QuotationService {

    List<News> selectAllNews();

    List<Announce> selectAllAnnounce();
}
