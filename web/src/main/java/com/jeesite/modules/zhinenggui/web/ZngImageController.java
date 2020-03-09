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
import com.jeesite.modules.zhinenggui.entity.ZngImage;
import com.jeesite.modules.zhinenggui.service.ZngImageService;

/**
 * zhinenggui_imageController
 * @author lnn
 * @version 2018-09-06
 */
@Controller
@RequestMapping(value = "${frontPath}/zhinenggui/zhinengguiImage")
public class ZngImageController extends BaseController {

	@Autowired
	private ZngImageService zhinengguiImageService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ZngImage get(String id, boolean isNewRecord) {
		return zhinengguiImageService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiImage:view")
	@RequestMapping(value = {"list", ""})
	public String list(ZngImage zhinengguiImage, Model model) {
		model.addAttribute("zhinengguiImage", zhinengguiImage);
		return "modules/zhinenggui/zhinengguiImageList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("zhinenggui:zhinengguiImage:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ZngImage> listData(ZngImage zhinengguiImage, HttpServletRequest request, HttpServletResponse response) {
		Page<ZngImage> page = zhinengguiImageService.findPage(new Page<ZngImage>(request, response), zhinengguiImage);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("zhinenggui:zhinengguiImage:view")
	@RequestMapping(value = "form")
	public String form(ZngImage zhinengguiImage, Model model) {
		model.addAttribute("zhinengguiImage", zhinengguiImage);
		return "modules/zhinenggui/zhinengguiImageForm";
	}

	/**
	 * 保存zhinenggui_image
	 */
	@RequiresPermissions("zhinenggui:zhinengguiImage:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ZngImage zhinengguiImage) {
		zhinengguiImageService.save(zhinengguiImage);
		return renderResult(Global.TRUE, text("保存zhinenggui_image成功！"));
	}
	
	/**
	 * 停用zhinenggui_image
	 */
	@RequiresPermissions("zhinenggui:zhinengguiImage:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(ZngImage zhinengguiImage) {
		zhinengguiImage.setStatus(ZngImage.STATUS_DISABLE);
		zhinengguiImageService.updateStatus(zhinengguiImage);
		return renderResult(Global.TRUE, text("停用zhinenggui_image成功"));
	}
	
	/**
	 * 启用zhinenggui_image
	 */
	@RequiresPermissions("zhinenggui:zhinengguiImage:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(ZngImage zhinengguiImage) {
		zhinengguiImage.setStatus(ZngImage.STATUS_NORMAL);
		zhinengguiImageService.updateStatus(zhinengguiImage);
		return renderResult(Global.TRUE, text("启用zhinenggui_image成功"));
	}
	
	/**
	 * 删除zhinenggui_image
	 */
	@RequiresPermissions("zhinenggui:zhinengguiImage:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ZngImage zhinengguiImage) {
		zhinengguiImageService.delete(zhinengguiImage);
		return renderResult(Global.TRUE, text("删除zhinenggui_image成功！"));
	}
	
}