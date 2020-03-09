/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.zhinenggui.entity.ZngCabinets;
import com.jeesite.modules.zhinenggui.entity.ZngDevice;
import com.jeesite.modules.zhinenggui.entity.ZngUser;
import com.jeesite.modules.zhinenggui.service.ZngCabinetsService;
import com.jeesite.modules.zhinenggui.service.ZngDeviceService;
import com.jeesite.modules.zhinenggui.service.ZngUserService;
import com.jeesite.modules.sys.dao.UserDataScopeDao;
import com.jeesite.modules.sys.entity.CompanyOffice;
import com.jeesite.modules.sys.service.CompanyService;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.utils.CommonUtils;
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
import com.jeesite.modules.zhinenggui.entity.ZngCard;
import com.jeesite.modules.zhinenggui.service.ZngCardService;

/**
 * ic卡表Controller
 * @author pfzheng
 * @version 2018-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/zhinenggui/zhinengguiCard")
public class ZngCardController extends BaseController {

	@Autowired
	private ZngCardService zhinengguiCardService;
	@Autowired
	private ZngUserService zhinengguiUserService;
	@Autowired
	private ZngDeviceService zhinengguiDeviceService;
	@Autowired
	private ZngCabinetsService zhinengguiCabinetsService;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ZngCard get(String id, boolean isNewRecord) {
		return zhinengguiCardService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCard:view")
	@RequestMapping(value = {"list", ""})
	public String list(ZngCard zhinengguiCard, Model model) {
		model.addAttribute("zhinengguiCard", zhinengguiCard);
		return "modules/zhinenggui/zhinengguiCardList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCard:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ZngCard> listData(ZngCard zhinengguiCard, HttpServletRequest request, HttpServletResponse response) {
//		UserUtils.getUser().getUserCode()
//		zhinengguiCard.setUserId();
		Page<ZngCard> page = zhinengguiCardService.findPage(new Page<ZngCard>(request, response), zhinengguiCard);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCard:view")
	@RequestMapping(value = "form")
	public String form(ZngCard zhinengguiCard, Model model, HttpServletRequest request) {
		ZngUser zhinengguiUser = zhinengguiUserService.get(zhinengguiCard.getUserId());
		if (zhinengguiUser != null) {
			zhinengguiCard.setPhone(zhinengguiUser.getPhone());
			zhinengguiCard.setUserName(zhinengguiUser.getName());
		}
		ZngCabinets zhinengguiCabinets = zhinengguiCabinetsService.get(zhinengguiCard.getDoorId());
		if (zhinengguiCabinets != null) zhinengguiCard.setTotalDoorId(zhinengguiCabinets.getTotalDoorId());
		if (request.getParameter("card_id") != null)
			zhinengguiCard.setCardId(request.getParameter("card_id"));
		model.addAttribute("zhinengguiCard", zhinengguiCard);
		return "modules/zhinenggui/zhinengguiCardForm";
	}

	@RequiresPermissions("zhinenggui:zhinengguiCard:view")
	@RequestMapping(value = "read")
	public String read(ZngCard zhinengguiCard, Model model) {
		model.addAttribute("zhinengguiCard", zhinengguiCard);
		return "modules/zhinenggui/zhinengguiCardRead";
	}

	@RequiresPermissions("zhinenggui:zhinengguiCard:view")
	@RequestMapping(value = "readAdd")
	public String readAdd(ZngCard zhinengguiCard, Model model) {
		model.addAttribute("zhinengguiCard", zhinengguiCard);
		return "modules/zhinenggui/zhinengguiCardReadAdd";
	}

	@RequiresPermissions("zhinenggui:zhinengguiCard:view")
	@RequestMapping(value = "readCard")
	@ResponseBody
	public String readCard(ZngCard zhinengguiCard, Model model) {
		ZngCard zhinengguiCard1 = zhinengguiCardService.findByCardId(zhinengguiCard.getCardId());
		if (zhinengguiCard1 == null)
			return renderResult(Global.TRUE, text("ic卡未注册！"));
		else
			return renderResult(Global.TRUE, text(zhinengguiCard1.getId()));
	}

	@RequiresPermissions("zhinenggui:zhinengguiCard:view")
	@RequestMapping(value = "readCardAdd")
	@ResponseBody
	public String readCardAdd(ZngCard zhinengguiCard, Model model) {
		ZngCard zhinengguiCard1 = zhinengguiCardService.findByCardId(zhinengguiCard.getCardId());
		if (zhinengguiCard1 != null)
			return renderResult(Global.TRUE, text("ic已注册！"));
		else
			return renderResult(Global.TRUE, text(zhinengguiCard.getCardId()));
	}

	/**
	 * 保存ic卡表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCard:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ZngCard zhinengguiCard) {
		if (StringUtils.isBlank(zhinengguiCard.getPhone())
				||StringUtils.isBlank(zhinengguiCard.getUserName())
				||StringUtils.isBlank(zhinengguiCard.getType())
				|| StringUtils.isBlank(zhinengguiCard.getDeviceId())) {
			return renderResult(Global.FALSE, text("必填信息不能为空！"));
		}
		ZngDevice zhinengguiDevice = zhinengguiDeviceService.getByDeviceId(zhinengguiCard.getDeviceId());
		if (zhinengguiDevice == null)
			return renderResult(Global.FALSE, text("设备信息错误！"));
		if (zhinengguiCard.getIsNewRecord()){
			if (zhinengguiCardService.getByCardNumAndDeviceId(zhinengguiCard.getCardNum(), zhinengguiCard.getDeviceId()) != null) {
				return renderResult(Global.FALSE, text("卡号重复！"));
			}
		}else {
			ZngCard card = zhinengguiCardService.get(zhinengguiCard.getId());
			if (card != null && !zhinengguiCard.getCardNum().equals(card.getCardNum()) && zhinengguiCardService.getByCardNumAndDeviceId(zhinengguiCard.getCardNum(), zhinengguiCard.getDeviceId()) != null){
				return renderResult(Global.FALSE, text("卡号重复！"));
			}
		}

		if (!StringUtils.isBlank(zhinengguiCard.getTotalDoorId())) {
			ZngCabinets zhinengguiCabinets = zhinengguiCabinetsService.getOneByTotalDoorIdNoStatus(zhinengguiCard.getDeviceId(), zhinengguiCard.getTotalDoorId());
			if (zhinengguiCabinets == null)
				return renderResult(Global.FALSE, text("柜门信息错误！"));
			ZngCard card = zhinengguiCardService.get(zhinengguiCard.getId());
			String dId = card==null?"":card.getDoorId();
			if (!zhinengguiCabinets.getId().equals(dId) && "2".equals(zhinengguiCabinets.getState()))
				return renderResult(Global.FALSE, text("柜门已被占用！"));
			//绑定信息更新
			if (!StringUtils.isBlank(dId) && !zhinengguiCabinets.getId().equals(dId)) {
				ZngCabinets oldCabinets = zhinengguiCabinetsService.get(dId);
				oldCabinets.setState("1");
				zhinengguiCabinetsService.update(oldCabinets);
			}
			zhinengguiCabinets.setState("2");
			zhinengguiCabinetsService.update(zhinengguiCabinets);
			zhinengguiCard.setDoorId(zhinengguiCabinets.getId());
		}else {
			ZngCard card = zhinengguiCardService.get(zhinengguiCard.getId());
			//绑定信息更新
			if (card != null && !StringUtils.isBlank(card.getDoorId())) {
				ZngCabinets oldCabinets = zhinengguiCabinetsService.get(card.getDoorId());
				oldCabinets.setState("1");
				zhinengguiCabinetsService.update(oldCabinets);
			}
			zhinengguiCard.setDoorId("");
		}
		ZngUser zhinengguiUser = zhinengguiUserService.findByTel(zhinengguiCard.getPhone());
		if (zhinengguiUser == null) {
			zhinengguiUser = new ZngUser();
			zhinengguiUser.setIsManager("5");
			zhinengguiUser.setLoginName(zhinengguiCard.getPhone());
			zhinengguiUser.setName(zhinengguiCard.getUserName());
			zhinengguiUser.setPhone(zhinengguiCard.getPhone());
			zhinengguiUserService.save(zhinengguiUser);
		}
		zhinengguiCard.setUserId(zhinengguiUserService.findByTel(zhinengguiCard.getPhone()).getId());
		zhinengguiCardService.save(zhinengguiCard);
		return renderResult(Global.TRUE, text("保存ic卡表成功！"));
	}

	/**
	 * 停用ic卡表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCard:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(ZngCard zhinengguiCard) {
		System.out.println(zhinengguiCard.toString());
//		if (StringUtils.isNotBlank(zhinengguiCard.getDoorId())){
//			ZngCabinets zhinengguiCabinets = zhinengguiCabinetsService.get(zhinengguiCard.getDoorId());
//			if (zhinengguiCabinets!=null){
//				zhinengguiCabinets.setState("1");
//				zhinengguiCabinetsService.update(zhinengguiCabinets);
//			}
//			zhinengguiCard.setDoorId("");
//		}
		zhinengguiCard.setStatus(ZngCard.STATUS_DISABLE);
		zhinengguiCardService.updateStatus(zhinengguiCard);
		return renderResult(Global.TRUE, text("停用ic卡表成功"));
	}
	
	/**
	 * 启用ic卡表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCard:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(ZngCard zhinengguiCard) {
		zhinengguiCard.setStatus(ZngCard.STATUS_NORMAL);
		zhinengguiCardService.updateStatus(zhinengguiCard);
		return renderResult(Global.TRUE, text("启用ic卡表成功"));
	}
	
	/**
	 * 删除ic卡表
	 */
	@RequiresPermissions("zhinenggui:zhinengguiCard:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ZngCard zhinengguiCard) {
		System.out.println(zhinengguiCard.toString());
		if (StringUtils.isNotBlank(zhinengguiCard.getDoorId())){
			ZngCabinets zhinengguiCabinets = zhinengguiCabinetsService.get(zhinengguiCard.getDoorId());
			if (zhinengguiCabinets!=null){
				zhinengguiCabinets.setState("1");
				zhinengguiCabinetsService.update(zhinengguiCabinets);
			}
		}
		zhinengguiCardService.delete(zhinengguiCard);
		return renderResult(Global.TRUE, text("删除ic卡表成功！"));
	}
	
}