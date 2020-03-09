package com.jeesite.modules.zhinenggui.search;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
public class DoorPriceSearch {

    private String id;
    private String deviceId;		// 设备ID
    private String plateId;		// 板/柜ID
    private String doorId;		// 单柜门ID
    private String totalDoorId;		// 总柜门ID
    private String state;		// 状态
    private String doorSize;		// 门大小型号
    private String money="0";	//默认值
    private String overTime="0"; //默认值
    private String overMoney="0"; //默认值
    private String overUnit="0"; //默认值
    private String deviceRemark; //柜子地址

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDoorSize() {
        return doorSize;
    }

    public void setDoorSize(String doorSize) {
        this.doorSize = doorSize;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

    public String getOverMoney() {
        return overMoney;
    }

    public void setOverMoney(String overMoney) {
        this.overMoney = overMoney;
    }

    public String getOverUnit() {
        return overUnit;
    }

    public void setOverUnit(String overUnit) {
        this.overUnit = overUnit;
    }

    public String getDeviceRemark() {
        return deviceRemark;
    }

    public void setDeviceRemark(String deviceRemark) {
        this.deviceRemark = deviceRemark;
    }
}
