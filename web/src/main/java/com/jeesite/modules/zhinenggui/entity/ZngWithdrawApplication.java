/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * zhinenggui_withdraw_applicationEntity
 * @author lfy
 * @version 2018-11-23
 */
@Table(name="zhinenggui_withdraw_application", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="user_id", attrName="userId", label="user_id"),
		@Column(name="create_time", attrName="createTime", label="create_time"),
		@Column(name="money", attrName="money", label="money"),
		@Column(name="state", attrName="state", label="state"),
		@Column(name="type", attrName="type", label="1", comment="1:申请中2:申请通过3:申请驳回"),
	}, orderBy="a.id DESC"
)
public class ZngWithdrawApplication extends DataEntity<ZngWithdrawApplication> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// user_id
	private Date createTime;		// create_time
	private Double money;		// money
	private Integer state;		// state
	private Integer type;		// 1:申请中2:申请通过3:申请驳回
	
	public ZngWithdrawApplication() {
		this(null);
	}

	public ZngWithdrawApplication(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="user_id长度不能超过 255 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
	
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	@NotNull(message="1不能为空")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public Date getCreateTime_gte() {
		return sqlMap.getWhere().getValue("create_time", QueryType.GTE);
	}

	public void setCreateTime_gte(Date createTime) {
		sqlMap.getWhere().and("create_time", QueryType.GTE, createTime);
	}
	
	public Date getCreateTime_lte() {
		return sqlMap.getWhere().getValue("create_time", QueryType.LTE);
	}

	public void setCreateTime_lte(Date createTime) {
		sqlMap.getWhere().and("create_time", QueryType.LTE, createTime);
	}
	
	public Double getMoney_gte() {
		return sqlMap.getWhere().getValue("money", QueryType.GTE);
	}

	public void setMoney_gte(Double money) {
		sqlMap.getWhere().and("money", QueryType.GTE, money);
	}
	
	public Double getMoney_lte() {
		return sqlMap.getWhere().getValue("money", QueryType.LTE);
	}

	public void setMoney_lte(Double money) {
		sqlMap.getWhere().and("money", QueryType.LTE, money);
	}

	@Override
	public String toString() {
		return "ZngWithdrawApplication{" +
				"userId='" + userId + '\'' +
				", createTime=" + createTime +
				", money=" + money +
				", state=" + state +
				", type=" + type +
				'}';
	}
}