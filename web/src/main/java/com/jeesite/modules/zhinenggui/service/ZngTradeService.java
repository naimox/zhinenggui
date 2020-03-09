/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.zhinenggui.dao.ZngTradeDao;
import com.jeesite.modules.zhinenggui.entity.ZngTrade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 交易明细Service
 * @author lzy
 * @version 2018-08-28
 */
@Service
@Transactional(readOnly=true)
public class ZngTradeService extends CrudService<ZngTradeDao, ZngTrade> {
	
	/**
	 * 获取单条数据
	 * @param zhinengguiTrade
	 * @return
	 */
	@Override
	public ZngTrade get(ZngTrade zhinengguiTrade) {
		return super.get(zhinengguiTrade);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param zhinengguiTrade
	 * @return
	 */
	@Override
	public Page<ZngTrade> findPage(Page<ZngTrade> page, ZngTrade zhinengguiTrade) {
		return super.findPage(page, zhinengguiTrade);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param zhinengguiTrade
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ZngTrade zhinengguiTrade) {
		super.save(zhinengguiTrade);
	}
	
	/**
	 * 更新状态
	 * @param zhinengguiTrade
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ZngTrade zhinengguiTrade) {
		super.updateStatus(zhinengguiTrade);
	}
	
	/**
	 * 删除数据
	 * @param zhinengguiTrade
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ZngTrade zhinengguiTrade) {
		super.delete(zhinengguiTrade);
	}
	
}