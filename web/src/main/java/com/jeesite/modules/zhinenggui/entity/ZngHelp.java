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
 * 帮助信息Entity
 * @author lzy
 * @version 2018-12-26
 */
@Table(name="zhinenggui_help", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="title", attrName="title", label="标题", queryType=QueryType.LIKE),
		@Column(name="content", attrName="content", label="内容"),
		@Column(name="by_company", attrName="byCompany", label="所属公司"),
		@Column(name="by_user", attrName="byUser", label="所属用户"),
		@Column(name="by_office", attrName="byOffice", label="所属部门"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class ZngHelp extends DataEntity<ZngHelp> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String content;		// 内容
	private String byCompany;		// 所属公司
	private String byUser;		// 所属用户
	private String byOffice;		// 所属部门
	
	public ZngHelp() {
		this(null);
	}

	public ZngHelp(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="标题长度不能超过 255 个字符")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="内容长度不能超过 255 个字符")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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