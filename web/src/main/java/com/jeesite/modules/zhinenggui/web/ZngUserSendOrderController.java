/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.io.FileUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.common.AuthorizedUser;
import com.jeesite.modules.common.ResultMsg;
import com.jeesite.modules.zhinenggui.entity.*;
import com.jeesite.modules.zhinenggui.search.*;
import com.jeesite.modules.zhinenggui.service.*;
import com.jeesite.modules.file.entity.FileEntity;
import com.jeesite.modules.file.entity.FileUpload;
import com.jeesite.modules.file.service.FileEntityService;
import com.jeesite.modules.file.service.FileUploadService;
import com.jeesite.modules.sys.entity.Employee;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.EmployeeService;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.sys.utils.ConfigUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.utils.OpenDoorUtils;
import com.jeesite.modules.utils.QRCodeUtils;
import com.jeesite.modules.utils.SendCodeUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户寄件接口
 * @author lzy
 * @version 2018-08-28
 */
@Controller
@RequestMapping(value = "${frontPath}/zhinenggui/zhinengguiOrder")
public class ZngUserSendOrderController extends BaseController {

	@Autowired
	private ZngOrderService zhinengguiOrderService;
	@Autowired
	private ZngDeviceService zhinengguiDeviceService;
	@Autowired
	private ZngCabinetsService zhinengguiCabinetsService;
	@Autowired
	private ZngCodeService zhinengguiCodeService;
	@Autowired
	private ZngUserService zhinengguiUserService;
	@Autowired
	private ZngWalletService zhinengguiWalletService;
	@Autowired
	private FileEntityService fileEntityService;
	@Autowired
	private FileUploadService fileUploadServer;
	@Autowired
	ZngDoorPriceService zhinengguiDoorPriceService;
	@Autowired
	ZngDetailService zhinengguiDetailService;
	@Autowired
	ZngCodeStatisticsService zhinengguiCodeStatisticsService;
	@Autowired
	UserService userService;
	@Autowired
	private ZngWithdrawApplicationService zhinengguiWithdrawApplicationService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private ZngDeviceViewDataService zhinengguiDeviceViewDataService;
	/**
	 * 用户准备寄件
	 */
	@RequestMapping(value = "userSend")
	public void userSend(Model model, HttpServletRequest request) {
		//获取柜体信息
		String deviceId = request.getParameter("deviceId");
		ZngDevice device = zhinengguiDeviceService.getByDeviceId(deviceId);
		//正常不会没有device
		if(device == null){//没有设备信息，返回
			return ;
		}
		//所有空门
		List<DoorSizeCount> doorSizeCount = zhinengguiCabinetsService.getCount(deviceId);
		model.addAttribute("data", doorSizeCount);
		model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
		model.addAttribute("msg",ResultMsg.MSG_SUCCESS_ZN);
		return ;
	}

	/**
	 * 用户保存订单信息判断是否需要付款，并通知快递员取件
	 * senderUserName 送件人姓名
	 * senderPhone		送件人电话
	 * senderRemark	送件人备注
	 * getterUserName	收件人姓名
	 * getterAddress	收件人地址
	 * getterPhone		收件人手机号
	 * doorSize			门型号
	 * deviceId			设备号
	 */
	@PostMapping(value = "saveAddressToDoor")
	public void saveAddressToDoor(ZngUser zhinengguiUser,ZngOrder zhinengguiOrder,HttpServletRequest request,Model model) {
		String deviceId = request.getParameter("deviceId");
		ZngDevice device = zhinengguiDeviceService.getByDeviceId(deviceId);
		if(checkDevice(model, device)){
			return;
		}
		String doorSize = request.getParameter("doorSize");//柜门大小
		String m = request.getParameter("money");//柜门大小
		ZngCabinets cabinets = zhinengguiCabinetsService.getOneByDeviceId(deviceId, doorSize, m, null);//根据设备id取空柜门
		if(cabinets == null){
			return;
		}
		Float money = 0f;
		ZngDoorPrice price = zhinengguiDoorPriceService.getDoorPriceByDoorId(cabinets.getId());
		if(price!=null&&price.getMoney()!=null){
			money = Float.parseFloat(price.getMoney());
		}
//		if(money!=0f){
//			//TODO 保存订单去支付，支付后开门
//			return;
//		}
		zhinengguiOrder.setSendDeviceId(cabinets.getDeviceId());
		zhinengguiOrder.setSendDoorId(cabinets.getId());
		zhinengguiOrder.setGetTotalMoney(String.valueOf(money));
		zhinengguiOrder.setState("200");
		zhinengguiOrder.setOrderType("3");//3:寄件
		//保存订单信息
		zhinengguiOrderService.save(zhinengguiOrder);

		//向快递员发送短信验证码
		ZngUser courier = zhinengguiUserService.get(device.getCourierId());
		if(courier!=null) {
			ZngCode code = new ZngCode();
			code.setContent("，详细情况请与用户联系，联系电话:" + zhinengguiOrder.getSenderPhone());
			if (sendCode(model, deviceId, courier.getPhone(), "1", code)){
				return;
			}
			boolean flag = false;
			if(cabinets.getState().equals("1")) {
				flag = OpenDoorUtils.openTheDoor(deviceId, cabinets.getPlateId(), cabinets.getDoorId());
				if(!flag){
					model.addAttribute("data", new HashMap<String,String>());
					model.addAttribute("flag", ResultMsg.INTERFACE_ERROR);
					model.addAttribute("msg",ResultMsg.ERR_OPEN_DOOR_ZN);
					return;
				}
			}else{
				model.addAttribute("data", new HashMap<String,String>());
				model.addAttribute("flag", ResultMsg.INTERFACE_ERROR);
				model.addAttribute("msg",ResultMsg.ERR_OPEN_DOOR_ZN);
				return;
			}
			cabinets.setState("2");
			zhinengguiCabinetsService.update(cabinets);
			//修改所有此柜门之前完成的订单的验证码的status状态改为6让他们不能在这个订单之后使用再次验证码开门
			zhinengguiCodeService.updateOrderCode6(cabinets.getId());

		}
		Map<String ,String> map = new HashMap<>();
		map.put("plateId", cabinets.getPlateId());
		map.put("doorId", cabinets.getDoorId());
		map.put("totalDoorId", cabinets.getTotalDoorId());
		model.addAttribute("data", map);
		model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
		model.addAttribute("msg",ResultMsg.MSG_SUCCESS_ZN);
		return ;
	}


