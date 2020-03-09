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
 * 设备表Entity
 * @author lzy
 * @version 2018-08-28
 */
@Table(name="zhinenggui_device", alias="a",extWhereKeys="dsf", columns={
		@Column(name="id", attrName="id", label="主键", isPK=true),
		@Column(name="device_id", attrName="deviceId", label="设备ID"),
		@Column(name="device_name", attrName="deviceName", label="设备名称", queryType=QueryType.LIKE),
		@Column(name="device_remark", attrName="deviceRemark", label="设备备注"),
		@Column(name="code", attrName="code", label="二维码地址"),
		@Column(name="wx_code", attrName="wxCode", label="微信二维码地址"),
		@Column(name="enable", attrName="enable", label="是否启用"),
		@Column(name="on_line", attrName="onLine", label="是否在线"),
		@Column(name="by_company", attrName="byCompany", label="所属公司"),
		@Column(name="by_office", attrName="byOffice", label="所属部门"),
		@Column(name="by_user", attrName="byUser", label="所属用户"),
		@Column(name="longitudeX", attrName="longitudeX", label="X坐标"),
		@Column(name="latitudeY", attrName="latitudeY", label="Y坐标"),
		@Column(name="state", attrName="state", label="状态"),
		@Column(name="device_tpye", attrName="deviceTpye", label="设备类型"),
		@Column(name="courier_id", attrName="courierId", label="快递员电话"),
		@Column(name="company_user_id", attrName="companyUserId", label="设备关联所属用户"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class ZngDevice extends DataEntity<ZngDevice> {
	
	private static final long serialVersionUID = 1L;
	private String deviceId;		// 设备ID
	private String deviceName;		// 设备名称
	private String deviceRemark;	// 设备备注
	private String code;			// 二维码地址
	private String wxCode;			// 微信二维码地址
	private String enable;			// 是否启用
	private String onLine;			// 是否在线
	private String byCompany;		// 所属公司
	private String byOffice;		//所属部门
	private String byUser;			// 所属用户
	private String state;			// 状态
	private String deviceTpye;		// 设备类型
	private String courierId;		// 快递员
	private String companyUserId;		// 柜体关联用户
	private String courierPhone;
	private String longitudeX;	//X坐标
	private String latitudeY;	//Y坐标

	public ZngDevice() {
		this(null);
	}

	public ZngDevice(String id){
		super(id);
	}
	
	
	public String getLongitudeX() {
		return longitudeX;
	}

	public void setLongitudeX(String longitudeX) {
		this.longitudeX = longitudeX;
	}

	public String getLatitudeY() {
		return latitudeY;
	}

	public void setLatitudeY(String latitudeY) {
		this.latitudeY = latitudeY;
	}

	@Length(min=0, max=255, message="设备ID长度不能超过 255 个字符")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	@Length(min=0, max=255, message="设备名称长度不能超过 255 个字符")
	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
	@Length(min=0, max=255, message="设备备注长度不能超过 255 个字符")
	public String getDeviceRemark() {
		return deviceRemark;
	}

	public void setDeviceRemark(String deviceRemark) {
		this.deviceRemark = deviceRemark;
	}
	
	@Length(min=0, max=255, message="二维码地址长度不能超过 255 个字符")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=255, message="微信二维码地址长度不能超过 255 个字符")
	public String getWxCode() {
		return wxCode;
	}

	public void setWxCode(String wxCode) {
		this.wxCode = wxCode;
	}
	
	@Length(min=0, max=255, message="是否启用长度不能超过 255 个字符")
	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}
	
	@Length(min=0, max=255, message="是否在线长度不能超过 255 个字符")
	public String getOnLine() {
		return onLine;
	}

	public void setOnLine(String onLine) {
		this.onLine = onLine;
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
	
	@Length(min=0, max=255, message="状态长度不能超过 255 个字符")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Length(min=0, max=255, message="设备类型长度不能超过 255 个字符")
	public String getDeviceTpye() {
		return deviceTpye;
	}

	public void setDeviceTpye(String deviceTpye) {
		this.deviceTpye = deviceTpye;
	}

	public String getCourierId() {
		return courierId;
	}

	public void setCourierId(String courierId) {
		this.courierId = courierId;
	}

	public String getCompanyUserId() {
		return companyUserId;
	}

	public void setCompanyUserId(String companyUserId) {
		this.companyUserId = companyUserId;
	}

	public String getCourierPhone() {
		return courierPhone;
	}

	public void setCourierPhone(String courierPhone) {
		this.courierPhone = courierPhone;
	}
}