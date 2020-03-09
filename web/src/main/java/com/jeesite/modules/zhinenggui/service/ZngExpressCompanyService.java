/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.zhinenggui.dao.ZngExpressCompanyDao;
import com.jeesite.modules.zhinenggui.entity.ZngExpressCompany;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 快递公司表Service
 * @author lzy
 * @version 2018-08-28
 */
@Service
@Transactional(readOnly=true)
public class ZngExpressCompanyService extends CrudService<ZngExpressCompanyDao, ZngExpressCompany> {
	
	/**
	 * 获取单条数据
	 * @param zhinengguiExpressCompany
	 * @return
	 */
	@Override
	public ZngExpressCompany get(ZngExpressCompany zhinengguiExpressCompany) {
		return super.get(zhinengguiExpressCompany);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param zhinengguiExpressCompany
	 * @return
	 */
	@Override
	public Page<ZngExpressCompany> findPage(Page<ZngExpressCompany> page, ZngExpressCompany zhinengguiExpressCompany) {
		return super.findPage(page, zhinengguiExpressCompany);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param zhinengguiExpressCompany
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ZngExpressCompany zhinengguiExpressCompany) {
		super.save(zhinengguiExpressCompany);
	}
	
	/**
	 * 更新状态
	 * @param zhinengguiExpressCompany
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ZngExpressCompany zhinengguiExpressCompany) {
		super.updateStatus(zhinengguiExpressCompany);
	}
	
	/**
	 * 删除数据
	 * @param zhinengguiExpressCompany
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ZngExpressCompany zhinengguiExpressCompany) {
		super.delete(zhinengguiExpressCompany);
	}
	
}