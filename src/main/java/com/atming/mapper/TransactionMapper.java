package com.atming.mapper;

import com.atming.entity.*;

import java.util.List;

/**
 * @author annoming
 * @date 2021/4/9 9:32 上午
 */

public interface TransactionMapper {

    List selectStock();

    List selectBuyStock(String stockCode);

    Share selectShareById(String userId);

    Market selectMarketInfo(String organizeId);

    Fund selectFundByStock(String stockCode);

    Fund selectFundByIdAndStock(String userId, String stockCode);

    FiveOrder selectFiveOrder(String stockCode);

    int insertRevoke(RevokeList revokeList);

    int insertTodayEntrust(TodayEntrust entrust);

    int updateTodayEntrust(TodayEntrust entrust);

    int insertTodayTransaction(TodayTransaction todayTransaction);

    int insertTodayFund(Fund fund);

    int updateTodayFund(Fund fund);

    int deleteRevokeById(String id);

    int updateShareById(Share share);

    List selectRevokeByUserId(String userId);

    List selectEntrustByUserId(String userId,String entrustDate);

    List selectEntrustByStockCode(String stockCode);

    List selectFundByUserId(String userId);

    List selectTodayTransaction(String userId,String date);

    List selectTransaction(String userId);

}
