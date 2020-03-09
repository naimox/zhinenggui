/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.zhinenggui.entity.ZngWithdrawApplication;
import com.jeesite.modules.zhinenggui.dao.ZngWithdrawApplicationDao;

/**
 * zhinenggui_withdraw_applicationService
 * @author lfy
 * @version 2018-11-23
 */
@Service
@Transactional(readOnly=true)
public class ZngWithdrawApplicationService extends CrudService<ZngWithdrawApplicationDao, ZngWithdrawApplication> {
	@Autowired
	private ZngWithdrawApplicationDao zhinengguiWithdrawApplicationDao;

	/**
	 * 获取单条数据
	 * @param zhinengguiWithdrawApplication
	 * @return
	 */
	@Override
	public ZngWithdrawApplication get(ZngWithdrawApplication zhinengguiWithdrawApplication) {
		return super.get(zhinengguiWithdrawApplication);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param zhinengguiWithdrawApplication
	 * @return
	 */
	@Override
	public Page<ZngWithdrawApplication> findPage(Page<ZngWithdrawApplication> page, ZngWithdrawApplication zhinengguiWithdrawApplication) {
		return super.findPage(page, zhinengguiWithdrawApplication);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param zhinengguiWithdrawApplication
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ZngWithdrawApplication zhinengguiWithdrawApplication) {
		super.save(zhinengguiWithdrawApplication);
	}
	
	/**
	 * 更新状态
	 * @param zhinengguiWithdrawApplication
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ZngWithdrawApplication zhinengguiWithdrawApplication) {
		super.updateStatus(zhinengguiWithdrawApplication);
	}
	
	/**
	 * 删除数据
	 * @param zhinengguiWithdrawApplication
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ZngWithdrawApplication zhinengguiWithdrawApplication) {
		super.delete(zhinengguiWithdrawApplication);
	}

    public Collection<ZngWithdrawApplication> getWithdrawApplicationByUserId(String userId, String type) {
		return zhinengguiWithdrawApplicationDao.getWithdrawApplicationByUserId(userId, type);
    }

	public int adopt(String id) {
		return zhinengguiWithdrawApplicationDao.adopt(id);
	}

	@Transactional(readOnly=false)
	public void upApplicationType(String userId, double money, int type) {
		zhinengguiWithdrawApplicationDao.upApplicationType(userId, money, type);
	}

    public ZngWithdrawApplication getTop(String userId) {
		return zhinengguiWithdrawApplicationDao.getTop(userId);
    }
}