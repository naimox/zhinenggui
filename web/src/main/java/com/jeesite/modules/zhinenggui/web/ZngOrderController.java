/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.CookieUtils;
import com.jeesite.modules.common.AuthorizedUser;
import com.jeesite.modules.zhinenggui.entity.ZngCabinets;
import com.jeesite.modules.zhinenggui.entity.ZngDevice;
import com.jeesite.modules.zhinenggui.entity.ZngUser;
import com.jeesite.modules.zhinenggui.search.CabinetsAndOrder;
import com.jeesite.modules.zhinenggui.search.DoorSizeCount;
import com.jeesite.modules.zhinenggui.search.OrderSearch;
import com.jeesite.modules.zhinenggui.service.ZngCabinetsService;
import com.jeesite.modules.zhinenggui.service.ZngDeviceService;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.utils.CommonUtils;
import com.jeesite.modules.utils.OpenDoorUtils;
import com.jeesite.tio.common.TioConstants;
import com.jeesite.tio.common.TioPacket;
import com.jeesite.tio.model.KeyMessage;
import com.jeesite.tio.server.TioServerGo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.zhinenggui.entity.ZngOrder;
import com.jeesite.modules.zhinenggui.service.ZngOrderService;
import org.tio.core.Tio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 订单表Controller
 * @author lzy
 * @version 2018-08-28
 */
@Controller
@RequestMapping(value = "${adminPath}/zhinenggui/zhinengguiOrder")
public class ZngOrderController extends BaseController {

	@Autowired
	private ZngOrderService zhinengguiOrderService;
	@Autowired
	private ZngDeviceService zhinengguiDeviceService;
	@Autowired
	private ZngCabinetsService zhinengguiCabinetsService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ZngOrder get(String id, boolean isNewRecord) {
		return zhinengguiOrderService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(OrderSearch zhinengguiOrder, Model model) {
		model.addAttribute("zhinengguiOrder", zhinengguiOrder);
		return "modules/zhinenggui/zhinengguiOrderList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDevice:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<OrderSearch> listData(OrderSearch orderSearch, HttpServletRequest request, HttpServletResponse response) {
		User user = UserUtils.getUser();
		if (!"system".equalsIgnoreCase(user.getId())) {
			orderSearch.setUserCode(UserUtils.getUser().getUserCode());
		}

		Page<OrderSearch> page = zhinengguiOrderService.findPage(new Page<OrderSearch>(request, response), orderSearch,request.getParameter("pageSize"),request.getParameter("pageNo"));
		return page;
	}
//	/**
//	 * 查询列表数据
//	 */
//	@RequiresPermissions("zhinenggui:zhinengguiOrder:view")
//	@RequestMapping(value = "listData2")
//	@ResponseBody
//	public Page<OrderSearch> listData2(OrderSearch zhinengguiOrder, HttpServletRequest request, HttpServletResponse response) {
//		Page<OrderSearch> page = new Page<>(request,response);
//		User user = UserUtils.getUser();
//		if (!"system".equalsIgnoreCase(user.getId())) {
//			String userId = user.getId();
//			ZngDevice zhinengguiDevice = new ZngDevice();
//			zhinengguiDevice.setByUser(userId);
//			List<ZngDevice> deviceList = zhinengguiDeviceService.findList(zhinengguiDevice);
//			String deviceIds = CommonUtils.getDevicesIds(deviceList);
//			if(deviceIds.equals("")) {
//				deviceIds="null";
//			}
//			zhinengguiOrder.setDeviceId(deviceIds);
//		}
//		List<OrderSearch> list = zhinengguiOrderService.findList(zhinengguiOrder);
//		String sPageSize = request.getParameter("pageSize");
//		Integer pageSize = 20;
//		String sPageNo = request.getParameter("pageNo");
//		Integer pageNo = 1;
//		if(StringUtils.isNotBlank(sPageSize)){
//			pageSize = Integer.parseInt(sPageSize);
//		}
//		if(StringUtils.isNotBlank(sPageNo)){
//			pageNo = Integer.parseInt(sPageNo);
//		}
//		if (list.size() > pageNo*pageSize) {
//			page.setList(list.subList((pageNo-1)*pageSize, pageNo*pageSize));
//		}else{
//			if (list.size() < (pageNo - 1) * pageSize) {
//				page.setList(list.subList(0, list.size()));
//				page.setPageNo(1);
//			}else {
//				page.setList(list.subList((pageNo-1)*pageSize, list.size()));
//			}
//		}
//		page.setCount(list.size());
//		return page;
//	}
//	public Page<ZngOrder> listData(ZngOrder zhinengguiOrder, HttpServletRequest request, HttpServletResponse response) {
////		Page<ZngOrder> page = zhinengguiOrderService.findPage(new Page<ZngOrder>(request, response), zhinengguiOrder);
//		Page<ZngOrder> page = new Page<>(request,response);
//		page.setList(zhinengguiOrderService.findList(zhinengguiOrder));
//		return page;
//	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("zhinenggui:zhinengguiOrder:view")
	@RequestMapping(value = "form")
	public String form(ZngOrder zhinengguiOrder, Model model) {
		model.addAttribute("zhinengguiOrder", zhinengguiOrder);
		return "modules/zhinenggui/zhinengguiOrderForm";
	}

	/**
	 * 保存订单表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiOrder:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ZngOrder zhinengguiOrder) {
		String msg = "";
		String flag = Global.TRUE;
		try {
			zhinengguiOrderService.save(zhinengguiOrder);
			if("1".equalsIgnoreCase(zhinengguiOrder.getState())){
				msg = "保存订单表成功！";
			}else{

			}
		}catch (Exception e){
			flag = Global.FALSE;
			msg = "订单保存失败";
			logger.error(msg + " ：" + e);
		}
		return renderResult(flag, text(msg));
	}
	
	/**
	 * 停用订单表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiOrder:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(ZngOrder zhinengguiOrder) {
		zhinengguiOrder.setStatus(ZngOrder.STATUS_DISABLE);
		zhinengguiOrderService.updateStatus(zhinengguiOrder);
		return renderResult(Global.TRUE, text("停用订单表成功"));
	}
	
	/**
	 * 启用订单表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiOrder:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(ZngOrder zhinengguiOrder) {
		zhinengguiOrder.setStatus(ZngOrder.STATUS_NORMAL);
		zhinengguiOrderService.updateStatus(zhinengguiOrder);
		return renderResult(Global.TRUE, text("启用订单表成功"));
	}
	
	/**
	 * 删除订单表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiOrder:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ZngOrder zhinengguiOrder) {
		zhinengguiOrderService.delete(zhinengguiOrder);
		return renderResult(Global.TRUE, text("删除订单表成功！"));
	}




	
}