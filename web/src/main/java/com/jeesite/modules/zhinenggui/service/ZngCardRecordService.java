/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.zhinenggui.dao.ZngCardRecordDao;
import com.jeesite.modules.zhinenggui.entity.ZngCardRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ic卡操作记录表Service
 * @author lfy
 * @version 2018-12-26
 */
@Service
@Transactional(readOnly=true)
public class ZngCardRecordService extends CrudService<ZngCardRecordDao, ZngCardRecord> {
	
	/**
	 * 获取单条数据
	 * @param zhinengguiCardRecord
	 * @return
	 */
	@Override
	public ZngCardRecord get(ZngCardRecord zhinengguiCardRecord) {
		return super.get(zhinengguiCardRecord);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param zhinengguiCardRecord
	 * @return
	 */
	@Override
	public Page<ZngCardRecord> findPage(Page<ZngCardRecord> page, ZngCardRecord zhinengguiCardRecord) {
		return super.findPage(page, zhinengguiCardRecord);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param zhinengguiCardRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ZngCardRecord zhinengguiCardRecord) {
		super.save(zhinengguiCardRecord);
	}
	
	/**
	 * 更新状态
	 * @param zhinengguiCardRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ZngCardRecord zhinengguiCardRecord) {
		super.updateStatus(zhinengguiCardRecord);
	}
	
	/**
	 * 删除数据
	 * @param zhinengguiCardRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ZngCardRecord zhinengguiCardRecord) {
		super.delete(zhinengguiCardRecord);
	}
	
}