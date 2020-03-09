/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.zhinenggui.entity.ZngCabinets;
import com.jeesite.modules.zhinenggui.search.DoorPriceSearch;
import com.jeesite.modules.zhinenggui.search.DoorSizeCount;

/**
 * 柜门柜门表DAO接口
 * @author lzy
 * @version 2018-08-30
 */
@MyBatisDao
public interface ZngCabinetsDao extends CrudDao<ZngCabinets> {
    List<ZngCabinets> getAllListByDeviceId(String deviceId);

    ZngCabinets getOneByDeviceId(@Param("deviceId")String deviceId, @Param("doorSize")String doorSize, @Param("money")String money, @Param("sendDoorMoney")String sendDoorMoney);

    ZngCabinets getOneByDeviceId2(@Param("deviceId")String deviceId, @Param("doorSize")String doorSize, @Param("money")String money);

    List<DoorSizeCount> getCount(@Param("deviceId")String deviceId);

    List<DoorSizeCount> getCount1(@Param("deviceId")String deviceId);

    List<ZngCabinets> getPlateNum(@Param("deviceId")String deviceId);

    ZngCabinets getOneByTotalDoorId(@Param("deviceId")String deviceId, @Param("doorId")String doorId);

    ZngCabinets getOneByTotalDoorIdNoStatus(@Param("deviceId")String deviceId, @Param("totalDoorId")String totalDoorId);

    List<DoorPriceSearch> getDoorPriceList(@Param("deviceId")String deviceId, @Param("doorSize")String doorSize);

    DoorPriceSearch getDoorDetailById(@Param("doorId")String doorId);

    List<ZngCabinets> getListByDeviceId(@Param("deviceId")String deviceId);
}