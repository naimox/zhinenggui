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
 * zhinenggui_imageEntity
 * @author lnn
 * @version 2018-09-06
 */
@Table(name="zhinenggui_image", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="image_path", attrName="imagePath", label="图片地址"),
		@Column(name="order_id", attrName="orderId", label="订单ID"),
		@Column(name="type", attrName="type", label="图片类型"),
		@Column(name="image_name", attrName="imageName", label="图片名字", queryType=QueryType.LIKE),
		@Column(name="device_id", attrName="deviceId", label="设备ID"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class ZngImage extends DataEntity<ZngImage> {
	
	private static final long serialVersionUID = 1L;
	private String imagePath;		// 图片地址
	private String orderId;		// 订单ID
	private String type;		// 图片类型
	private String imageName;		// 图片名字
	private String deviceId;		// 设备ID
	
	public ZngImage() {
		this(null);
	}

	public ZngImage(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="图片地址长度不能超过 255 个字符")
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	@Length(min=0, max=255, message="订单ID长度不能超过 255 个字符")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Length(min=0, max=255, message="图片类型长度不能超过 255 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=255, message="图片名字长度不能超过 255 个字符")
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	@Length(min=0, max=255, message="设备ID长度不能超过 255 个字符")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
}