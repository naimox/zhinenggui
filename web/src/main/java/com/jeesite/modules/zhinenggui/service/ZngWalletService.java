/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.zhinenggui.dao.ZngWalletDao;
import com.jeesite.modules.zhinenggui.entity.ZngWallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 钱包表Service
 * @author lzy
 * @version 2018-08-28
 */
@Service
@Transactional(readOnly=true)
public class ZngWalletService extends CrudService<ZngWalletDao, ZngWallet> {

	@Autowired
	private ZngWalletDao zhinengguiWalletDao;
	
	/**
	 * 获取单条数据
	 * @param zhinengguiWallet
	 * @return
	 */
	@Override
	public ZngWallet get(ZngWallet zhinengguiWallet) {
		return super.get(zhinengguiWallet);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param zhinengguiWallet
	 * @return
	 */
	@Override
	public Page<ZngWallet> findPage(Page<ZngWallet> page, ZngWallet zhinengguiWallet) {
		return super.findPage(page, zhinengguiWallet);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param zhinengguiWallet
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ZngWallet zhinengguiWallet) {
		super.save(zhinengguiWallet);
	}
	
	/**
	 * 更新状态
	 * @param zhinengguiWallet
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ZngWallet zhinengguiWallet) {
		super.updateStatus(zhinengguiWallet);
	}
	
	/**
	 * 删除数据
	 * @param zhinengguiWallet
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ZngWallet zhinengguiWallet) {
		super.delete(zhinengguiWallet);
	}

	@Transactional(readOnly=false)
	public ZngWallet getWalletByUserId(String userId) {
		ZngWallet wallet = zhinengguiWalletDao.getWalletByUserId(userId);
		if(wallet == null){
			wallet = new ZngWallet();
			wallet.setUserId(userId);
			wallet.setBalance(0d);
			this.save(wallet);
		}
		return wallet;
	}

	@Transactional(readOnly=false)
	public boolean saveCompanyWallet(String money, String companyUserId){
		boolean flag = true;
		try {
			if (StringUtils.isBlank(companyUserId)){
				return false;
			}
			ZngWallet wallet = getWalletByUserId(companyUserId);
			wallet.setBalance(wallet.getBalance() + Double.parseDouble(money));
			update(wallet);
		}catch (Exception e){
			flag = false;
		}
		return flag;
	}

	public ZngWallet getWalletByOpenId(String openid){
		return zhinengguiWalletDao.getWalletByOpenId(openid);
	}

	@Transactional(readOnly=false)
	public String withdraw(Double money, String openid) {
		try {
			if (StringUtils.isBlank(openid))
				return null;
			ZngWallet wallet = getWalletByOpenId(openid);
			if (wallet == null || wallet.getBalance() < money)
				return null;
			wallet.setBalance(wallet.getBalance() - money);
			save(wallet);
			return wallet.getUserId();
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

}