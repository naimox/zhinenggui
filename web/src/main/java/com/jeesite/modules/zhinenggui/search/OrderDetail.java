package com.jeesite.modules.zhinenggui.search;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
public class OrderDetail {

    private String orderId;
    private String deviceRemark;
    private String plateId;		// 板/柜ID
    private String doorId;		// 单柜门ID
    private String totalDoorId;		// 总柜门ID
    private String courierSendTime;		// 寄件时间
    private String getterPhone;		// 所属人
    private String state;		// 状态

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDeviceRemark() {
        return deviceRemark;
    }

    public void setDeviceRemark(String deviceRemark) {
        this.deviceRemark = deviceRemark;
    }

    public String getPlateId() {
        return plateId;
    }

    public void setPlateId(String plateId) {
        this.plateId = plateId;
    }

    public String getDoorId() {
        return doorId;
    }

    public void setDoorId(String doorId) {
        this.doorId = doorId;
    }

    public String getTotalDoorId() {
        return totalDoorId;
    }

    public void setTotalDoorId(String totalDoorId) {
        this.totalDoorId = totalDoorId;
    }

    public String getCourierSendTime() {
        return courierSendTime;
    }

    public void setCourierSendTime(String courierSendTime) {
        this.courierSendTime = courierSendTime;
    }

    public String getGetterPhone() {
        return getterPhone;
    }

    public void setGetterPhone(String getterPhone) {
        this.getterPhone = getterPhone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
