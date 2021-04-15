package com.atming.entity;

/**
 * @author annoming
 * @date 2021/4/9 1:38 下午
 */

public class RevokeList {

    /**合同编号*/
    private String id;

    /**用户编号*/
    private String userId;

    /**证券代码*/
    private String stockCode;

    /**证券名称*/
    private String stockName;

    /**委托价格*/
    private Double entrustPrice;

    /**委托数量*/
    private Integer entrustNumber;

    /**成交均价*/
    private Double transactionPrice;

    /**成交数量*/
    private Integer transactionNumber;

    /**委托日期*/
    private String date;

    /**操作   买入   卖出*/
    private String operation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Double getEntrustPrice() {
        return entrustPrice;
    }

    public void setEntrustPrice(Double entrustPrice) {
        this.entrustPrice = entrustPrice;
    }

    public Integer getEntrustNumber() {
        return entrustNumber;
    }

    public void setEntrustNumber(Integer entrustNumber) {
        this.entrustNumber = entrustNumber;
    }

    public Double getTransactionPrice() {
        return transactionPrice;
    }

    public void setTransactionPrice(Double transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    public Integer getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(Integer transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "RevokeList{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", stockCode='" + stockCode + '\'' +
                ", stockName='" + stockName + '\'' +
                ", entrustPrice=" + entrustPrice +
                ", entrustNumber=" + entrustNumber +
                ", transactionPrice=" + transactionPrice +
                ", transactionNumber=" + transactionNumber +
                ", date='" + date + '\'' +
                ", operation='" + operation + '\'' +
                '}';
    }
}

