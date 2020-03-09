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
 * 验证码通用表Entity
 * @author lzy
 * @version 2018-08-28
 */
@Table(name="zhinenggui_code", alias="a", columns={
		@Column(name="id", attrName="id", label="主键", isPK=true),
		@Column(name="code", attrName="code", label="验证码"),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="type", attrName="type", label="验证码类型/区分验证码用途"),
		@Column(name="phone", attrName="phone", label="手机号备份"),
		@Column(name = "content", attrName = "content", label = "附加文本"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class ZngCode extends DataEntity<ZngCode> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 验证码
	private String userId;		// 用户ID
	private String type;		// 验证码类型/区分验证码用途
	private String phone;		// 手机号备份
	private String content;
	
	public ZngCode() {
		this(null);
	}

	public ZngCode(String id){
		super(id);
	}
	
	@Length(min=0, max=30, message="验证码长度不能超过 30 个字符")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=255, message="用户ID长度不能超过 255 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=6, message="验证码类型/区分验证码用途长度不能超过 6 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="手机号备份长度不能超过 255 个字符")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}