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
import com.jeesite.modules.zhinenggui.entity.ZngDevice;
import com.jeesite.modules.zhinenggui.search.FileImageSearch;
import com.jeesite.modules.zhinenggui.dao.ZngDeviceDao;
import com.jeesite.modules.file.entity.FileUpload;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.jeesite.modules.sys.service.DataScopeService;

/**
 * 设备表Service
 * @author lzy
 * @version 2018-08-28
 */
@Service("zhinengguiDeviceService")
@Transactional(readOnly=true)
public class ZngDeviceService extends CrudService<ZngDeviceDao, ZngDevice> {

	@Autowired
	private ZngDeviceDao zhinengguiDeviceDao;
	/**
	 * 获取单条数据
	 * @param zhinengguiDevice
	 * @return
	 */
	@Override
	public ZngDevice get(ZngDevice zhinengguiDevice) {
		return super.get(zhinengguiDevice);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param zhinengguiDevice
	 * @return
	 */
	@Override
	public Page<ZngDevice> findPage(Page<ZngDevice> page, ZngDevice zhinengguiDevice) {
		zhinengguiDevice.getSqlMap().getDataScope().addFilter("dsf", "Office",
                "a.by_office","a.by_user", DataScope.CTRL_PERMI_HAVE);
		return super.findPage(page, zhinengguiDevice);
	}
	/**
	 * 查询设备
	 * @return
	 */
	@Override
	public List<ZngDevice> findList(ZngDevice zhinengguiDevice){
		return super.findList(zhinengguiDevice);
	}
	/**
	 * 保存数据（插入或更新）
	 * @param zhinengguiDevice
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ZngDevice zhinengguiDevice) {
		super.save(zhinengguiDevice);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(zhinengguiDevice.getId(), "zhinengguiDevice_image");//轮播图
		FileUploadUtils.saveFileUpload(zhinengguiDevice.getId(), "zhinengguiDevice_protect_image");//滚动图
	}
	
	
	/**
	 * 获取设备下所有订单的所有图片
	 * @param deviceId
	 */
	@Transactional(readOnly=false)
	public List<FileImageSearch> getFileImageList(String deviceId) {
		return zhinengguiDeviceDao.getFileImageList(deviceId);
	}
	/**
	 * 获取设备下所有订单的所有图片
	 * @param deviceId
	 */
	@Transactional(readOnly=false)
	public List<FileUpload> getFileDeviceImageList(String deviceId) {
		return zhinengguiDeviceDao.getFileDeviceImageList(deviceId);
	}
	/**
	 * 更新状态
	 * @param zhinengguiDevice
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ZngDevice zhinengguiDevice) {
		super.updateStatus(zhinengguiDevice);
	}
	
	/**
	 * 删除数据
	 * @param zhinengguiDevice
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ZngDevice zhinengguiDevice) {
		super.delete(zhinengguiDevice);
	}

	public ZngDevice getByDeviceId(String deviceId){
		return zhinengguiDeviceDao.getOneByDeviceId(deviceId);
	}
	
}