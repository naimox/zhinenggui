/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.zhinenggui.dao.ZngPointNominateDao;
import com.jeesite.modules.zhinenggui.entity.ZngPointNominate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 点位推荐表Service
 * @author lzy
 * @version 2018-12-26
 */
@Service
@Transactional(readOnly=true)
public class ZngPointNominateService extends CrudService<ZngPointNominateDao, ZngPointNominate> {
	
	/**
	 * 获取单条数据
	 * @param zhinengguiPointNominate
	 * @return
	 */
	@Override
	public ZngPointNominate get(ZngPointNominate zhinengguiPointNominate) {
		return super.get(zhinengguiPointNominate);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param zhinengguiPointNominate
	 * @return
	 */
	@Override
	public Page<ZngPointNominate> findPage(Page<ZngPointNominate> page, ZngPointNominate zhinengguiPointNominate) {
		return super.findPage(page, zhinengguiPointNominate);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param zhinengguiPointNominate
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ZngPointNominate zhinengguiPointNominate) {
		super.save(zhinengguiPointNominate);
	}
	
	/**
	 * 更新状态
	 * @param zhinengguiPointNominate
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ZngPointNominate zhinengguiPointNominate) {
		super.updateStatus(zhinengguiPointNominate);
	}
	
	/**
	 * 删除数据
	 * @param zhinengguiPointNominate
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ZngPointNominate zhinengguiPointNominate) {
		super.delete(zhinengguiPointNominate);
	}
	
}