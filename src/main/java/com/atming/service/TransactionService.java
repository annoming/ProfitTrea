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

    FiveOrder findFiveOrder(String stockCode);

    List<RevokeList> findUserRevoke(String userId);

    List<TodayEntrust> findUserEntrust(String userId,String date);

    List<TodayEntrust> findStockEntrust(String stockCode);

    List<Fund> findUserFund(String userId);

    Fund findFundByStock(String stockCode);

    List<TodayTransaction> findUserTodayTransaction(String userId, String date);

    List<TodayTransaction> findUserTransaction(String userId);

    Fund findFundByIdAndStock(String userId, String stockCode);

    int insertRevokeToList(RevokeList revokeList);

    int insertEntrust(TodayEntrust todayEntrust);

    int updateEntrust(TodayEntrust todayEntrust);

    int insertTransaction(TodayTransaction todayTransaction);

    int insertStockFund(Fund fund);

    int updateStockFund(Fund fund);

    int updateShare(Share share);

    int deleteRevokeById(String id);
}
