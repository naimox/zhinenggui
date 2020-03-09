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
import com.jeesite.modules.zhinenggui.entity.ZngPointNominate;
import com.jeesite.modules.zhinenggui.service.ZngPointNominateService;

/**
 * 点位推荐表Controller
 * @author lzy
 * @version 2018-12-26
 */
@Controller
@RequestMapping(value = "${adminPath}/zhinenggui/zhinengguiPointNominate")
public class ZngPointNominateController extends BaseController {

	@Autowired
	private ZngPointNominateService zhinengguiPointNominateService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ZngPointNominate get(String id, boolean isNewRecord) {
		return zhinengguiPointNominateService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiPointNominate:view")
	@RequestMapping(value = {"list", ""})
	public String list(ZngPointNominate zhinengguiPointNominate, Model model) {
		model.addAttribute("zhinengguiPointNominate", zhinengguiPointNominate);
		return "modules/zhinenggui/zhinengguiPointNominateList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("zhinenggui:zhinengguiPointNominate:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ZngPointNominate> listData(ZngPointNominate zhinengguiPointNominate, HttpServletRequest request, HttpServletResponse response) {
		Page<ZngPointNominate> page = zhinengguiPointNominateService.findPage(new Page<ZngPointNominate>(request, response), zhinengguiPointNominate);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("zhinenggui:zhinengguiPointNominate:view")
	@RequestMapping(value = "form")
	public String form(ZngPointNominate zhinengguiPointNominate, Model model) {
		model.addAttribute("zhinengguiPointNominate", zhinengguiPointNominate);
		return "modules/zhinenggui/zhinengguiPointNominateForm";
	}

	/**
	 * 保存点位推荐表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiPointNominate:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ZngPointNominate zhinengguiPointNominate) {
		zhinengguiPointNominateService.save(zhinengguiPointNominate);
		return renderResult(Global.TRUE, text("保存点位推荐表成功！"));
	}
	
	/**
	 * 删除点位推荐表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiPointNominate:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ZngPointNominate zhinengguiPointNominate) {
		zhinengguiPointNominateService.delete(zhinengguiPointNominate);
		return renderResult(Global.TRUE, text("删除点位推荐表成功！"));
	}
	
}