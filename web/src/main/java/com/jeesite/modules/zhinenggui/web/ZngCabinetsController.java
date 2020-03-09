/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.zhinenggui.entity.ZngDevice;
import com.jeesite.modules.zhinenggui.service.ZngDeviceService;
import com.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.zhinenggui.entity.ZngCabinets;
import com.jeesite.modules.zhinenggui.service.ZngCabinetsService;

/**
 * 柜门柜门表Controller
 * @author lzy
 * @version 2018-08-30
 */
@Controller
@RequestMapping(value = "${adminPath}/zhinenggui/zhinengguiCabinets")
public class ZngCabinetsController extends BaseController {

	@Autowired
	private ZngCabinetsService zhinengguiCabinetsService;
	@Autowired
	private ZngDeviceService zhinengguiDeviceService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ZngCabinets get(String id, boolean isNewRecord) {
		return zhinengguiCabinetsService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDevice:view")
	@RequestMapping(value = {"list", ""})
	public String list(ZngCabinets zhinengguiCabinets, Model model) {
		model.addAttribute("zhinengguiCabinets", zhinengguiCabinets);
		ZngDevice zhinengguiDevice = zhinengguiDeviceService.getByDeviceId(zhinengguiCabinets.getDeviceId());
		model.addAttribute("device", zhinengguiDevice);
		return "modules/zhinenggui/zhinengguiCabinetsList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDevice:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ZngCabinets> listData(ZngCabinets zhinengguiCabinets, HttpServletRequest request, HttpServletResponse response) {
//		zhinengguiCabinets.setByCompany();
		Page<ZngCabinets> page = zhinengguiCabinetsService.findPage(new Page<ZngCabinets>(request, response), zhinengguiCabinets);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDevice:view")
	@RequestMapping(value = "form")
	public String form(ZngCabinets zhinengguiCabinets, Model model) {
		model.addAttribute("zhinengguiCabinets", zhinengguiCabinets);
		return "modules/zhinenggui/zhinengguiCabinetsForm";
	}

	/**
	 * 保存柜门柜门表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDevice:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ZngCabinets zhinengguiCabinets) {
		zhinengguiCabinetsService.save(zhinengguiCabinets);
		return renderResult(Global.TRUE, text("保存柜门柜门表成功！"));
	}
	
	/**
	 * 停用柜门柜门表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDevice:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(ZngCabinets zhinengguiCabinets) {
		zhinengguiCabinets.setStatus(ZngCabinets.STATUS_DISABLE);
		zhinengguiCabinetsService.updateStatus(zhinengguiCabinets);
		return renderResult(Global.TRUE, text("停用柜门柜门表成功"));
	}
	
	/**
	 * 启用柜门柜门表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDevice:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(ZngCabinets zhinengguiCabinets) {
		zhinengguiCabinets.setStatus(ZngCabinets.STATUS_NORMAL);
		zhinengguiCabinetsService.updateStatus(zhinengguiCabinets);
		return renderResult(Global.TRUE, text("启用柜门柜门表成功"));
	}
	
	/**
	 * 删除柜门柜门表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDevice:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ZngCabinets zhinengguiCabinets) {
		zhinengguiCabinetsService.delete(zhinengguiCabinets);
		return renderResult(Global.TRUE, text("删除柜门柜门表成功！"));
	}
	
}