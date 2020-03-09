/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.zhinenggui.entity.ZngCardRecord;

/**
 * ic卡操作记录表DAO接口
 * @author lfy
 * @version 2018-12-26
 */
@MyBatisDao
public interface ZngCardRecordDao extends CrudDao<ZngCardRecord> {
	
}