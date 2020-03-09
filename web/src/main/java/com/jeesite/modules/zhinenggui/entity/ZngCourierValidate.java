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
 * 快递员信息验证表Entity
 * @author lzy
 * @version 2018-08-28
 */
@Table(name="zhinenggui_courier_validate", alias="a", columns={
		@Column(name="id", attrName="id", label="主键", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="name", attrName="name", label="姓名", queryType=QueryType.LIKE),
		@Column(name="id_card", attrName="idCard", label="身份证号"),
		@Column(name="sex", attrName="sex", label="性别"),
		@Column(name="by_company", attrName="byCompany", label="所属公司"),
		@Column(name="image1", attrName="image1", label="照片路径1"),
		@Column(name="image2", attrName="image2", label="照片路径2"),
		@Column(name="image3", attrName="image3", label="照片路径3"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class ZngCourierValidate extends DataEntity<ZngCourierValidate> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private String name;		// 姓名
	private String idCard;		// 身份证号
	private String sex;		// 性别
	private String byCompany;		// 所属公司
	private String image1;		// 照片路径1
	private String image2;		// 照片路径2
	private String image3;		// 照片路径3
	
	public ZngCourierValidate() {
		this(null);
	}

	public ZngCourierValidate(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="用户ID长度不能超过 255 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=255, message="姓名长度不能超过 255 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="身份证号长度不能超过 255 个字符")
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	@Length(min=0, max=255, message="性别长度不能超过 255 个字符")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=255, message="所属公司长度不能超过 255 个字符")
	public String getByCompany() {
		return byCompany;
	}

	public void setByCompany(String byCompany) {
		this.byCompany = byCompany;
	}
	
	@Length(min=0, max=255, message="照片路径1长度不能超过 255 个字符")
	public String getImage1() {
		return image1;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}
	
	@Length(min=0, max=255, message="照片路径2长度不能超过 255 个字符")
	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}
	
	@Length(min=0, max=255, message="照片路径3长度不能超过 255 个字符")
	public String getImage3() {
		return image3;
	}

	public void setImage3(String image3) {
		this.image3 = image3;
	}
	
}