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
 * 综合图片Entity
 * @author lzy
 * @version 2019-01-14
 */
@Table(name="zhinenggui_function_images", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="type", attrName="type", label="用途", comment="用途:roll:滚动图"),
		@Column(name="explain", attrName="explain", label="说明"),
		@Column(includeEntity=DataEntity.class),
		@Column(name="by_company", attrName="byCompany", label="所属公司"),
		@Column(name="by_user", attrName="byUser", label="所属用户"),
		@Column(name="by_office", attrName="byOffice", label="所属组织"),
	}, orderBy="a.update_date DESC"
)
public class ZngFunctionImages extends DataEntity<ZngFunctionImages> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 用途:roll:滚动图
	private String explain;		// 说明
	private String byCompany;		// 所属公司
	private String byUser;		// 所属用户
	private String byOffice;		// 所属组织
	
	public ZngFunctionImages() {
		this(null);
	}

	public ZngFunctionImages(String id){
		super(id);
	}
	
	@Length(min=0, max=20, message="用途长度不能超过 20 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="说明长度不能超过 255 个字符")
	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
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
	
	@Length(min=0, max=255, message="所属组织长度不能超过 255 个字符")
	public String getByOffice() {
		return byOffice;
	}

	public void setByOffice(String byOffice) {
		this.byOffice = byOffice;
	}
	
}