package com.atming.entity;

/**
 * @author annoming
 * @date 2021/4/3 11:52 下午
 */

public class City {

    /**省/市*/
    private String cityName;

    /**市/区*/
    private String country;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "City{" +
                "cityName='" + cityName + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
