package com.cavin.culture.bean;

import java.util.Date;

public class History {

    private Integer historyId;
    private String historySubject;
    private String historyPredicate;
    private String historyObject;
    private String historyScope;
    private String historyType;
    private Date createDate;
    private String userName;

    public History() {
    }

    public History(Integer historyId, String historySubject, String historyPredicate, String historyObject, String historyScope, String historyType, Date createDate, String userName) {
        this.historyId = historyId;
        this.historySubject = historySubject;
        this.historyPredicate = historyPredicate;
        this.historyObject = historyObject;
        this.historyScope = historyScope;
        this.historyType = historyType;
        this.createDate = createDate;
        this.userName = userName;
    }

    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

    public String getHistorySubject() {
        return historySubject;
    }

    public void setHistorySubject(String historySubject) {
        this.historySubject = historySubject;
    }

    public String getHistoryPredicate() {
        return historyPredicate;
    }

    public void setHistoryPredicate(String historyPredicate) {
        this.historyPredicate = historyPredicate;
    }

    public String getHistoryObject() {
        return historyObject;
    }

    public void setHistoryObject(String historyObject) {
        this.historyObject = historyObject;
    }

    public String getHistoryScope() {
        return historyScope;
    }

    public void setHistoryScope(String historyScope) {
        this.historyScope = historyScope;
    }

    public String getHistoryType() {
        return historyType;
    }

    public void setHistoryType(String historyType) {
        this.historyType = historyType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    @Override
    public String toString() {
        return "History{" +
                "historyId=" + historyId +
                ", historySubject='" + historySubject + '\'' +
                ", historyPredicate='" + historyPredicate + '\'' +
                ", historyObject='" + historyObject + '\'' +
                ", historyScope='" + historyScope + '\'' +
                ", historyType='" + historyType + '\'' +
                ", createDate=" + createDate +
                ", userName='" + userName + '\'' +
                '}';
    }

}
