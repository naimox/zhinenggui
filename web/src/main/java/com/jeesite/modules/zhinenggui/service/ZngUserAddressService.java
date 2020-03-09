/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.zhinenggui.entity.ZngUserAddress;
import com.jeesite.modules.zhinenggui.dao.ZngUserAddressDao;

/**
 * 用户地址表Service
 * @author lzy
 * @version 2018-08-28
 */
@Service
@Transactional(readOnly=true)
public class ZngUserAddressService extends CrudService<ZngUserAddressDao, ZngUserAddress> {
	
	@Autowired
	private ZngUserAddressDao zhinengguiUserAddressDao;
	
	/**
	 * 根据用户ID来获取该用户下的所有地址信息
	 * @return
	 */
	public List<ZngUserAddress> findByUserId(String userId) {
		return zhinengguiUserAddressDao.findByUserId(userId);
	}
	
	/**
	 * 获取单条数据
	 * @param zhinengguiUserAddress
	 * @return
	 */
	@Override
	public ZngUserAddress get(ZngUserAddress zhinengguiUserAddress) {
		return super.get(zhinengguiUserAddress);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param zhinengguiUserAddress
	 * @return
	 */
	@Override
	public Page<ZngUserAddress> findPage(Page<ZngUserAddress> page, ZngUserAddress zhinengguiUserAddress) {
		return super.findPage(page, zhinengguiUserAddress);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param zhinengguiUserAddress
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ZngUserAddress zhinengguiUserAddress) {
		super.save(zhinengguiUserAddress);
	}
	
	/**
	 * 更新状态
	 * @param zhinengguiUserAddress
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ZngUserAddress zhinengguiUserAddress) {
		super.updateStatus(zhinengguiUserAddress);
	}
	
	/**
	 * 删除数据
	 * @param zhinengguiUserAddress
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ZngUserAddress zhinengguiUserAddress) {
		super.delete(zhinengguiUserAddress);
	}
	
}