	/**
	 * 快递员取件
	 *
	 */
	@RequestMapping(value = "getExpressByCourier")
	public void getExpressByCourier(Model model, HttpServletRequest request){
		String deviceId = request.getParameter("deviceId");
		AuthorizedUser currentUser = (AuthorizedUser) UserUtils.getCache(deviceId);
		if (checkCurrentUser(model,currentUser)) {
			return;
		}
		ZngDevice device = zhinengguiDeviceService.getByDeviceId(deviceId);
		//正常不会没有device
		if (checkDevice(model,device)) {
			return;
		}
		List<ZngOrder> list = new ArrayList<>();
		if (!currentUser.getId().equalsIgnoreCase(device.getCourierId())) {//不是设备绑定快递员

		}else {
			list = zhinengguiOrderService.getToSendOrderByCourier(deviceId);
		}
		model.addAttribute("data", list);
		model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
		model.addAttribute("msg",ResultMsg.MSG_SUCCESS_ZN);
		return;
	}

	/**
	 * 快递员开门
	 */
	@RequestMapping(value = "collectExpresses")
	public void collectExpresses(Model model, HttpServletRequest request){
		String deviceId = request.getParameter("deviceId");
		AuthorizedUser currentUser = (AuthorizedUser) UserUtils.getCache(deviceId);
		if (checkCurrentUser(model,currentUser)) {
			return;
		}
		ZngDevice device = zhinengguiDeviceService.getByDeviceId(deviceId);
		//正常不会没有device
		if (checkDevice(model,device)) {
			return;
		}
		String orderId = request.getParameter("orderId");
		ZngOrder order = zhinengguiOrderService.get(orderId);
		if(order==null){
			model.addAttribute("data", new HashMap<String, String>());
			model.addAttribute("flag", ResultMsg.MODEL_NULL);
			model.addAttribute("msg", ResultMsg.ERR_NULL_DATA);
			return;
		}
		ZngCabinets cabinets = zhinengguiCabinetsService.get(order.getGetDoorId());
		if(cabinets==null){
			model.addAttribute("data", new HashMap<String, String>());
			model.addAttribute("flag", ResultMsg.MODEL_NULL);
			model.addAttribute("msg", ResultMsg.ERR_NULL_DEVICE_ZN);
			return;
		}
		boolean flag = false;
		flag = OpenDoorUtils.openTheDoor(deviceId, cabinets.getPlateId(), cabinets.getDoorId());
		if(!flag){
			model.addAttribute("data", new HashMap<String,String>());
			model.addAttribute("flag", ResultMsg.INTERFACE_ERROR);
			model.addAttribute("msg",ResultMsg.ERR_OPEN_DOOR_ZN);
			return;
		}else{
			order.setState("300");
			order.setCourierGetId(currentUser.getId());
			order.setCourierGetName(currentUser.getName());
			order.setCourierGetTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			zhinengguiOrderService.update(order);
			cabinets.setState("1");
			zhinengguiCabinetsService.update(cabinets);
			Map<String ,String> map = new HashMap<>();
			map.put("plateId", cabinets.getPlateId());
			map.put("doorId", cabinets.getDoorId());
			map.put("totalDoorId", cabinets.getTotalDoorId());
			model.addAttribute("data", map);
			model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
			model.addAttribute("msg",ResultMsg.MSG_SUCCESS_ZN);
			return;
		}
	}

	public void orderList(Model model, HttpServletRequest request){
		String deviceId = request.getParameter("deviceId");
		AuthorizedUser currentUser = (AuthorizedUser) UserUtils.getCache(deviceId);
		if (checkCurrentUser(model,currentUser)) {
			return;
		}
		zhinengguiOrderService.getOrderByUserIdAndDeviceId(deviceId, currentUser.getUser().getId());
	}


	/**  ---------- 暂存 start ---------------   */
	/**
	 * 暂存 1.获取空门信息
	 */
	@RequestMapping(value = "readToZancun")
	public void readToZancun(HttpServletRequest request, Model model){
		//1.获取设备现有的空门信息
		String deviceId = request.getParameter("deviceId");
		List<DoorSizeCount> doorSizeCount = zhinengguiCabinetsService.getCount1(deviceId);
		model.addAttribute("data", doorSizeCount);
		model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
		model.addAttribute("msg",ResultMsg.MSG_SUCCESS_ZN);
		return;
	}

	/**
	 * 暂存 2.选择空门信息并付款
	 */
	@RequestMapping(value = "chooseZancunDoor")
	public void chooseZancunDoor(HttpServletRequest request, Model model){
		//1.根据柜门型号计算价钱
		String deviceId = request.getParameter("deviceId");
		String doorSize = request.getParameter("doorSize");
		String money = request.getParameter("money");
		String phone = request.getParameter("phone");
		if(StringUtils.isBlank(deviceId)||StringUtils.isBlank(doorSize)||StringUtils.isBlank(phone)){
			model.addAttribute("flag", ResultMsg.INPUT_NULL);
			model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
			model.addAttribute("data", new HashMap<>());
			return;
		}
		ZngCabinets cabinets = zhinengguiCabinetsService.getOneByDeviceId2(deviceId, doorSize, money);
		if(cabinets == null){
			return;
		}
		ZngOrder order = new ZngOrder();
		order.setState("100");
		order.setGetterPhone(phone);
		order.setSendDeviceId(deviceId);
		order.setOrderType("2");//2:暂存
		order.setSendDoorId(cabinets.getId());
		zhinengguiOrderService.save(order);

		boolean flag = OpenDoorUtils.openTheDoor(deviceId, cabinets.getPlateId(), cabinets.getDoorId());
		if(flag){
//			处理订单，发送消息提示用户取件。
			if(!"0".equalsIgnoreCase(cabinets.getStatus())){
				model.addAttribute("flag", ResultMsg.DEVICE_NULL_ERROR);
				model.addAttribute("msg", ResultMsg.ERR_NULL_DEVICE_ZN);
				model.addAttribute("data", new HashMap<>());
				return;
			}
			ZngCode zhinengguiCode = new ZngCode();
			if (sendCode(model, deviceId, phone, "3", zhinengguiCode)){
				return;
			}
//			ZngCodeStatistics zhinengguiCodeStatistics = zhinengguiCodeStatisticsService.getCodeStatisticsByDeviceId(deviceId);
//			if (zhinengguiCodeStatistics.getCodeNum() > 0) {
//				zhinengguiCodeService.getValidCode(phone, "3", zhinengguiCode);
//				zhinengguiCodeStatisticsService.reduceCodeNum(zhinengguiCodeStatistics.getId());
//			}
			order.setCodeId(zhinengguiCode.getId());
			order.setState("400");
			order.setSenderTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			zhinengguiOrderService.update(order);
			//修改柜门状态
			cabinets.setState("2");
			zhinengguiCabinetsService.update(cabinets);
			
			
			//修改所有此柜门之前完成的订单的验证码的status状态改为6让他们不能在这个订单之后使用再次验证码开门
			zhinengguiCodeService.updateOrderCode6(cabinets.getId());
		}else{
			model.addAttribute("flag", ResultMsg.INTERFACE_ERROR);
			model.addAttribute("msg", ResultMsg.ERR_OPEN_DOOR_ZN);
			model.addAttribute("data", new HashMap<>());
			return;
		}
		Map<String, String> map = new HashMap<>();
		map.put("plateId", cabinets.getPlateId());
		map.put("doorId", cabinets.getDoorId());
		map.put("totalDoorId", cabinets.getTotalDoorId());
		map.put("code", "noPay");
		model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
		model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
		model.addAttribute("data", map);
		return;
	}

