/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.common.AuthorizedUser;
import com.jeesite.modules.common.ResultMsg;
import com.jeesite.modules.zhinenggui.entity.*;
import com.jeesite.modules.zhinenggui.search.FileImageSearch;
import com.jeesite.modules.zhinenggui.service.*;
import com.jeesite.modules.file.entity.FileUpload;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户表Controller
 * @author lzy
 * @version 2018-08-23
 */
@Controller
@RequestMapping(value = "${frontPath}/zhinenggui/zhinengguiUser")
public class ZngUserFrontController extends BaseController {

	@Autowired
	private ZngUserService zhinengguiUserService;
	@Autowired
	private ZngCodeService zhinengguiCodeService;
	@Autowired
	private ZngDeviceService zhinengguiDeviceService;
	@Autowired
	private ZngVersionService zhinengguiVersionService;
	@Autowired
	private ZngCabinetsService zhinengguiCabinetsService;
	@Autowired
	private ZngOrderService zhinengguiOrderService;
	/**
	 * 获取数据
	 * @param id
	 * @param isNewRecord
	 * @return
	 */
	@ModelAttribute
	public ZngUser get(String id, boolean isNewRecord) {
		return zhinengguiUserService.get(id, isNewRecord);
	}


	/**
	 * 跳转用户登录页面
	 * @param zhinengguiUser
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "login")
	public String login(ZngUser zhinengguiUser, Model model) {
		return "modules/zhinengguiFront/zhinengguiUserLoginForm";
	}

	/**
	 * 快递员进行登录
	 * @param zhinengguiUser
	 * @param model
	 * @param request
	 */
	@RequestMapping(value = "doLogin")
	public void doLogin(ZngUser zhinengguiUser, Model model, HttpServletRequest request) {
		String msg = "";
		String deviceId = request.getParameter("deviceId");
		if(StringUtils.isBlank(zhinengguiUser.getPhone())){
			model.addAttribute("flag", ResultMsg.INPUT_NULL);
			model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
			model.addAttribute("data", new HashMap<>());
			return;
		}
		if(StringUtils.isBlank(zhinengguiUser.getLoginPwd())){
			model.addAttribute("flag", ResultMsg.INPUT_NULL);
			model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
			model.addAttribute("data", new HashMap<>());
			return;
		}
		//根据手机号判断是否为快递员
		ZngUser user = zhinengguiUserService.findByTel(zhinengguiUser.getPhone());
		if(null == user || user.equals("")){
			msg = "查无此用户";
			model.addAttribute("flag", ResultMsg.MODEL_NULL);
			model.addAttribute("msg", msg);
			model.addAttribute("data", new HashMap<>());
			return;
		}
		if(!(user.getIsManager().equals("1"))){
			msg = "您不是快递员，无法登录！";
			model.addAttribute("flag", ResultMsg.PERMISSION_ERROR);
			model.addAttribute("msg", msg);
			model.addAttribute("data", new HashMap<>());
			return;
		}
		if(!zhinengguiUser.getLoginPwd().equalsIgnoreCase(user.getLoginPwd())){
			msg = "密码错误！";
			model.addAttribute("flag", ResultMsg.MODEL_NULL);
			model.addAttribute("msg", msg);
			model.addAttribute("data", new HashMap<>());
			return;
		}

		//获取当前会话对象
		AuthorizedUser currentUser = new AuthorizedUser(user);
		UserUtils.putCache(deviceId, currentUser);
		Map<String,Object> map = new HashMap<>();
		map.put("id", currentUser.getUser().getId());
		map.put("name", currentUser.getName());
		model.addAttribute("data", map);
		model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
		model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
	}
	/**
	 * 管理员登录
	 * @param model
	 * @param request
	 */
	@RequestMapping(value = "adminLogin")
	public void adminLogin(Model model, HttpServletRequest request) {
		String msg = "";
		String deviceId = request.getParameter("deviceId");
		String loginName = request.getParameter("loginName");
		String loginPwd = request.getParameter("loginPwd");
		if(StringUtils.isBlank(loginName)||StringUtils.isBlank(loginPwd)){
			model.addAttribute("flag", ResultMsg.INPUT_NULL);
			model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
			model.addAttribute("data", new HashMap<>());
			return;
		}
		//根据账号密码是否正确
		ZngUser admin = zhinengguiUserService.findByLoginName(loginName);
		if(null == admin || admin.equals("")){
			msg = "账号不存在！！";
			model.addAttribute("flag", ResultMsg.PERMISSION_ERROR);
			model.addAttribute("msg", msg);
			model.addAttribute("data", new HashMap<>());
			return;
		}
		if(!(admin.getIsManager().equals("0"))){
			msg = "您不是管理员，无法登录！";
			model.addAttribute("flag", ResultMsg.PERMISSION_ERROR);
			model.addAttribute("msg", msg);
			model.addAttribute("data", new HashMap<>());
			return;
		}

		//得到登录信息 验证登录密码是否正确
		if(loginPwd.equals(admin.getLoginPwd())){
			//获取当前会话对象
			AuthorizedUser currentUser = new AuthorizedUser(admin);
			UserUtils.putCache(deviceId, currentUser);


			//图片服务器地址，类型，时间
			Map<String,Object> map = new HashMap<>();
			map.put("id", currentUser.getUser().getId());
			map.put("name", currentUser.getName());

			model.addAttribute("data", map);
			model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
			model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
			return;
		}else{
			msg = "密码错误！请重新输入";
			model.addAttribute("flag", ResultMsg.INPUT_ERROR);
			model.addAttribute("msg", msg);
			model.addAttribute("data", new HashMap<>());
			return;
		}
	}
	/**
	 * 用户登录
	 * @param model
	 * @param request
	 */
	@RequestMapping(value = "userLogin")
	public void userLogin(Model model, HttpServletRequest request) {
		ZngUserSendOrderController zhinengguiUserSendOrderController = new ZngUserSendOrderController();
		String msg = "";
		String deviceId = request.getParameter("deviceId");
		String phone = request.getParameter("phone");
		String code = request.getParameter("code");
		if(StringUtils.isBlank(phone)){
			model.addAttribute("flag", ResultMsg.INPUT_NULL);
			model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
			model.addAttribute("data", new HashMap<>());
			return;
		}
		ZngDevice device = zhinengguiDeviceService.getByDeviceId(deviceId);
		if(zhinengguiUserSendOrderController.checkDevice(model, device)){
			return;
		}
		ZngCode zhinengguiCode = new ZngCode();
		zhinengguiCode.setCode(code);
		zhinengguiCode.setPhone(phone);
		zhinengguiCode.setStatus("0");
		zhinengguiCode = zhinengguiCodeService.getByPhoneAndCode(zhinengguiCode);
		if (zhinengguiCode==null) {
			msg = "登录失败";
			model.addAttribute("flag", ResultMsg.MODEL_NULL);
			model.addAttribute("msg", msg);
			model.addAttribute("data", new HashMap<>());
			return;
		}
		zhinengguiCodeService.updateStatusById(zhinengguiCode.getId());
		model.addAttribute("data", new HashMap<String, String>());
		model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
		model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
		return;
	}
	/**
	 * 跳转用户登录成功页面
	 * @param zhinengguiUser
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "goLogin")
	public String goLogin(ZngUser zhinengguiUser, Model model) {
		AuthorizedUser currentuser = (AuthorizedUser) UserUtils.getCache("zhinengguiUserCache");
		zhinengguiUser = currentuser.getUser();

		model.addAttribute("zhinengguiUser", zhinengguiUser);
		return "modules/zhinengguiFront/zhinengguiUserLoginList";
	}

	/**
	 * 用户页面注册
	 * @param zhinengguiUser
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "register")
	public String register(ZngUser zhinengguiUser, Model model) {
		model.addAttribute("zhinengguiUser", zhinengguiUser);
		return "modules/zhinengguiFront/zhinengguiUserInfoForm";
		//return "modules/zhinengguiFront/zhinengguiRegisterUserForm";
	}

	/**
	 * 保存注册用户的信息
	 * @param zhinengguiUser
	 * @param zhinengguiCode
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = "saveRegisterInfo")
	@ResponseBody
	public String saveRegisterInfo(@Validated ZngUser zhinengguiUser,@Validated ZngCode zhinengguiCode,HttpServletRequest request,HttpServletResponse response) {
		//判断验证码是否正确
		Page<ZngCode> page = zhinengguiCodeService.findPage(new Page<ZngCode>(request, response), zhinengguiCode);
		List<ZngCode> zhinengguiCodeList = page.getList();
		if(zhinengguiCodeList.size() == 0){
			return renderResult("1", text("验证码错误请重新输入！"));
		}
		if(null == zhinengguiCode.getCode() || zhinengguiCode.getCode().equals("")){
			return renderResult("2", text("请输入验证码！"));
		}
		if(null == zhinengguiUser.getName() || zhinengguiUser.getName().equals("")){
			return renderResult("3", text("请输入真实姓名！"));
		}
		//保存注册的用户信息
		zhinengguiUser.setCreateBy(zhinengguiUser.getPhone());;
		zhinengguiUser.setIsManager("2");
		zhinengguiUser.setLoginName(zhinengguiUser.getPhone());
		zhinengguiUser.setStatus("0");
		zhinengguiUserService.save(zhinengguiUser);
		return renderResult("0", text("注册成功！"));
	}

	/**
	 * 查询列表
	 * @param zhinengguiUser
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"list", ""})
	public String list(ZngUser zhinengguiUser, Model model) {
		model.addAttribute("zhinengguiUser", zhinengguiUser);
		return "modules/zhinengguiFront/zhinengguiUserFrontList";
	}

	/**
	 * 查询列表数据
	 * @param zhinengguiUser
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ZngUser> listData(ZngUser zhinengguiUser, HttpServletRequest request, HttpServletResponse response) {

		Page<ZngUser> page = zhinengguiUserService.findPage(new Page<ZngUser>(request, response), zhinengguiUser);
		return page;
	}

	/**
	 * 查看编辑表单
	 * @param zhinengguiUser
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "form")
	public String form(ZngUser zhinengguiUser, Model model) {
		AuthorizedUser currentuser = (AuthorizedUser) UserUtils.getCache("zhinengguiUserCache");
		String userId = currentuser.getUser().getId();

		zhinengguiUser = zhinengguiUserService.get(userId);
		model.addAttribute("zhinengguiUser", zhinengguiUser);
		return "modules/zhinengguiFront/zhinengguiUserInfoForm";
	}

	/**
	 * 保存用户表
	 * @param zhinengguiUser
	 * @return
	 */
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ZngUser zhinengguiUser) {
		zhinengguiUserService.save(zhinengguiUser);
		return renderResult(Global.TRUE, text("保存用户表成功！"));
	}

	/**
	 * 删除用户表
	 * @param zhinengguiUser
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ZngUser zhinengguiUser) {
		zhinengguiUserService.delete(zhinengguiUser);
		return renderResult(Global.TRUE, text("删除用户表成功！"));
	}

	/**
	 * 删除用户表
	 * @param model
	 * @param request
	 */
	@RequestMapping(value = "getPlateNum")
	public void getPlateNum(Model model, HttpServletRequest request) {
		String deviceId = request.getParameter("deviceId");
		List<ZngCabinets> list = zhinengguiCabinetsService.getPlateNum(deviceId);
		if(list.size() > 0){
			model.addAttribute("data", list.size());
			model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
			model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
		}else{
			model.addAttribute("data", 0);
			model.addAttribute("flag", ResultMsg.DEVICE_NULL_ERROR);
			model.addAttribute("msg", ResultMsg.ERR_NULL_DEVICE_ZN);
		}
		return;
	}
	/**
	 * 管理员取件
	 * @param model
	 * @param request
	 */
	@RequestMapping(value = "openOneDoor")
	public void openOneDoor(Model model, HttpServletRequest request) {
		String deviceId = request.getParameter("deviceId");
		String totalDoorId = request.getParameter("totalDoorId");
		ZngCabinets cabinets = zhinengguiCabinetsService.getOneByTotalDoorId(deviceId, totalDoorId);

		if(cabinets != null) {
			List<ZngOrder> zhinengguiOrderList = zhinengguiOrderService.getOrderByDoorIdAndDeviceId(cabinets.getDeviceId(), cabinets.getId());
			if (zhinengguiOrderList != null && !zhinengguiOrderList.isEmpty()){
				for(ZngOrder zhinengguiOrder:zhinengguiOrderList) {
					zhinengguiOrder.setState("600");
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					zhinengguiOrder.setGetterTime(formatter.format(new Date()));
					ZngCode zhinengguiCode=zhinengguiCodeService.get(zhinengguiOrder.getCodeId());
					if(zhinengguiCode!=null) {
						zhinengguiCode.setStatus("6");//让订单进入6状态彻底完成状态
						zhinengguiCodeService.update(zhinengguiCode);
					}
					zhinengguiOrderService.update(zhinengguiOrder);
					
				}
				cabinets.setState("1");//1空2使用中
				zhinengguiCabinetsService.update(cabinets);
			}
			Map<String, String> map = new HashMap<>();
			map.put("plateId", cabinets.getPlateId());
			map.put("doorId", cabinets.getDoorId());
			map.put("totalDoorId", cabinets.getTotalDoorId());
			model.addAttribute("data", map);
			model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
			model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
		}else{
			model.addAttribute("data", new HashMap<String, String>());
			model.addAttribute("flag", ResultMsg.DEVICE_NULL_ERROR);
			model.addAttribute("msg", ResultMsg.ERR_NULL_DEVICE_ZN);
		}
		return;
	}


	/**
	 * 管理员开门,不改变订单状态
	 * @param model
	 * @param request
	 */
	@RequestMapping(value = "openOneDoorNoUpdateStatus")
	public void openOneDoorNoUpdateStatus了(Model model, HttpServletRequest request) {
		String deviceId = request.getParameter("deviceId");
		String totalDoorId = request.getParameter("totalDoorId");
		ZngCabinets cabinets = zhinengguiCabinetsService.getOneByTotalDoorId(deviceId, totalDoorId);
		if(cabinets != null) {
			Map<String, String> map = new HashMap<>();
			map.put("plateId", cabinets.getPlateId());
			map.put("doorId", cabinets.getDoorId());
			map.put("totalDoorId", cabinets.getTotalDoorId());
			model.addAttribute("data", map);
			model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
			model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
		}else{
			model.addAttribute("data", new HashMap<String, String>());
			model.addAttribute("flag", ResultMsg.DEVICE_NULL_ERROR);
			model.addAttribute("msg", ResultMsg.ERR_NULL_DEVICE_ZN);
		}
		return;
	}


	/**
	 * 管理员登录后查看设备照片信息
	 * @param zhinengguiUser
	 * @param fileImageSearch
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "getImageList")
	public void getImageList(ZngUser zhinengguiUser,FileImageSearch fileImageSearch,HttpServletRequest request, Model model) {
		String deviceId = request.getParameter("deviceId");
		List<FileImageSearch> imageList = zhinengguiDeviceService.getFileImageList(deviceId);
		//String property = com.jeesite.common.lang.StringUtils.substringBeforeLast(System.getProperty("user.dir"),"WEB-INF");
		String uploadPath = "zhinengguikdg/userfiles/fileupload/";
		Map<String,Object> dateList = new HashMap<>();
		dateList.put("dataList", imageList);
		dateList.put("domainUrl", uploadPath);
		model.addAttribute("data", dateList);
		model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
		model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
	}

	
	/**
	 * 返回设备轮播图
	 * @param zhinengguiUser
	 * @param fileImageSearch
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "getDeviceImageList")
	public void getDeviceImageList(ZngUser zhinengguiUser,FileImageSearch fileImageSearch,HttpServletRequest request, Model model) {
		String deviceId = request.getParameter("deviceId");
		
		ZngDevice zhinengguiDevice = zhinengguiDeviceService.getByDeviceId(deviceId);
		if(StringUtils.isEmpty(deviceId)) {
			model.addAttribute("data", new ArrayList<>());
			model.addAttribute("flag", ResultMsg.DEVICE_NULL_ERROR);
			model.addAttribute("msg", ResultMsg.ERR_NULL_DEVICE_ZN);
			return;
		}
		List<FileUpload> listImage = FileUploadUtils.findFileUpload(zhinengguiDevice.getId(),"zhinengguiDevice_image");
		List<String> dateList = new ArrayList<>();
		String url = request.getScheme()+"://";
		url+=request.getServerName();
		if(request.getServerPort()!=80){
			url+= ":" + request.getServerPort();
		}
		url=url + request.getContextPath();
		if(listImage.size()>0) {
			for(FileUpload item : listImage) {
				dateList.add(url+item.getFileUrl());	
			}
		}else {
			dateList.add(url+"/userfiles/fileupload/system/default.png");
		}

		model.addAttribute("data", dateList);
		model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
		model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
	}
	

	/**
	 * 获取系统版本信息
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "getVersionInfo")
	public void getVersionInfo(HttpServletRequest request, Model model) {
		String packetName = request.getParameter("packetName");

		//根据设备号找到该设备下的所有图片
		ZngVersion zhinengguiVersion = zhinengguiVersionService.getInfoByPacketName(packetName);

		if(null == zhinengguiVersion){
			String msg = "查找不到信息";
			model.addAttribute("data", new HashMap<>());
			model.addAttribute("flag", ResultMsg.MODEL_NULL);
			model.addAttribute("msg", msg);
			return;
		}
		model.addAttribute("data", zhinengguiVersion);
		model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
		model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
		return;

	}
}