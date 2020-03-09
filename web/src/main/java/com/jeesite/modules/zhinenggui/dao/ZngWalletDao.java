/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.dao;

import org.apache.ibatis.annotations.Param;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.zhinenggui.entity.ZngWallet;

/**
 * 钱包表DAO接口
 * @author lzy
 * @version 2018-08-28
 */
@MyBatisDao
public interface ZngWalletDao extends CrudDao<ZngWallet> {

    ZngWallet getWalletByUserId(@Param("userId")String userId);

    ZngWallet getWalletByOpenId(@Param("openid")String openid);
}