	/**  ---------- 暂存 end ---------------   */


	/**  ---------- 快递员派件 start ---------------   */
	/**
	 * 准备寄件
	 * 返回空柜门信息与价格
	 */
	@RequestMapping(value = "readyToSend")
	public void readyToSend(Model model, HttpServletRequest request){
		String deviceId = request.getParameter("deviceId");
		AuthorizedUser currentUser = (AuthorizedUser) UserUtils.getCache(deviceId);
		if (checkCurrentUser(model,currentUser)) {
			return;
		}
		//获取柜体信息，空柜门信息，
		ZngDevice device = zhinengguiDeviceService.getByDeviceId(deviceId);
		//正常不会没有device
		if (checkDevice(model,device)) {
			return;
		}
		ZngWallet wallet = zhinengguiWalletService.getWalletByUserId(currentUser.getId());
		Map<String, Object> map = new HashMap<>();
		map.put("balance", wallet.getBalance());
		//所有空门
		List<DoorSizeCount> doorSizeCount = zhinengguiCabinetsService.getCount(deviceId);
		map.put("doorSizeCount", doorSizeCount);
		model.addAttribute("data", map);
		model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
		model.addAttribute("msg",ResultMsg.MSG_SUCCESS_ZN);
		return;
	}

	/**
	 * 根据传送的柜门信息与用户信息分配并开启柜门:订单开始
	 */
	@RequestMapping(value = "sendOrder")
	public void sendOrder(ZngOrder zhinengguiOrder, Model model, HttpServletRequest request) {
		String deviceId = request.getParameter("deviceId");
		AuthorizedUser currentUser = (AuthorizedUser) UserUtils.getCache(deviceId);
		if (checkCurrentUser(model,currentUser)) {
			return;
		}
		String expressNumber = zhinengguiOrder.getExpressNumber();//快递单号
		String phone = request.getParameter("phone");//收件人手机号
		String doorSize = request.getParameter("doorSize");//柜门大小
		String money = request.getParameter("money");//柜门价钱
		String sendDoorMoney = request.getParameter("sendDoorMoney");//快递员柜门收费
		if (StringUtils.isBlank(phone)||StringUtils.isBlank(doorSize)||StringUtils.isBlank(expressNumber)){
			model.addAttribute("flag", ResultMsg.INPUT_NULL);
			model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
			model.addAttribute("data", new HashMap<>());
			return;
		}
		ZngCabinets cabinets = zhinengguiCabinetsService.getOneByDeviceId(deviceId, doorSize, money, sendDoorMoney);//根据设备id取空柜门
		if(cabinets == null){
			return;
		}
		ZngWallet wallet = zhinengguiWalletService.getWalletByUserId(currentUser.getId());
		if (wallet.getBalance() < Double.parseDouble(sendDoorMoney)){
			model.addAttribute("flag", ResultMsg.MANEY_FAIL);
			model.addAttribute("msg", ResultMsg.ERR_MANEY_FAIL);
			return;
		}
		//填入单号空判断，空则生成订单   快递员默认手中有单号信息，用户存件无单号
		if("1".equalsIgnoreCase(currentUser.getUserType())){
			ZngOrder existOrder = zhinengguiOrderService.getOrderByExpressNumber(expressNumber);
			if(existOrder==null){
				existOrder = new ZngOrder();
			}else {
				zhinengguiOrderService.saveCopyOrder(expressNumber);
			}
			String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			existOrder.setExpressNumber(expressNumber);
			existOrder.setState("300");
			existOrder.setCourierSendId(currentUser.getId());
			existOrder.setCourierSendName(currentUser.getName());
			existOrder.setCourierSendTime(time);
			existOrder.setGetterPhone(phone);
			existOrder.setOrderType("1");//订单类型1:派件 2:暂存 3:寄件
			existOrder.setSendDoorId(cabinets.getId());
			existOrder.setSendDeviceId(deviceId);
			//开门 发短信
			boolean flag = false;
			if(cabinets.getState().equals("1")) {
				flag = OpenDoorUtils.openTheDoor(deviceId, cabinets.getPlateId(), cabinets.getDoorId());
			}

			if(flag){
//				处理订单，发送消息提示用户取件。
				ZngCode zhinengguiCode = new ZngCode();
				ZngDevice device = zhinengguiDeviceService.getByDeviceId(deviceId);
				String content = "，已入" + device.getDeviceRemark() + "快递柜" + cabinets.getTotalDoorId() + "号门，如有问题请拨打:" + currentUser.getUser().getPhone(); //国内版
//				String content = "Please arr arrive at "+cabinets.getTotalDoorId()+" door of "+device.getDeviceRemark()+" locker"; //柬埔寨版
				zhinengguiCode.setContent(content);
				if (sendCode(model, deviceId, phone, "2", zhinengguiCode)){
					return;
				}
//				ZngCodeStatistics zhinengguiCodeStatistics = zhinengguiCodeStatisticsService.getCodeStatisticsByDeviceId(deviceId);
//				if (zhinengguiCodeStatistics.getCodeNum() > 0) {
//					zhinengguiCodeService.getValidCode(phone, "2", zhinengguiCode);
//					zhinengguiCodeStatisticsService.reduceCodeNum(zhinengguiCodeStatistics.getId());
//				}
				existOrder.setCodeId(zhinengguiCode.getId());
				existOrder.setState("400");
				if(StringUtils.isBlank(existOrder.getId())){//输入订单号数据库中不存在
					existOrder.setUserId(currentUser.getId());
					zhinengguiOrderService.save(existOrder);
				}else{
					zhinengguiOrderService.update(existOrder);
				}
				//修改柜门状态
				cabinets.setState("2");
				//修改所有此柜门之前完成的订单的验证码的status状态改为6让他们不能在这个订单之后使用再次验证码开门
				zhinengguiCodeService.updateOrderCode6(cabinets.getId());
				zhinengguiCabinetsService.update(cabinets);
				if (!"0".equalsIgnoreCase(sendDoorMoney)) {
					wallet.setBalance(wallet.getBalance()-Double.parseDouble(sendDoorMoney));
					if (zhinengguiWalletService.saveCompanyWallet(sendDoorMoney, device.getCompanyUserId())){
						zhinengguiWalletService.update(wallet);
						ZngDetail detail = new ZngDetail();
						detail.setMoney(Double.parseDouble(sendDoorMoney));
						detail.setDeviceId(deviceId);
						detail.setType(1);
						detail.setUserId(currentUser.getId());
						zhinengguiDetailService.save(detail);
						detail = new ZngDetail();
						detail.setMoney(Double.parseDouble(sendDoorMoney));
						detail.setDeviceId(deviceId);
						detail.setType(1);
						detail.setUserId(device.getCompanyUserId());
						zhinengguiDetailService.save(detail);
//						1:快递员派件 2:快递员取件 3:用户暂存 4:用户暂取 5:用户寄件 6:用户取快递件 10:快递员充值 20:客户提现
					}else{
						model.addAttribute("flag", ResultMsg.INTERFACE_ERROR);
						model.addAttribute("msg", "订单操作失败");
						model.addAttribute("data", new HashMap<>());
						return;
					}
				}
			}else{
				model.addAttribute("flag", ResultMsg.INTERFACE_ERROR);
				model.addAttribute("msg", ResultMsg.ERR_OPEN_DOOR_ZN);
				model.addAttribute("data", new HashMap<>());
				return;
			}
			Map<String, String> map = new HashMap<>();
			map.put("plateId", cabinets.getPlateId());
			map.put("doorId", cabinets.getDoorId());
			map.put("totalDoorId", cabinets.getTotalDoorId());
			map.put("orderId", existOrder.getId());
			map.put("totalDoorId", cabinets.getTotalDoorId());
			model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
			model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
			model.addAttribute("data", map);
			return;
		}else{//非快递员
			model.addAttribute("flag", ResultMsg.PERMISSION_ERROR);
			model.addAttribute("msg", ResultMsg.ERR_EXPRESS_ZN);
			model.addAttribute("data", new HashMap<>());
			return;
		}
	}
	/**  ---------- 快递员派件 end ---------------   */

