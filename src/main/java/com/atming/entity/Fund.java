package com.atming.entity;

/**
 * @author annoming
 * @date 2021/4/9 10:23 上午
 */

public class Fund {

    /**编号*/
    private Integer id;

    /**用户id*/
    private String userId;

    /**证券代码*/
    private String stockCode;

    /**证券名称*/
    private String stockName;

    /**证券数量*/
    private Integer stockNumber;

    /**可用数量*/
    private Integer usefulNumber;

    /**冻结数量*/
    private Integer freezeNumber;

    /**成本价*/
    private Double costPrice;

    /**市价*/
    private Double marketPrice;

    /**市值 = 市价  * 证券数量*/
    private Double marketValue;

    /**浮动盈亏 = （市价 - （成本价- 手续费））*100*/
    private Double floatProfit;

    /**盈亏比例 = （市价  - 成本价）/市价*100*/
    private String profitRatio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(Integer stockNumber) {
        this.stockNumber = stockNumber;
    }

    public Integer getUsefulNumber() {
        return usefulNumber;
    }

    public void setUsefulNumber(Integer usefulNumber) {
        this.usefulNumber = usefulNumber;
    }

    public Integer getFreezeNumber() {
        return freezeNumber;
    }

    public void setFreezeNumber(Integer freezeNumber) {
        this.freezeNumber = freezeNumber;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(Double marketValue) {
        this.marketValue = marketValue;
    }

    public Double getFloatProfit() {
        return floatProfit;
    }

    public void setFloatProfit(Double floatProfit) {
        this.floatProfit = floatProfit;
    }

    public String getProfitRatio() {
        return profitRatio;
    }

    public void setProfitRatio(String profitRatio) {
        this.profitRatio = profitRatio;
    }

    @Override
    public String toString() {
        return "Fund{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", stockCode='" + stockCode + '\'' +
                ", stockName='" + stockName + '\'' +
                ", stockNumber=" + stockNumber +
                ", usefulNumber=" + usefulNumber +
                ", freezeNumber=" + freezeNumber +
                ", costPrice=" + costPrice +
                ", marketPrice=" + marketPrice +
                ", marketValue=" + marketValue +
                ", floatProfit=" + floatProfit +
                ", profitRatio='" + profitRatio + '\'' +
                '}';
    }
}
