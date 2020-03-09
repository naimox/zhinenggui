/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.zhinenggui.entity.ZngHelp;

/**
 * 帮助信息DAO接口
 * @author lzy
 * @version 2018-12-26
 */
@MyBatisDao
public interface ZngHelpDao extends CrudDao<ZngHelp> {
	
}