	/**
	 * 快递员未完成订单列表
	 */
	@RequestMapping(value = "getCourierOrderList")
	public void getCourierOrderList(Model model, HttpServletRequest request){
		String deviceId = request.getParameter("deviceId");
		AuthorizedUser currentUser = (AuthorizedUser) UserUtils.getCache(deviceId);
		if (checkCurrentUser(model,currentUser) || checkDevice(model, zhinengguiDeviceService.getByDeviceId(deviceId))) {
			return;
		}
		model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
		model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
		model.addAttribute("data", zhinengguiOrderService.getCourierOrder(deviceId, currentUser.getUser().getId()));
		return;
	}

	@RequestMapping(value = "getDeviceOrderList")
	public void getDeviceOrderList(Model model, HttpServletRequest request){
		String deviceId = request.getParameter("deviceId");
//		AuthorizedUser currentUser = (AuthorizedUser) UserUtils.getCache(deviceId);
		if (checkDevice(model, zhinengguiDeviceService.getByDeviceId(deviceId))) {
			return;
		}
		model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
		model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
		model.addAttribute("data", zhinengguiOrderService.getNotDeviceOrderList(deviceId));
		return;
	}

	/**
	 * 取件
	 */
	@RequestMapping(value = "getExpress")
	public void getExpress(Model model, HttpServletRequest request){
		String deviceId = request.getParameter("deviceId");
		String getCode = request.getParameter("getCode");
		ZngDevice device = zhinengguiDeviceService.getByDeviceId(deviceId);
		//正常不会没有device
		if (checkDevice(model,device)) {
			return;
		}
		if(!OpenDoorUtils.getDeviceOnlinState(deviceId)) {
			model.addAttribute("flag", ResultMsg.INTERFACE_ERROR);
			model.addAttribute("msg", ResultMsg.ERR_OPEN_DOOR_ZN);
			model.addAttribute("data", new HashMap<>());
			return;//判断设备是否在线
		}
		CabinetsAndOrder cabinetsAndOrder = zhinengguiOrderService.getDoorByDeviceIdAndCode(deviceId, getCode);
		if(cabinetsAndOrder == null){
			model.addAttribute("flag", ResultMsg.MODEL_NULL);
			model.addAttribute("msg", ResultMsg.ERR_ORDER_ZN);
			model.addAttribute("data", new HashMap<>());
			return;
		}
		if(StringUtils.isBlank(cabinetsAndOrder.getType())){
			model.addAttribute("flag", ResultMsg.MODEL_NULL);
			model.addAttribute("msg", ResultMsg.ERR_ORDER_ZN);
			model.addAttribute("data", new HashMap<>());
			return;
		}
		qujian(model, cabinetsAndOrder, deviceId, getCode);
	}

	/**
	 * 取件
	 * 根据订单
	 */
	@RequestMapping(value = "orderOpenDoor")
	public void orderOpenDoor(Model model, HttpServletRequest request){
		String deviceId = request.getParameter("deviceId");
		String oid = request.getParameter("oid");
		ZngDevice device = zhinengguiDeviceService.getByDeviceId(deviceId);
		//正常不会没有device
		if (checkDevice(model,device)) {
			return;
		}
		if(!OpenDoorUtils.getDeviceOnlinState(deviceId)) {
			model.addAttribute("flag", ResultMsg.INTERFACE_ERROR);
			model.addAttribute("msg", ResultMsg.ERR_OPEN_DOOR_ZN);
			model.addAttribute("data", new HashMap<>());
			return;//判断设备是否在线
		}
		CabinetsAndOrder cabinetsAndOrder = zhinengguiOrderService.getDoorById(oid);
		if(cabinetsAndOrder == null){
			model.addAttribute("flag", ResultMsg.MODEL_NULL);
			model.addAttribute("msg", ResultMsg.ERR_ORDER_ZN);
			model.addAttribute("data", new HashMap<>());
			return;
		}
		if(StringUtils.isBlank(cabinetsAndOrder.getType())){
			model.addAttribute("flag", ResultMsg.MODEL_NULL);
			model.addAttribute("msg", ResultMsg.ERR_ORDER_ZN);
			model.addAttribute("data", new HashMap<>());
			return;
		}
		qujian(model, cabinetsAndOrder, deviceId, zhinengguiCodeService.get(cabinetsAndOrder.getCodeId()).getCode());
	}
    /**
     * 验证码重开门
     */
    @RequestMapping(value = "reOpenDoor")
    public void reOpenDoor(Model model, HttpServletRequest request){
        String deviceId = request.getParameter("deviceId");
        String getCode = request.getParameter("getCode");
        ZngDevice device = zhinengguiDeviceService.getByDeviceId(deviceId);
        //正常不会没有device
        if (checkDevice(model,device) && getCode != null) {
            return;
        }
        LostValidCode lostValidCode = zhinengguiCodeService.getLostValidCode(deviceId, getCode);
        if (lostValidCode == null) {
            model.addAttribute("flag", ResultMsg.MODEL_NULL);
            model.addAttribute("msg", ResultMsg.ERR_ORDER_ZN);
            model.addAttribute("data", new HashMap<>());
        }else {
            model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
            model.addAttribute("msg", ResultMsg.SUCCESS_FLAG);
            model.addAttribute("data", lostValidCode);
        }
        return;
    }

