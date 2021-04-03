package com.atming.entity;

/**
 * @author annoming
 * @date 2021/4/3 6:09 下午
 */

public class News {

    /**新闻编号*/
    private Integer id;

    /**新闻标题*/
    private String newsTitle;

    /**新闻内容*/
    private String newsContent;

    /**新闻地址*/
    private String newsUrl;

    /**图片地址*/
    private String imgUrl;

    /**新闻日期*/
    private String newsDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", newsTitle='" + newsTitle + '\'' +
                ", newsContent='" + newsContent + '\'' +
                ", newsUrl='" + newsUrl + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", newsDate='" + newsDate + '\'' +
                '}';
    }
}
