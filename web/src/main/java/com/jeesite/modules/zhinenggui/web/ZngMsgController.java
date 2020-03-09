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
import com.jeesite.modules.zhinenggui.entity.ZngMsg;
import com.jeesite.modules.zhinenggui.service.ZngMsgService;

/**
 * zhinenggui_msgController
 * @author lzy
 * @version 2018-12-24
 */
@Controller
@RequestMapping(value = "${adminPath}/zhinenggui/zhinengguiMsg")
public class ZngMsgController extends BaseController {

	@Autowired
	private ZngMsgService zhinengguiMsgService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ZngMsg get(String id, boolean isNewRecord) {
		return zhinengguiMsgService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiMsg:view")
	@RequestMapping(value = {"list", ""})
	public String list(ZngMsg zhinengguiMsg, Model model) {
		model.addAttribute("zhinengguiMsg", zhinengguiMsg);
		return "modules/zhinenggui/zhinengguiMsgList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("zhinenggui:zhinengguiMsg:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ZngMsg> listData(ZngMsg zhinengguiMsg, HttpServletRequest request, HttpServletResponse response) {
		Page<ZngMsg> page = zhinengguiMsgService.findPage(new Page<ZngMsg>(request, response), zhinengguiMsg);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("zhinenggui:zhinengguiMsg:view")
	@RequestMapping(value = "form")
	public String form(ZngMsg zhinengguiMsg, Model model) {
		model.addAttribute("zhinengguiMsg", zhinengguiMsg);
		return "modules/zhinenggui/zhinengguiMsgForm";
	}

	/**
	 * 保存zhinenggui_msg
	 */
	@RequiresPermissions("zhinenggui:zhinengguiMsg:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ZngMsg zhinengguiMsg) {
		zhinengguiMsgService.save(zhinengguiMsg);
		return renderResult(Global.TRUE, text("保存zhinenggui_msg成功！"));
	}
	
	/**
	 * 停用zhinenggui_msg
	 */
	@RequiresPermissions("zhinenggui:zhinengguiMsg:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(ZngMsg zhinengguiMsg) {
		zhinengguiMsg.setStatus(ZngMsg.STATUS_DISABLE);
		zhinengguiMsgService.updateStatus(zhinengguiMsg);
		return renderResult(Global.TRUE, text("停用zhinenggui_msg成功"));
	}
	
	/**
	 * 启用zhinenggui_msg
	 */
	@RequiresPermissions("zhinenggui:zhinengguiMsg:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(ZngMsg zhinengguiMsg) {
		zhinengguiMsg.setStatus(ZngMsg.STATUS_NORMAL);
		zhinengguiMsgService.updateStatus(zhinengguiMsg);
		return renderResult(Global.TRUE, text("启用zhinenggui_msg成功"));
	}
	
	/**
	 * 删除zhinenggui_msg
	 */
	@RequiresPermissions("zhinenggui:zhinengguiMsg:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ZngMsg zhinengguiMsg) {
		zhinengguiMsgService.delete(zhinengguiMsg);
		return renderResult(Global.TRUE, text("删除zhinenggui_msg成功！"));
	}
	
}