	@RequestMapping(value = "reSendCode")
    public void reSendCode(Model model, HttpServletRequest request){
		String deviceId = request.getParameter("deviceId");
		String phone = request.getParameter("phone");
		String orderId = request.getParameter("orderId");
		if(StringUtils.isBlank(deviceId)||StringUtils.isBlank(phone)){
			model.addAttribute("flag", ResultMsg.INPUT_NULL);
			model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
			model.addAttribute("data", new HashMap<>());
			return;
		}
		List<ZngCode> list = zhinengguiCodeService.getValidCodeList(phone, orderId);
		ZngCodeStatistics zhinengguiCodeStatistics = zhinengguiCodeStatisticsService.getCodeStatisticsByDeviceId(deviceId);
		if (zhinengguiCodeStatistics.getCodeNum() >= list.size() && !list.isEmpty()) {
			List<ZngCode> successList = new ArrayList<>();
			for (ZngCode zhinengguiCode : list){
				if (!"0".equals(SendCodeUtils.reSendCode(zhinengguiCode.getType(), phone, zhinengguiCode.getCode(), zhinengguiCode))){
					zhinengguiCode.setId(null);
					zhinengguiCode.setStatus("6");
					zhinengguiCode.setCreateDate(new Date());
					successList.add(zhinengguiCode);
					zhinengguiCodeService.save(zhinengguiCode);
				}
			}

			zhinengguiCodeStatisticsService.reduceCodeNum(zhinengguiCodeStatistics.getId(), successList.size());
			model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
			model.addAttribute("msg", "成功："+successList.size()+" 失败："+ (list.size() - successList.size()));
			model.addAttribute("data", new HashMap<>());
		}else {
			model.addAttribute("flag", ResultMsg.ERR_CODE_ERROR);
			model.addAttribute("msg", ResultMsg.ERR_CODE_NULL+"或无验证");
			model.addAttribute("data", new HashMap<>());
		}
	}

	public void qujian(Model model, CabinetsAndOrder cabinetsAndOrder, String deviceId, String getCode){
		//计算该订单的价钱
		Float money;
		String type = cabinetsAndOrder.getType();
		switch (type) {
			case "2":
				money = zhinengguiOrderService.getOrderMoney(cabinetsAndOrder);
				break;
			case "3":
				money = zhinengguiOrderService.getOrderMoney1(cabinetsAndOrder);
				break;
			default:
				money = 0f;
				break;
		}
		//model.addAttribute("money", money);
		ZngOrder zhinengguiOrder = zhinengguiOrderService.get(cabinetsAndOrder.getOrderId());
		if(zhinengguiOrder.getState().equals("500")) {
			money=0f;
		}
		if(money > 0f){
			zhinengguiOrder.setSendTotalMoney(money.toString());
			zhinengguiOrderService.update(zhinengguiOrder);
			String result = QRCodeUtils.doPostMethod(cabinetsAndOrder.getOrderId(),money+"");
			Map<String ,String> map = new HashMap<>();
			map.put("code", result.trim());
			map.put("orderId", cabinetsAndOrder.getOrderId());
			map.put("money", money.toString());
			map.put("totalDoorId", cabinetsAndOrder.getTotalDoorId());
			model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
			model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
			model.addAttribute("data", map);
			return;
		}else{
			if(OpenDoorUtils.openTheDoor(deviceId,cabinetsAndOrder.getPlateId(),cabinetsAndOrder.getDoorId())){
				zhinengguiOrder.setState("600");
				zhinengguiOrder.setSendTotalMoney(money.toString());
				zhinengguiOrder.setGetterTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
				zhinengguiOrderService.update(zhinengguiOrder);
				ZngCabinets cabinets = zhinengguiCabinetsService.get(cabinetsAndOrder.getId());
				cabinets.setState("1");
				zhinengguiCabinetsService.update(cabinets);
				zhinengguiCodeService.updateStatusByTelAndCode(zhinengguiOrder.getGetterPhone(), getCode);
				model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
				model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
				Map<String ,String> map = new HashMap<>();
				map.put("code", "noPay");
				map.put("orderId", cabinetsAndOrder.getOrderId());
				map.put("plateId", cabinetsAndOrder.getPlateId());
				map.put("doorId", cabinetsAndOrder.getDoorId());
				map.put("totalDoorId", cabinets.getTotalDoorId());
				model.addAttribute("data", map);
			}else{
				model.addAttribute("flag", ResultMsg.INTERFACE_ERROR);
				model.addAttribute("msg", ResultMsg.ERR_OPEN_DOOR_ZN);
				model.addAttribute("data", new HashMap<>());
			}
			return;
		}
	}

//	public void zanqu(Model model, CabinetsAndOrder cabinetsAndOrder, String deviceId, String getCode){
//		if(OpenDoorUtils.openTheDoor(deviceId,cabinetsAndOrder.getPlateId(),cabinetsAndOrder.getDoorId())){
//			ZngOrder zhinengguiOrder = zhinengguiOrderService.get(cabinetsAndOrder.getOrderId());
//			zhinengguiOrder.setState("600");
//			zhinengguiOrder.setGetterTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
//			zhinengguiOrderService.update(zhinengguiOrder);
//			ZngCabinets cabinets = zhinengguiCabinetsService.get(cabinetsAndOrder.getId());
//			cabinets.setState("1");
//			zhinengguiCabinetsService.update(cabinets);
//			zhinengguiCodeService.updateStatusByTelAndCode(zhinengguiOrder.getGetterPhone(), getCode);
//			model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
//			model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
//			Map<String ,String> map = new HashMap<>();
//			map.put("code", "noPay");
//			map.put("orderId", cabinetsAndOrder.getOrderId());
//			map.put("plateId", cabinetsAndOrder.getPlateId());
//			map.put("doorId", cabinetsAndOrder.getDoorId());
//			model.addAttribute("data", map);
//		}else{
//			model.addAttribute("flag", ResultMsg.INTERFACE_ERROR);
//			model.addAttribute("msg", ResultMsg.ERR_OPEN_DOOR_ZN);
//			model.addAttribute("data", new HashMap<>());
//		}
//	}
	/**
	 * 付款  调取支付接口
	 *
	 */
	@RequestMapping(value = "paySucAfter")
	public String paySucAfter(ZngOrder zhinengguiOrder, Model model, HttpServletRequest request){
		String orderId = zhinengguiOrder.getId();
		if(orderId == null){
			return "订单号为空";
		}
		ZngOrder existOrder = zhinengguiOrderService.get(orderId);
		if(existOrder==null){
			return "订单号订单不存在";
		}
		String deviceId = existOrder.getSendDeviceId();
		String doorId = existOrder.getSendDoorId();
		if(StringUtils.isBlank(deviceId)||
				StringUtils.isBlank(doorId)){
			return "没有设备和柜门信息";
		}
		ZngDevice device = zhinengguiDeviceService.getByDeviceId(deviceId);
		ZngWallet wallet = zhinengguiWalletService.getWalletByUserId(device.getCompanyUserId());
		wallet.setBalance(wallet.getBalance()+Double.parseDouble(existOrder.getSendTotalMoney()));
		zhinengguiWalletService.update(wallet);
		ZngDetail detail = new ZngDetail();
		detail.setMoney(Double.parseDouble(existOrder.getSendTotalMoney()));
		if(StringUtils.isBlank(existOrder.getUserId())){
			detail.setType(4);
		}else{
			detail.setType(6);
		}
		detail.setDeviceId(deviceId);
		detail.setUserId(existOrder.getGetterPhone());
		zhinengguiDetailService.save(detail);
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		existOrder.setGetterTime(time);
		existOrder.setState("600");
		model.addAttribute("zhinengguiOrder", existOrder);
		//开门更新订单
		ZngCabinets cabinets = zhinengguiCabinetsService.get(doorId);

		boolean flag = false;
		if("0".equalsIgnoreCase(cabinets.getStatus())){
			flag = OpenDoorUtils.openTheDoorPay(deviceId, cabinets.getPlateId(), cabinets.getDoorId(), orderId);
			System.out.println("設備ID："+deviceId+"|"+cabinets.getPlateId()+"|"+cabinets.getDoorId()+"|"+orderId);
		}
		System.out.println("if:"+flag);
		if(flag){
			//处理订单。
			existOrder.setSendTotalMoney(existOrder.getSendTotalMoney());
			cabinets.setState("1");
			zhinengguiCabinetsService.update(cabinets);
			zhinengguiOrderService.update(existOrder);
			zhinengguiCodeService.updateStatusById(existOrder.getCodeId());
		}else{
			//已支付，未开门
			existOrder.setState("500");
			zhinengguiOrderService.update(existOrder);
		}
		return "success";
	}

