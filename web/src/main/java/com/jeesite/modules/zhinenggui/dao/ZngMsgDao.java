/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.zhinenggui.entity.ZngMsg;

/**
 * zhinenggui_msgDAO接口
 * @author pfzheng
 * @version 2018-12-18
 */
@MyBatisDao
public interface ZngMsgDao extends CrudDao<ZngMsg> {
	
}