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
 * 视图数据Entity
 * @author lzy
 * @version 2019-01-24
 */
@Table(name="zhinenggui_device_view_data", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="device_id", attrName="deviceId", label="设备ID"),
		@Column(name="device_number", attrName="deviceNumber", label="设备编号", queryType=QueryType.LIKE),
		@Column(name="device_graph_view", attrName="deviceGraphView", label="视图数据"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class ZngDeviceViewData extends DataEntity<ZngDeviceViewData> {
	
	private static final long serialVersionUID = 1L;
	private String deviceId;		// 设备ID
	private String deviceNumber;		// 设备编号
	private String deviceGraphView;		// 视图数据
	
	public ZngDeviceViewData() {
		this(null);
	}

	public ZngDeviceViewData(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="设备ID长度不能超过 255 个字符")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	@Length(min=0, max=255, message="设备编号长度不能超过 255 个字符")
	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}
	
	public String getDeviceGraphView() {
		return deviceGraphView;
	}

	public void setDeviceGraphView(String deviceGraphView) {
		this.deviceGraphView = deviceGraphView;
	}
	
}