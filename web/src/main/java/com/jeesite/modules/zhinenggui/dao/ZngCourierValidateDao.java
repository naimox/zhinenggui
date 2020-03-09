/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.zhinenggui.entity.ZngCourierValidate;

/**
 * 快递员信息验证表DAO接口
 * @author lzy
 * @version 2018-08-28
 */
@MyBatisDao
public interface ZngCourierValidateDao extends CrudDao<ZngCourierValidate> {
	
}