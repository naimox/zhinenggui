/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.zhinenggui.dao.ZngHelpDao;
import com.jeesite.modules.zhinenggui.entity.ZngHelp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 帮助信息Service
 * @author lzy
 * @version 2018-12-26
 */
@Service
@Transactional(readOnly=true)
public class ZngHelpService extends CrudService<ZngHelpDao, ZngHelp> {
	
	/**
	 * 获取单条数据
	 * @param zhinengguiHelp
	 * @return
	 */
	@Override
	public ZngHelp get(ZngHelp zhinengguiHelp) {
		return super.get(zhinengguiHelp);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param zhinengguiHelp
	 * @return
	 */
	@Override
	public Page<ZngHelp> findPage(Page<ZngHelp> page, ZngHelp zhinengguiHelp) {
		return super.findPage(page, zhinengguiHelp);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param zhinengguiHelp
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ZngHelp zhinengguiHelp) {
		super.save(zhinengguiHelp);
	}
	
	/**
	 * 更新状态
	 * @param zhinengguiHelp
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ZngHelp zhinengguiHelp) {
		super.updateStatus(zhinengguiHelp);
	}
	
	/**
	 * 删除数据
	 * @param zhinengguiHelp
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ZngHelp zhinengguiHelp) {
		super.delete(zhinengguiHelp);
	}
	
}