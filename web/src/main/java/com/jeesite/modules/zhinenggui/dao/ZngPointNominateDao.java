/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.zhinenggui.entity.ZngPointNominate;

/**
 * 点位推荐表DAO接口
 * @author lzy
 * @version 2018-12-26
 */
@MyBatisDao
public interface ZngPointNominateDao extends CrudDao<ZngPointNominate> {
	
}