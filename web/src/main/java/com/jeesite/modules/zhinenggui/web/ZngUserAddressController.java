/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.jeesite.modules.zhinenggui.entity.ZngUserAddress;
import com.jeesite.modules.zhinenggui.service.ZngUserAddressService;

/**
 * 用户地址表Controller
 * @author lzy
 * @version 2018-08-28
 */
@Controller
@RequestMapping(value = "${adminPath}/zhinenggui/zhinengguiUserAddress")
public class ZngUserAddressController extends BaseController {

	@Autowired
	private ZngUserAddressService zhinengguiUserAddressService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ZngUserAddress get(String id, boolean isNewRecord) {
		return zhinengguiUserAddressService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiUserAddress:view")
	@RequestMapping(value = {"list", ""})
	public String list(ZngUserAddress zhinengguiUserAddress, Model model) {
		model.addAttribute("zhinengguiUserAddress", zhinengguiUserAddress);
		return "modules/zhinenggui/zhinengguiUserAddressList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("zhinenggui:zhinengguiUserAddress:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ZngUserAddress> listData(ZngUserAddress zhinengguiUserAddress, HttpServletRequest request, HttpServletResponse response) {
		Page<ZngUserAddress> page = zhinengguiUserAddressService.findPage(new Page<ZngUserAddress>(request, response), zhinengguiUserAddress);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("zhinenggui:zhinengguiUserAddress:view")
	@RequestMapping(value = "form")
	public String form(ZngUserAddress zhinengguiUserAddress, Model model) {
		model.addAttribute("zhinengguiUserAddress", zhinengguiUserAddress);
		return "modules/zhinenggui/zhinengguiUserAddressForm";
	}

	/**
	 * 保存用户地址表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiUserAddress:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ZngUserAddress zhinengguiUserAddress) {
		zhinengguiUserAddressService.save(zhinengguiUserAddress);
		return renderResult(Global.TRUE, text("保存用户地址表成功！"));
	}
	
	/**
	 * 停用用户地址表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiUserAddress:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(ZngUserAddress zhinengguiUserAddress) {
		zhinengguiUserAddress.setStatus(ZngUserAddress.STATUS_DISABLE);
		zhinengguiUserAddressService.updateStatus(zhinengguiUserAddress);
		return renderResult(Global.TRUE, text("停用用户地址表成功"));
	}
	
	/**
	 * 启用用户地址表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiUserAddress:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(ZngUserAddress zhinengguiUserAddress) {
		zhinengguiUserAddress.setStatus(ZngUserAddress.STATUS_NORMAL);
		zhinengguiUserAddressService.updateStatus(zhinengguiUserAddress);
		return renderResult(Global.TRUE, text("启用用户地址表成功"));
	}
	
	/**
	 * 删除用户地址表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiUserAddress:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ZngUserAddress zhinengguiUserAddress) {
		zhinengguiUserAddressService.delete(zhinengguiUserAddress);
		return renderResult(Global.TRUE, text("删除用户地址表成功！"));
	}
	
}