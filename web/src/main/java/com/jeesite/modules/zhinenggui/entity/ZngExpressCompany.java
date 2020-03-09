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
 * 快递公司表Entity
 * @author lzy
 * @version 2018-08-28
 */
@Table(name="zhinenggui_express_company", alias="a", columns={
		@Column(name="id", attrName="id", label="主键", isPK=true),
		@Column(name="name", attrName="name", label="快递公司名称", queryType=QueryType.LIKE),
		@Column(name="image", attrName="image", label="快递公司log图片地址"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class ZngExpressCompany extends DataEntity<ZngExpressCompany> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 快递公司名称
	private String image;		// 快递公司log图片地址
	
	public ZngExpressCompany() {
		this(null);
	}

	public ZngExpressCompany(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="快递公司名称长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="快递公司log图片地址长度不能超过 255 个字符")
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
}