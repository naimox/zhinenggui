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
import com.jeesite.modules.zhinenggui.entity.ZngImage;
import com.jeesite.modules.zhinenggui.dao.ZngImageDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * zhinenggui_imageService
 * @author lnn
 * @version 2018-09-06
 */
@Service
@Transactional(readOnly=true)
public class ZngImageService extends CrudService<ZngImageDao, ZngImage> {
	
	@Autowired
	private ZngImageDao zhinengguiImageDao;
	
	/**
	 * 根据设备号找到该设备下的所有图片
	 * @param zhinengguiImage
	 * @return
	 */
	public List<ZngImage> findImageListByDeviceId(String deviceId) {
		
		return zhinengguiImageDao.findImageListByDeviceId(deviceId);
	}
	
	/**
	 * 获取单条数据
	 * @param zhinengguiImage
	 * @return
	 */
	@Override
	public ZngImage get(ZngImage zhinengguiImage) {
		return super.get(zhinengguiImage);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param zhinengguiImage
	 * @return
	 */
	@Override
	public Page<ZngImage> findPage(Page<ZngImage> page, ZngImage zhinengguiImage) {
		return super.findPage(page, zhinengguiImage);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param zhinengguiImage
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ZngImage zhinengguiImage) {
		super.save(zhinengguiImage);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(zhinengguiImage.getId(), "zhinengguiImage_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(zhinengguiImage.getId(), "zhinengguiImage_file");
	}
	
	/**
	 * 更新状态
	 * @param zhinengguiImage
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ZngImage zhinengguiImage) {
		super.updateStatus(zhinengguiImage);
	}
	
	/**
	 * 删除数据
	 * @param zhinengguiImage
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ZngImage zhinengguiImage) {
		super.delete(zhinengguiImage);
	}
	
}