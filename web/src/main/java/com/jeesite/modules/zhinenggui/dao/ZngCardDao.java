/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.dao;

import org.apache.ibatis.annotations.Param;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.zhinenggui.entity.ZngCard;

/**
 * ic卡表DAO接口
 * @author pfzheng
 * @version 2018-12-13
 */
@MyBatisDao
public interface ZngCardDao extends CrudDao<ZngCard> {

    ZngCard checkDoorIdExist(@Param("totalDoorId")String totalDoorId, @Param("id")String id);

    ZngCard checkCardIdExist(@Param("cardId")String cardId, @Param("id")String id);

    ZngCard findByCardId(@Param("cardId")String cardId);

    ZngCard findByDoorId(@Param("doorId")String doorId);
    
    ZngCard getByCardNumAndDeviceId(@Param("cardNum")String cardNum, @Param("deviceId")String deviceId);
}