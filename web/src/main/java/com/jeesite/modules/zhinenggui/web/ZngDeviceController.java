/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.zhinenggui.entity.ZngCabinets;
import com.jeesite.modules.zhinenggui.entity.ZngUser;
import com.jeesite.modules.zhinenggui.service.ZngCabinetsService;
import com.jeesite.modules.zhinenggui.service.ZngUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hamcrest.core.Is;
import org.jsoup.select.Evaluator.IsEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.DataScope;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.zhinenggui.entity.ZngDevice;
import com.jeesite.modules.zhinenggui.service.ZngDeviceService;
import com.jeesite.modules.file.entity.FileEntity;
import com.jeesite.modules.sys.entity.Company;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.utils.OpenDoorUtils;

import java.util.List;

/**
 * 设备表Controller
 * 
 * @author lzy
 * @version 2018-08-28
 */
@Controller
@RequestMapping(value = "${adminPath}/zhinenggui/zhinengguiDevice")
public class ZngDeviceController extends BaseController {

	@Autowired
	private ZngDeviceService zhinengguiDeviceService;
	@Autowired
	private ZngUserService zhinengguiUserService;
	@Autowired
	private ZngCabinetsService zhinengguiCabinetsService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ZngDevice get(String id, boolean isNewRecord) {
		return zhinengguiDeviceService.get(id, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDevice:view")
	@RequestMapping(value = { "list", "" })
	public String list(ZngDevice zhinengguiDevice, Model model) {
		model.addAttribute("zhinengguiDevice", zhinengguiDevice);
		return "modules/zhinenggui/zhinengguiDeviceList";
	}
	/**
	 * 坐标拾取器返回
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDevice:view")
	@RequestMapping(value = { "picker", "" })
	public String picker( @RequestParam("type") String type,Model model) {
		if(type.equals("0")) {
			return "modules/zhinengguiFront/coordinatePickerGD";
		}else if(type.equals("1")){
			return "modules/zhinengguiFront/coordinatePickerTX";
		}
			return "modules/zhinengguiFront/coordinatePickerTX";
	}
	

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDevice:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ZngDevice> listData(ZngDevice zhinengguiDevice, HttpServletRequest request, HttpServletResponse response) {
		Page<ZngDevice> page = zhinengguiDeviceService.findPage(new Page<ZngDevice>(request, response), zhinengguiDevice);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDevice:view")
	@RequestMapping(value = "form")
	public String form(ZngDevice zhinengguiDevice, Model model) {
		model.addAttribute("zhinengguiDevice", zhinengguiDevice);
		if (zhinengguiDevice.getCourierId()!=null) {
			ZngUser zhinengguiUser = zhinengguiUserService.get(zhinengguiDevice.getCourierId());
			zhinengguiDevice.setCourierPhone(zhinengguiUser.getPhone());
		}
		return "modules/zhinenggui/zhinengguiDeviceForm";
	}

	/**
	 * 设备控制表单
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDevice:view")
	@RequestMapping(value = "adminForm")
	public String adminForm(ZngDevice zhinengguiDevice, Model model, HttpServletRequest request) {
		model.addAttribute("zhinengguiDevice", zhinengguiDevice);
		model.addAttribute("plateId", request.getParameter("plateId"));
		model.addAttribute("doorId", request.getParameter("doorId"));
		return "modules/zhinenggui/zhinengguiDeviceAdminForm";
	}

	/**
	 * 设备控制open
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDevice:edit")
	@PostMapping(value = "openDoorAdmin")
	@ResponseBody
	public String openDoorAdmin(@RequestParam("deviceId") String deviceId, @RequestParam("plateId") String guiId,
			@RequestParam("doorId") String menId) {
		System.out.println("openTheDoor的值为：" + deviceId);
		System.out.println("guiId的值为：" + guiId);
		System.out.println("menId的值为：" + menId);
		if (StringUtils.isEmpty(deviceId) || StringUtils.isEmpty(guiId) || StringUtils.isEmpty(menId)) {
			return renderResult(Global.FALSE, text("参数错误..."));
		}
		boolean state = true;
		state = OpenDoorUtils.openTheDoor(deviceId, guiId, menId);
		// 不知道为什么去数据库搜一下所有的锁控板，这样会造成开00号锁控板的锁连带的所有相同锁控板的柜门都会打开
/*
		if ("00".equalsIgnoreCase(guiId)) {
			List<ZngCabinets> list = zhinengguiCabinetsService.getPlateNum(deviceId);
			if (list.size() > 0) {
				for (ZngCabinets cabinets : list) {
					state = state ? OpenDoorUtils.openTheDoor(deviceId, cabinets.getPlateId(), menId) : false;
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}else {
			state = OpenDoorUtils.openTheDoor(deviceId, guiId, menId);
		}
*/
		if (state)
			return renderResult(Global.TRUE, text("开锁成功..."));
		else
			return renderResult(Global.FALSE, text("开锁失败..."));
	}

	
	
	

	/**
	 * 远程版本更新推送
	 * deviceId 设备ID
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDevice:edit")
	@PostMapping(value = "sendEditionUpdate")
	@ResponseBody
	public String sendEditionUpdate(@RequestParam("deviceId") String deviceId) {
		System.out.println(StringUtils.isEmpty(deviceId));
		if (StringUtils.isEmpty(deviceId)) {
			return renderResult(Global.FALSE, text("操作失败,设备号为空!"));
		}
		boolean state = OpenDoorUtils.sendEditionUpdate(deviceId);
		if (state)
			return renderResult(Global.TRUE, text("更新推送成功!"));
		else
			return renderResult(Global.FALSE, text("设备不在线,更新推送失败!"));
	}
	
	
	/**
	 * 保存设备表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDevice:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ZngDevice zhinengguiDevice) {
		zhinengguiDevice.setCompanyUserId(zhinengguiDevice.getByUser());
		String phone = zhinengguiDevice.getCourierPhone();
		ZngUser zhinengguiUser = zhinengguiUserService.findByTel(phone);
		if (zhinengguiUser==null||!"1".equalsIgnoreCase(zhinengguiUser.getIsManager())){
			return renderResult(Global.FALSE, text("快递员不存在，请先添加快递员！"));
		}else{
			zhinengguiDevice.setCourierId(zhinengguiUser.getId());
			zhinengguiDeviceService.save(zhinengguiDevice);
			return renderResult(Global.TRUE, text("保存设备表成功！"));
		}
	}

	/**
	 * 停用设备表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDevice:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(ZngDevice zhinengguiDevice) {
		zhinengguiDevice.setStatus(ZngDevice.STATUS_DISABLE);
		zhinengguiDeviceService.updateStatus(zhinengguiDevice);
		return renderResult(Global.TRUE, text("停用设备表成功"));
	}

	/**
	 * 启用设备表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDevice:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(ZngDevice zhinengguiDevice) {
		zhinengguiDevice.setStatus(ZngDevice.STATUS_NORMAL);
		zhinengguiDeviceService.updateStatus(zhinengguiDevice);
		return renderResult(Global.TRUE, text("启用设备表成功"));
	}

	/**
	 * 删除设备表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDevice:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ZngDevice zhinengguiDevice) {
		zhinengguiDeviceService.delete(zhinengguiDevice);
		return renderResult(Global.TRUE, text("删除设备表成功！"));
	}

}