	@RequestMapping(value = "recharge")
	public void recharge(Model model ,HttpServletRequest request){
		String deviceId = request.getParameter("deviceId");
		String money = request.getParameter("money");
		AuthorizedUser currentUser = (AuthorizedUser) UserUtils.getCache(deviceId);
		if (checkCurrentUser(model,currentUser)) {
			return;
		}
		ZngDevice device = zhinengguiDeviceService.getByDeviceId(deviceId);
		//正常不会没有device
		if (checkDevice(model,device)) {
			return;
		}
		String result = QRCodeUtils.recharge(deviceId,currentUser.getId(),money);
		Map<String ,String> map = new HashMap<>();
		map.put("code", result.trim());
		map.put("userId", currentUser.getId());
		model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
		model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
		model.addAttribute("data", map);
	}
	/**
	 * 支付成功回调
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "rechargeSucAfter")
	public String rechargeSucAfter(Model model, HttpServletRequest request){
		String userId = request.getParameter("userId");
		String money = request.getParameter("money");
		if(userId == null){
			System.out.println("userId为空");
			return "userId为空";
		}
		ZngUser user = zhinengguiUserService.get(userId);
		if(user==null){
			System.out.println("user为空");
			return "user为空";
		}
		String deviceId = request.getParameter("deviceId");
		ZngDevice device = zhinengguiDeviceService.getByDeviceId(deviceId);
		if(device==null){
			System.out.println("没有设备信息");
			return "没有设备信息";
		}
		ZngWallet wallet = zhinengguiWalletService.getWalletByUserId(userId);
		wallet.setBalance(wallet.getBalance()+Double.parseDouble(money));
		zhinengguiWalletService.update(wallet);
		ZngDetail detail = new ZngDetail();
		detail.setMoney(Double.parseDouble(money));
		detail.setType(10);
		detail.setUserId(userId);
		zhinengguiDetailService.save(detail);

		OpenDoorUtils.getPayMoney(deviceId, userId);
		return "success";
	}
	
	@RequestMapping(value = "refreshWallet")
	public void refreshWallet(Model model, HttpServletRequest request){
		String deviceId = request.getParameter("deviceId");
		AuthorizedUser currentUser = (AuthorizedUser) UserUtils.getCache(deviceId);
		if (checkCurrentUser(model,currentUser)) {
			return;
		}
		//获取柜体信息，空柜门信息，
		ZngDevice device = zhinengguiDeviceService.getByDeviceId(deviceId);
		//正常不会没有device
		if (checkDevice(model,device)) {
			return;
		}
		ZngWallet wallet = zhinengguiWalletService.getWalletByUserId(currentUser.getId());
		Map<String, Object> map = new HashMap<>();
		map.put("balance", wallet.getBalance());
		//所有空门
		model.addAttribute("data", map);
		model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
		model.addAttribute("msg",ResultMsg.MSG_SUCCESS_ZN);
		return;
	}
	/**
	 * 获取设备门数据
	 * @param model
	 * @param request
	 */
	@RequestMapping(value = "getDeviceDoors")
	public void getDeviceDoors(Model model ,HttpServletRequest request){
		String deviceId = request.getParameter("deviceId");
//		AuthorizedUser currentUser = (AuthorizedUser) UserUtils.getCache(deviceId);
//		if (checkCurrentUser(model,currentUser)) {
//			return;
//		}
		ZngDevice device = zhinengguiDeviceService.getByDeviceId(deviceId);
		//正常不会没有device
		if (checkDevice(model,device)) {
			return;
		}
		List<ZngCabinets> list = zhinengguiCabinetsService.getAllListByDeviceId(deviceId);
		model.addAttribute("data", list);
		model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
		model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
	}
	
	
	/**
	 * 获取设备视图数据
	 * @param model
	 * @param request
	 */
	@RequestMapping(value = "getDeviceViewData")
	public void getDeviceViewData(Model model ,HttpServletRequest request){
		String deviceId = request.getParameter("deviceId");
//		AuthorizedUser currentUser = (AuthorizedUser) UserUtils.getCache(deviceId);
//		if (checkCurrentUser(model,currentUser)) {
//			return;
//		}
		ZngDevice device = zhinengguiDeviceService.getByDeviceId(deviceId);
		//正常不会没有device
		if (checkDevice(model,device)) {
			return;
		}
		ZngDeviceViewData zhinengguiDeviceViewData = zhinengguiDeviceViewDataService.getOneByNumber(deviceId);
		Map<String,Object> maps;
		try {
			maps = JSON.parseObject(zhinengguiDeviceViewData.getDeviceGraphView(),Map.class);
		} catch (Exception e) {
			maps = new HashMap<>();
			maps.put("flag",ResultMsg.DEVICE_VIEW_NULL_ERROR);
			maps.put("msg",ResultMsg.ERR_DEVICE_VIEW_NULL);
		}
		model.addAttribute("data", maps);
		model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
		model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
	}
	

