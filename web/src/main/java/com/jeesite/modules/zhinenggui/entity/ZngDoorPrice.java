/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.Table;
import org.hibernate.validator.constraints.Length;

/**
 * 柜门价格表Entity
 * @author lzy
 * @version 2018-08-28
 */
@Table(name="zhinenggui_door_price", alias="a", columns={
		@Column(name="id", attrName="id", label="主键", isPK=true),
		@Column(name="door_id", attrName="doorId", label="门ID"),
		@Column(name="money", attrName="money", label="价格"),
		@Column(name="over_time", attrName="overTime", label="超时时间"),
		@Column(name="over_type", attrName="overType", label="超时类型,秒/分/时/天/月"),
		@Column(name="over_money", attrName="overMoney", label="超时价格"),
		@Column(name="over_unit", attrName="overUnit", label="超时时间单位"),
		@Column(name="money1", attrName="money1", label="价格"),
		@Column(name="over_time1", attrName="overTime1", label="超时时间"),
		@Column(name="over_type1", attrName="overType1", label="超时类型,秒/分/时/天/月"),
		@Column(name="over_money1", attrName="overMoney1", label="超时价格"),
		@Column(name="over_unit1", attrName="overUnit1", label="超时时间单位"),
		@Column(name="courier_send_money", attrName="courierSendMoney", label="快递员寄件门计费"),
		@Column(includeEntity=DataEntity.class),
	},
		/*joinTable={
				@JoinTable(type= JoinTable.Type.LEFT_JOIN, entity=Office.class, alias="o",
						on="o.office_code = a.office_name",
						columns={@Column(includeEntity=Office.class)}),
				@JoinTable(type=Type.LEFT_JOIN, entity=Company.class, alias="c",
						on="c.company_code = a.company_name",
						columns={@Column(includeEntity=Company.class)}),
		},*/
		orderBy="a.update_date DESC"
)
public class ZngDoorPrice extends DataEntity<ZngDoorPrice> {
	
	private static final long serialVersionUID = 1L;
	private String doorId;		// 门ID
	private String money;		// 价格
	private String overTime;		// 超时时间
	private String overType;		// 超时类型,秒/分/时/天/月
	private String overMoney;		// 超时价格
	private String overUnit;		// 超时价格
	private String money1;		// 价格
	private String overTime1;		// 超时时间
	private String overType1;		// 超时类型,秒/分/时/天/月
	private String overMoney1;		// 超时价格
	private String overUnit1;		// 超时价格
	private String courierSendMoney;

	private String name;
	
	public ZngDoorPrice() {
		this(null);
	}

	public ZngDoorPrice(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="门ID长度不能超过 255 个字符")
	public String getDoorId() {
		return doorId;
	}

	public void setDoorId(String doorId) {
		this.doorId = doorId;
	}
	
	@Length(min=0, max=255, message="价格长度不能超过 255 个字符")
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public String getOverTime() {
		return overTime;
	}

	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}
	
	@Length(min=0, max=255, message="超时类型,秒/分/时/天/月长度不能超过 255 个字符")
	public String getOverType() {
		return overType;
	}

	public void setOverType(String overType) {
		this.overType = overType;
	}
	
	@Length(min=0, max=255, message="超时价格长度不能超过 255 个字符")
	public String getOverMoney() {
		return overMoney;
	}

	public void setOverMoney(String overMoney) {
		this.overMoney = overMoney;
	}

	public String getOverUnit() {
		return overUnit;
	}

	public void setOverUnit(String overUnit) {
		this.overUnit = overUnit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMoney1() {
		return money1;
	}

	public void setMoney1(String money1) {
		this.money1 = money1;
	}

	public String getOverTime1() {
		return overTime1;
	}

	public void setOverTime1(String overTime1) {
		this.overTime1 = overTime1;
	}

	public String getOverType1() {
		return overType1;
	}

	public void setOverType1(String overType1) {
		this.overType1 = overType1;
	}

	public String getOverMoney1() {
		return overMoney1;
	}

	public void setOverMoney1(String overMoney1) {
		this.overMoney1 = overMoney1;
	}

	public String getOverUnit1() {
		return overUnit1;
	}

	public void setOverUnit1(String overUnit1) {
		this.overUnit1 = overUnit1;
	}

	public String getCourierSendMoney() {
		return courierSendMoney;
	}

	public void setCourierSendMoney(String courierSendMoney) {
		this.courierSendMoney = courierSendMoney;
	}
}