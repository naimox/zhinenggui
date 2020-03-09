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
 * 交易明细Entity
 * @author lzy
 * @version 2018-08-28
 */
@Table(name="zhinenggui_trade", alias="a", columns={
		@Column(name="id", attrName="id", label="主键", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="handle", attrName="handle", label="操作", comment="操作:进账/出账"),
		@Column(name="money", attrName="money", label="金额"),
		@Column(name="order_id", attrName="orderId", label="订单ID"),
		@Column(name="balance", attrName="balance", label="完成后的余额"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class ZngTrade extends DataEntity<ZngTrade> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private String handle;		// 操作:进账/出账
	private Double money;		// 金额
	private String orderId;		// 订单ID
	private String balance;		// 完成后的余额
	
	public ZngTrade() {
		this(null);
	}

	public ZngTrade(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="用户ID长度不能超过 255 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=255, message="操作长度不能超过 255 个字符")
	public String getHandle() {
		return handle;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}
	
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
	
	@Length(min=0, max=255, message="订单ID长度不能超过 255 个字符")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Length(min=0, max=255, message="完成后的余额长度不能超过 255 个字符")
	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
	
}