/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.web;

import java.util.List;

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
import com.jeesite.modules.common.AuthorizedUser;
import com.jeesite.modules.zhinenggui.entity.ZngUser;
import com.jeesite.modules.zhinenggui.entity.ZngUserAddress;
import com.jeesite.modules.zhinenggui.service.ZngUserAddressService;
import com.jeesite.modules.zhinenggui.service.ZngUserService;
import com.jeesite.modules.sys.utils.UserUtils;

/**
 * 前端用户登录后地址管理
 * @author lzy
 * @version 2018-08-23
 */
@Controller
@RequestMapping(value = "${frontPath}/zhinenggui/zhinengguiUserAddress")
public class ZngUserAddressFrontController extends BaseController {

	@Autowired
	private ZngUserAddressService zhinengguiUserAddressService;
	@Autowired
	private ZngUserService zhinengguiUserService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ZngUserAddress get(String id, boolean isNewRecord,Model model) {
		
		return zhinengguiUserAddressService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequestMapping(value = {"list", ""})
	public String list(ZngUserAddress zhinengguiUserAddress, Model model) {
		model.addAttribute("zhinengguiUserAddress", zhinengguiUserAddress);
		return "modules/zhinengguiFront/zhinengguiUserAddressList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ZngUserAddress> listData(ZngUserAddress zhinengguiUserAddress, HttpServletRequest request, HttpServletResponse response) {
		
		//该用户下的所有地址信息
		AuthorizedUser currentuser = (AuthorizedUser) UserUtils.getCache("zhinengguiUserCache");
		String userId = currentuser.getUser().getId();
		ZngUserAddress address = new ZngUserAddress();
		address.setUserId(userId);
		
		Page<ZngUserAddress> page = zhinengguiUserAddressService.findPage(new Page<ZngUserAddress>(request, response), address);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequestMapping(value = "form")
	public String form(ZngUserAddress zhinengguiUserAddress, Model model) {
		model.addAttribute("zhinengguiUserAddress", zhinengguiUserAddress);
		return "modules/zhinengguiFront/zhinengguiUserAddressForm";
	}

	/**
	 * 保存用户地址表
	 */
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ZngUserAddress zhinengguiUserAddress) {
		AuthorizedUser currentuser = (AuthorizedUser) UserUtils.getCache("zhinengguiUserCache");
		String userId = currentuser.getUser().getId();
		zhinengguiUserAddress.setUserId(userId);
		zhinengguiUserAddressService.save(zhinengguiUserAddress);
		return renderResult(Global.TRUE, text("保存用户地址表成功！"));
	}
	
	/**
	 * 停用用户地址表
	 */
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(ZngUserAddress zhinengguiUserAddress) {
		zhinengguiUserAddress.setStatus(ZngUserAddress.STATUS_DISABLE);
		zhinengguiUserAddressService.updateStatus(zhinengguiUserAddress);
		return renderResult(Global.TRUE, text("停用用户地址表成功"));
	}
	
	/**
	 * 启用用户地址表
	 */
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(ZngUserAddress zhinengguiUserAddress) {
		zhinengguiUserAddress.setStatus(ZngUserAddress.STATUS_NORMAL);
		zhinengguiUserAddressService.updateStatus(zhinengguiUserAddress);
		return renderResult(Global.TRUE, text("启用用户地址表成功"));
	}
	
	/**
	 * 删除用户地址表
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ZngUserAddress zhinengguiUserAddress) {
		zhinengguiUserAddressService.delete(zhinengguiUserAddress);
		return renderResult(Global.TRUE, text("删除用户地址表成功！"));
	}
	
}