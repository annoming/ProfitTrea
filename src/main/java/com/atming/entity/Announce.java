package com.atming.entity;

/**
 * @author annoming
 * @date 2021/4/3 10:18 下午
 */

public class Announce {

    /**公告编号*/
    private Integer id;

    /**新三板公告代码*/
    private String announceCode;

    /**新三板股票代码*/
    private String announceName;

    /**公告标题*/
    private String announceTitle;

    /**pdf的路径*/
    private String pdfUrl;

    /**日期*/
    private String date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnnounceCode() {
        return announceCode;
    }

    public void setAnnounceCode(String announceCode) {
        this.announceCode = announceCode;
    }

    public String getAnnounceName() {
        return announceName;
    }

    public void setAnnounceName(String announceName) {
        this.announceName = announceName;
    }

    public String getAnnounceTitle() {
        return announceTitle;
    }

    public void setAnnounceTitle(String announceTitle) {
        this.announceTitle = announceTitle;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Announce{" +
                "id=" + id +
                ", announceCode='" + announceCode + '\'' +
                ", announceName='" + announceName + '\'' +
                ", announceTitle='" + announceTitle + '\'' +
                ", pdfUrl='" + pdfUrl + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
