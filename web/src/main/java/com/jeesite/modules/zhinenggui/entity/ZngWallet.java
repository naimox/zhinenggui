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
 * 钱包表Entity
 * @author lzy
 * @version 2018-08-28
 */
@Table(name="zhinenggui_wallet", alias="a", columns={
		@Column(name="id", attrName="id", label="主键", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="balance", attrName="balance", label="余额"),
		@Column(name="payment_pwd", attrName="paymentPwd", label="支付密码"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class ZngWallet extends DataEntity<ZngWallet> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private Double balance;		// 余额
	private String paymentPwd;		// 支付密码
	
	public ZngWallet() {
		this(null);
	}

	public ZngWallet(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="用户ID长度不能超过 255 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	@Length(min=0, max=255, message="支付密码长度不能超过 255 个字符")
	public String getPaymentPwd() {
		return paymentPwd;
	}

	public void setPaymentPwd(String paymentPwd) {
		this.paymentPwd = paymentPwd;
	}
	
}