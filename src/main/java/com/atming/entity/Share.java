package com.atming.entity;

/**
 * @author annoming
 * @date 2021/4/9 4:49 下午
 */

public class Share {

    /**资产编号*/
    private Integer id;

    /**用户编号*/
    private String userId;

    /**可用余额*/
    private Double availableBalance;

    /**资金余额*/
    private Double fundBalance;

    /**冻结余额*/
    private Double freezeBalance;

    /**总资产*/
    private Double totalFund;

    /**总盈亏*/
    private Double totalProfit;

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

    public Double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public Double getFundBalance() {
        return fundBalance;
    }

    public void setFundBalance(Double fundBalance) {
        this.fundBalance = fundBalance;
    }

    public Double getFreezeBalance() {
        return freezeBalance;
    }

    public void setFreezeBalance(Double freezeBalance) {
        this.freezeBalance = freezeBalance;
    }

    public Double getTotalFund() {
        return totalFund;
    }

    public void setTotalFund(Double totalFund) {
        this.totalFund = totalFund;
    }

    public Double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(Double totalProfit) {
        this.totalProfit = totalProfit;
    }

    @Override
    public String toString() {
        return "Share{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", availableBalance=" + availableBalance +
                ", fundBalance=" + fundBalance +
                ", freezeBalance=" + freezeBalance +
                ", totalFund=" + totalFund +
                ", totalProfit=" + totalProfit +
                '}';
    }
}
