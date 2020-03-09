/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.zhinenggui.dao.ZngVersionDao;
import com.jeesite.modules.zhinenggui.entity.ZngVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * zhinenggui_versionService
 * @author lnn
 * @version 2018-09-08
 */
@Service
@Transactional(readOnly=true)
public class ZngVersionService extends CrudService<ZngVersionDao, ZngVersion> {
	@Autowired
	private ZngVersionDao zhinengguiVersionDao;
	
	/**
	 * 获取单条数据
	 * @return
	 */
	public ZngVersion getInfoByPacketName(String packetName) {
		return zhinengguiVersionDao.getInfoByPacketName(packetName);
	}
	
	/**
	 * 获取单条数据
	 * @param zhinengguiVersion
	 * @return
	 */
	@Override
	public ZngVersion get(ZngVersion zhinengguiVersion) {
		return super.get(zhinengguiVersion);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param zhinengguiVersion
	 * @return
	 */
	@Override
	public Page<ZngVersion> findPage(Page<ZngVersion> page, ZngVersion zhinengguiVersion) {
		return super.findPage(page, zhinengguiVersion);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param zhinengguiVersion
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ZngVersion zhinengguiVersion) {
		super.save(zhinengguiVersion);
	}
	
	/**
	 * 更新状态
	 * @param zhinengguiVersion
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ZngVersion zhinengguiVersion) {
		super.updateStatus(zhinengguiVersion);
	}
	
	/**
	 * 删除数据
	 * @param zhinengguiVersion
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ZngVersion zhinengguiVersion) {
		super.delete(zhinengguiVersion);
	}
	
}