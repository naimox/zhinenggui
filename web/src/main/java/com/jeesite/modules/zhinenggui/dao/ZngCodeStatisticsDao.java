/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.zhinenggui.entity.ZngCode;
import com.jeesite.modules.zhinenggui.entity.ZngCodeStatistics;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 验证码统计DAO接口
 * @author lfy
 * @version 2018-11-29
 */
@MyBatisDao
public interface ZngCodeStatisticsDao extends CrudDao<ZngCodeStatistics> {

    ZngCodeStatistics getCodeStatisticsByDeviceId(@Param("deviceId")String deviceId);

    void reduceCodeNum(@Param("id")String id, @Param("num")int num);

    ZngCodeStatistics getCodeStatisticsByUserId(@Param("userId")String userId);

}