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
import com.jeesite.modules.zhinenggui.entity.ZngExpressCompany;
import com.jeesite.modules.zhinenggui.service.ZngExpressCompanyService;

/**
 * 快递公司表Controller
 * @author lzy
 * @version 2018-08-28
 */
@Controller
@RequestMapping(value = "${adminPath}/zhinenggui/zhinengguiExpressCompany")
public class ZngExpressCompanyController extends BaseController {

	@Autowired
	private ZngExpressCompanyService zhinengguiExpressCompanyService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ZngExpressCompany get(String id, boolean isNewRecord) {
		return zhinengguiExpressCompanyService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiExpressCompany:view")
	@RequestMapping(value = {"list", ""})
	public String list(ZngExpressCompany zhinengguiExpressCompany, Model model) {
		model.addAttribute("zhinengguiExpressCompany", zhinengguiExpressCompany);
		return "modules/zhinenggui/zhinengguiExpressCompanyList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("zhinenggui:zhinengguiExpressCompany:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ZngExpressCompany> listData(ZngExpressCompany zhinengguiExpressCompany, HttpServletRequest request, HttpServletResponse response) {
		Page<ZngExpressCompany> page = zhinengguiExpressCompanyService.findPage(new Page<ZngExpressCompany>(request, response), zhinengguiExpressCompany);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("zhinenggui:zhinengguiExpressCompany:view")
	@RequestMapping(value = "form")
	public String form(ZngExpressCompany zhinengguiExpressCompany, Model model) {
		model.addAttribute("zhinengguiExpressCompany", zhinengguiExpressCompany);
		return "modules/zhinenggui/zhinengguiExpressCompanyForm";
	}

	/**
	 * 保存快递公司表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiExpressCompany:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ZngExpressCompany zhinengguiExpressCompany) {
		zhinengguiExpressCompanyService.save(zhinengguiExpressCompany);
		return renderResult(Global.TRUE, text("保存快递公司表成功！"));
	}
	
	/**
	 * 停用快递公司表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiExpressCompany:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(ZngExpressCompany zhinengguiExpressCompany) {
		zhinengguiExpressCompany.setStatus(ZngExpressCompany.STATUS_DISABLE);
		zhinengguiExpressCompanyService.updateStatus(zhinengguiExpressCompany);
		return renderResult(Global.TRUE, text("停用快递公司表成功"));
	}
	
	/**
	 * 启用快递公司表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiExpressCompany:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(ZngExpressCompany zhinengguiExpressCompany) {
		zhinengguiExpressCompany.setStatus(ZngExpressCompany.STATUS_NORMAL);
		zhinengguiExpressCompanyService.updateStatus(zhinengguiExpressCompany);
		return renderResult(Global.TRUE, text("启用快递公司表成功"));
	}
	
	/**
	 * 删除快递公司表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiExpressCompany:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ZngExpressCompany zhinengguiExpressCompany) {
		zhinengguiExpressCompanyService.delete(zhinengguiExpressCompany);
		return renderResult(Global.TRUE, text("删除快递公司表成功！"));
	}
	
}