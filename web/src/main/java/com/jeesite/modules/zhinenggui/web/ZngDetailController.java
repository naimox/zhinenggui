/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.modules.zhinenggui.entity.ZngWithdrawApplication;
import com.jeesite.modules.zhinenggui.search.OrderSearch;
import com.jeesite.modules.zhinenggui.search.WalletDetail;
import com.jeesite.modules.zhinenggui.service.ZngWalletService;
import com.jeesite.modules.zhinenggui.service.ZngWithdrawApplicationService;
import com.jeesite.modules.sys.utils.EmpUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.zhinenggui.entity.ZngDetail;
import com.jeesite.modules.zhinenggui.service.ZngDetailService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * zhinenggui_detailController
 * @author pfzheng
 * @version 2018-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/zhinenggui/zhinengguiDetail")
public class ZngDetailController extends BaseController {

	@Autowired
	private ZngDetailService zhinengguiDetailService;

	@Autowired
	private ZngWalletService zhinengguiWalletService;

	@Autowired
	private ZngWithdrawApplicationService zhinengguiWithdrawApplicationService;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ZngDetail get(String id, boolean isNewRecord) {
		return zhinengguiDetailService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(ZngDetail zhinengguiDetail, Model model) {
		ZngWithdrawApplication zhinengguiWithdrawApplication = zhinengguiWithdrawApplicationService.getTop(UserUtils.getUser().getId());
		model.addAttribute("money", zhinengguiWalletService.getWalletByUserId(UserUtils.getUser().getId()).getBalance());
		model.addAttribute("applicationType", getApplicationType(zhinengguiWithdrawApplication == null?-1:zhinengguiWithdrawApplication.getType()));
		model.addAttribute("zhinengguiDetail", zhinengguiDetail);
		return "modules/zhinenggui/zhinengguiDetailList";
	}
//	申请中	1
//	申请通过	2
//	申请驳回	3
//	提现中	4
	private String getApplicationType(int type){
		String s = "";
		switch (type){
			case 1:
				s = "申请中";
				break;
			case 3:
				s = "申请驳回";
				break;
			case 4:
				s = "提现中";
				break;
			case 5:
				s = "提现中";
				break;
		}
		return s;
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDetail:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ZngDetail> listData(@RequestParam("money_gte") String moneyGet, @RequestParam("money_lte") String moneyLte,
									 @RequestParam("deviceId") String deviceName, @RequestParam("userId") String userPhone,
									 @RequestParam("type") String type,
									 @RequestParam("createTime_gte") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date createTimeGte,
									 @RequestParam("createTime_lte") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date createTimeLte,
												 HttpServletRequest request, HttpServletResponse response) {
		Page<ZngDetail> page = new Page<>(request,response);
		WalletDetail walletDetail = new WalletDetail();
		try {
			walletDetail.setMoneyGte(Double.parseDouble(moneyGet));
			walletDetail.setMoneyLte(Double.parseDouble(moneyLte));
		}catch (Exception e){
			walletDetail.setMoneyGte(0);
			walletDetail.setMoneyLte(0);
		}
		try {
			walletDetail.setDetailType(Integer.parseInt(type));
		}catch (Exception e){
			walletDetail.setDetailType(-1);
		}
		walletDetail.setUserId(userPhone);
		walletDetail.setByUser(UserUtils.getUser().getId());
		walletDetail.setDriverId(deviceName);
		walletDetail.setCreateTimeGte(createTimeGte);
		walletDetail.setCreateTimeLte(createTimeLte);
		List<ZngDetail> list = zhinengguiDetailService.findDetailList(walletDetail);

		String sPageSize = request.getParameter("pageSize");
		Integer pageSize = 20;
		String sPageNo = request.getParameter("pageNo");
		Integer pageNo = 1;
		if(com.jeesite.common.lang.StringUtils.isNotBlank(sPageSize)){
			pageSize = Integer.parseInt(sPageSize);
		}
		if(com.jeesite.common.lang.StringUtils.isNotBlank(sPageNo)){
			pageNo = Integer.parseInt(sPageNo);
		}
		if (list.size() > pageNo*pageSize) {
			page.setList(list.subList((pageNo-1)*pageSize, pageNo*pageSize));
		}else{
			page.setList(list.subList(0, list.size()));
		}
		page.setCount(list.size());
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDetail:view")
	@RequestMapping(value = "form")
	public String form(ZngDetail zhinengguiDetail, Model model) {
		if (StringUtils.isEmpty(UserUtils.getUser().getWxOpenid()))
			model.addAttribute("binWxUrl", "http://dongdongkeji.cn/php_api/api/api/to_x_money?user_id="+UserUtils.getUser().getId());
		model.addAttribute("zhinengguiDetail", zhinengguiDetail);
		return "modules/zhinenggui/zhinengguiDetailForm";
	}

	/**
	 * 保存zhinenggui_detail
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDetail:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ZngDetail zhinengguiDetail) {
		zhinengguiDetailService.save(zhinengguiDetail);
		return renderResult(Global.TRUE, text("保存zhinenggui_detail成功！"));
	}

	@RequiresPermissions("zhinenggui:zhinengguiDetail:edit")
	@PostMapping(value = "getUserOpenId")
	@ResponseBody
	public String getUserOpenId(){
		return UserUtils.getUser().getWxOpenid();
	}

	/**
	 * 删除zhinenggui_detail
	 */
	@RequiresPermissions("zhinenggui:zhinengguiDetail:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ZngDetail zhinengguiDetail) {
		zhinengguiDetailService.delete(zhinengguiDetail);
		return renderResult(Global.TRUE, text("删除zhinenggui_detail成功！"));
	}
	
}