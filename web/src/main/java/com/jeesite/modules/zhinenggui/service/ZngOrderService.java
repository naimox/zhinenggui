/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.jeesite.modules.zhinenggui.search.CabinetsAndOrder;
import com.jeesite.modules.zhinenggui.search.CourierOrderDetail;
import com.jeesite.modules.zhinenggui.search.OrderDetail;
import com.jeesite.modules.zhinenggui.search.OrderSearch;
import com.jeesite.modules.file.utils.FileUploadUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.service.CrudService;
import com.jeesite.common.service.QueryService;
import com.jeesite.modules.common.AuthorizedUser;
import com.jeesite.modules.zhinenggui.dao.ZngOrderDao;
import com.jeesite.modules.zhinenggui.entity.ZngOrder;
import com.jeesite.modules.zhinenggui.entity.ZngUser;
import com.jeesite.modules.sys.utils.UserUtils;

/**
 * 订单表Service
 * @author lzy
 * @version 2018-08-28
 */
@Service
@Transactional(readOnly=true)
public class ZngOrderService extends CrudService<ZngOrderDao, ZngOrder> {

	@Autowired
	ZngOrderDao zhinengguiOrderDao;
	/**
	 * 获取单条数据
	 * @param zhinengguiOrder
	 * @return
	 */
	@Override
	public ZngOrder get(ZngOrder zhinengguiOrder) {
		return super.get(zhinengguiOrder);
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param zhinengguiOrder
	 * @return
	 */
	@Override
	public Page<ZngOrder> findPage(Page<ZngOrder> page, ZngOrder zhinengguiOrder) {
		return super.findPage(page, zhinengguiOrder);
	}
	/**
	 * 自定义分页查询
	 * @param page
	 * @param zhinengguiOrder
	 * @return
	 */
	public Page<OrderSearch> findPage(Page<OrderSearch> page, OrderSearch zhinengguiOrder,String sPageSize,String sPageNo) {
		List<OrderSearch> orderSearchPage = zhinengguiOrderDao.findList(zhinengguiOrder);
		Integer pageSize = 20;
		Integer pageNo = 1;
		page.setCount(orderSearchPage.size());
		if(StringUtils.isNotBlank(sPageSize)){
			pageSize = Integer.parseInt(sPageSize);
		}
		if(StringUtils.isNotBlank(sPageNo)){
			pageNo = Integer.parseInt(sPageNo);
		}
		if (orderSearchPage.size() > pageNo*pageSize) {
			page.setList(orderSearchPage.subList((pageNo-1)*pageSize, pageNo*pageSize));
		}else{
			if (orderSearchPage.size() < (pageNo - 1) * pageSize) {
				page.setList(orderSearchPage.subList(0, orderSearchPage.size()));
				page.setPageNo(1);
			}else {
				page.setList(orderSearchPage.subList((pageNo-1)*pageSize, orderSearchPage.size()));
			}
		}
		
		return page;
	}
	public List<OrderSearch> findList(OrderSearch zhinengguiOrder) {

		return zhinengguiOrderDao.findList(zhinengguiOrder);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param zhinengguiOrder
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ZngOrder zhinengguiOrder) {
		super.save(zhinengguiOrder);
		FileUploadUtils.saveFileUpload(zhinengguiOrder.getId(), "zhinengguiOrder_image");
	}



	/**
	 * 保存数据（插入或更新）
	 * @param zhinengguiOrder
	 */
	@Transactional(readOnly=false)
	public void add(ZngOrder zhinengguiOrder) {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");
		Date date = new Date();
		String sendTime = sdf.format(date);
		zhinengguiOrder.setSenderTime(sendTime);//存件时间

		AuthorizedUser currentuser = (AuthorizedUser) UserUtils.getCache("zhinengguiUserCache");
		zhinengguiOrder.setUserId(currentuser.getUser().getId());

		super.save(zhinengguiOrder);
		FileUploadUtils.saveFileUpload(zhinengguiOrder.getId(), "zhinengguiOrder_image");
	}

	/**
	 * 更新状态
	 * @param zhinengguiOrder
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ZngOrder zhinengguiOrder) {
		super.updateStatus(zhinengguiOrder);
	}

	/**
	 * 删除数据
	 * @param zhinengguiOrder
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ZngOrder zhinengguiOrder) {
		super.delete(zhinengguiOrder);
	}

	public ZngOrder getOrderByExpressNumber(String expressNumber){
		return zhinengguiOrderDao.getOrderByExpressNumber(expressNumber);
	}

	public List<ZngOrder> getList(ZngOrder zhinengguiOrder){
		List<ZngOrder> list = zhinengguiOrderDao.findList(zhinengguiOrder);
		return list;
	}

	public List<ZngOrder> getToSendOrderByCourier(String deviceId){
		return zhinengguiOrderDao.getToSendOrderByCourier(deviceId);
	}

	/**
	 * 生成订单号
	 */
	public String genOrderCode(){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		stringBuffer.append((int)(Math.random()*900)+100);
		return stringBuffer.toString();
	}

	public CabinetsAndOrder getDoorByDeviceIdAndCode(String deviceId, String validCode){
		return zhinengguiOrderDao.getDoorByDeviceIdAndCode(validCode, deviceId);
	}

	public CabinetsAndOrder getDoorByDeviceIdAndSize(String deviceId, String doorSize){
		return zhinengguiOrderDao.getDoorByDeviceIdAndSize(deviceId, doorSize);
	}

	public CabinetsAndOrder getDoorByOrderIdAndPhone(String sendDeviceId, String phone) {
		return zhinengguiOrderDao.getDoorByOrderIdAndPhone(sendDeviceId, phone);
	}

	/**
	 *
	 */
	public CabinetsAndOrder getDoorByDeviceId(String deviceId){
		return zhinengguiOrderDao.getDoorByDeviceId(deviceId);
	}


	/**
	 * 计算此次取件柜门总共花费金额
	 * @param cabinetsAndOrder
	 * @return
	 */
	public Float getOrderMoney(CabinetsAndOrder cabinetsAndOrder){
		float money = Float.parseFloat(cabinetsAndOrder.getMoney());
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String courierSendTime = cabinetsAndOrder.getCourierSendTime();
		String overType = cabinetsAndOrder.getOverType();
		String overMoney = cabinetsAndOrder.getOverMoney();
		String ovewTime = cabinetsAndOrder.getOverTime();
		String overUnit = cabinetsAndOrder.getOverUnit();
		int iOverUtit = Integer.parseInt(overUnit);
		if (iOverUtit == 0){
			iOverUtit = 1;
		}
		int i = 1;
		switch(overType){
			case "0":
				i *= 1000;
				break;
			case "1":
				i *= 1000 * 60;
				break;
			case "2":
				i *= 1000 * 60 * 60;
				break;
			case "3":
				i *= 1000 * 60 * 60 * 24;
				break;
			default:
				break;
		}
		int count = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			count = Math.round((sdf.parse(now).getTime() - sdf.parse(courierSendTime).getTime())/i);
			count = count > Integer.parseInt(ovewTime) ? count - Integer.parseInt(ovewTime) : 0;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		money = money + (count / iOverUtit) * Float.parseFloat(overMoney);
		return money;
	}

	public Float getOrderMoney1(CabinetsAndOrder cabinetsAndOrder){
		// 当前时间
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		// 起存时间
		String senderTime = cabinetsAndOrder.getSenderTime();
		// 预存金额
		float money = Float.parseFloat(cabinetsAndOrder.getMoney1());
		// 预存时间
		String ovewTime = cabinetsAndOrder.getOverTime1();
		// 超出金额
		String overMoney = cabinetsAndOrder.getOverMoney1();
		// 超出时间
		String overUnit = cabinetsAndOrder.getOverUnit1();
		int iOverUtit = Integer.parseInt(overUnit);


		// 计价类型（无用）
		String overType = cabinetsAndOrder.getOverType1();


		if (iOverUtit == 0){
			iOverUtit = 1;
		}
		int i = 1;
		switch(overType){
			case "0":
				i *= 1000;
				break;
			case "1":
				i *= 1000 * 60;
				break;
			case "2":
				i *= 1000 * 60 * 60;
				break;
			case "3":
				i *= 1000 * 60 * 60 * 24;
				break;
			default:
				break;
		}
		float count = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			// 总共存了多少分钟
			count = (sdf.parse(now).getTime() - sdf.parse(senderTime).getTime())/60000;
			// 如果总共存的时间超过了预存支付的时间，则计算出超出的时间
			count = count > Integer.parseInt(ovewTime) ? count - Integer.parseInt(ovewTime) : 0;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 计算，并向上取整
		BigDecimal times = new BigDecimal(Math.ceil(count/iOverUtit));
		BigDecimal needPayPrice = times.multiply(new BigDecimal(overMoney));
		return needPayPrice.floatValue();
	}


	/**
	 * 计算用户本次寄件的金钱总额
	 * @param cabinetsAndOrder
	 * @return
	 */
	public Float getSendOrderMoney(CabinetsAndOrder cabinetsAndOrder,ZngOrder zhinengguiOrder){

		String payType = zhinengguiOrder.getPaymentTpye();//获得支付类型 (现付0 到付1)
		if(payType.equals("0")){
			//计算快递费用

		}

		//计算柜费
		float money = Float.parseFloat(cabinetsAndOrder.getMoney());

		return money;
	}

	/**
	 * 根据快递员id查询此快递员送货信息中，未取件订单信息
	 */
	public List<OrderDetail> getNotGetOrder(String userId){
		return zhinengguiOrderDao.getNotGetOrder(userId);
	}

	public List<OrderDetail> getOrderByUserIdAndDeviceId(String deviceId, String id) {
		return zhinengguiOrderDao.getOrderByUserIdAndDeviceId(deviceId, id);
	}

	public List<CourierOrderDetail> getCourierOrder(String deviceId, String userId) {
		return zhinengguiOrderDao.getCourierOrder(deviceId, userId);
	}

	public List<CourierOrderDetail> getNotDeviceOrderList(String deviceId) {
		return zhinengguiOrderDao.getNotDeviceOrderList(deviceId);
	}

	public List<OrderSearch> getOrderListByPhone(String deviceId, String phone) {
		return zhinengguiOrderDao.getOrderListByPhone(deviceId, phone);
	}

	public List<OrderSearch> getOrderListByPhoneNoState(String deviceId, String phone) {
		return zhinengguiOrderDao.getOrderListByPhoneNoState(deviceId, phone);
	}
	public List<OrderSearch> getOrderListByPhoneNoOver(String deviceId, String phone) {
		return zhinengguiOrderDao.getOrderListByPhoneNoOver(deviceId, phone);
	}
	
	public List<OrderSearch> getOverOrderListByPhone(String phone) {
		return zhinengguiOrderDao.getOverOrderListByPhone(phone);
	}

	@Transactional(readOnly=false)
	public void saveCopyOrder(String expressNumber) {
		zhinengguiOrderDao.saveCopyOrder(expressNumber);
	}

    public List<ZngOrder> getOrderByDoorIdAndDeviceId(String deviceId, String doorId) {
		return zhinengguiOrderDao.getOrderByDoorIdAndDeviceId(deviceId, doorId);
    }
    public List<ZngOrder> getOverOrderByDoorIdAndDeviceId(String deviceId, String doorId) {
		return zhinengguiOrderDao.getOrderByDoorIdAndDeviceId(deviceId, doorId);
    }

	public CabinetsAndOrder getDoorById(String oid) {
		return zhinengguiOrderDao.getDoorById(oid);
	}
}