/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.zhinenggui.dao.ZngCodeStatisticsDao;
import com.jeesite.modules.zhinenggui.dao.ZngDeviceDao;
import com.jeesite.modules.zhinenggui.entity.ZngCodeStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 验证码统计Service
 * @author lfy
 * @version 2018-11-29
 */
@Service
@Transactional(readOnly=true)
public class ZngCodeStatisticsService extends CrudService<ZngCodeStatisticsDao, ZngCodeStatistics> {
	@Autowired
	private ZngCodeStatisticsDao zhinengguiCodeStatisticsDao;
	@Autowired
	private ZngDeviceDao zhinengguiDeviceDao;
	/**
	 * 获取单条数据
	 * @param zhinengguiCodeStatistics
	 * @return
	 */
	@Override
	public ZngCodeStatistics get(ZngCodeStatistics zhinengguiCodeStatistics) {
		return super.get(zhinengguiCodeStatistics);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param zhinengguiCodeStatistics
	 * @return
	 */
	@Override
	public Page<ZngCodeStatistics> findPage(Page<ZngCodeStatistics> page, ZngCodeStatistics zhinengguiCodeStatistics) {
		return super.findPage(page, zhinengguiCodeStatistics);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param zhinengguiCodeStatistics
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ZngCodeStatistics zhinengguiCodeStatistics) {
		super.save(zhinengguiCodeStatistics);
	}
	
	/**
	 * 更新状态
	 * @param zhinengguiCodeStatistics
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ZngCodeStatistics zhinengguiCodeStatistics) {
		super.updateStatus(zhinengguiCodeStatistics);
	}
	
	/**
	 * 删除数据
	 * @param zhinengguiCodeStatistics
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ZngCodeStatistics zhinengguiCodeStatistics) {
		super.delete(zhinengguiCodeStatistics);
	}

	@Transactional(readOnly=false)
	public ZngCodeStatistics getCodeStatisticsByDeviceId(String deviceId) {
		ZngCodeStatistics zhinengguiCodeStatistics = zhinengguiCodeStatisticsDao.getCodeStatisticsByDeviceId(deviceId);
		if (zhinengguiCodeStatistics == null){
			zhinengguiCodeStatistics = new ZngCodeStatistics();
			String byUser = zhinengguiDeviceDao.getOneByDeviceId(deviceId).getByUser();
			if (StringUtils.isNotEmpty(byUser)) {
				zhinengguiCodeStatistics.setUserBy(zhinengguiDeviceDao.getOneByDeviceId(deviceId).getByUser());
				zhinengguiCodeStatistics.setCodeNum(300);
				save(zhinengguiCodeStatistics);
			}else {
				zhinengguiCodeStatistics.setCodeNum(0);
			}
		}
		return zhinengguiCodeStatistics;
	}

	@Transactional(readOnly=false)
	public void reduceCodeNum(String id) {
		zhinengguiCodeStatisticsDao.reduceCodeNum(id, 1);
	}

	@Transactional(readOnly=false)
	public void reduceCodeNum(String id, int num) {
		if (num > 0) zhinengguiCodeStatisticsDao.reduceCodeNum(id, num);
	}

	@Transactional(readOnly=false)
	public ZngCodeStatistics getCodeStatisticsByUserId(String userId) {
		ZngCodeStatistics zhinengguiCodeStatistics = zhinengguiCodeStatisticsDao.getCodeStatisticsByUserId(userId);
		if (zhinengguiCodeStatistics == null){
			zhinengguiCodeStatistics = new ZngCodeStatistics();
			zhinengguiCodeStatistics.setCodeNum(300);
			zhinengguiCodeStatistics.setUserBy(userId);
			save(zhinengguiCodeStatistics);
		}
		return zhinengguiCodeStatistics;
	}
}