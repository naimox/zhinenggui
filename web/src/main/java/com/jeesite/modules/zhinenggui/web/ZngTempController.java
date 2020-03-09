/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.web;

import com.jeesite.common.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

/**
 * 用户寄件接口
 * @author lzy
 * @version 2018-08-28
 */
@Controller
@RequestMapping(value = "${frontPath}/zhinenggui/zhinengguiTemp")
public class ZngTempController extends BaseController {

	/**
	 * 快递员扫码登录
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"temp"})
	public String temp(Model model) {
		model.addAttribute("data", new HashMap<String, String>());
		return "敬请期待。。。";
	}

}