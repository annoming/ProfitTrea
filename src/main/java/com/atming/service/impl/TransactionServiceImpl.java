package com.atming.service.impl;

import com.atming.entity.*;
import com.atming.mapper.TransactionMapper;
import com.atming.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author annoming
 * @date 2021/4/9 9:32 上午
 */

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public List<Stock> findStock() {
        return transactionMapper.selectStock();
    }

    @Override
    public List<Stock> findBuyStock(String stockCode) {
        return transactionMapper.selectBuyStock(stockCode);
    }

    @Override
    public Share findOneShare(String userId) {
        return transactionMapper.selectShareById(userId);
    }

    @Override
    public Market findMarket(String organizeId) {
        return transactionMapper.selectMarketInfo(organizeId);
    }

    @Override
    public FiveOrder findFiveOrder(String stockCode) {
        return transactionMapper.selectFiveOrder(stockCode);
    }

    @Override
    public int insertRevokeToList(RevokeList revokeList) {
        return transactionMapper.insertRevoke(revokeList);
    }

    @Override
    public List<RevokeList> findUserRevoke(String userId) {
        return transactionMapper.selectRevokeByUserId(userId);
    }

    @Override
    public List<TodayEntrust> findUserEntrust(String userId,String date) {
        return transactionMapper.selectEntrustByUserId(userId,date);
    }

    @Override
    public List<TodayEntrust> findStockEntrust(String stockCode) {
        return transactionMapper.selectEntrustByStockCode(stockCode);
    }

    @Override
    public List<Fund> findUserFund(String userId) {
        return transactionMapper.selectFundByUserId(userId);
    }

    @Override
    public Fund findFundByStock(String stockCode) {
        return transactionMapper.selectFundByStock(stockCode);
    }

    @Override
    public List<TodayTransaction> findUserTodayTransaction(String userId, String date) {
        return transactionMapper.selectTodayTransaction(userId,date);
    }

    @Override
    public List<TodayTransaction> findUserTransaction(String userId) {
        return transactionMapper.selectTransaction(userId);
    }

    @Override
    public Fund findFundByIdAndStock(String userId, String stockCode) {
        return transactionMapper.selectFundByIdAndStock(userId,stockCode);
    }

    @Override
    public int insertEntrust(TodayEntrust todayEntrust) {
        return transactionMapper.insertTodayEntrust(todayEntrust);
    }

    @Override
    public int updateEntrust(TodayEntrust todayEntrust) {
        return transactionMapper.updateTodayEntrust(todayEntrust);
    }

    @Override
    public int insertTransaction(TodayTransaction todayTransaction) {
        return transactionMapper.insertTodayTransaction(todayTransaction);
    }

    @Override
    public int insertStockFund(Fund fund) {
        return transactionMapper.insertTodayFund(fund);
    }

    @Override
    public int updateStockFund(Fund fund) {
        return transactionMapper.updateTodayFund(fund);
    }

    @Override
    public int updateShare(Share share) {
        return transactionMapper.updateShareById(share);
    }

    @Override
    public int deleteRevokeById(String id) {
        return transactionMapper.deleteRevokeById(id);
    }

    @Override
    public int deleteByTodayEntrust(String stockCode, String date) {
        return transactionMapper.deleteTodayEntrustByCode(stockCode,date);
    }

}
