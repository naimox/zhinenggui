/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.parser.Entity;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.zhinenggui.entity.ZngDeviceViewData;
import com.jeesite.modules.zhinenggui.service.ZngDeviceViewDataService;

/**
 * 视图数据Controller
 * @author lzy
 * @version 2019-01-24
 */
@Controller
@RequestMapping(value = "${adminPath}/zhinenggui/zhinengguiDeviceViewData")
public class ZngDeviceViewDataController extends BaseController {

	@Autowired
	private ZngDeviceViewDataService zhinengguiDeviceViewDataService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ZngDeviceViewData get(String id, boolean isNewRecord) {
		return zhinengguiDeviceViewDataService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDeviceViewData:view")
	@RequestMapping(value = {"list", ""})
	public String list(ZngDeviceViewData zhinengguiDeviceViewData, Model model) {
		model.addAttribute("zhinengguiDeviceViewData", zhinengguiDeviceViewData);
		return "modules/zhinenggui/zhinengguiDeviceViewDataList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDeviceViewData:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ZngDeviceViewData> listData(ZngDeviceViewData zhinengguiDeviceViewData, HttpServletRequest request, HttpServletResponse response) {
		zhinengguiDeviceViewData.setPage(new Page<>(request, response));
		Page<ZngDeviceViewData> page = zhinengguiDeviceViewDataService.findPage(zhinengguiDeviceViewData);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDeviceViewData:view")
	@RequestMapping(value = "form")
	public String form(ZngDeviceViewData zhinengguiDeviceViewData, Model model) {
		ZngDeviceViewData zhinengguiDeviceViewDataNew =   zhinengguiDeviceViewDataService.getOneByIdAndNumber(zhinengguiDeviceViewData.getDeviceId(),zhinengguiDeviceViewData.getDeviceNumber());
		if(zhinengguiDeviceViewDataNew==null){
			model.addAttribute("zhinengguiDeviceViewData", zhinengguiDeviceViewData);
			return "modules/zhinenggui/zhinengguiDeviceViewDataForm";
		}
		model.addAttribute("zhinengguiDeviceViewData", zhinengguiDeviceViewDataNew);
		return "modules/zhinenggui/zhinengguiDeviceViewDataForm";
	}

	/**
	 * 保存视图数据
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDeviceViewData:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ZngDeviceViewData zhinengguiDeviceViewData) {
		zhinengguiDeviceViewData.setDeviceGraphView(StringUtils.deleteWhitespace(zhinengguiDeviceViewData.getDeviceGraphView()));
		try {
			JSONObject data = (JSONObject) JSONObject.parse(zhinengguiDeviceViewData.getDeviceGraphView());
		} catch (Exception e) {
			return renderResult(Global.FALSE, text("保存失败,JSON数据格式错误!"));
		}
		
		zhinengguiDeviceViewDataService.save(zhinengguiDeviceViewData);
		return renderResult(Global.TRUE, text("保存视图数据成功！"));
	}
	
}