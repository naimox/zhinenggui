/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import java.util.Date;

/**
 * ic卡表Entity
 * @author pfzheng
 * @version 2018-12-13
 */
@Table(name="zhinenggui_card", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="card_id", attrName="cardId", label="card_id"),
		@Column(name = "card_num", attrName = "cardNum", label = "card_num"),
		@Column(name="user_id", attrName="userId", label="user_id"),
		@Column(name="device_id", attrName="deviceId", label="device_id"),
		@Column(name="door_id", attrName="doorId", label="door_id"),
		@Column(name = "type", attrName = "type", label = "type"),
		@Column(name = "end_time", attrName = "endTime", label = "end_time"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.id DESC"
)
public class ZngCard extends DataEntity<ZngCard> {
	
	private static final long serialVersionUID = 1L;
	private String cardId;		// user_id
	private String cardNum;
	private String userId;		// user_id
	private String deviceId;		// device_id
	private String doorId;		// door_id
	private String userName;
	private String phone;
	private String totalDoorId;
	private String deviceRemark;
	private String companyName;
	private String type;
	private Date endTime;
	private String cardType;

	public ZngCard() {
		this(null);
	}

	public ZngCard(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="card_id长度不能超过 255 个字符")
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	@Length(min=0, max=255, message="card_num长度不能超过 255 个字符")
	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	@Length(min=0, max=255, message="user_id长度不能超过 255 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Length(min=0, max=255, message="device_id长度不能超过 255 个字符")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	@Length(min=0, max=255, message="door_id长度不能超过 255 个字符")
	public String getDoorId() {
		return doorId;
	}

	public void setDoorId(String doorId) {
		this.doorId = doorId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTotalDoorId() {
		return totalDoorId;
	}

	public void setTotalDoorId(String totalDoorId) {
		this.totalDoorId = totalDoorId;
	}

	public String getDeviceRemark() {
		return deviceRemark;
	}

	public void setDeviceRemark(String deviceRemark) {
		this.deviceRemark = deviceRemark;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	@Override
	public String toString() {
		return "ZngCard{" +
				"cardId='" + cardId + '\'' +
				", cardNum='" + cardNum + '\'' +
				", userId='" + userId + '\'' +
				", deviceId='" + deviceId + '\'' +
				", doorId='" + doorId + '\'' +
				", userName='" + userName + '\'' +
				", phone='" + phone + '\'' +
				", totalDoorId='" + totalDoorId + '\'' +
				", deviceRemark='" + deviceRemark + '\'' +
				", companyName='" + companyName + '\'' +
				", type='" + type + '\'' +
				", endTime=" + endTime +
				", cardType='" + cardType + '\'' +
				'}';
	}
}