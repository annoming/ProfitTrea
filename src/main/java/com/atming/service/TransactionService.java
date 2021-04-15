package com.atming.service;

import com.atming.entity.*;
import sun.plugin2.message.MarkTaintedMessage;

import java.util.List;

/**
 * @author annoming
 * @date 2021/4/9 9:31 上午
 */

public interface TransactionService {

    List<Stock> findStock();

    List<Stock> findBuyStock(String stockCode);

    Share findOneShare(String userId);

    Market findMarket(String organizeId);

    int insertRevokeToList(RevokeList revokeList);

    List<RevokeList> findUserRevoke(String userId);

    List<TodayEntrust> findUserEntrust(String userId);

    List<Fund> findUserFund(String userId);

    List<TodayTransaction> findUserTodayTransaction(String userId, String date);

    List<TodayTransaction> findUserTransaction(String userId);

    int insertEntrust(TodayEntrust todayEntrust);

    int insertTransaction(TodayTransaction todayTransaction);

    int insertStockFund(Fund fund);
}
