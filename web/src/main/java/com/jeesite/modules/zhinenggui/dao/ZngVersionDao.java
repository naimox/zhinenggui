/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.dao;

import org.apache.ibatis.annotations.Param;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.zhinenggui.entity.ZngVersion;

/**
 * zhinenggui_versionDAO接口
 * @author lnn
 * @version 2018-09-08
 */
@MyBatisDao
public interface ZngVersionDao extends CrudDao<ZngVersion> {
	public ZngVersion getInfoByPacketName(@Param("packetName")String packetName);
}