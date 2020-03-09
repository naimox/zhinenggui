package com.jeesite.modules.zhinenggui.search;

import java.util.Date;
public class OrderSearch  {
	private String orderId;
    private String addr;
    private String orderType;
    private String expressNumber;
    private String courierSendName;
    private String courierPhone;
    private String sendTime;
    private String getterPhone;
    private String getterTime;
    private Double sendTotalMoney;
    private String state;
    private String code;
    private String status;
    private String totalDoorId;
    private String deviceId;
    private String deviceRemark;
    private String userCode;
    private Date createDate;

    
    



	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getCourierSendName() {
        return courierSendName;
    }

    public void setCourierSendName(String courierSendName) {
        this.courierSendName = courierSendName;
    }

    public String getCourierPhone() {
        return courierPhone;
    }

    public void setCourierPhone(String courierPhone) {
        this.courierPhone = courierPhone;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getGetterPhone() {
        return getterPhone;
    }

    public void setGetterPhone(String getterPhone) {
        this.getterPhone = getterPhone;
    }

    public String getGetterTime() {
        return getterTime;
    }

    public void setGetterTime(String getterTime) {
        this.getterTime = getterTime;
    }

    public Double getSendTotalMoney() {
        return sendTotalMoney;
    }

    public void setSendTotalMoney(Double sendTotalMoney) {
        this.sendTotalMoney = sendTotalMoney;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalDoorId() {
        return totalDoorId;
    }

    public void setTotalDoorId(String totalDoorId) {
        this.totalDoorId = totalDoorId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

	public String getDeviceRemark() {
		return deviceRemark;
	}

	public void setDeviceRemark(String deviceRemark) {
		this.deviceRemark = deviceRemark;
	}
    
}
