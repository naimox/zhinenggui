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
 * zhinenggui_msgEntity
 * @author lzy
 * @version 2018-12-24
 */
@Table(name="zhinenggui_msg", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="wx_id", attrName="wxId", label="wx_id"),
		@Column(name="name", attrName="name", label="name", queryType=QueryType.LIKE),
		@Column(name="content", attrName="content", label="content"),
		@Column(name="image", attrName="image", label="image"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class ZngMsg extends DataEntity<ZngMsg> {
	
	private static final long serialVersionUID = 1L;
	private String wxId;		// wx_id
	private String name;		// name
	private String content;		// content
	private String image;		// image
	
	public ZngMsg() {
		this(null);
	}

	public ZngMsg(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="wx_id长度不能超过 255 个字符")
	public String getWxId() {
		return wxId;
	}

	public void setWxId(String wxId) {
		this.wxId = wxId;
	}
	
	@Length(min=0, max=255, message="name长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=2000, message="content长度不能超过 2000 个字符")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=2000, message="image长度不能超过 2000 个字符")
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
}