/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.common.ResultMsg;
import com.jeesite.modules.zhinenggui.entity.ZngCode;
import com.jeesite.modules.zhinenggui.service.ZngCodeService;
import com.jeesite.modules.sys.entity.Config;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.ConfigService;
import com.jeesite.modules.sys.utils.ConfigUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.utils.QRCodeUtils;
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
import com.jeesite.modules.zhinenggui.entity.ZngCodeStatistics;
import com.jeesite.modules.zhinenggui.service.ZngCodeStatisticsService;

import java.util.List;

/**
 * 验证码统计Controller
 * @author lfy
 * @version 2018-11-29
 */
@Controller
@RequestMapping(value = "${adminPath}/zhinenggui/zhinengguiCodeStatistics")
public class ZngCodeStatisticsController extends BaseController {

	@Autowired
	private ZngCodeStatisticsService zhinengguiCodeStatisticsService;
	@Autowired
	private ZngCodeService zhinengguiCodeService;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ZngCodeStatistics get(String id, boolean isNewRecord) {
		return zhinengguiCodeStatisticsService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCodeStatistics:view")
	@RequestMapping(value = {"list", ""})
	public String list(ZngCodeStatistics zhinengguiCodeStatistics, Model model) {
		model.addAttribute("zhinengguiCodeStatistics", zhinengguiCodeStatistics);
		return "modules/zhinenggui/zhinengguiCodeStatisticsList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCodeStatistics:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ZngCodeStatistics> listData(ZngCodeStatistics zhinengguiCodeStatistics, HttpServletRequest request, HttpServletResponse response) {
		Page<ZngCodeStatistics> page = zhinengguiCodeStatisticsService.findPage(new Page<ZngCodeStatistics>(request, response), zhinengguiCodeStatistics);
		return page;
	}



	@RequiresPermissions("zhinenggui:zhinengguiCodeStatistics:view")
	@RequestMapping(value = "codeList")
	@ResponseBody
	public Page<ZngCode> codeList(ZngCode zhinengguiCode, HttpServletRequest request, HttpServletResponse response) {
        Page<ZngCode> page = new Page<ZngCode>(request, response);
		zhinengguiCode.setUserId(UserUtils.getUser().getId());
		List<ZngCode> list = zhinengguiCodeService.findByUserId(zhinengguiCode);
        String sPageSize = request.getParameter("pageSize");
        Integer pageSize = 20;
        String sPageNo = request.getParameter("pageNo");
        Integer pageNo = 1;
        if(StringUtils.isNotBlank(sPageSize)){
            pageSize = Integer.parseInt(sPageSize);
        }
        if(StringUtils.isNotBlank(sPageNo)){
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
	 * 充值页面
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCodeStatistics:view")
	@RequestMapping(value = "rechargeView")
	public String rechargeView(ZngCodeStatistics zhinengguiCodeStatistics, Model model) {
		model.addAttribute("zhinengguiCodeStatistics", zhinengguiCodeStatistics);
		return "modules/zhinenggui/zhinengguiCodeStatisticsRecharge";
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCodeStatistics:view")
	@RequestMapping(value = "form")
	public String form(ZngCodeStatistics zhinengguiCodeStatistics, Model model) {
		model.addAttribute("zhinengguiCodeStatistics", zhinengguiCodeStatistics);
		return "modules/zhinenggui/zhinengguiCodeStatisticsForm";
	}

	/**
	 * 保存验证码统计
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCodeStatistics:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ZngCodeStatistics zhinengguiCodeStatistics) {
		zhinengguiCodeStatisticsService.save(zhinengguiCodeStatistics);
		return renderResult(Global.TRUE, text("保存验证码统计成功！"));
	}
	
	/**
	 * 删除验证码统计
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCodeStatistics:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ZngCodeStatistics zhinengguiCodeStatistics) {
		zhinengguiCodeStatisticsService.delete(zhinengguiCodeStatistics);
		return renderResult(Global.TRUE, text("删除验证码统计成功！"));
	}

	@RequiresPermissions("zhinenggui:zhinengguiCodeStatistics:view")
	@RequestMapping(value = "show")
	public String show(ZngCode zhinengguiCode, Model model){
		ZngCodeStatistics zhinengguiCodeStatistics = zhinengguiCodeStatisticsService.getCodeStatisticsByUserId(UserUtils.getUser().getId());
		model.addAttribute("zhinengguiCode", zhinengguiCode);
		model.addAttribute("codeNum", zhinengguiCodeStatistics.getCodeNum());
		return "modules/zhinenggui/zhinengguiCodeStatisticsShow";
	}

	@RequiresPermissions("zhinenggui:zhinengguiCodeStatistics:view")
	@RequestMapping(value = "recharge")
	public void recharge(Model model, int rechargeNum ){
//		int rechargeNum = codeNum;
		User user = UserUtils.getUser();
		Double money = rechargeNum * Double.parseDouble(ConfigUtils.getConfig("code_money").getConfigValue());
		model.addAttribute("code", ResultMsg.SUCCESS_FLAG);
		model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
		model.addAttribute("imgUrl", QRCodeUtils.rechargeCode(user.getId(), money));
		model.addAttribute("rechargeNum", rechargeNum);
//		User user = UserUtils.getUser();
//		if (StringUtils.isNotBlank(user.getWxOpenid())){
//			Double money = rechargeNum * Double.parseDouble(ConfigUtils.getConfig("code_money").getConfigValue());
//			model.addAttribute("code", ResultMsg.SUCCESS_FLAG);
//			model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
//			model.addAttribute("imgUrl", QRCodeUtils.rechargeCode(user.getId(), money));
//			model.addAttribute("rechargeNum", rechargeNum);
//		}else {
//			model.addAttribute("code", ResultMsg.INTERFACE_ERROR);
//			model.addAttribute("msg", "未绑定微信");
//		}
//		model.addAttribute('imgUrl', QRCodeUtils.ge)
//		return "modules/zhinenggui/zhinengguiCodeStatisticsRecharge";
	}

}