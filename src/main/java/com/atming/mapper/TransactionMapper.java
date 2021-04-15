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

    int insertRevoke(RevokeList revokeList);

    int insertTodayEntrust(TodayEntrust entrust);

    int insertTodayTransaction(TodayTransaction todayTransaction);

    int insertTodayFund(Fund fund);

    List selectRevokeByUserId(String userId);

    List selectEntrustByUserId(String userId);

    List selectFundByUserId(String userId);

    List selectTodayTransaction(String userId,String date);

    List selectTransaction(String userId);

}
