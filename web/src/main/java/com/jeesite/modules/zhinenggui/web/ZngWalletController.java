/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.zhinenggui.entity.ZngWithdrawApplication;
import com.jeesite.modules.zhinenggui.service.ZngUserService;
import com.jeesite.modules.zhinenggui.service.ZngWithdrawApplicationService;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.utils.QRCodeUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.zhinenggui.entity.ZngWallet;
import com.jeesite.modules.zhinenggui.service.ZngWalletService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 钱包表Controller
 * @author lzy
 * @version 2018-08-28
 */
@Controller
@RequestMapping(value = "${adminPath}/zhinenggui/zhinengguiWallet")
public class ZngWalletController extends BaseController {

	@Autowired
	private ZngWalletService zhinengguiWalletService;

	@Autowired
	private ZngWithdrawApplicationService zhinengguiWithdrawApplicationService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ZngWallet get(String id, boolean isNewRecord) {
		return zhinengguiWalletService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiWallet:view")
	@RequestMapping(value = {"list", ""})
	public String list(ZngWallet zhinengguiWallet, Model model) {
		model.addAttribute("zhinengguiWallet", zhinengguiWallet);
		return "modules/zhinenggui/zhinengguiWalletList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("zhinenggui:zhinengguiWallet:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ZngWallet> listData(ZngWallet zhinengguiWallet, HttpServletRequest request, HttpServletResponse response) {
		Page<ZngWallet> page = zhinengguiWalletService.findPage(new Page<ZngWallet>(request, response), zhinengguiWallet);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("zhinenggui:zhinengguiWallet:view")
	@RequestMapping(value = "form")
	public String form(ZngWallet zhinengguiWallet, Model model) {
		model.addAttribute("openId", UserUtils.getUser().getWxOpenid());
		model.addAttribute("money", zhinengguiWalletService.getWalletByUserId(UserUtils.getUser().getId()).getBalance());
		model.addAttribute("zhinengguiWallet", zhinengguiWallet);
		return "modules/zhinenggui/zhinengguiWalletForm";
	}

	/**
	 * 保存钱包表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiWallet:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ZngWallet zhinengguiWallet) {
		zhinengguiWalletService.save(zhinengguiWallet);
		return renderResult(Global.TRUE, text("保存钱包表成功！"));
	}
	
	/**
	 * 停用钱包表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiWallet:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(ZngWallet zhinengguiWallet) {
		zhinengguiWallet.setStatus(ZngWallet.STATUS_DISABLE);
		zhinengguiWalletService.updateStatus(zhinengguiWallet);
		return renderResult(Global.TRUE, text("停用钱包表成功"));
	}
	
	/**
	 * 启用钱包表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiWallet:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(ZngWallet zhinengguiWallet) {
		zhinengguiWallet.setStatus(ZngWallet.STATUS_NORMAL);
		zhinengguiWalletService.updateStatus(zhinengguiWallet);
		return renderResult(Global.TRUE, text("启用钱包表成功"));
	}
	
	/**
	 * 删除钱包表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiWallet:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ZngWallet zhinengguiWallet) {
		zhinengguiWalletService.delete(zhinengguiWallet);
		return renderResult(Global.TRUE, text("删除钱包表成功！"));
	}

	@RequiresPermissions("zhinenggui:zhinengguiWallet:view")
	@RequestMapping(value = "withdrawForm")
	public String withdrawForm(ZngWallet zhinengguiWallet, Model model) {
		model.addAttribute("openId", UserUtils.getUser().getWxOpenid());
		model.addAttribute("money", zhinengguiWalletService.getWalletByUserId(UserUtils.getUser().getId()).getBalance());
		model.addAttribute("zhinengguiWallet", zhinengguiWallet);
		return "modules/zhinenggui/zhinengguiWithdraw";
	}

	@RequiresPermissions("zhinenggui:zhinengguiWallet:edit")
	@RequestMapping(value = "withdraw")
	@ResponseBody
	public String withdraw(@RequestParam("balance") String balanceStr) {
	    Double balance = 0.0;
	    try {
	        balance = Double.parseDouble(balanceStr);
        }catch (Exception e){
	        return renderResult(Global.FALSE, text("请输入正确金额"));
        }
		if (balance <= 0)
			return renderResult(Global.FALSE, text("请输入正确金额"));
		else if (balance < 1){
			return renderResult(Global.FALSE, text("提現金額必須大余一元"));
		} else if (balance > zhinengguiWalletService.getWalletByUserId(UserUtils.getUser().getId()).getBalance()){
			return renderResult(Global.FALSE, text("余额不足"));
		} else if (StringUtils.isEmpty(UserUtils.getUser().getWxOpenid())){
			return renderResult(Global.FALSE, text("请先绑定微信"));
		}else if (zhinengguiWithdrawApplicationService.getWithdrawApplicationByUserId(UserUtils.getUser().getId(), "1").size() > 0){
			return renderResult(Global.FALSE, text("请不要重复申请提现"));
		}else {
			ZngWithdrawApplication zhinengguiWithdrawApplication = new ZngWithdrawApplication();
			zhinengguiWithdrawApplication.setCreateTime(new Date());
			zhinengguiWithdrawApplication.setMoney(balance);
			zhinengguiWithdrawApplication.setUserId(UserUtils.getUser().getId());
			zhinengguiWithdrawApplication.setType(1);
			zhinengguiWithdrawApplicationService.save(zhinengguiWithdrawApplication);
			return renderResult(Global.TRUE, text("提现申请提交成功"));
		}
	}

    @RequiresPermissions("zhinenggui:zhinengguiWallet:edit")
    @RequestMapping(value = "withdrawAll")
    @ResponseBody
    public String withdrawAll() {
        Double balance = zhinengguiWalletService.getWalletByUserId(UserUtils.getUser().getId()).getBalance();
        if (balance < 1){
            return renderResult(Global.FALSE, text("余额不足"));
        } else if (StringUtils.isEmpty(UserUtils.getUser().getWxOpenid())){
            return renderResult(Global.FALSE, text("请先绑定微信"));
        }else if (zhinengguiWithdrawApplicationService.getWithdrawApplicationByUserId(UserUtils.getUser().getId(), "1").size() > 0){
            return renderResult(Global.FALSE, text("请不要重复申请提现"));
        }else {
            ZngWithdrawApplication zhinengguiWithdrawApplication = new ZngWithdrawApplication();
            zhinengguiWithdrawApplication.setCreateTime(new Date());
            zhinengguiWithdrawApplication.setMoney(balance);
            zhinengguiWithdrawApplication.setUserId(UserUtils.getUser().getId());
            zhinengguiWithdrawApplication.setType(1);
            zhinengguiWithdrawApplicationService.save(zhinengguiWithdrawApplication);
            return renderResult(Global.TRUE, text("提现申请提交成功"));
        }
    }

	@RequiresPermissions("zhinenggui:zhinengguiWallet:view")
	@RequestMapping(value = "bindOpenId")
	@ResponseBody
	public Map bindOpenId() {
		Map<String, String> map = new HashedMap();
		map.put("imgUrl", QRCodeUtils.httpPost("http://dongdongkeji.cn/php_api/api/api/to_x_money?user_id="+UserUtils.getUser().getId(), null, false));
		return map;
	}
	
}