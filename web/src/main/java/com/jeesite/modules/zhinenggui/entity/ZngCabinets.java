/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 柜门柜门表Entity
 * @author lzy
 * @version 2018-08-30
 */
@Table(name="zhinenggui_cabinets", alias="a", columns={
		@Column(name="id", attrName="id", label="主键", isPK=true),
		@Column(name="device_id", attrName="deviceId", label="设备ID"),
		@Column(name="plate_id", attrName="plateId", label="板/柜ID"),
		@Column(name="door_id", attrName="doorId", label="单柜门ID"),
		@Column(name="total_door_id", attrName="totalDoorId", label="总柜门ID"),
		@Column(name="by_company", attrName="byCompany", label="所属公司"),
		@Column(name="by_user", attrName="byUser", label="所属人"),
		@Column(name="state", attrName="state", label="状态"),
		@Column(name="device_type", attrName="deviceType", label="设备类型"),
		@Column(name="door_size", attrName="doorSize", label="门大小型号"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class ZngCabinets extends DataEntity<ZngCabinets> {
	
	private static final long serialVersionUID = 1L;
	private String deviceId;		// 设备ID
	private String plateId;		// 板/柜ID
	private String doorId;		// 单柜门ID
	private String totalDoorId;		// 总柜门ID
	private String byCompany;		// 所属公司
	private String byUser;		// 所属人
	private String state;		// 状态
	private String deviceType;		// 设备类型
	private String doorSize;		// 门大小型号
	
	public ZngCabinets() {
		this(null);
	}

	public ZngCabinets(String id){
		super(id);
	}
	
	@NotBlank(message="设备ID不能为空")
	@Length(min=0, max=255, message="设备ID长度不能超过 255 个字符")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	@Length(min=0, max=255, message="板/柜ID长度不能超过 255 个字符")
	public String getPlateId() {
		return plateId;
	}

	public void setPlateId(String plateId) {
		this.plateId = plateId;
	}
	
	@Length(min=0, max=255, message="单柜门ID长度不能超过 255 个字符")
	public String getDoorId() {
		return doorId;
	}

	public void setDoorId(String doorId) {
		this.doorId = doorId;
	}
	
	@Length(min=0, max=255, message="总柜门ID长度不能超过 255 个字符")
	public String getTotalDoorId() {
		return totalDoorId;
	}

	public void setTotalDoorId(String totalDoorId) {
		this.totalDoorId = totalDoorId;
	}
	
	@Length(min=0, max=255, message="所属公司长度不能超过 255 个字符")
	public String getByCompany() {
		return byCompany;
	}

	public void setByCompany(String byCompany) {
		this.byCompany = byCompany;
	}
	
	@Length(min=0, max=255, message="所属人长度不能超过 255 个字符")
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
	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
	@Length(min=0, max=255, message="门大小型号长度不能超过 255 个字符")
	public String getDoorSize() {
		return doorSize;
	}

	public void setDoorSize(String doorSize) {
		this.doorSize = doorSize;
	}

	@Override
	public String toString() {
		return "ZngCabinets{" +
				"deviceId='" + deviceId + '\'' +
				", plateId='" + plateId + '\'' +
				", doorId='" + doorId + '\'' +
				", totalDoorId='" + totalDoorId + '\'' +
				", byCompany='" + byCompany + '\'' +
				", byUser='" + byUser + '\'' +
				", state='" + state + '\'' +
				", deviceType='" + deviceType + '\'' +
				", doorSize='" + doorSize + '\'' +
				'}';
	}
}