	@RequestMapping(value = "goToExpressFindOrder")
	public String goToExpressFindOrder(Model model ,HttpServletRequest request){
		return "modules/zhinengguiFront/goToExpressFindOrder";
	}
	/**
	 * 上传存件取件拍照图片
	 * @param order_id
	 * @param model
	 * @param request
	 * @param myfile
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = "uploadImageByOrder")
	@ResponseBody
	public Model uploadImageByOrder(String order_id,Model model, HttpServletRequest request,@RequestParam("zhinengguiOrder_image") MultipartFile myfile) throws IOException {
		String imgMd5  = DigestUtils.md5Hex(myfile.getInputStream());	//文件MD5效验
		ZngOrder zhinengguiOrder = zhinengguiOrderService.get(order_id);
		FileEntity fileEntity = new FileEntity();
		fileEntity.setFileMd5(imgMd5);
		fileEntity =  fileEntityService.getByMd5(fileEntity);	//查找数据是否有此文件

		if(zhinengguiOrder!=null&&fileEntity.getId()==null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");			//声明年月格式用于做上传文件夹
			String path = sdf.format(new Date());						//配置文件夹目录
			String property = com.jeesite.common.lang.StringUtils.substringBeforeLast(System.getProperty("user.dir"),"WEB-INF");
			System.out.println(property);
			String uploadPath = 
					property
					+"/userfiles/fileupload/"
					+path+"/";  //配置上传目录
			//fileEntity.setId(fileId);
			fileEntity.setFileContentType(myfile.getContentType());
			fileEntity.setFileSize(myfile.getSize());
			fileEntity.setFilePath(path+"/");
			fileEntity.setFileMd5(imgMd5);
			fileEntity.setFileExtension(myfile.getOriginalFilename().substring(myfile.getOriginalFilename().lastIndexOf(".") + 1));
			fileEntityService.save(fileEntity);
			FileUtils.copyInputStreamToFile(				//上传文件
					myfile.getInputStream(),
					new File(
							uploadPath,						//上传目录
							fileEntity.getId()	//文件名
									+"."+
									fileEntity.getFileExtension()	//后缀名
					)
			);
			FileUpload fileUpload = new FileUpload();
			fileUpload.setFileEntity(fileEntity);
			fileUpload.setBizKey(order_id);
			fileUpload.setBizType("zhinengguiOrder_image");
			fileUpload.setFileName(myfile.getOriginalFilename());
			fileUpload.setFileType("image");
			fileUploadServer.save(fileUpload);
			model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
			model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
			return model;
		}else {
			model.addAttribute("flag", ResultMsg.IMG_FIAL);
			model.addAttribute("msg", ResultMsg.ERR_IMG_FAIL);
			return model;
		}

	}
	/**
	 * 快递员查询未取订单明细
	 * @param model
	 * @param request
	 */
	@RequestMapping(value = "expressFindOrder")
	public String expressFindOrder(Model model ,HttpServletRequest request){
		String phone = request.getParameter("phone");
		String type = request.getParameter("returnType");
		if(StringUtils.isBlank(phone)){
			model.addAttribute("flag", ResultMsg.INPUT_ERROR);
			model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
			model.addAttribute("data", new HashMap<>());
			return "";
		}
		ZngUser user = zhinengguiUserService.findByTel(phone);
		if(user == null){
			model.addAttribute("flag", ResultMsg.MODEL_NULL);
			model.addAttribute("msg", ResultMsg.ERR_NULL_DATA);
			model.addAttribute("data", new HashMap<>());
			return "";
		}
		if(type!=null&&type.equals("1")) {
			List<OrderDetail> list = zhinengguiOrderService.getNotGetOrder(user.getId());
			model.addAttribute("data", list);
			model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
			model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
			return "";
		}else {
			List<OrderDetail> list = zhinengguiOrderService.getNotGetOrder(user.getId());
			model.addAttribute("data", list);
			model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
			model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
			return "modules/zhinengguiFront/expressFindOrder";
		}

	}
	/**
	 * 根据手机号获取订单列表
	 * @param model
	 * @param request
	 */
	@RequestMapping(value = "getOrderListByPhone")
	public void getOrderListByPhone(Model model ,HttpServletRequest request){
		String phone = request.getParameter("phone");
		String deviceId = request.getParameter("deviceId");
		ZngDevice device = zhinengguiDeviceService.getByDeviceId(deviceId);
		//正常不会没有device
		if (checkDevice(model,device)) {
			return;
		}
		List<OrderSearch> list = zhinengguiOrderService.getOrderListByPhone(deviceId, phone);
		model.addAttribute("data", list);
		model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
		model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
		return;
	}

	/**
	 * 验证是否为空
	 * @param model
	 * @param currentUser
	 * @return
	 */
	private boolean checkCurrentUser(Model model, AuthorizedUser currentUser){
		boolean flag = false;
		if(currentUser==null){
			model.addAttribute("flag", ResultMsg.LOGIN_OVER_TIME);
			model.addAttribute("msg", ResultMsg.ERR_OVER_TIME_ZN);
			model.addAttribute("data", new HashMap<>());
			flag = true;
		}
		return flag;
	}
	/**
	 * 验证是否为空
	 * @param model
	 * @param device
	 * @return
	 */
	public boolean checkDevice(Model model, ZngDevice device){
		boolean flag = false;
		if(device == null){//没有设备信息，返回
			model.addAttribute("flag", ResultMsg.DEVICE_NULL_ERROR);
			model.addAttribute("msg", ResultMsg.ERR_NULL_DEVICE_ZN);
			model.addAttribute("data", new HashMap<>());
			flag = true;
		}
		return flag;
	}

    /**
     * 用户绑定微信openid
     * @param userId
     * @param openid
     * @return
     */
    @RequestMapping(value = "upUserOpenId")
	public String upUserOpenId(@RequestParam("user_id")String userId, @RequestParam("openid")String openid){
	    System.out.println("user_id: "+ userId + " openid："+ openid);
	    User userOpenid = new User();
	    userOpenid.setWxOpenid(openid);
	    if (userService.get(userOpenid) != null){
	    	return "openId重复绑定";
		}
		User user = userService.get(userId);
		if (user == null){
			return "用户不存在";
		}else {
			user.setWxOpenid(openid);
			userService.save(user);
		}
        return "SUCCESS";
    }
/**
 * 用户提现成功异步回调
 * @param money
 * @param openid
 * @param code
 */
    @RequestMapping(value = "withdraw")
	public void withdraw(@RequestParam("money")double money,  @RequestParam("openid")String openid, @RequestParam("code")String code){
	    int type = 5;
//		String userId = zhinengguiWalletService.withdraw(money, openid);
        String userId = zhinengguiWalletService.getWalletByOpenId(openid).getUserId();
		if ("1".equals(code)) {
		    type = 2;
            zhinengguiWalletService.withdraw(money, openid);
			ZngDetail zhinengguiDetail = new ZngDetail();
			zhinengguiDetail.setUserId(userId);
			zhinengguiDetail.setMoney(-money);
			zhinengguiDetail.setType(20);
			zhinengguiDetail.setCreateTime(new Date());
			zhinengguiDetailService.save(zhinengguiDetail);
		}
		zhinengguiWithdrawApplicationService.upApplicationType(userId, money, type);
	}

