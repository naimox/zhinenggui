/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.mobileMessage.sendingUtil.SmsSendUtil;
import com.jeesite.mobileMessage.sendingZtMsgUtil.ZtForeignSendingMsgUtil;
import com.jeesite.mobileMessage.sendingZtMsgUtil.ZtSendingMsgUtil;
import com.jeesite.modules.zhinenggui.dao.ZngCodeDao;
import com.jeesite.modules.zhinenggui.entity.ZngCode;
import com.jeesite.modules.zhinenggui.search.LostValidCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 验证码通用表Service
 * @author lzy
 * @version 2018-08-28
 */
@Service
@Transactional(readOnly=true)
public class ZngCodeService extends CrudService<ZngCodeDao, ZngCode> {
	
	
	@Autowired
	private ZngCodeDao zhinengguiCodeDao;
	/**
	 * 根据userID获取验证码
	 * @param phone
	 * @return
	 */
	public ZngCode getInfo(String phone, String type) {
		return zhinengguiCodeDao.getInfo(phone, type);
	}

	public ZngCode getByPhoneAndCode(ZngCode zhinengguiCode) {
		return zhinengguiCodeDao.getByPhoneAndCode(zhinengguiCode);
	}


	/**
	 * 获取单条数据
	 * @param zhinengguiCode
	 * @return
	 */
	@Override
	public ZngCode get(ZngCode zhinengguiCode) {
		return super.get(zhinengguiCode);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param zhinengguiCode
	 * @return
	 */
	@Override
	public Page<ZngCode> findPage(Page<ZngCode> page, ZngCode zhinengguiCode) {
		return super.findPage(page, zhinengguiCode);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param zhinengguiCode
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ZngCode zhinengguiCode) {
		super.save(zhinengguiCode);
	}
	
	/**
	 * 更新状态
	 * @param zhinengguiCode
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ZngCode zhinengguiCode) {
		super.updateStatus(zhinengguiCode);
	}
	
	/**
	 * 删除数据
	 * @param zhinengguiCode
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ZngCode zhinengguiCode) {
		super.delete(zhinengguiCode);
	}
	/**
	 * type 0:普通验证码,1:通知快递员,2:收件取件码,3:暂存取件码
	 * @param phone
	 * @param type
	 * @param zhinengguiCode
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly=false)
	public String getValidCode(String phone, String type, ZngCode zhinengguiCode, String userId){
		String result = "-1";
		String code = SmsSendUtil.getCode();
		String message = "";
		switch (type){
			case "0" ://普通验证码
				message += "【智能柜短信】您的验证码为:"+code;
//				message += "【PsarTaoOnline】 Your verification code is:"+code;
				break;
			case "1" ://通知快递员
				message += "【智能柜短信】已有快件到达快递箱，需要您联系用户进行寄送快件操作" + zhinengguiCode.getContent();
//				message += "【PsarTaoOnline】 Your verification code is:"+code;
				break;
			case "2" ://收件验证码
//				message +="【PsarTaoOnline】HURRAH! Your order is ready in our 'Luck & Lock' smart rt locker."
//						+zhinengguiCode.getContent()+ " and  use the code "+code+" to verify & "
//						+ "experience the easiest pickup method now. "
//						+ "If you have any question,please call ll 086986868."
//						+ "Valid for for 5 days then back in our office! "
//						+ "More 'Luck & Lock' will be placed in Phnom Penh soon! Here to discover right now (map): https://goo.gl/jZ6yNw";
				message += "【智能柜短信】您的快件取件码:" + code + zhinengguiCode.getContent();
				break;
			case "3" :
				message += "【智能柜短信】，您的暂存取件码为:" + code;
//				message += "【PsarTaoOnline】 Your verification code is:"+code;
				break;
			default:
				break;
		}
		try {
			result = SmsSendUtil.sendMessage(message,phone);
			if (!"0".equals(result))
				return result;
//			result = ZtForeignSendingMsgUtil.sendMsg(phone,message); //国外
//			String s[] = result.split(",");
//			if (!"1".equals(s[0].equalsIgnoreCase("1")))
//				return result;
			List<ZngCode> codeList = zhinengguiCodeDao.getCodeListByPhoneAndType(phone, type);
			if(codeList.size() > 0){
				if ("0".equalsIgnoreCase(type)){
					zhinengguiCodeDao.updateCodeByPhoneAndType(phone, type);
				}
			}
			//验证码临时表存放用户注册验证码
			zhinengguiCode.setUserId(userId);
			zhinengguiCode.setType(type);
			zhinengguiCode.setPhone(phone);
			zhinengguiCode.setCode(code);
			save(zhinengguiCode);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result ;
	}

	@Transactional(readOnly=false)
	public void updateStatusById(String id){
		zhinengguiCodeDao.updateStatusById(id);
	}

	@Transactional(readOnly=false)
	public void updateStatusByTelAndCode(String phone, String code){
		zhinengguiCodeDao.updateStatusByTelAndCode(phone, code);
	}
	@Transactional(readOnly=false)
	public void updateOrderCode6(String codeId) {
		zhinengguiCodeDao.updateOrderCode6(codeId);
	}
	
	@Transactional(readOnly=false)
    public LostValidCode getLostValidCode(String deviceId, String getCode) {
//		Date lastTime = new Date(new Date() .getTime() - 300000);
		List<LostValidCode> list = zhinengguiCodeDao.getLostValidCode(deviceId, getCode);
		if (list == null || list.isEmpty()){
			return null;
		}
		zhinengguiCodeDao.addStatusById(list.get(0).getId());
		return list.get(0);
    }

	public List<ZngCode> getValidCodeList(String phone, String orderId) {
		return zhinengguiCodeDao.getValidCodeList(phone, orderId);
	}

    public List<ZngCode> findByUserId(ZngCode zhinengguiCode) {
	    return zhinengguiCodeDao.findByUserId(zhinengguiCode);
    }
}