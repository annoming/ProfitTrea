package com.atming.entity;

/**
 * @author annoming
 * @date 2021/4/9 10:39 上午
 */

public class TodayTransaction {

    /**成交编号*/
    private String transactionId;

    /**成交数量*/
    private Integer transactionNumber;

    /**成交价格*/
    private Double transactionPrice;

    /**成交金额*/
    private Double transactionAmount;

    /**成交时间*/
    private String transactionDate;

    /**操作   买入  卖出*/
    private String operation;

    /**撤单数量*/
    private Integer revokeNumber;

    /**证券代码*/
    private String stockCode;

    /**证券名称*/
    private String stockName;

    /**用户编号*/
    private String userId;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(Integer transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public Double getTransactionPrice() {
        return transactionPrice;
    }

    public void setTransactionPrice(Double transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getRevokeNumber() {
        return revokeNumber;
    }

    public void setRevokeNumber(Integer revokeNumber) {
        this.revokeNumber = revokeNumber;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
