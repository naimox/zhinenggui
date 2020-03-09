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
 * 订单表Entity
 * @author lzy
 * @version 2018-08-28
 */
@Table(name="zhinenggui_order", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户ID"),	
		@Column(name="image_one", attrName="imageOne", label="图片1/存件取件"),
		@Column(name="image_two", attrName="imageTwo", label="图片2/存件取件"),
		@Column(name="state", attrName="state", label="订单状态"),
		@Column(name="sender_user_name", attrName="senderUserName", label="送件人姓名"),
		@Column(name="sender_phone", attrName="senderPhone", label="送件人电话"),
		@Column(name="sender_time", attrName="senderTime", label="送件时间"),
		@Column(name="sender_address", attrName="senderAddress", label="送件地址"),
		@Column(name="sender_remark", attrName="senderRemark", label="送件备注"),
		@Column(name="getter_user_name", attrName="getterUserName", label="收货人姓名"),
		@Column(name="getter_phone", attrName="getterPhone", label="收货人电话"),
		@Column(name="getter_address", attrName="getterAddress", label="收货地址"),
		@Column(name="getter_time", attrName="getterTime", label="收货时间"),
		@Column(name="getter_comment", attrName="getterComment", label="收货备注"),
		@Column(name="courier_get_id", attrName="courierGetId", label="快递收件员ID"),
		@Column(name="courier_get_name", attrName="courierGetName", label="快件收件员姓名"),
		@Column(name="courier_get_time", attrName="courierGetTime", label="快递员收件时间"),
		@Column(name="courier_send_id", attrName="courierSendId", label="快递送件员ID"),
		@Column(name="courier_send_name", attrName="courierSendName", label="快递送件员姓名"),
		@Column(name="courier_send_time", attrName="courierSendTime", label="快递送件时间"),
		@Column(name="get_device_id", attrName="getDeviceId", label="寄件柜id"),
		@Column(name="get_door_id", attrName="getDoorId", label="寄件门id"),
		@Column(name="send_device_id", attrName="sendDeviceId", label="收件柜id"),
		@Column(name="send_door_id", attrName="sendDoorId", label="收件柜id"),
		@Column(name="get_total_money", attrName="getTotalMoney", label="寄件总金额"),
		@Column(name="send_total_money", attrName="sendTotalMoney", label="收件总金额"),
		@Column(name="express_number", attrName="expressNumber", label="快递单号"),
		@Column(name="express_company", attrName="expressCompany", label="快递公司"),
		@Column(name="code_id", attrName="codeId", label="取件码ID"),
		@Column(name="payment_tpye", attrName="paymentTpye", label="支付类型"),
		@Column(name="order_type", attrName="orderType", label="订单类型"),
		@Column(name="order_sn", attrName="orderSn", label="商户订单编号"),
		@Column(name="finish_order_sn", attrName="finishOrderSn", label="取件时支付的商户订单编号"),
		@Column(name="prepay_id", attrName="prepayId", label="微信统一下单ID"),
		@Column(name="finish_prepay_id", attrName = "finishPrepayId", label="取件时支付的微信统一下单ID"),
		@Column(name="prepaid_amount", attrName="prepaidAmount"),
		@Column(name="pick_up_amount", attrName="pickUpAmount"),
		@Column(includeEntity=DataEntity.class),
	},orderBy="a.update_date DESC"
)
public class ZngOrder extends DataEntity<ZngOrder> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private String imageOne;		// 图片1/存件取件
	private String imageTwo;		// 图片2/存件取件
	private String state;		// 订单状态
	private String senderUserName;		// 送件人姓名
	private String senderTime;		// 送件时间
	private String senderPhone;		// 送件人电话
	private String senderAddress;		// 送件地址
	private String senderRemark;		// 送件备注
	private String getterUserName;		// 收货人姓名
	private String getterAddress;		// 收货地址
	private String getterTime;		// 收货时间
	private String getterPhone;		// 收件人电话
	private String getterComment;		// 收货备注
	private String courierGetId;		// 快递收件员ID
	private String courierGetName;		// 快件收件员姓名
	private String courierGetTime;		// 快递员收件时间
	private String courierSendId;		// 快递送件员ID
	private String courierSendName;		// 快递送件员姓名
	private String courierSendTime;		// 快递送件时间
	private String getDeviceId;		// 寄件柜id
	private String getDoorId;		// 寄件门id
	private String sendDeviceId;		// 收件柜id
	private String sendDoorId;		// 收件门id
	private String getTotalMoney;		// 寄件总金额
	private String sendTotalMoney;		// 收件总金额
	private String prepaidAmount;		// 预支付金额
	private String pickUpAmount;		// 取件金额
	private String expressNumber;		// 快递单号
	private String expressCompany;		// 快递公司
	private String codeId;		// 取件码ID
	private String paymentTpye;		// 支付类型
	private String expressMoney;		// 支付类型
	private String doorId;
	private String plateId;
	private String totalDoorId;
	private String orderType;			//订单类型
	private String orderSn;				// 商户订单编号
	private String prepayId;		// 微信统一下单ID
	private String finishOrderSn;	// 取件时支付的商户订单编号
	private String finishPrepayId;	// 取件时支付的微信统一下单ID
	

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public ZngOrder() {
		this(null);
	}

	public ZngOrder(String id){
		super(id);
	}
	
	@NotBlank(message="用户ID不能为空")
	@Length(min=0, max=255, message="用户ID长度不能超过 255 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=255, message="图片1/存件取件长度不能超过 255 个字符")
	public String getImageOne() {
		return imageOne;
	}

	public void setImageOne(String imageOne) {
		this.imageOne = imageOne;
	}
	
	@Length(min=0, max=255, message="图片2/存件取件长度不能超过 255 个字符")
	public String getImageTwo() {
		return imageTwo;
	}

	public void setImageTwo(String imageTwo) {
		this.imageTwo = imageTwo;
	}
	
	@Length(min=0, max=6, message="订单状态长度不能超过 6 个字符")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Length(min=0, max=255, message="送件人姓名长度不能超过 255 个字符")
	public String getSenderUserName() {
		return senderUserName;
	}

	public void setSenderUserName(String senderUserName) {
		this.senderUserName = senderUserName;
	}
	
	@Length(min=0, max=255, message="送件时间长度不能超过 255 个字符")
	public String getSenderTime() {
		return senderTime;
	}

	public void setSenderTime(String senderTime) {
		this.senderTime = senderTime;
	}
	
	@Length(min=0, max=255, message="送件地址长度不能超过 255 个字符")
	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}
	
	@Length(min=0, max=255, message="送件备注长度不能超过 255 个字符")
	public String getSenderRemark() {
		return senderRemark;
	}

	public void setSenderRemark(String senderRemark) {
		this.senderRemark = senderRemark;
	}
	
	@Length(min=0, max=255, message="收货人姓名长度不能超过 255 个字符")
	public String getGetterUserName() {
		return getterUserName;
	}

	public void setGetterUserName(String getterUserName) {
		this.getterUserName = getterUserName;
	}
	
	@Length(min=0, max=255, message="收货地址长度不能超过 255 个字符")
	public String getGetterAddress() {
		return getterAddress;
	}

	public void setGetterAddress(String getterAddress) {
		this.getterAddress = getterAddress;
	}
	
	@Length(min=0, max=255, message="收货时间长度不能超过 255 个字符")
	public String getGetterTime() {
		return getterTime;
	}

	public void setGetterTime(String getterTime) {
		this.getterTime = getterTime;
	}
	
	@Length(min=0, max=255, message="收货备注长度不能超过 255 个字符")
	public String getGetterComment() {
		return getterComment;
	}

	public void setGetterComment(String getterComment) {
		this.getterComment = getterComment;
	}
	
	@Length(min=0, max=255, message="快递收件员ID长度不能超过 255 个字符")
	public String getCourierGetId() {
		return courierGetId;
	}

	public void setCourierGetId(String courierGetId) {
		this.courierGetId = courierGetId;
	}
	
	@Length(min=0, max=255, message="快件收件员姓名长度不能超过 255 个字符")
	public String getCourierGetName() {
		return courierGetName;
	}

	public void setCourierGetName(String courierGetName) {
		this.courierGetName = courierGetName;
	}
	
	@Length(min=0, max=255, message="快递员收件时间长度不能超过 255 个字符")
	public String getCourierGetTime() {
		return courierGetTime;
	}

	public void setCourierGetTime(String courierGetTime) {
		this.courierGetTime = courierGetTime;
	}
	
	@Length(min=0, max=255, message="快递送件员ID长度不能超过 255 个字符")
	public String getCourierSendId() {
		return courierSendId;
	}

	public void setCourierSendId(String courierSendId) {
		this.courierSendId = courierSendId;
	}
	
	@Length(min=0, max=255, message="快递送件员姓名长度不能超过 255 个字符")
	public String getCourierSendName() {
		return courierSendName;
	}

	public void setCourierSendName(String courierSendName) {
		this.courierSendName = courierSendName;
	}
	
	@Length(min=0, max=255, message="快递送件时间长度不能超过 255 个字符")
	public String getCourierSendTime() {
		return courierSendTime;
	}

	public void setCourierSendTime(String courierSendTime) {
		this.courierSendTime = courierSendTime;
	}
	
	@Length(min=0, max=255, message="寄件柜门id长度不能超过 255 个字符")
	public String getGetDoorId() {
		return getDoorId;
	}

	public void setGetDoorId(String getDoorId) {
		this.getDoorId = getDoorId;
	}
	
	@Length(min=0, max=255, message="收件柜门id长度不能超过 255 个字符")
	public String getSendDoorId() {
		return sendDoorId;
	}

	public void setSendDoorId(String sendDoorId) {
		this.sendDoorId = sendDoorId;
	}
	
	@Length(min=0, max=255, message="寄件总金额长度不能超过 255 个字符")
	public String getGetTotalMoney() {
		return getTotalMoney;
	}

	public void setGetTotalMoney(String getTotalMoney) {
		this.getTotalMoney = getTotalMoney;
	}
	
	@Length(min=0, max=255, message="收件总金额长度不能超过 255 个字符")
	public String getSendTotalMoney() {
		return sendTotalMoney;
	}

	public void setSendTotalMoney(String sendTotalMoney) {
		this.sendTotalMoney = sendTotalMoney;
	}
	
	@Length(min=0, max=255, message="快递单号长度不能超过 255 个字符")
	public String getExpressNumber() {
		return expressNumber;
	}

	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}
	
	@Length(min=0, max=255, message="快递公司长度不能超过 255 个字符")
	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}
	
	@Length(min=0, max=255, message="取件码ID长度不能超过 255 个字符")
	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	
	@Length(min=0, max=6, message="支付类型长度不能超过 6 个字符")
	public String getPaymentTpye() {
		return paymentTpye;
	}

	public void setPaymentTpye(String paymentTpye) {
		this.paymentTpye = paymentTpye;
	}

	@Length(min=0, max=255, message="寄件柜长度不能超过 255 个字符")
	public String getGetDeviceId() {
		return getDeviceId;
	}

	public void setGetDeviceId(String getDeviceId) {
		this.getDeviceId = getDeviceId;
	}

	@Length(min=0, max=255, message="收件柜长度不能超过 255 个字符")
	public String getSendDeviceId() {
		return sendDeviceId;
	}

	public void setSendDeviceId(String sendDeviceId) {
		this.sendDeviceId = sendDeviceId;
	}

	@Length(min=0, max=255, message="收件人电话长度不能超过 255 个字符")
	public String getSenderPhone() {
		return senderPhone;
	}

	public void setSenderPhone(String senderPhone) {
		this.senderPhone = senderPhone;
	}

	@Length(min=0, max=255, message="送件人电话柜长度不能超过 255 个字符")
	public String getGetterPhone() {
		return getterPhone;
	}

	public void setGetterPhone(String getterPhone) {
		this.getterPhone = getterPhone;
	}

	public String getExpressMoney() {
		return expressMoney;
	}

	public void setExpressMoney(String expressMoney) {
		this.expressMoney = expressMoney;
	}

	public String getDoorId() {
		return doorId;
	}

	public void setDoorId(String doorId) {
		this.doorId = doorId;
	}

	public String getPlateId() {
		return plateId;
	}

	public void setPlateId(String plateId) {
		this.plateId = plateId;
	}

	public String getTotalDoorId() {
		return totalDoorId;
	}

	public void setTotalDoorId(String totalDoorId) {
		this.totalDoorId = totalDoorId;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public String getFinishOrderSn() {
		return finishOrderSn;
	}

	public void setFinishOrderSn(String finishOrderSn) {
		this.finishOrderSn = finishOrderSn;
	}

	public String getFinishPrepayId() {
		return finishPrepayId;
	}

	public void setFinishPrepayId(String finishPrepayId) {
		this.finishPrepayId = finishPrepayId;
	}

	public String getPrepaidAmount() {
		return prepaidAmount;
	}

	public void setPrepaidAmount(String prepaidAmount) {
		this.prepaidAmount = prepaidAmount;
	}

	public String getPickUpAmount() {
		return pickUpAmount;
	}

	public void setPickUpAmount(String pickUpAmount) {
		this.pickUpAmount = pickUpAmount;
	}
}