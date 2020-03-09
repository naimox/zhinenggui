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
import com.jeesite.modules.zhinenggui.entity.ZngTrade;
import com.jeesite.modules.zhinenggui.service.ZngTradeService;

/**
 * 交易明细Controller
 * @author lzy
 * @version 2018-08-28
 */
@Controller
@RequestMapping(value = "${adminPath}/zhinenggui/zhinengguiTrade")
public class ZngTradeController extends BaseController {

	@Autowired
	private ZngTradeService zhinengguiTradeService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ZngTrade get(String id, boolean isNewRecord) {
		return zhinengguiTradeService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiTrade:view")
	@RequestMapping(value = {"list", ""})
	public String list(ZngTrade zhinengguiTrade, Model model) {
		model.addAttribute("zhinengguiTrade", zhinengguiTrade);
		return "modules/zhinenggui/zhinengguiTradeList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("zhinenggui:zhinengguiTrade:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ZngTrade> listData(ZngTrade zhinengguiTrade, HttpServletRequest request, HttpServletResponse response) {
		Page<ZngTrade> page = zhinengguiTradeService.findPage(new Page<ZngTrade>(request, response), zhinengguiTrade);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("zhinenggui:zhinengguiTrade:view")
	@RequestMapping(value = "form")
	public String form(ZngTrade zhinengguiTrade, Model model) {
		model.addAttribute("zhinengguiTrade", zhinengguiTrade);
		return "modules/zhinenggui/zhinengguiTradeForm";
	}

	/**
	 * 保存交易明细
	 */
	@RequiresPermissions("zhinenggui:zhinengguiTrade:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ZngTrade zhinengguiTrade) {
		zhinengguiTradeService.save(zhinengguiTrade);
		return renderResult(Global.TRUE, text("保存交易明细成功！"));
	}
	
	/**
	 * 停用交易明细
	 */
	@RequiresPermissions("zhinenggui:zhinengguiTrade:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(ZngTrade zhinengguiTrade) {
		zhinengguiTrade.setStatus(ZngTrade.STATUS_DISABLE);
		zhinengguiTradeService.updateStatus(zhinengguiTrade);
		return renderResult(Global.TRUE, text("停用交易明细成功"));
	}
	
	/**
	 * 启用交易明细
	 */
	@RequiresPermissions("zhinenggui:zhinengguiTrade:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(ZngTrade zhinengguiTrade) {
		zhinengguiTrade.setStatus(ZngTrade.STATUS_NORMAL);
		zhinengguiTradeService.updateStatus(zhinengguiTrade);
		return renderResult(Global.TRUE, text("启用交易明细成功"));
	}
	
	/**
	 * 删除交易明细
	 */
	@RequiresPermissions("zhinenggui:zhinengguiTrade:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ZngTrade zhinengguiTrade) {
		zhinengguiTradeService.delete(zhinengguiTrade);
		return renderResult(Global.TRUE, text("删除交易明细成功！"));
	}
	
}