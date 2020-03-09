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
 * 用户地址表Entity
 * @author lzy
 * @version 2018-08-28
 */
@Table(name="zhinenggui_user_address", alias="a", columns={
		@Column(name="id", attrName="id", label="主键", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="address", attrName="address", label="地址"),
		@Column(name="type", attrName="type", label="地址类型", comment="地址类型:收货地址/发货地址"),
		@Column(name="name", attrName="name", label="姓名", queryType=QueryType.LIKE),
		@Column(name="phone", attrName="phone", label="电话"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class ZngUserAddress extends DataEntity<ZngUserAddress> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private String address;		// 地址
	private String type;		// 地址类型:收货地址/发货地址
	private String name;		// 姓名
	private String phone;		// 电话
	
	public ZngUserAddress() {
		this(null);
	}

	public ZngUserAddress(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="用户ID长度不能超过 255 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=255, message="地址长度不能超过 255 个字符")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=255, message="地址类型长度不能超过 255 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="姓名长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="电话长度不能超过 255 个字符")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}