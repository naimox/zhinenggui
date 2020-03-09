/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.zhinenggui.entity.ZngUser;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 用户表DAO接口
 * @author lzy
 * @version 2018-08-28
 */
@MyBatisDao
public interface ZngUserDao extends CrudDao<ZngUser> {
	//根据账号(手机号)查找用户
	ZngUser findByTel(@Param("tel")String tel);
	
	//根据登录账号查找用户核信息
	ZngUser findByLoginName(@Param("loginName")String loginName);
	
	List<ZngUser> findByLoginNameReList(@Param("loginName")String loginName);

    ZngUser findOneByWxId(@Param("wxId")String wxId);

}