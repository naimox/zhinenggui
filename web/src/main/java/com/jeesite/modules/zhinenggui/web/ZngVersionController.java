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
import com.jeesite.modules.zhinenggui.entity.ZngVersion;
import com.jeesite.modules.zhinenggui.service.ZngVersionService;

/**
 * zhinenggui_versionController
 * @author lnn
 * @version 2018-09-08
 */
@Controller
@RequestMapping(value = "${adminPath}/zhinenggui/zhinengguiVersion")
public class ZngVersionController extends BaseController {

	@Autowired
	private ZngVersionService zhinengguiVersionService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ZngVersion get(String id, boolean isNewRecord) {
		return zhinengguiVersionService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiVersion:view")
	@RequestMapping(value = {"list", ""})
	public String list(ZngVersion zhinengguiVersion, Model model) {
		model.addAttribute("zhinengguiVersion", zhinengguiVersion);
		return "modules/zhinenggui/zhinengguiVersionList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("zhinenggui:zhinengguiVersion:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ZngVersion> listData(ZngVersion zhinengguiVersion, HttpServletRequest request, HttpServletResponse response) {
		Page<ZngVersion> page = zhinengguiVersionService.findPage(new Page<ZngVersion>(request, response), zhinengguiVersion);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("zhinenggui:zhinengguiVersion:view")
	@RequestMapping(value = "form")
	public String form(ZngVersion zhinengguiVersion, Model model) {
		model.addAttribute("zhinengguiVersion", zhinengguiVersion);
		return "modules/zhinenggui/zhinengguiVersionForm";
	}

	/**
	 * 保存zhinenggui_version
	 */
	@RequiresPermissions("zhinenggui:zhinengguiVersion:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ZngVersion zhinengguiVersion) {
		zhinengguiVersionService.save(zhinengguiVersion);
		return renderResult(Global.TRUE, text("保存zhinenggui_version成功！"));
	}
	
	/**
	 * 删除zhinenggui_version
	 */
	@RequiresPermissions("zhinenggui:zhinengguiVersion:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ZngVersion zhinengguiVersion) {
		zhinengguiVersionService.delete(zhinengguiVersion);
		return renderResult(Global.TRUE, text("删除zhinenggui_version成功！"));
	}
	
}