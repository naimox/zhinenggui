/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.zhinenggui.entity.ZngDoorPrice;
import com.jeesite.modules.zhinenggui.entity.ZngDoorPriceTree;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 柜门价格表DAO接口
 * @author lzy
 * @version 2018-08-28
 */
@MyBatisDao
public interface ZngDoorPriceDao extends CrudDao<ZngDoorPrice> {

    List<ZngDoorPriceTree> findTreeList();

    ZngDoorPrice getDoorPriceByDoorId(@Param("doorId")String doorId);
}