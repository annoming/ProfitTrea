package com.atming.entity;

/**
 * @author annoming
 * @date 2021/4/9 10:48 上午
 */

public class TodayEntrust {

    /**合同编号*/
    private String entrustId;

    /**委托数量*/
    private Integer entrustNumber;

    /**委托价格*/
    private Double entrustPrice;

    /**成交价格*/
    private Double transactionPrice;

    /**成交数量*/
    private Integer transactionNumber;

    /**委托日期*/
    private String entrustDate;

    /**委托时间*/
    private String entrustTime;

    /**报价方式     限价  市价*/
    private String entrustType;

    /**备注   全部成交    部分成交  未成交*/
    private String description;

    /**操作   买入  卖出*/
    private String operation;

    /**证券代码*/
    private String stockCode;

    /**证券名称*/
    private String stockName;

    private String userId;

    public String getEntrustId() {
        return entrustId;
    }

    public void setEntrustId(String entrustId) {
        this.entrustId = entrustId;
    }

    public Integer getEntrustNumber() {
        return entrustNumber;
    }

    public void setEntrustNumber(Integer entrustNumber) {
        this.entrustNumber = entrustNumber;
    }

    public Integer getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(Integer transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public Double getEntrustPrice() {
        return entrustPrice;
    }

    public void setEntrustPrice(Double entrustPrice) {
        this.entrustPrice = entrustPrice;
    }

    public String getEntrustDate() {
        return entrustDate;
    }

    public void setEntrustDate(String entrustDate) {
        this.entrustDate = entrustDate;
    }

    public String getEntrustTime() {
        return entrustTime;
    }

    public void setEntrustTime(String entrustTime) {
        this.entrustTime = entrustTime;
    }

    public String getEntrustType() {
        return entrustType;
    }

    public void setEntrustType(String entrustType) {
        this.entrustType = entrustType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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

    public Double getTransactionPrice() {
        return transactionPrice;
    }

    public void setTransactionPrice(Double transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    @Override
    public String toString() {
        return "TodayEntrust{" +
                "entrustId='" + entrustId + '\'' +
                ", entrustNumber=" + entrustNumber +
                ", transactionNumber=" + transactionNumber +
                ", entrustPrice=" + entrustPrice +
                ", entrustDate='" + entrustDate + '\'' +
                ", entrustTime='" + entrustTime + '\'' +
                ", entrustType='" + entrustType + '\'' +
                ", description='" + description + '\'' +
                ", operation='" + operation + '\'' +
                ", stockCode='" + stockCode + '\'' +
                ", stockName='" + stockName + '\'' +
                '}';
    }
}
