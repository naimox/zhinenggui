/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.zhinenggui.dao.ZngCabinetsDao;
import com.jeesite.modules.zhinenggui.entity.ZngCabinets;
import com.jeesite.modules.zhinenggui.search.DoorPriceSearch;
import com.jeesite.modules.zhinenggui.search.DoorSizeCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 柜门柜门表Service
 * @author lzy
 * @version 2018-08-28
 */
@Service
@Transactional(readOnly=true)
public class ZngCabinetsService extends CrudService<ZngCabinetsDao, ZngCabinets> {

	@Autowired
	ZngCabinetsDao zhinengguiCabinetsDao;
	/**
	 * 获取单条数据
	 * @param zhinengguiCabinets
	 * @return
	 */
	@Override
	public ZngCabinets get(ZngCabinets zhinengguiCabinets) {
		return super.get(zhinengguiCabinets);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param zhinengguiCabinets
	 * @return
	 */
	@Override
	public Page<ZngCabinets> findPage(Page<ZngCabinets> page, ZngCabinets zhinengguiCabinets) {
		return super.findPage(page, zhinengguiCabinets);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param zhinengguiCabinets
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ZngCabinets zhinengguiCabinets) {
		super.save(zhinengguiCabinets);
	}
	
	/**
	 * 更新状态
	 * @param zhinengguiCabinets
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ZngCabinets zhinengguiCabinets) {
		super.updateStatus(zhinengguiCabinets);
	}
	
	/**
	 * 删除数据
	 * @param zhinengguiCabinets
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ZngCabinets zhinengguiCabinets) {
		super.delete(zhinengguiCabinets);
	}
	


	public List<ZngCabinets> getAllListByDeviceId(String deviceId){
		return zhinengguiCabinetsDao.getAllListByDeviceId(deviceId);
	}

	public ZngCabinets getOneByDeviceId(String deviceId, String doorSize, String money, String sendDoorMoney){
		return zhinengguiCabinetsDao.getOneByDeviceId(deviceId, doorSize, money, sendDoorMoney);
	}

	public ZngCabinets getOneByDeviceId2(String deviceId, String doorSize, String money){
		return zhinengguiCabinetsDao.getOneByDeviceId2(deviceId, doorSize, money);
	}

	public List<DoorSizeCount> getCount(String deviceId){
		return zhinengguiCabinetsDao.getCount(deviceId);
	}

	public List<DoorSizeCount> getCount1(String deviceId){
		return zhinengguiCabinetsDao.getCount1(deviceId);
	}

	public List<ZngCabinets> getPlateNum(String deviceId) {
		return zhinengguiCabinetsDao.getPlateNum(deviceId);
	}

	public ZngCabinets getOneByTotalDoorId(String deviceId, String totalDoorId){
		return zhinengguiCabinetsDao.getOneByTotalDoorId(deviceId, totalDoorId);
	}

	public ZngCabinets getOneByTotalDoorIdNoStatus(String deviceId, String totalDoorId){
		return zhinengguiCabinetsDao.getOneByTotalDoorIdNoStatus(deviceId, totalDoorId);
	}

	public List<DoorPriceSearch> getDoorPriceList(String deviceId, String doorSize) {
		return zhinengguiCabinetsDao.getDoorPriceList(deviceId, doorSize);
	}


	public DoorPriceSearch getDoorDetailById(String doorId) {
		return zhinengguiCabinetsDao.getDoorDetailById(doorId);
	}

	public List<ZngCabinets> getListByDeviceId(String deviceId) {
		return zhinengguiCabinetsDao.getListByDeviceId(deviceId);
	}
}