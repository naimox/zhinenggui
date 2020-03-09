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
import com.jeesite.modules.zhinenggui.entity.ZngCourierValidate;
import com.jeesite.modules.zhinenggui.service.ZngCourierValidateService;

/**
 * 快递员信息验证表Controller
 * @author lzy
 * @version 2018-08-28
 */
@Controller
@RequestMapping(value = "${adminPath}/zhinenggui/zhinengguiCourierValidate")
public class ZngCourierValidateController extends BaseController {

	@Autowired
	private ZngCourierValidateService zhinengguiCourierValidateService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ZngCourierValidate get(String id, boolean isNewRecord) {
		return zhinengguiCourierValidateService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCourierValidate:view")
	@RequestMapping(value = {"list", ""})
	public String list(ZngCourierValidate zhinengguiCourierValidate, Model model) {
		model.addAttribute("zhinengguiCourierValidate", zhinengguiCourierValidate);
		return "modules/zhinenggui/zhinengguiCourierValidateList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCourierValidate:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ZngCourierValidate> listData(ZngCourierValidate zhinengguiCourierValidate, HttpServletRequest request, HttpServletResponse response) {
		Page<ZngCourierValidate> page = zhinengguiCourierValidateService.findPage(new Page<ZngCourierValidate>(request, response), zhinengguiCourierValidate);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCourierValidate:view")
	@RequestMapping(value = "form")
	public String form(ZngCourierValidate zhinengguiCourierValidate, Model model) {
		model.addAttribute("zhinengguiCourierValidate", zhinengguiCourierValidate);
		return "modules/zhinenggui/zhinengguiCourierValidateForm";
	}

	/**
	 * 保存快递员信息验证表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCourierValidate:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ZngCourierValidate zhinengguiCourierValidate) {
		zhinengguiCourierValidateService.save(zhinengguiCourierValidate);
		return renderResult(Global.TRUE, text("保存快递员信息验证表成功！"));
	}
	
	/**
	 * 停用快递员信息验证表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCourierValidate:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(ZngCourierValidate zhinengguiCourierValidate) {
		zhinengguiCourierValidate.setStatus(ZngCourierValidate.STATUS_DISABLE);
		zhinengguiCourierValidateService.updateStatus(zhinengguiCourierValidate);
		return renderResult(Global.TRUE, text("停用快递员信息验证表成功"));
	}
	
	/**
	 * 启用快递员信息验证表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCourierValidate:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(ZngCourierValidate zhinengguiCourierValidate) {
		zhinengguiCourierValidate.setStatus(ZngCourierValidate.STATUS_NORMAL);
		zhinengguiCourierValidateService.updateStatus(zhinengguiCourierValidate);
		return renderResult(Global.TRUE, text("启用快递员信息验证表成功"));
	}
	
	/**
	 * 删除快递员信息验证表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCourierValidate:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ZngCourierValidate zhinengguiCourierValidate) {
		zhinengguiCourierValidateService.delete(zhinengguiCourierValidate);
		return renderResult(Global.TRUE, text("删除快递员信息验证表成功！"));
	}
	
}