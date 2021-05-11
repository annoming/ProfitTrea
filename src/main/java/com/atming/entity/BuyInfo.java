package com.atming.entity;

import java.util.ArrayList;

/**
 * @author annoming
 * @date 2021/4/10 8:46 下午
 */

public class BuyInfo {

    /**股票代码*/
    private String stockCode;

    /**股票代码*/
    private String stockName;

    /**委托类型*/
    private String entrustType;

    /**买入价格*/
    private Double buyPrice;

    /**可用资金*/
    private Double usefulFund;

    /**买入数量*/
    private Integer buyNumber;

    private Integer maxBuyNumber;

    /**组织编号*/
    private String organizationId;

    /**用户编号*/
    private String userId;

    private String operation;

    private ArrayList revoke;

    public ArrayList getRevoke() {
        return revoke;
    }

    public void setRevoke(ArrayList revoke) {
        this.revoke = revoke;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getEntrustType() {
        return entrustType;
    }

    public void setEntrustType(String entrustType) {
        this.entrustType = entrustType;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Double getUsefulFund() {
        return usefulFund;
    }

    public void setUsefulFund(Double usefulFund) {
        this.usefulFund = usefulFund;
    }

    public Integer getBuyNumber() {
        return buyNumber;
    }

    public void setBuyNumber(Integer buyNumber) {
        this.buyNumber = buyNumber;
    }

    public Integer getMaxBuyNumber() {
        return maxBuyNumber;
    }

    public void setMaxBuyNumber(Integer maxBuyNumber) {
        this.maxBuyNumber = maxBuyNumber;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
