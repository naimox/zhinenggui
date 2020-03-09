/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.web;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.zhinenggui.entity.ZngDoorPrice;
import com.jeesite.modules.zhinenggui.entity.ZngDoorPriceTree;
import com.jeesite.modules.zhinenggui.service.ZngDoorPriceService;
import com.jeesite.modules.test.entity.TestTree;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 柜门价格表Controller
 * @author lzy
 * @version 2018-08-28
 */
@Controller
@RequestMapping(value = "${adminPath}/zhinenggui/zhinengguiDoorPrice")
public class ZngDoorPriceController extends BaseController {

	@Autowired
	private ZngDoorPriceService zhinengguiDoorPriceService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ZngDoorPrice get(String id, boolean isNewRecord) {
		return zhinengguiDoorPriceService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDevice:view")
	@RequestMapping(value = {"list", ""})
	public String list(ZngDoorPrice zhinengguiDoorPrice, Model model) {
		model.addAttribute("zhinengguiDoorPrice", zhinengguiDoorPrice);
		return "modules/zhinenggui/zhinengguiDoorPriceList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDevice:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ZngDoorPrice> listData(ZngDoorPrice zhinengguiDoorPrice, HttpServletRequest request, HttpServletResponse response) {
		Page<ZngDoorPrice> page = zhinengguiDoorPriceService.findPage(new Page<ZngDoorPrice>(request, response), zhinengguiDoorPrice);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDevice:view")
	@RequestMapping(value = "form")
	public String form(ZngDoorPrice zhinengguiDoorPrice, Model model) {
		String doorId = zhinengguiDoorPrice.getDoorId();
		model.addAttribute("doorId", doorId);
		zhinengguiDoorPrice = zhinengguiDoorPriceService.getDoorPriceByDoorId(doorId);
		if (zhinengguiDoorPrice==null) zhinengguiDoorPrice = new ZngDoorPrice();
		zhinengguiDoorPrice.setDoorId(doorId);
		model.addAttribute("zhinengguiDoorPrice", zhinengguiDoorPrice);
		return "modules/zhinenggui/zhinengguiDoorPriceForm";
	}

	/**
	 * 保存柜门价格表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDevice:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ZngDoorPrice zhinengguiDoorPrice) {
		zhinengguiDoorPriceService.save(zhinengguiDoorPrice);
		return renderResult(Global.TRUE, text("保存柜门价格表成功！"));
	}
	
	/**
	 * 停用柜门价格表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDevice:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(ZngDoorPrice zhinengguiDoorPrice) {
		zhinengguiDoorPrice.setStatus(ZngDoorPrice.STATUS_DISABLE);
		zhinengguiDoorPriceService.updateStatus(zhinengguiDoorPrice);
		return renderResult(Global.TRUE, text("停用柜门价格表成功"));
	}
	
	/**
	 * 启用柜门价格表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDevice:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(ZngDoorPrice zhinengguiDoorPrice) {
		zhinengguiDoorPrice.setStatus(ZngDoorPrice.STATUS_NORMAL);
		zhinengguiDoorPriceService.updateStatus(zhinengguiDoorPrice);
		return renderResult(Global.TRUE, text("启用柜门价格表成功"));
	}
	
	/**
	 * 删除柜门价格表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDevice:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ZngDoorPrice zhinengguiDoorPrice) {
		zhinengguiDoorPriceService.delete(zhinengguiDoorPrice);
		return renderResult(Global.TRUE, text("删除柜门价格表成功！"));
	}

	@RequestMapping(value = "treeData")
	@ResponseBody
	public List<Map<String, Object>> treeData() {
		List<Map<String, Object>> mapList = ListUtils.newArrayList();
		List<ZngDoorPriceTree> list = zhinengguiDoorPriceService.findTreeList();
		for (int i=0; i<list.size(); i++){
			ZngDoorPriceTree e = list.get(i);
			Map<String, Object> map = MapUtils.newHashMap();
			map.put("id", e.getDoorId());
			map.put("pId", e.getPid());
			map.put("name", e.getName());
			mapList.add(map);
		}
		return mapList;
	}
	
}