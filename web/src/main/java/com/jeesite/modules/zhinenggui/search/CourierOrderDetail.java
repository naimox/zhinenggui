package com.jeesite.modules.zhinenggui.search;

import java.util.Date;

public class CourierOrderDetail {
    private String id;
    private String senderPhone;
    private String getterPhone;
    private String courierPhone;
    private Date updateDate;
    private String expressNumber;
    private String totalDoorId;
    private String doorSize;
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getGetterPhone() {
        return getterPhone;
    }

    public void setGetterPhone(String getterPhone) {
        this.getterPhone = getterPhone;
    }

    public String getCourierPhone() {
        return courierPhone;
    }

    public void setCourierPhone(String courierPhone) {
        this.courierPhone = courierPhone;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getTotalDoorId() {
        return totalDoorId;
    }

    public void setTotalDoorId(String totalDoorId) {
        this.totalDoorId = totalDoorId;
    }

    public String getDoorSize() {
        return doorSize;
    }

    public void setDoorSize(String doorSize) {
        this.doorSize = doorSize;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "CourierOrderDetail{" +
                "id='" + id + '\'' +
                ", senderPhone='" + senderPhone + '\'' +
                ", getterPhone='" + getterPhone + '\'' +
                ", updateDate=" + updateDate +
                ", expressNumber='" + expressNumber + '\'' +
                ", totalDoorId='" + totalDoorId + '\'' +
                ", doorSize='" + doorSize + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
