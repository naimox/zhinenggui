/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.zhinenggui.dao.ZngFunctionImagesDao;
import com.jeesite.modules.zhinenggui.entity.ZngFunctionImages;
import com.jeesite.modules.file.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 综合图片Service
 * @author lzy
 * @version 2019-01-14
 */
@Service
@Transactional(readOnly=true)
public class ZngFunctionImagesService extends CrudService<ZngFunctionImagesDao, ZngFunctionImages> {
	@Autowired
	private ZngFunctionImagesDao zhinengguiDeviceDao;
	/**
	 * 获取单条数据
	 * @param zhinengguiFunctionImages
	 * @return
	 */
	@Override
	public ZngFunctionImages get(ZngFunctionImages zhinengguiFunctionImages) {
		return super.get(zhinengguiFunctionImages);
	}
	
	/**
	 * 查询分页数据
	 * @param zhinengguiFunctionImages 查询条件
	 * @param zhinengguiFunctionImages.page 分页对象
	 * @return
	 */
	@Override
	public Page<ZngFunctionImages> findPage(ZngFunctionImages zhinengguiFunctionImages) {
		return super.findPage(zhinengguiFunctionImages);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param zhinengguiFunctionImages
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ZngFunctionImages zhinengguiFunctionImages) {
		super.save(zhinengguiFunctionImages);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(zhinengguiFunctionImages.getId(), "zhinengguiFunctionImages_image");
	}
	
	/**
	 * 更新状态
	 * @param zhinengguiFunctionImages
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ZngFunctionImages zhinengguiFunctionImages) {
		super.updateStatus(zhinengguiFunctionImages);
	}
	
	/**
	 * 删除数据
	 * @param zhinengguiFunctionImages
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ZngFunctionImages zhinengguiFunctionImages) {
		super.delete(zhinengguiFunctionImages);
	}
	/**
	 * 获取单条数据
	 * @param zhinengguiFunctionImages
	 * @return
	 */
	public ZngFunctionImages getByUserIdAndType(String userId,String type) {
		return zhinengguiDeviceDao.getByUserIdAndType(userId, type);
	}
	
	
}