/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.zhinenggui.entity.ZngFunctionImages;
import com.jeesite.modules.zhinenggui.service.ZngFunctionImagesService;
import com.jeesite.modules.sys.entity.Employee;
import com.jeesite.modules.sys.service.EmployeeService;
import com.jeesite.modules.sys.utils.UserUtils;

/**
 * 综合图片Controller
 * @author lzy
 * @version 2019-01-14
 */
@Controller
@RequestMapping(value = "${adminPath}/zhinenggui/zhinengguiFunctionImages")
public class ZngFunctionImagesController extends BaseController {

	@Autowired
	private ZngFunctionImagesService zhinengguiFunctionImagesService;
	@Autowired
	private EmployeeService employeeService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ZngFunctionImages get(String id, boolean isNewRecord) {
		return zhinengguiFunctionImagesService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiFunctionImages:view")
	@RequestMapping(value = {"list", ""})
	public String list(ZngFunctionImages zhinengguiFunctionImages, Model model) {
		model.addAttribute("zhinengguiFunctionImages", zhinengguiFunctionImages);
		return "modules/zhinenggui/zhinengguiFunctionImagesList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("zhinenggui:zhinengguiFunctionImages:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ZngFunctionImages> listData(ZngFunctionImages zhinengguiFunctionImages, HttpServletRequest request, HttpServletResponse response) {
		zhinengguiFunctionImages.setPage(new Page<>(request, response));
		Page<ZngFunctionImages> page = zhinengguiFunctionImagesService.findPage(zhinengguiFunctionImages);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("zhinenggui:zhinengguiFunctionImages:view")
	@RequestMapping(value = "form")
	public String form(ZngFunctionImages zhinengguiFunctionImages, Model model) {
		model.addAttribute("zhinengguiFunctionImages", zhinengguiFunctionImages);
		return "modules/zhinenggui/zhinengguiFunctionImagesForm";
	}

	/**
	 * 保存综合图片
	 */
	@RequiresPermissions("zhinenggui:zhinengguiFunctionImages:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ZngFunctionImages zhinengguiFunctionImages) {
		if(StringUtils.isEmpty(zhinengguiFunctionImages.getByCompany())||
		StringUtils.isEmpty(zhinengguiFunctionImages.getByOffice())||
		StringUtils.isEmpty(zhinengguiFunctionImages.getByUser())) {
			Employee employee = new Employee();
			employee.setEmpCode(UserUtils.getUser().getUserCode());
			zhinengguiFunctionImages.setByUser(UserUtils.getUser().getUserCode());
			employee = employeeService.get(employee);
			zhinengguiFunctionImages.setByUser(UserUtils.getUser().getUserCode());
			zhinengguiFunctionImages.setByCompany(employee.getCompany().getCompanyCode());
			zhinengguiFunctionImages.setByOffice(employee.getOffice().getOfficeCode());
		}
		zhinengguiFunctionImagesService.save(zhinengguiFunctionImages);
		return renderResult(Global.TRUE, text("保存综合图片成功！"));
	}
	
	/**
	 * 停用综合图片
	 */
	@RequiresPermissions("zhinenggui:zhinengguiFunctionImages:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(ZngFunctionImages zhinengguiFunctionImages) {
		zhinengguiFunctionImages.setStatus(ZngFunctionImages.STATUS_DISABLE);
		zhinengguiFunctionImagesService.updateStatus(zhinengguiFunctionImages);
		return renderResult(Global.TRUE, text("停用综合图片成功"));
	}
	
	/**
	 * 启用综合图片
	 */
	@RequiresPermissions("zhinenggui:zhinengguiFunctionImages:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(ZngFunctionImages zhinengguiFunctionImages) {
		zhinengguiFunctionImages.setStatus(ZngFunctionImages.STATUS_NORMAL);
		zhinengguiFunctionImagesService.updateStatus(zhinengguiFunctionImages);
		return renderResult(Global.TRUE, text("启用综合图片成功"));
	}
	
	/**
	 * 删除综合图片
	 */
	@RequiresPermissions("zhinenggui:zhinengguiFunctionImages:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ZngFunctionImages zhinengguiFunctionImages) {
		zhinengguiFunctionImagesService.delete(zhinengguiFunctionImages);
		return renderResult(Global.TRUE, text("删除综合图片成功！"));
	}
	
}