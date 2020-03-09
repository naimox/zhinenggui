package com.jeesite.modules.zhinenggui.search;

import com.jeesite.common.lang.StringUtils;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
public class CabinetsAndOrder{

    private String id;
    private String orderId;
    private String SenderTime;      // 寄存时间
    private String courierSendTime;
    private String sendTotalMoney;
    private String deviceId;		// 设备ID
    private String plateId;		// 板/柜ID
    private String doorId;		// 单柜门ID
    private String totalDoorId;		// 总柜门ID
    private String byCompany;		// 所属公司
    private String byUser;		// 所属人
    private String state;		// 状态
    private String deviceType;		// 设备类型
    private String doorSize;		// 门大小型号
    private String type;
    private String money;
    private String overTime;
    private String overType;
    private String overMoney;
    private String overUnit;
    private String money1;  // 初始金额
    private String overTime1;   // 初始时间
    private String overType1;       //计时类型
    private String overMoney1;      // 超时时间
    private String overUnit1;       // 超时金额
    private String codeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCourierSendTime() {
        return courierSendTime;
    }

    public void setCourierSendTime(String courierSendTime) {
        this.courierSendTime = courierSendTime;
    }

    public String getSendTotalMoney() {
        return sendTotalMoney;
    }

    public void setSendTotalMoney(String sendTotalMoney) {
        this.sendTotalMoney = sendTotalMoney;
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

    public String getByCompany() {
        return byCompany;
    }

    public void setByCompany(String byCompany) {
        this.byCompany = byCompany;
    }

    public String getByUser() {
        return byUser;
    }

    public void setByUser(String byUser) {
        this.byUser = byUser;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDoorSize() {
        return doorSize;
    }

    public void setDoorSize(String doorSize) {
        this.doorSize = doorSize;
    }

    public String getMoney() {
        return money==null?"0":money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getOverTime() {
        return overTime==null?"0":overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

    public String getOverType() {
        return overType==null?"0":overType;
    }

    public void setOverType(String overType) {
        this.overType = overType;
    }

    public String getOverMoney() {
        return overMoney==null?"0":overMoney;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoney1() {
        return money1;
    }

    public void setMoney1(String money1) {
        this.money1 = money1;
    }

    public String getOverTime1() {
        return overTime1;
    }

    public void setOverTime1(String overTime1) {
        this.overTime1 = overTime1;
    }

    public String getOverType1() {
        return overType1;
    }

    public void setOverType1(String overType1) {
        this.overType1 = overType1;
    }

    public String getOverMoney1() {
        return overMoney1;
    }

    public void setOverMoney1(String overMoney1) {
        this.overMoney1 = overMoney1;
    }

    public String getOverUnit1() {
        return overUnit1;
    }

    public void setOverUnit1(String overUnit1) {
        this.overUnit1 = overUnit1;
    }

    public String getSenderTime() {
        return SenderTime;
    }

    public void setSenderTime(String senderTime) {
        SenderTime = senderTime;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }
}
