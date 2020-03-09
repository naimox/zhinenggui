/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.dao;

import org.apache.ibatis.annotations.Param;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.zhinenggui.entity.ZngDeviceViewData;

/**
 * 视图数据DAO接口
 * @author lzy
 * @version 2019-01-24
 */
@MyBatisDao
public interface ZngDeviceViewDataDao extends CrudDao<ZngDeviceViewData> {
	ZngDeviceViewData getOneByIdAndNumber(@Param("deviceId")String deviceId,@Param("deviceNumber")String deviceNumber);
	
	ZngDeviceViewData getOneByNumber (@Param("deviceNumber")String deviceNumber);
}