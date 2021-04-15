package com.atming.entity;

/**
 * @author annoming
 * @date 2021/4/9 10:58 上午
 */

public class HistoryTransaction {

    /**合同编号*/
    private String transactionId;

    /**成交数量*/
    private Integer transactionNumber;

    /**成交价格*/
    private Double transactionPrice;

    /**成交时间*/
    private String transactionDate;

    /**操作   买入  卖出*/
    private String operation;

    /**证券代码*/
    private String stockCode;

    /**证券名称*/
    private String stockName;

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

    @Override
    public String toString() {
        return "HistoryTransaction{" +
                "transactionId='" + transactionId + '\'' +
                ", transactionNumber=" + transactionNumber +
                ", transactionPrice=" + transactionPrice +
                ", transactionDate='" + transactionDate + '\'' +
                ", operation='" + operation + '\'' +
                ", stockCode='" + stockCode + '\'' +
                ", stockName='" + stockName + '\'' +
                '}';
    }
}
