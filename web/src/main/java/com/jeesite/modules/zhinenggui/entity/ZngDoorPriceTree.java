/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.entity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.entity.TreeEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 测试树表Entity
 * @author ThinkGem
 * @version 2018-04-22
 */
public class ZngDoorPriceTree extends TreeEntity<ZngDoorPriceTree> {

	private static final long serialVersionUID = 1L;
	private String id;		// 节点编码
	private String doorId;
	private String pid;		// 节点编码
	private String name;		// 节点名称

	public ZngDoorPriceTree() {
		this(null);
	}

	public ZngDoorPriceTree(String id){
		super(id);
	}
	
	@Override
	public ZngDoorPriceTree getParent() {
		return parent;
	}

	@Override
	public void setParent(ZngDoorPriceTree parent) {
		this.parent = parent;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getDoorId() {
		return doorId;
	}

	public void setDoorId(String doorId) {
		this.doorId = doorId;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}