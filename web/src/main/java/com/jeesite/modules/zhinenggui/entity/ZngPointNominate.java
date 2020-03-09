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
 * 点位推荐表Entity
 * @author lzy
 * @version 2018-12-26
 */
@Table(name="zhinenggui_point_nominate", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="name", attrName="name", label="姓名", queryType=QueryType.LIKE),
		@Column(name="phone", attrName="phone", label="联系电话"),
		@Column(name="address", attrName="address", label="投放地点"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="by_company", attrName="byCompany", label="所属公司"),
		@Column(name="by_user", attrName="byUser", label="所属用户"),
		@Column(name="by_office", attrName="byOffice", label="所属部门"),
	}, orderBy="a.update_date DESC"
)
public class ZngPointNominate extends DataEntity<ZngPointNominate> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String phone;		// 联系电话
	private String address;		// 投放地点
	private String byCompany;		// 所属公司
	private String byUser;		// 所属用户
	private String byOffice;		// 所属部门
	
	public ZngPointNominate() {
		this(null);
	}

	public ZngPointNominate(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="姓名长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="联系电话长度不能超过 255 个字符")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=255, message="投放地点长度不能超过 255 个字符")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=255, message="所属公司长度不能超过 255 个字符")
	public String getByCompany() {
		return byCompany;
	}

	public void setByCompany(String byCompany) {
		this.byCompany = byCompany;
	}
	
	@Length(min=0, max=255, message="所属用户长度不能超过 255 个字符")
	public String getByUser() {
		return byUser;
	}

	public void setByUser(String byUser) {
		this.byUser = byUser;
	}
	
	@Length(min=0, max=255, message="所属部门长度不能超过 255 个字符")
	public String getByOffice() {
		return byOffice;
	}

	public void setByOffice(String byOffice) {
		this.byOffice = byOffice;
	}
	
}