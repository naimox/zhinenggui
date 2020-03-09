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
import com.jeesite.modules.zhinenggui.entity.ZngCode;
import com.jeesite.modules.zhinenggui.service.ZngCodeService;

/**
 * 验证码通用表Controller
 * @author lzy
 * @version 2018-08-28
 */
@Controller
@RequestMapping(value = "${adminPath}/zhinenggui/zhinengguiCode")
public class ZngCodeController extends BaseController {

	@Autowired
	private ZngCodeService zhinengguiCodeService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ZngCode get(String id, boolean isNewRecord) {
		return zhinengguiCodeService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCode:view")
	@RequestMapping(value = {"list", ""})
	public String list(ZngCode zhinengguiCode, Model model) {
		model.addAttribute("zhinengguiCode", zhinengguiCode);
		return "modules/zhinenggui/zhinengguiCodeList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCode:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ZngCode> listData(ZngCode zhinengguiCode, HttpServletRequest request, HttpServletResponse response) {
		Page<ZngCode> page = zhinengguiCodeService.findPage(new Page<ZngCode>(request, response), zhinengguiCode);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCode:view")
	@RequestMapping(value = "form")
	public String form(ZngCode zhinengguiCode, Model model) {
		model.addAttribute("zhinengguiCode", zhinengguiCode);
		return "modules/zhinenggui/zhinengguiCodeForm";
	}

	/**
	 * 保存验证码通用表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCode:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ZngCode zhinengguiCode) {
		zhinengguiCodeService.save(zhinengguiCode);
		return renderResult(Global.TRUE, text("保存验证码通用表成功！"));
	}
	
	/**
	 * 停用验证码通用表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCode:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(ZngCode zhinengguiCode) {
		zhinengguiCode.setStatus(ZngCode.STATUS_DISABLE);
		zhinengguiCodeService.updateStatus(zhinengguiCode);
		return renderResult(Global.TRUE, text("停用验证码通用表成功"));
	}
	
	/**
	 * 启用验证码通用表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCode:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(ZngCode zhinengguiCode) {
		zhinengguiCode.setStatus(ZngCode.STATUS_NORMAL);
		zhinengguiCodeService.updateStatus(zhinengguiCode);
		return renderResult(Global.TRUE, text("启用验证码通用表成功"));
	}
	
	/**
	 * 删除验证码通用表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCode:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ZngCode zhinengguiCode) {
		zhinengguiCodeService.delete(zhinengguiCode);
		return renderResult(Global.TRUE, text("删除验证码通用表成功！"));
	}
	
}