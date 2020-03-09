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
 * 用户表Entity
 * @author lzy
 * @version 2018-08-28
 */
@Table(name="zhinenggui_user",extWhereKeys="userQx", alias="a", columns={
		@Column(name="id", attrName="id", label="主键", isPK=true),
		@Column(name="name", attrName="name", label="姓名", queryType=QueryType.LIKE),
		@Column(name="login_name", attrName="loginName", label="登录账号", queryType=QueryType.LIKE),
		@Column(name="login_pwd", attrName="loginPwd", label="登录密码"),
		@Column(name="phone", attrName="phone", label="手机号"),
		@Column(name="wx_id", attrName="wxId", label="微信关联ID"),
		@Column(name="is_manager", attrName="isManager", label="角色", comment="角色:快递员/用户/管理员"),
		@Column(name="by_company", attrName="byCompany", label="所属公司"),
		@Column(name="by_Office", attrName="byOffice", label="所属部门"),
		@Column(name="by_User", attrName="byUser", label="所属用户"),
		@Column(name="is_checked", attrName="isChecked", label="身份核实"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class ZngUser extends DataEntity<ZngUser> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String loginName;		// 登录账号
	private String loginPwd;		// 登录密码
	private String phone;		// 手机号
	private String wxId;		// 微信关联ID
	private String isManager;		// 角色:快递员/用户/管理员
	private String byCompany;		//所属公司
	private String byOffice;		//所属部门
	private String byUser;			//所属用户
	private String isChecked;		// 身份核实
	private String validCode;
	
	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public ZngUser() {
		this(null);
	}

	public ZngUser(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="姓名长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="登录账号长度不能超过 255 个字符")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	@Length(min=0, max=255, message="登录密码长度不能超过 255 个字符")
	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
	
	@Length(min=0, max=255, message="手机号长度不能超过 255 个字符")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=255, message="微信关联ID长度不能超过 255 个字符")
	public String getWxId() {
		return wxId;
	}

	public void setWxId(String wxId) {
		this.wxId = wxId;
	}
	
	@Length(min=0, max=255, message="角色长度不能超过 255 个字符")
	public String getIsManager() {
		return isManager;
	}

	public void setIsManager(String isManager) {
		this.isManager = isManager;
	}
	
	@Length(min=0, max=255, message="所属公司长度不能超过 255 个字符")
	public String getByCompany() {
		return byCompany;
	}

	public void setByCompany(String byCompany) {
		this.byCompany = byCompany;
	}
	
	@Length(min=0, max=255, message="所属部门长度不能超过 255 个字符")
	public String getByOffice() {
		return byOffice;
	}

	public void setByOffice(String byOffice) {
		this.byOffice = byOffice;
	}
	
	@Length(min=0, max=255, message="所属用户长度不能超过 255 个字符")
	public String getByUser() {
		return byUser;
	}

	public void setByUser(String byUser) {
		this.byUser = byUser;
	}
	
	@Length(min=0, max=2, message="身份核实长度不能超过 2 个字符")
	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

}