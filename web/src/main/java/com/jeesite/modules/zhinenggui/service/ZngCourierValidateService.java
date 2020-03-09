/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.zhinenggui.dao.ZngCourierValidateDao;
import com.jeesite.modules.zhinenggui.entity.ZngCourierValidate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 快递员信息验证表Service
 * @author lzy
 * @version 2018-08-28
 */
@Service
@Transactional(readOnly=true)
public class ZngCourierValidateService extends CrudService<ZngCourierValidateDao, ZngCourierValidate> {
	
	/**
	 * 获取单条数据
	 * @param zhinengguiCourierValidate
	 * @return
	 */
	@Override
	public ZngCourierValidate get(ZngCourierValidate zhinengguiCourierValidate) {
		return super.get(zhinengguiCourierValidate);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param zhinengguiCourierValidate
	 * @return
	 */
	@Override
	public Page<ZngCourierValidate> findPage(Page<ZngCourierValidate> page, ZngCourierValidate zhinengguiCourierValidate) {
		return super.findPage(page, zhinengguiCourierValidate);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param zhinengguiCourierValidate
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ZngCourierValidate zhinengguiCourierValidate) {
		super.save(zhinengguiCourierValidate);
	}
	
	/**
	 * 更新状态
	 * @param zhinengguiCourierValidate
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ZngCourierValidate zhinengguiCourierValidate) {
		super.updateStatus(zhinengguiCourierValidate);
	}
	
	/**
	 * 删除数据
	 * @param zhinengguiCourierValidate
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ZngCourierValidate zhinengguiCourierValidate) {
		super.delete(zhinengguiCourierValidate);
	}
	
}