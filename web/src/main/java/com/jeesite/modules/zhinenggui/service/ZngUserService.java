/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.DataScope;
import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.zhinenggui.entity.ZngUser;
import com.jeesite.modules.zhinenggui.dao.ZngUserDao;

/**
 * 用户表Service
 * @author lzy
 * @version 2018-08-28
 */
@Service
@Transactional(readOnly=true)
public class ZngUserService extends CrudService<ZngUserDao, ZngUser> {

	@Autowired
	private ZngUserDao zhinengguiUserDao;
	
	/**
	 * 根据电话获取信息
	 * @param loginName
	 * @return	ZngUser
	 */
	public ZngUser findByLoginName(String loginName) {
		return zhinengguiUserDao.findByLoginName(loginName);
	}
	/**
	 * 根据电话获取信息
	 * @param loginName
	 * @return	List<ZngUser>
	 */
	public List<ZngUser> findByLoginNameReList(String loginName){
		return zhinengguiUserDao.findByLoginNameReList(loginName);
	}
	/**
	 * 根据电话获取信息
	 * @param tel
	 * @return
	 */
	public ZngUser findByTel(String tel) {
		
		return zhinengguiUserDao.findByTel(tel);
	}
	
	/**
	 * 获取单条数据
	 * @param zhinengguiUser
	 * @return
	 */
	@Override
	public ZngUser get(ZngUser zhinengguiUser) {
		return super.get(zhinengguiUser);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param zhinengguiUser
	 * @return
	 */
	@Override
	public Page<ZngUser> findPage(Page<ZngUser> page, ZngUser zhinengguiUser) {
		zhinengguiUser.getSqlMap().getDataScope().addFilter("userQx", "Office",
                "a.by_office","a.by_user", DataScope.CTRL_PERMI_HAVE);
		return super.findPage(page, zhinengguiUser);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param zhinengguiUser
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ZngUser zhinengguiUser) {
		super.save(zhinengguiUser);
	}
	
	/**
	 * 更新状态
	 * @param zhinengguiUser
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ZngUser zhinengguiUser) {
		super.updateStatus(zhinengguiUser);
	}
	
	/**
	 * 删除数据
	 * @param zhinengguiUser
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ZngUser zhinengguiUser) {
		super.delete(zhinengguiUser);
	}

    public ZngUser findOneByWxId(String wxId) {
		return zhinengguiUserDao.findOneByWxId(wxId);
    }
}