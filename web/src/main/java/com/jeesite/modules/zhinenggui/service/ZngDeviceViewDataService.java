/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.zhinenggui.dao.ZngDeviceViewDataDao;
import com.jeesite.modules.zhinenggui.entity.ZngDeviceViewData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 视图数据Service
 * @author lzy
 * @version 2019-01-24
 */
@Service
@Transactional(readOnly=true)
public class ZngDeviceViewDataService extends CrudService<ZngDeviceViewDataDao, ZngDeviceViewData> {
	@Autowired
	private ZngDeviceViewDataDao zhinengguiDeviceViewDataDao;
	
	/**
	 * 获取单条数据
	 * @param zhinengguiDeviceViewData
	 * @return
	 */
	@Override
	public ZngDeviceViewData get(ZngDeviceViewData zhinengguiDeviceViewData) {
		return super.get(zhinengguiDeviceViewData);
	}
	/**
	 * 根据设备ID与设备编号获取设备图形化数据信息
	 * @param deviceId
	 * @param deviceNumber
	 * @return
	 */
	public ZngDeviceViewData getOneByIdAndNumber(String deviceId,String deviceNumber) {
		return zhinengguiDeviceViewDataDao.getOneByIdAndNumber(deviceId,deviceNumber);
	}
	/**
	 * 根据设备编号获取设备图形化数据信息
	 * @param deviceId
	 * @param deviceNumber
	 * @return
	 */
	public ZngDeviceViewData getOneByNumber(String deviceNumber) {
		return zhinengguiDeviceViewDataDao.getOneByNumber(deviceNumber);
	}
	/**
	 * 查询分页数据
	 * @param zhinengguiDeviceViewData 查询条件
	 * @param zhinengguiDeviceViewData.page 分页对象
	 * @return
	 */
	@Override
	public Page<ZngDeviceViewData> findPage(ZngDeviceViewData zhinengguiDeviceViewData) {
		return super.findPage(zhinengguiDeviceViewData);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param zhinengguiDeviceViewData
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ZngDeviceViewData zhinengguiDeviceViewData) {
		super.save(zhinengguiDeviceViewData);
	}
	
	/**
	 * 更新状态
	 * @param zhinengguiDeviceViewData
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ZngDeviceViewData zhinengguiDeviceViewData) {
		super.updateStatus(zhinengguiDeviceViewData);
	}
	
	/**
	 * 删除数据
	 * @param zhinengguiDeviceViewData
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ZngDeviceViewData zhinengguiDeviceViewData) {
		super.delete(zhinengguiDeviceViewData);
	}
	
}