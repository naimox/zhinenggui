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
import com.jeesite.modules.zhinenggui.entity.ZngUser;
import com.jeesite.modules.zhinenggui.service.ZngUserService;
import com.jeesite.modules.sys.entity.Employee;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.EmployeeService;
import com.jeesite.modules.sys.utils.UserUtils;

/**
 * 用户表Controller
 * @author lzy
 * @version 2018-08-28
 */
@Controller
@RequestMapping(value = "${adminPath}/zhinenggui/zhinengguiUser")
public class ZngUserController extends BaseController {

	@Autowired
	private ZngUserService zhinengguiUserService;
	@Autowired
	private EmployeeService employeeService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ZngUser get(String id, boolean isNewRecord) {
		return zhinengguiUserService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(ZngUser zhinengguiUser, Model model) {
		model.addAttribute("zhinengguiUser", zhinengguiUser);
		return "modules/zhinenggui/zhinengguiUserList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("zhinenggui:zhinengguiUser:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ZngUser> listData(ZngUser zhinengguiUser, HttpServletRequest request, HttpServletResponse response) {
		Page<ZngUser> page = zhinengguiUserService.findPage(new Page<ZngUser>(request, response), zhinengguiUser);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("zhinenggui:zhinengguiUser:view")
	@RequestMapping(value = "form")
	public String form(ZngUser zhinengguiUser, Model model) {
		model.addAttribute("zhinengguiUser", zhinengguiUser);
		return "modules/zhinenggui/zhinengguiUserForm";
	}

	/**
	 * 保存用户表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiUser:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ZngUser zhinengguiUser) {
		Employee employee = new Employee();
		if(UserUtils.getUser().getUserType().equals("none")) {//none为管理员 
			if(zhinengguiUser.getByUser().equals(",")) {
				return renderResult(Global.FALSE, text("所属人不能为空!"));
			}
			if(!StringUtils.isEmpty(zhinengguiUser.getByUser())&&zhinengguiUser.getByUser().indexOf(",")>=0) {
				zhinengguiUser.setByUser(zhinengguiUser.getByUser().substring(zhinengguiUser.getByUser().indexOf(",")+1, zhinengguiUser.getByUser().length()));
			}
			employee.setEmpCode(zhinengguiUser.getByUser());
		}else {
			employee.setEmpCode(UserUtils.getUser().getUserCode());
			zhinengguiUser.setByUser(UserUtils.getUser().getUserCode());
		}
		employee = employeeService.get(employee);
		zhinengguiUser.setByCompany(employee.getCompany().getCompanyCode());
		zhinengguiUser.setByOffice(employee.getOffice().getOfficeCode());
		
		zhinengguiUserService.save(zhinengguiUser);
		return renderResult(Global.TRUE, text("保存用户表成功！"));
	}
	
	/**
	 * 停用用户表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiUser:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(ZngUser zhinengguiUser) {
		zhinengguiUser.setStatus(ZngUser.STATUS_DISABLE);
		zhinengguiUserService.updateStatus(zhinengguiUser);
		return renderResult(Global.TRUE, text("停用用户表成功"));
	}
	
	/**
	 * 启用用户表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiUser:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(ZngUser zhinengguiUser) {
		zhinengguiUser.setStatus(ZngUser.STATUS_NORMAL);
		zhinengguiUserService.updateStatus(zhinengguiUser);
		return renderResult(Global.TRUE, text("启用用户表成功"));
	}
	
	/**
	 * 删除用户表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiUser:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ZngUser zhinengguiUser) {
		zhinengguiUserService.delete(zhinengguiUser);
		return renderResult(Global.TRUE, text("删除用户表成功！"));
	}
	
}