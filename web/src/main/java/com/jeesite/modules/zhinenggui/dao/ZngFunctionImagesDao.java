/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.dao;

import org.apache.ibatis.annotations.Param;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.zhinenggui.entity.ZngDevice;
import com.jeesite.modules.zhinenggui.entity.ZngFunctionImages;

/**
 * 综合图片DAO接口
 * @author lzy
 * @version 2019-01-14
 */
@MyBatisDao
public interface ZngFunctionImagesDao extends CrudDao<ZngFunctionImages> {
	
	
	ZngFunctionImages getByUserIdAndType(@Param("userId")String userId,@Param("type")String type);
}