/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.zhinenggui.entity.ZngImage;

/**
 * zhinenggui_imageDAO接口
 * @author lnn
 * @version 2018-09-06
 */
@MyBatisDao
public interface ZngImageDao extends CrudDao<ZngImage> {
	
	/**
	 * 根据设备号找到该设备下的所有图片
	 * @param zhinengguiImage
	 * @return
	 */
	public List<ZngImage> findImageListByDeviceId(@Param("deviceId")String deviceId) ;
	
}