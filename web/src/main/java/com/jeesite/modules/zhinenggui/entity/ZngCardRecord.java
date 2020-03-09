/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * ic卡操作记录表Entity
 * @author lfy
 * @version 2018-12-26
 */
@Table(name="zhinenggui_card_record", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="state", attrName="state", label="状态", comment="状态：0开门，1临时选择柜门，2新增用户，3删除用户,4结束使用"),
		@Column(name="user_id", attrName="userId", label="user_id"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="card_id", attrName="cardId", label="卡号", queryType=QueryType.LIKE),
		@Column(name="door_id", attrName="doorId", label="door_id"),
		@Column(name="device_id", attrName="deviceId", label="device_id"),
	}, orderBy="a.id DESC"
)
public class ZngCardRecord extends DataEntity<ZngCardRecord> {
	
	private static final long serialVersionUID = 1L;
	private String state;		// 状态：0开门，1临时选择柜门，2新增用户，3删除用户,4结束使用
	private String userId;		// user_id
	private String cardId;		// 卡号
	private String doorId;		// door_id
	private String deviceId;		// device_id
	
	public ZngCardRecord() {
		this(null);
	}

	public ZngCardRecord(String id){
		super(id);
	}
	
	@Length(min=0, max=1, message="状态长度不能超过 1 个字符")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Length(min=0, max=255, message="user_id长度不能超过 255 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=255, message="卡号长度不能超过 255 个字符")
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	@Length(min=0, max=255, message="door_id长度不能超过 255 个字符")
	public String getDoorId() {
		return doorId;
	}

	public void setDoorId(String doorId) {
		this.doorId = doorId;
	}
	
	@Length(min=0, max=255, message="device_id长度不能超过 255 个字符")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
}