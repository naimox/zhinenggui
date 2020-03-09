/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.zhinenggui.dao.ZngDetailDao;
import com.jeesite.modules.zhinenggui.entity.ZngDetail;
import com.jeesite.modules.zhinenggui.search.WalletDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * zhinenggui_detailService
 * @author pfzheng
 * @version 2018-11-22
 */
@Service
@Transactional(readOnly=true)
public class ZngDetailService extends CrudService<ZngDetailDao, ZngDetail> {

	@Autowired
	ZngDetailDao zhinengguiDetailDao;
	/**
	 * 获取单条数据
	 * @param zhinengguiDetail
	 * @return
	 */
	@Override
	public ZngDetail get(ZngDetail zhinengguiDetail) {
		return super.get(zhinengguiDetail);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param zhinengguiDetail
	 * @return
	 */
	@Override
	public Page<ZngDetail> findPage(Page<ZngDetail> page, ZngDetail zhinengguiDetail) {
		return super.findPage(page, zhinengguiDetail);
	}

	public List<ZngDetail> findDetailList(WalletDetail walletDetail){
		return zhinengguiDetailDao.findDetailList(walletDetail);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param zhinengguiDetail
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ZngDetail zhinengguiDetail) {
		super.save(zhinengguiDetail);
	}
	
	/**
	 * 更新状态
	 * @param zhinengguiDetail
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ZngDetail zhinengguiDetail) {
		super.updateStatus(zhinengguiDetail);
	}
	
	/**
	 * 删除数据
	 * @param zhinengguiDetail
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ZngDetail zhinengguiDetail) {
		super.delete(zhinengguiDetail);
	}

//	@Transactional(readOnly=true)
//	public void findPageM(ZngDetail zhinengguiDetail, Date dateGte, Date dateLte, Double moneyGte, Double moneyLte, Page page) {
//		zhinengguiDetailDao.findListM(dateGte, dateGte, moneyGte, moneyGte, zhinengguiDetail.getType(), zhinengguiDetail.getDeviceId());
//	}
}