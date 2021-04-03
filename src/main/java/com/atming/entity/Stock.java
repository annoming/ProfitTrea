package com.atming.entity;

/**
 * @author annoming
 * @date 2021/3/30 5:38 下午
 */

public class Stock {

    /**证券编码*/
    private int stockId;

    /**证券代码*/
    private String stockCode;

    /**证券名称*/
    private String stockName;

    /**最新价*/
    private String newPrice;

    /**涨跌额*/
    private String priceAmount;

    /**涨跌幅*/
    private String priceLimit;

    /**成交量*/
    private String volumeNumber;

    /**成交额*/
    private String volumeAmount;

    /**昨收*/
    private String lastGet;

    /**今开*/
    private String nowSet;

    /**最高*/
    private String highest;

    /**最低*/
    private String lowest;

    /**证券类型 STOCK表示股票   FOUNDATION  表示基金    BOND表示债券  FOREX表示*/
    private String stockType;


    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
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

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }

    public String getPriceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(String priceAmount) {
        this.priceAmount = priceAmount;
    }

    public String getPriceLimit() {
        return priceLimit;
    }

    public void setPriceLimit(String priceLimit) {
        this.priceLimit = priceLimit;
    }

    public String getVolumeNumber() {
        return volumeNumber;
    }

    public void setVolumeNumber(String volumeNumber) {
        this.volumeNumber = volumeNumber;
    }

    public String getVolumeAmount() {
        return volumeAmount;
    }

    public void setVolumeAmount(String volumeAmount) {
        this.volumeAmount = volumeAmount;
    }

    public String getLastGet() {
        return lastGet;
    }

    public void setLastGet(String lastGet) {
        this.lastGet = lastGet;
    }

    public String getNowSet() {
        return nowSet;
    }

    public void setNowSet(String nowSet) {
        this.nowSet = nowSet;
    }

    public String getHighest() {
        return highest;
    }

    public void setHighest(String highest) {
        this.highest = highest;
    }

    public String getLowest() {
        return lowest;
    }

    public void setLowest(String lowest) {
        this.lowest = lowest;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stockId=" + stockId +
                ", stockCode='" + stockCode + '\'' +
                ", stockName='" + stockName + '\'' +
                ", newPrice='" + newPrice + '\'' +
                ", priceAmount='" + priceAmount + '\'' +
                ", priceLimit='" + priceLimit + '\'' +
                ", volumeNumber='" + volumeNumber + '\'' +
                ", volumeAmount='" + volumeAmount + '\'' +
                ", lastGet='" + lastGet + '\'' +
                ", nowSet='" + nowSet + '\'' +
                ", highest='" + highest + '\'' +
                ", lowest='" + lowest + '\'' +
                ", stockType='" + stockType + '\'' +
                '}';
    }
}
