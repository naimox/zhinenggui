/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * zhinenggui_detailEntity
 * @author pfzheng
 * @version 2018-11-21
 */
@Table(name="zhinenggui_detail", alias="a", columns={
        @Column(name="id", attrName="id", label="id", isPK=true),
        @Column(name="user_id", attrName="userId", label="user_id"),
        @Column(name="money", attrName="money", label="money"),
        @Column(name = "device_id", attrName = "deviceId", label = "device_id"),
        @Column(name="type", attrName="type", label="type"),
        @Column(name="create_time", attrName="createTime", label="create_time"),
        @Column(name="state", attrName="state", label="state"),
}, orderBy="a.id DESC"
)
public class ZngDetail extends DataEntity<ZngDetail> {

    private static final long serialVersionUID = 1L;
    private String userId;		// user_id
    private Double money;		// money
    private String deviceId;		// money
    private Integer type;		// type
    private Date createTime;		// create_time
    private Integer state;		// state

    public ZngDetail() {
        this(null);
    }

    public ZngDetail(String id){
        super(id);
    }

    @Length(min=0, max=255, message="user_id长度不能超过 255 个字符")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

}