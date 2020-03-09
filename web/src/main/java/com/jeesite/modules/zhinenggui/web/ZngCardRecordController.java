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
import com.jeesite.modules.zhinenggui.entity.ZngCardRecord;
import com.jeesite.modules.zhinenggui.service.ZngCardRecordService;

/**
 * ic卡操作记录表Controller
 * @author lfy
 * @version 2018-12-26
 */
@Controller
@RequestMapping(value = "${adminPath}/zhinenggui/zhinengguiCardRecord")
public class ZngCardRecordController extends BaseController {

	@Autowired
	private ZngCardRecordService zhinengguiCardRecordService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ZngCardRecord get(String id, boolean isNewRecord) {
		return zhinengguiCardRecordService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCardRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(ZngCardRecord zhinengguiCardRecord, Model model) {
		model.addAttribute("zhinengguiCardRecord", zhinengguiCardRecord);
		return "modules/zhinenggui/zhinengguiCardRecordList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCardRecord:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ZngCardRecord> listData(ZngCardRecord zhinengguiCardRecord, HttpServletRequest request, HttpServletResponse response) {
		Page<ZngCardRecord> page = zhinengguiCardRecordService.findPage(new Page<ZngCardRecord>(request, response), zhinengguiCardRecord);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCardRecord:view")
	@RequestMapping(value = "form")
	public String form(ZngCardRecord zhinengguiCardRecord, Model model) {
		model.addAttribute("zhinengguiCardRecord", zhinengguiCardRecord);
		return "modules/zhinenggui/zhinengguiCardRecordForm";
	}

	/**
	 * 保存ic卡操作记录表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCardRecord:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ZngCardRecord zhinengguiCardRecord) {
		zhinengguiCardRecordService.save(zhinengguiCardRecord);
		return renderResult(Global.TRUE, text("保存ic卡操作记录表成功！"));
	}
	
	/**
	 * 删除ic卡操作记录表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCardRecord:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ZngCardRecord zhinengguiCardRecord) {
		zhinengguiCardRecordService.delete(zhinengguiCardRecord);
		return renderResult(Global.TRUE, text("删除ic卡操作记录表成功！"));
	}
	
}