/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.entity.Page;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.zhinenggui.entity.ZngDetail;
import com.jeesite.modules.zhinenggui.search.WalletDetail;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * zhinenggui_detailDAO接口
 * @author pfzheng
 * @version 2018-11-22
 */
@MyBatisDao
public interface ZngDetailDao extends CrudDao<ZngDetail> {

    List<ZngDetail> findDetailList(WalletDetail walletDetail);
}