    /**
     * 充值短信条数
     * @param money
     * @param userId
     * @param code
     */
	@RequestMapping(value = "rechargeCode")
	public void rechargeCode(@RequestParam("money")double money,  @RequestParam("user_id")String userId, @RequestParam("code")String code){
		if ("1".equals(code)) {
			ZngCodeStatistics zhinengguiCodeStatistics = zhinengguiCodeStatisticsService.getCodeStatisticsByUserId(userId);
			zhinengguiCodeStatistics.setCodeNum(zhinengguiCodeStatistics.getCodeNum() + (int)(money/ Double.parseDouble(ConfigUtils.getConfig("code_money").getConfigValue())));
			zhinengguiCodeStatisticsService.update(zhinengguiCodeStatistics);
		}
	}

	/**
	 * 验证码发送
	 * @param model
	 * @param deviceId 设备id
	 * @param phone 手机号
	 * @param type 验证码类型
	 * @param zhinengguiCode
	 * @return
	 */
	public boolean sendCode(Model model, String deviceId, String phone, String type, ZngCode zhinengguiCode){
		ZngCodeStatistics zhinengguiCodeStatistics = zhinengguiCodeStatisticsService.getCodeStatisticsByDeviceId(deviceId);
		zhinengguiCodeService.getValidCode(phone, type, zhinengguiCode, zhinengguiCodeStatistics.getUserBy());
		zhinengguiCodeStatisticsService.reduceCodeNum(zhinengguiCodeStatistics.getId());
		return false;
//		if (zhinengguiCodeStatistics.getCodeNum() > 0) {
//			if(!zhinengguiCodeService.getValidCode(phone, type, zhinengguiCode, zhinengguiCodeStatistics.getUserBy()).equals("0")){
//				model.addAttribute("data", new HashMap<String,String>());
//				model.addAttribute("flag", ResultMsg.INTERFACE_ERROR);
//				model.addAttribute("msg",ResultMsg.ERR_CODE_ERROR);
//				return true;
//			}
//			zhinengguiCodeStatisticsService.reduceCodeNum(zhinengguiCodeStatistics.getId());
//			return false;
//		}else {
//			model.addAttribute("data", new HashMap<String,String>());
//			model.addAttribute("flag", ResultMsg.INTERFACE_ERROR);
//			model.addAttribute("msg",ResultMsg.ERR_CODE_NULL);
//			return true;
//		}
	}

	/**
	 * 获得验证码
	 */
	@RequestMapping(value = "getValidCode")
	public void getValidCode(Model model, HttpServletRequest request){
		String deviceId = request.getParameter("deviceId");
		ZngDevice device = zhinengguiDeviceService.getByDeviceId(deviceId);
		if(checkDevice(model, device)){
			return;
		}
		String phone = request.getParameter("phone");
		String type = request.getParameter("type");
		ZngCode zhinengguiCode = new ZngCode();
		if (sendCode(model, deviceId, phone, type, zhinengguiCode)){
			return;
		}
		model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
		model.addAttribute("msg", ResultMsg.MSG_VALID_ZN);
		model.addAttribute("data", zhinengguiCode);
	}
	/**
	 * 添加设备测试
	 * @param model
	 */
	public void addDevice(Model model){
		int my = 10;
		int mx = 4;
		int sSize[] = {3, 4, 5, 6, 7, 8, 9};
		int mSize[] = {1, 2};
		int xSize[] = {10};
		String deviceId = "00001";
		for (int y = 1; y <= my; y++){
			for (int x = 1; x <= mx ; x++){
//				AddNewDeviceUtils addNewDeviceUtils = new AddNewDeviceUtils();
//				addNewDeviceUtils.add(deviceId, mx, );
//				String deviceId, int count, String plateId, int totalIdStart
				System.out.println("deviceId:"+deviceId+" count:"+((y*x)/20)*20 + (y*x)%20+" plateId:"+((y*x)/20)+1+" totalIdStart:"+x*y);
			}
		}

	}
	/**
	 * 快递员注册
	 * @param model
	 * @param request
	 */
	@RequestMapping(value = "addCourier")
	public void addaddCourier(Model model,
			@RequestParam("deviceId")String deviceId,
			@RequestParam("code")String code,
			@RequestParam("phone")String phone,
			@RequestParam("name")String name,
			@RequestParam("pwd")String pwd){
		ZngDevice zhinengguiDevice = zhinengguiDeviceService.getByDeviceId(deviceId);
		ZngCode zhinengguiCode = zhinengguiCodeService.getInfo(phone, "0");//0为普通短信通常注册使用
		if(StringUtils.isEmpty(deviceId)||
				StringUtils.isEmpty(code)||
				StringUtils.isEmpty(phone)||
				StringUtils.isEmpty(name)||
				StringUtils.isEmpty(pwd)) {
			model.addAttribute("flag", ResultMsg.MODEL_NULL);
			model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
			model.addAttribute("data",new HashMap<>());
			return;
		}
		if(zhinengguiCode==null) {
			model.addAttribute("flag", ResultMsg.MODEL_NULL);
			model.addAttribute("msg", ResultMsg.ERR_NULL_DATA);
			model.addAttribute("data",new HashMap<>());
			return;
		}
		if(zhinengguiDevice==null || StringUtils.isEmpty(zhinengguiDevice.getCompanyUserId())) {
			model.addAttribute("flag", ResultMsg.MODEL_NULL);
			model.addAttribute("msg", ResultMsg.ERR_COMPANY_USER_ID_FAIL);
			model.addAttribute("data",new HashMap<>());
			return;
		}
		if(zhinengguiUserService.findByLoginName(phone)!=null) {
			model.addAttribute("flag", ResultMsg.MODEL_NULL);
			model.addAttribute("msg", ResultMsg.ERR_USER_ADD_FAIL);
			model.addAttribute("data",new HashMap<>());
			return;
		}
		Employee employee = new Employee();
		ZngUser zhinengguiUser = new ZngUser();
		zhinengguiUser.setName(name);
		zhinengguiUser.setLoginName(phone);
		zhinengguiUser.setLoginPwd(pwd);
		zhinengguiUser.setPhone(phone);
		zhinengguiUser.setIsManager("1");
		employee.setEmpCode(zhinengguiDevice.getCompanyUserId());
		employee = employeeService.get(employee);
		if(employee==null) {
			model.addAttribute("flag", ResultMsg.MODEL_NULL);
			model.addAttribute("msg", ResultMsg.ERR_COMPANY_USER_ID_NULL);
			model.addAttribute("data",new HashMap<>());
		}
		zhinengguiUser.setByUser(employee.getEmpCode());
		zhinengguiUser.setByCompany(employee.getCompany().getCompanyCode());
		zhinengguiUser.setByOffice(employee.getOffice().getOfficeCode());
		zhinengguiUserService.save(zhinengguiUser);
		zhinengguiCodeService.updateStatusById(zhinengguiCode.getId());
		model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
		model.addAttribute("msg", ResultMsg.MSG_REGIST_ZN);
		model.addAttribute("data", new HashMap<>());
		return;
	}


}