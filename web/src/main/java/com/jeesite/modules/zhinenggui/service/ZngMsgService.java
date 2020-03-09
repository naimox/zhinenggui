/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.zhinenggui.dao.ZngMsgDao;
import com.jeesite.modules.zhinenggui.entity.ZngMsg;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * zhinenggui_msgService
 * @author lzy
 * @version 2018-12-24
 */
@Service
@Transactional(readOnly=true)
public class ZngMsgService extends CrudService<ZngMsgDao, ZngMsg> {
	
	/**
	 * 获取单条数据
	 * @param zhinengguiMsg
	 * @return
	 */
	@Override
	public ZngMsg get(ZngMsg zhinengguiMsg) {
		return super.get(zhinengguiMsg);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param zhinengguiMsg
	 * @return
	 */
	@Override
	public Page<ZngMsg> findPage(Page<ZngMsg> page, ZngMsg zhinengguiMsg) {
		return super.findPage(page, zhinengguiMsg);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param zhinengguiMsg
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ZngMsg zhinengguiMsg) {
		super.save(zhinengguiMsg);
	}
	
	/**
	 * 更新状态
	 * @param zhinengguiMsg
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ZngMsg zhinengguiMsg) {
		super.updateStatus(zhinengguiMsg);
	}
	
	/**
	 * 删除数据
	 * @param zhinengguiMsg
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ZngMsg zhinengguiMsg) {
		super.delete(zhinengguiMsg);
	}
	
}