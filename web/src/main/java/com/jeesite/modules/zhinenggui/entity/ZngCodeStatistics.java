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
 * 验证码统计Entity
 * @author lfy
 * @version 2018-11-29
 */
@Table(name="zhinenggui_code_statistics", alias="a", columns={
		@Column(name="id", attrName="id", label="主键", isPK=true),
		@Column(includeEntity=DataEntity.class),
		@Column(name="code_num", attrName="codeNum", label="验证码数量"),
		@Column(name="user_by", attrName="userBy", label="用户ID"),
	}, orderBy="a.update_date DESC"
)
public class ZngCodeStatistics extends DataEntity<ZngCodeStatistics> {
	
	private static final long serialVersionUID = 1L;
	private int codeNum;		// 验证码数量
	private String userBy;		// 用户ID
	
	public ZngCodeStatistics() {
		this(null);
	}

	public ZngCodeStatistics(String id){
		super(id);
	}
	
	public int getCodeNum() {
		return codeNum;
	}

	public void setCodeNum(int codeNum) {
		this.codeNum = codeNum;
	}

	@Length(min=0, max=255, message="用户ID长度不能超过 255 个字符")
	public String getUserBy() {
		return userBy;
	}

	public void setUserBy(String userBy) {
		this.userBy = userBy;
	}
	
}