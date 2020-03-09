/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.zhinenggui.dao.ZngCardDao;
import com.jeesite.modules.zhinenggui.entity.ZngCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ic卡表Service
 * @author pfzheng
 * @version 2018-12-13
 */
@Service
@Transactional(readOnly=true)
public class ZngCardService extends CrudService<ZngCardDao, ZngCard> {

	@Autowired
	private ZngCardDao zhinengguiCardDao;
	
	/**
	 * 获取单条数据
	 * @param zhinengguiCard
	 * @return
	 */
	@Override
	public ZngCard get(ZngCard zhinengguiCard) {
		return super.get(zhinengguiCard);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param zhinengguiCard
	 * @return
	 */
	@Override
	public Page<ZngCard> findPage(Page<ZngCard> page, ZngCard zhinengguiCard) {
		return super.findPage(page, zhinengguiCard);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param zhinengguiCard
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ZngCard zhinengguiCard) {
		super.save(zhinengguiCard);
	}
	
	/**
	 * 更新状态
	 * @param zhinengguiCard
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ZngCard zhinengguiCard) {
		super.updateStatus(zhinengguiCard);
	}
	
	/**
	 * 删除数据
	 * @param zhinengguiCard
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ZngCard zhinengguiCard) {
		super.delete(zhinengguiCard);
	}

    public ZngCard checkDoorIdExist(String totalDoorId, String id) {
		return zhinengguiCardDao.checkDoorIdExist(totalDoorId, id);
    }

	public ZngCard checkCardIdExist(String cardId, String id) {
		return zhinengguiCardDao.checkCardIdExist(cardId, id);
	}

	public ZngCard findByCardId(String cardId) {
		return zhinengguiCardDao.findByCardId(cardId);
	}

	public ZngCard findByDoorId(String doorId) {
		return zhinengguiCardDao.findByDoorId(doorId);
	}

    public ZngCard getByCardNumAndDeviceId(String cardNum, String deviceId) {
		return zhinengguiCardDao.getByCardNumAndDeviceId(cardNum, deviceId);
    }

}