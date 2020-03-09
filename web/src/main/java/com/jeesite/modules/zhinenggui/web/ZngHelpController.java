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
import com.jeesite.modules.zhinenggui.entity.ZngHelp;
import com.jeesite.modules.zhinenggui.service.ZngHelpService;

/**
 * 帮助信息Controller
 * @author lzy
 * @version 2018-12-26
 */
@Controller
@RequestMapping(value = "${adminPath}/zhinenggui/zhinengguiHelp")
public class ZngHelpController extends BaseController {

	@Autowired
	private ZngHelpService zhinengguiHelpService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ZngHelp get(String id, boolean isNewRecord) {
		return zhinengguiHelpService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiHelp:view")
	@RequestMapping(value = {"list", ""})
	public String list(ZngHelp zhinengguiHelp, Model model) {
		model.addAttribute("zhinengguiHelp", zhinengguiHelp);
		return "modules/zhinenggui/zhinengguiHelpList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("zhinenggui:zhinengguiHelp:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ZngHelp> listData(ZngHelp zhinengguiHelp, HttpServletRequest request, HttpServletResponse response) {
		Page<ZngHelp> page = zhinengguiHelpService.findPage(new Page<ZngHelp>(request, response), zhinengguiHelp);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("zhinenggui:zhinengguiHelp:view")
	@RequestMapping(value = "form")
	public String form(ZngHelp zhinengguiHelp, Model model) {
		model.addAttribute("zhinengguiHelp", zhinengguiHelp);
		return "modules/zhinenggui/zhinengguiHelpForm";
	}

	/**
	 * 保存帮助信息
	 */
	@RequiresPermissions("zhinenggui:zhinengguiHelp:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ZngHelp zhinengguiHelp) {
		zhinengguiHelpService.save(zhinengguiHelp);
		return renderResult(Global.TRUE, text("保存帮助信息成功！"));
	}
	
	/**
	 * 删除帮助信息
	 */
	@RequiresPermissions("zhinenggui:zhinengguiHelp:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ZngHelp zhinengguiHelp) {
		zhinengguiHelpService.delete(zhinengguiHelp);
		return renderResult(Global.TRUE, text("删除帮助信息成功！"));
	}
	
}