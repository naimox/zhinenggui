/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.zhinenggui.entity.ZngWithdrawApplication;

import java.util.Collection;

import org.apache.ibatis.annotations.Param;

/**
 * zhinenggui_withdraw_applicationDAO接口
 * @author lfy
 * @version 2018-11-23
 */
@MyBatisDao
public interface ZngWithdrawApplicationDao extends CrudDao<ZngWithdrawApplication> {

    Collection<ZngWithdrawApplication> getWithdrawApplicationByUserId(@Param("userId")String userId, @Param("type")String type);

    int adopt(@Param("id")String id);

    void upApplicationType(@Param("userId")String userId, @Param("money")double money, @Param("type")int type);

    ZngWithdrawApplication getTop(@Param("userId")String userId);
}