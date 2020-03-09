/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.zhinenggui.entity.ZngUserAddress;

/**
 * 用户地址表DAO接口
 * @author lzy
 * @version 2018-08-28
 */
@MyBatisDao
public interface ZngUserAddressDao extends CrudDao<ZngUserAddress> {
	//根据用户ID查询该用户下所有地址信息
	public List<ZngUserAddress> findByUserId(@Param("userId")String userId);
}