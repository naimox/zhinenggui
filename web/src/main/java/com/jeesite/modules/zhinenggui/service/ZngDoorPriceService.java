/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.service;

import java.util.List;

import com.jeesite.modules.zhinenggui.entity.ZngDoorPriceTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.zhinenggui.entity.ZngDoorPrice;
import com.jeesite.modules.zhinenggui.dao.ZngDoorPriceDao;

/**
 * 柜门价格表Service
 * @author lzy
 * @version 2018-08-28
 */
@Service
@Transactional(readOnly=true)
public class ZngDoorPriceService extends CrudService<ZngDoorPriceDao, ZngDoorPrice> {

	@Autowired
	ZngDoorPriceDao zhinengguiDoorPriceDao;
	/**
	 * 获取单条数据
	 * @param zhinengguiDoorPrice
	 * @return
	 */
	@Override
	public ZngDoorPrice get(ZngDoorPrice zhinengguiDoorPrice) {
		return super.get(zhinengguiDoorPrice);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param zhinengguiDoorPrice
	 * @return
	 */
	@Override
	public Page<ZngDoorPrice> findPage(Page<ZngDoorPrice> page, ZngDoorPrice zhinengguiDoorPrice) {
		return super.findPage(page, zhinengguiDoorPrice);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param zhinengguiDoorPrice
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ZngDoorPrice zhinengguiDoorPrice) {
		super.save(zhinengguiDoorPrice);
	}
	
	/**
	 * 更新状态
	 * @param zhinengguiDoorPrice
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ZngDoorPrice zhinengguiDoorPrice) {
		super.updateStatus(zhinengguiDoorPrice);
	}
	
	/**
	 * 删除数据
	 * @param zhinengguiDoorPrice
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ZngDoorPrice zhinengguiDoorPrice) {
		super.delete(zhinengguiDoorPrice);
	}

    public List<ZngDoorPriceTree> findTreeList() {
		return zhinengguiDoorPriceDao.findTreeList();
    }

    public ZngDoorPrice getDoorPriceByDoorId(String doorId){
		return zhinengguiDoorPriceDao.getDoorPriceByDoorId(doorId);
	}
}