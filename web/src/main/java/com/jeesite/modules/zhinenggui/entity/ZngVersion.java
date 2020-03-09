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
 * zhinenggui_versionEntity
 * @author lnn
 * @version 2018-09-08
 */
@Table(name="zhinenggui_version", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="packet_name", attrName="packetName", label="packet_name", queryType=QueryType.LIKE),
		@Column(name="level", attrName="level", label="level"),
		@Column(name="code", attrName="code", label="code"),
		@Column(name="explain", attrName="explain", label="explain"),
		@Column(name="down_url", attrName="downUrl", label="down_url"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class ZngVersion extends DataEntity<ZngVersion> {
	
	private static final long serialVersionUID = 1L;
	private String packetName;		// packet_name
	private String level;		// level
	private String code;		// code
	private String explain;		// explain
	private String downUrl;		// down_url
	
	public ZngVersion() {
		this(null);
	}

	public ZngVersion(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="packet_name长度不能超过 255 个字符")
	public String getPacketName() {
		return packetName;
	}

	public void setPacketName(String packetName) {
		this.packetName = packetName;
	}
	
	@Length(min=0, max=255, message="level长度不能超过 255 个字符")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	@Length(min=0, max=255, message="code长度不能超过 255 个字符")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=255, message="explain长度不能超过 255 个字符")
	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}
	
	@Length(min=0, max=255, message="down_url长度不能超过 255 个字符")
	public String getDownUrl() {
		return downUrl;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}
	
}