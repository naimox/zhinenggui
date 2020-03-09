/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.modules.zhinenggui.entity.ZngDetail;
import com.jeesite.modules.zhinenggui.service.ZngDetailService;
import com.jeesite.modules.zhinenggui.service.ZngWalletService;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.utils.QRCodeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.zhinenggui.entity.ZngWithdrawApplication;
import com.jeesite.modules.zhinenggui.service.ZngWithdrawApplicationService;

import java.util.Date;

/**
 * zhinenggui_withdraw_applicationController
 *
 * @author lfy
 * @version 2018-11-23
 */
@Controller
@RequestMapping(value = "${adminPath}/zhinenggui/zhinengguiWithdrawApplication")
public class ZngWithdrawApplicationController extends BaseController {

    @Autowired
    private ZngWithdrawApplicationService zhinengguiWithdrawApplicationService;

    @Autowired
    private UserService userService;
    /**
     * 获取数据
     */
    @ModelAttribute
    public ZngWithdrawApplication get(String id, boolean isNewRecord) {
        return zhinengguiWithdrawApplicationService.get(id, isNewRecord);
    }

    /**
     * 查询列表
     */
    @RequiresPermissions("zhinenggui:zhinengguiWithdrawApplication:view")
    @RequestMapping(value = {"list", ""})
    public String list(ZngWithdrawApplication zhinengguiWithdrawApplication, Model model) {
        model.addAttribute("zhinengguiWithdrawApplication", zhinengguiWithdrawApplication);
        return "modules/zhinenggui/zhinengguiWithdrawApplicationList";
    }

    /**
     * 查询列表数据
     */
    @RequiresPermissions("zhinenggui:zhinengguiWithdrawApplication:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<ZngWithdrawApplication> listData(ZngWithdrawApplication zhinengguiWithdrawApplication, HttpServletRequest request, HttpServletResponse response) {
        Page<ZngWithdrawApplication> page = zhinengguiWithdrawApplicationService.findPage(new Page<ZngWithdrawApplication>(request, response), zhinengguiWithdrawApplication);
        return page;
    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("zhinenggui:zhinengguiWithdrawApplication:view")
    @RequestMapping(value = "form")
    public String form(ZngWithdrawApplication zhinengguiWithdrawApplication, Model model) {
        model.addAttribute("zhinengguiWithdrawApplication", zhinengguiWithdrawApplication);
        return "modules/zhinenggui/zhinengguiWithdrawApplicationForm";
    }

    /**
     * 保存zhinenggui_withdraw_application
     */
    @RequiresPermissions("zhinenggui:zhinengguiWithdrawApplication:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated ZngWithdrawApplication zhinengguiWithdrawApplication) {
        if (zhinengguiWithdrawApplication.getType() == 2) {
			String str = QRCodeUtils.withdrawDeposit(userService.get(zhinengguiWithdrawApplication.getUserId()).getWxOpenid(), zhinengguiWithdrawApplication.getMoney(),zhinengguiWithdrawApplication.getUserId());
			System.out.println(str);
            JSONObject json = JSONObject.parseObject(str);
            if (1 == json.getInteger("code")) {
                zhinengguiWithdrawApplication.setType(2);//1:申请中2:申请通过3:申请驳回4:体现中5:提现失败
                zhinengguiWithdrawApplicationService.save(zhinengguiWithdrawApplication);
                return renderResult(Global.TRUE, text("审核申请提现成功！"));
            }else {
            	return renderResult(Global.FALSE, text(json.getString("msg")));
            }
        }
        zhinengguiWithdrawApplicationService.save(zhinengguiWithdrawApplication);
        return renderResult(Global.TRUE, text("提现申请已驳回！"));
    }

    /**
     * 删除zhinenggui_withdraw_application
     */
    @RequiresPermissions("zhinenggui:zhinengguiWithdrawApplication:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(ZngWithdrawApplication zhinengguiWithdrawApplication) {
        zhinengguiWithdrawApplicationService.delete(zhinengguiWithdrawApplication);
        return renderResult(Global.TRUE, text("删除zhinenggui_withdraw_application成功！"));
    }
}