package com.atming.entity;

import java.util.Date;

/**
 * @author annoming
 * @date 2021/4/10 9:20 下午
 */

public class Market {

    private Integer id;

    private String organizeId;

    /**上午开盘时间*/
    private String morningStart;

    /**上午收盘时间*/
    private String morningEnd;

    /**下午开盘时间*/
    private String afternoonStart;

    /**下午收盘时间*/
    private String afternoonEnd;

    /**交易手续费*/
    private Double commission;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrganizeId() {
        return organizeId;
    }

    public void setOrganizeId(String organizeId) {
        this.organizeId = organizeId;
    }

    public String getMorningStart() {
        return morningStart;
    }

    public void setMorningStart(String morningStart) {
        this.morningStart = morningStart;
    }

    public String getMorningEnd() {
        return morningEnd;
    }

    public void setMorningEnd(String morningEnd) {
        this.morningEnd = morningEnd;
    }

    public String getAfternoonStart() {
        return afternoonStart;
    }

    public void setAfternoonStart(String afternoonStart) {
        this.afternoonStart = afternoonStart;
    }

    public String getAfternoonEnd() {
        return afternoonEnd;
    }

    public void setAfternoonEnd(String afternoonEnd) {
        this.afternoonEnd = afternoonEnd;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    @Override
    public String toString() {
        return "Market{" +
                "id=" + id +
                ", organizeId='" + organizeId + '\'' +
                ", morningStart='" + morningStart + '\'' +
                ", morningEnd='" + morningEnd + '\'' +
                ", afternoonStart='" + afternoonStart + '\'' +
                ", afternoonEnd='" + afternoonEnd + '\'' +
                ", commission=" + commission +
                '}';
    }
}
