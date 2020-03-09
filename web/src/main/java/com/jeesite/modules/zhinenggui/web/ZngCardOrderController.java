package com.jeesite.modules.zhinenggui.web;

import com.jeesite.common.web.BaseController;
import com.jeesite.modules.common.ResultMsg;
import com.jeesite.modules.zhinenggui.entity.*;
import com.jeesite.modules.zhinenggui.service.*;
import com.jeesite.modules.utils.OpenDoorUtils;
import com.jeesite.modules.utils.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "${frontPath}/zhinenggui/zhinengguiCardOrder")
@Validated
public class ZngCardOrderController extends BaseController {

    @Autowired
    ZngUserService zhinengguiUserService;
    @Autowired
    ZngCardService zhinengguiCardService;
    @Autowired
    ZngDeviceService zhinengguiDeviceService;
    @Autowired
    ZngCabinetsService zhinengguiCabinetsService;
    @Autowired
    ZngCardRecordService zhinengguiCardRecordService;

    @RequestMapping(value = "checkPhone")
    public void checkPhone(HttpServletRequest request, Model model){
        String phone = request.getParameter("phone");
        if (StringUtils.isBlank(phone)){
            ResultUtils.inputNull(model);
            return;
        }
        if (zhinengguiUserService.findByTel(phone) != null){
            ResultUtils.makeMode(model, ResultMsg.INPUT_NULL, "手机号已注册");
            return;
        }
        ResultUtils.successMode(model);
    }

    @RequestMapping(value = "checkCardNum")
    public void checkCardNum(HttpServletRequest request, Model model){
        String cardNum = request.getParameter("cardNum");
        String deviceId = request.getParameter("deviceId");
        if (StringUtils.isBlank(cardNum) || StringUtils.isBlank(deviceId)){
            ResultUtils.inputNull(model);
            return;
        }
        ZngDevice zhinengguiDevice = zhinengguiDeviceService.getByDeviceId(deviceId);
        if (zhinengguiDevice == null){
            ResultUtils.noDevice(model);
            return;
        }
        if (zhinengguiCardService.getByCardNumAndDeviceId(cardNum, deviceId) != null){
            ResultUtils.makeMode(model, ResultMsg.INPUT_NULL, "卡号已注册");
            return;
        }
        ResultUtils.successMode(model);
    }

    @RequestMapping(value = "checkCardId")
    public void checkCardId(HttpServletRequest request, Model model){
        String cardId = request.getParameter("cardId");
        if (zhinengguiCardService.findByCardId(cardId) != null){
            ResultUtils.makeMode(model, ResultMsg.INPUT_NULL, "卡已注册");
            return;
        }
        ResultUtils.successMode(model);
    }

    @RequestMapping(value = "addUser")
    public void addUser(HttpServletRequest request, Model model){
        String deviceId = request.getParameter("deviceId");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String cardId = request.getParameter("cardId");
        String doorId = request.getParameter("doorId");
        String userId = request.getParameter("userId");
        String cardNum = request.getParameter("cardNum");
        if (StringUtils.isBlank(cardNum) || StringUtils.isBlank(deviceId) || StringUtils.isBlank(name)
                || StringUtils.isBlank(phone) || StringUtils.isBlank(cardId) || StringUtils.isBlank(doorId)){
            ResultUtils.inputNull(model);
            return;
        }
        ZngDevice zhinengguiDevice = zhinengguiDeviceService.getByDeviceId(deviceId);
        if (zhinengguiDevice == null){
            ResultUtils.noDevice(model);
            return;
        }
        ZngCabinets cabinets = zhinengguiCabinetsService.get(doorId);
        if(cabinets == null || cabinets.getState().equals("2")){
            ResultUtils.makeMode(model, ResultMsg.INPUT_NULL, "柜门被占用");
            return;
        }
        if (zhinengguiCardService.getByCardNumAndDeviceId(cardNum, deviceId) != null){
            ResultUtils.makeMode(model, ResultMsg.INPUT_NULL, "卡号已注册");
            return;
        }
        if (zhinengguiCardService.findByCardId(cardId) != null){
            ResultUtils.makeMode(model, ResultMsg.INPUT_NULL, "卡已注册");
            return;
        }
        if (zhinengguiCardService.findByDoorId(doorId) != null){
            ResultUtils.makeMode(model, ResultMsg.INPUT_NULL, "柜门已绑定");
            return;
        }
        ZngUser zhinengguiUser = zhinengguiUserService.findByTel(phone);
        if (zhinengguiUser == null){
            zhinengguiUser = new ZngUser();
            zhinengguiUser.setName(name);
            zhinengguiUser.setLoginName(phone);
            zhinengguiUser.setLoginPwd("123456");
            zhinengguiUser.setPhone(phone);
            zhinengguiUser.setIsManager("5");
            zhinengguiUser.setByCompany(zhinengguiDevice.getByCompany());
            zhinengguiUserService.save(zhinengguiUser);
        }
        ZngCard zhinengguiCard = new ZngCard();
        zhinengguiCard.setCardId(cardId);
        zhinengguiCard.setUserId(zhinengguiUserService.findByTel(phone).getId());
        zhinengguiCard.setDeviceId(deviceId);
        zhinengguiCard.setDoorId(doorId);
        zhinengguiCard.setType("1");
        zhinengguiCardService.save(zhinengguiCard);
        cabinets.setState("2");
        zhinengguiCabinetsService.update(cabinets);

        ZngCardRecord zhinengguiCardRecord = new ZngCardRecord();
        zhinengguiCardRecord.setCardId(cardId);
        zhinengguiCardRecord.setUserId(userId);
        zhinengguiCardRecord.setDeviceId(deviceId);
        zhinengguiCardRecord.setDoorId(doorId);
        zhinengguiCardRecord.setState("2");
        zhinengguiCardRecordService.save(zhinengguiCardRecord);

        ResultUtils.successMode(model);
    }

    @RequestMapping(value = "bindDoor")
    public void bindDoor(HttpServletRequest request, Model model){
        String cardId = request.getParameter("cardId");
        String deviceId = request.getParameter("deviceId");
        String doorId = request.getParameter("doorId");
        if (StringUtils.isBlank(cardId) || StringUtils.isBlank(deviceId) || StringUtils.isBlank(doorId)){
            ResultUtils.inputNull(model);
            return;
        }
        ZngCard zhinengguiCard = zhinengguiCardService.findByCardId(cardId);
        if (zhinengguiCard == null){
            ResultUtils.makeMode(model, ResultMsg.MODEL_NULL, "卡未注册");
            return;
        }
        if (!deviceId.equals(zhinengguiCard.getDeviceId())){
            ResultUtils.makeMode(model, ResultMsg.MODEL_NULL, "没有权限");
            return;
        }
        ZngCabinets zhinengguiCabinets = zhinengguiCabinetsService.get(doorId);
        if (!StringUtils.isBlank(zhinengguiCard.getDoorId()) ||  zhinengguiCabinets == null || !"1".equals(zhinengguiCabinets.getState())){
            ResultUtils.makeMode(model, ResultMsg.INPUT_NULL, "柜门已绑定");
            return;
        }
        zhinengguiCabinets.setState("2");
        zhinengguiCabinetsService.update(zhinengguiCabinets);
        zhinengguiCard.setDoorId(doorId);
        zhinengguiCard.setType("0");
        zhinengguiCardService.update(zhinengguiCard);

        ZngCardRecord zhinengguiCardRecord = new ZngCardRecord();
        zhinengguiCardRecord.setCardId(cardId);
        zhinengguiCardRecord.setUserId(zhinengguiCard.getUserId());
        zhinengguiCardRecord.setDeviceId(deviceId);
        zhinengguiCardRecord.setDoorId(zhinengguiCard.getDoorId());
        zhinengguiCardRecord.setState("1");
        zhinengguiCardRecordService.save(zhinengguiCardRecord);

        ResultUtils.successMode(model);
    }

    @RequestMapping(value = "untyingDoor")
    public void untyingDoor(HttpServletRequest request, Model model){
        String cardId = request.getParameter("cardId");
        String deviceId = request.getParameter("deviceId");
        String doorId = request.getParameter("doorId");
        if (StringUtils.isBlank(cardId) || StringUtils.isBlank(deviceId) || StringUtils.isBlank(doorId)){
            ResultUtils.inputNull(model);
            return;
        }
        ZngCard zhinengguiCard = zhinengguiCardService.findByCardId(cardId);
        if (zhinengguiCard == null){
            ResultUtils.makeMode(model, ResultMsg.MODEL_NULL, "卡未注册");
            return;
        }
        if (!deviceId.equals(zhinengguiCard.getDeviceId()) || "1".equals(zhinengguiCard.getType())){
            ResultUtils.makeMode(model, ResultMsg.MODEL_NULL, "没有权限");
            return;
        }
        ZngCabinets zhinengguiCabinets = zhinengguiCabinetsService.get(doorId);
        if (zhinengguiCabinets == null){
            ResultUtils.makeMode(model, ResultMsg.MODEL_NULL, "数据错误");
            return;
        }
        zhinengguiCabinets.setState("1");
        zhinengguiCabinetsService.update(zhinengguiCabinets);
        zhinengguiCard.setDoorId("");
        zhinengguiCard.setType("");
        zhinengguiCardService.update(zhinengguiCard);

        ZngCardRecord zhinengguiCardRecord = new ZngCardRecord();
        zhinengguiCardRecord.setCardId(cardId);
        zhinengguiCardRecord.setUserId(zhinengguiCard.getUserId());
        zhinengguiCardRecord.setDeviceId(deviceId);
        zhinengguiCardRecord.setDoorId(zhinengguiCard.getDoorId());
        zhinengguiCardRecord.setState("4");
        zhinengguiCardRecordService.save(zhinengguiCardRecord);

        ResultUtils.successMode(model);
    }

    @RequestMapping(value = "openDoor")
    public void openDoor(HttpServletRequest request, Model model){
        String deviceId = request.getParameter("deviceId");
        String doorId = request.getParameter("doorId");
        String userId = request.getParameter("userId");
        String cardId = request.getParameter("cardId");
        if (StringUtils.isBlank(deviceId) || StringUtils.isBlank(doorId)){
            ResultUtils.inputNull(model);
            return;
        }
        ZngCabinets zhinengguiCabinets = zhinengguiCabinetsService.get(doorId);
        if (zhinengguiCabinets == null  || !deviceId.equals(zhinengguiCabinets.getDeviceId())){
            ResultUtils.makeMode(model, ResultMsg.MODEL_NULL, "无权限操作");
            return;
        }
        if (OpenDoorUtils.openTheDoor(deviceId, zhinengguiCabinets.getPlateId(), zhinengguiCabinets.getDoorId())) {
            ZngCardRecord zhinengguiCardRecord = new ZngCardRecord();
            zhinengguiCardRecord.setCardId(cardId);
            zhinengguiCardRecord.setUserId(userId);
            zhinengguiCardRecord.setDeviceId(deviceId);
            zhinengguiCardRecord.setDoorId(doorId);
            zhinengguiCardRecord.setState("0");
            zhinengguiCardRecordService.save(zhinengguiCardRecord);
            ResultUtils.successMode(model);
        }else
            ResultUtils.makeMode(model, ResultMsg.MODEL_NULL, "设备链接错误");
    }

    /**
     * 根据卡号获取用户
     * @param request
     * @param model
     */
    @RequestMapping(value = "getUserByCard")
    public void getUserByCard(HttpServletRequest request, Model model){
        String cardId = request.getParameter("cardId");
        String deviceId = request.getParameter("deviceId");
        if (StringUtils.isBlank(cardId) || StringUtils.isBlank(deviceId)){
            ResultUtils.inputNull(model);
            return;
        }
        ZngCard zhinengguiCard = zhinengguiCardService.findByCardId(cardId);
        if (zhinengguiCard == null){
            ResultUtils.makeMode(model, ResultMsg.MODEL_NULL, "卡未注册");
            return;
        }else if (!deviceId.equals(zhinengguiCard.getDeviceId()) && !"1".equals(zhinengguiCard.getCardType())){
            ResultUtils.makeMode(model, ResultMsg.MODEL_NULL, "没有权限");
            return;
        }
        ResultUtils.successMode(model, zhinengguiCard);
    }

    /**
     * 删除用户
     * @param request
     * @param model
     */
    @RequestMapping(value = "deleteUser")
    public void deleteUser(HttpServletRequest request, Model model){
        String cardId = request.getParameter("cardId");
        String deviceId = request.getParameter("deviceId");
        String userId = request.getParameter("userId");
        if (StringUtils.isBlank(cardId) || StringUtils.isBlank(deviceId)){
            ResultUtils.inputNull(model);
            return;
        }
        ZngCard zhinengguiCard = zhinengguiCardService.findByCardId(cardId);
        if (zhinengguiCard == null){
            ResultUtils.makeMode(model, ResultMsg.MODEL_NULL, "卡未注册");
            return;
        }
        if (!deviceId.equals(zhinengguiCard.getDeviceId())){
            ResultUtils.makeMode(model, ResultMsg.MODEL_NULL, "没有权限");
            return;
        }
        if (!StringUtils.isBlank(zhinengguiCard.getDoorId())) {
            ZngCabinets zhinengguiCabinets = zhinengguiCabinetsService.get(zhinengguiCard.getDoorId());
            zhinengguiCabinets.setState("1");
            zhinengguiCabinetsService.update(zhinengguiCabinets);
        }
        zhinengguiCardService.delete(zhinengguiCard);

        ZngCardRecord zhinengguiCardRecord = new ZngCardRecord();
        zhinengguiCardRecord.setCardId(cardId);
        zhinengguiCardRecord.setUserId(userId);
        zhinengguiCardRecord.setDeviceId(deviceId);
        zhinengguiCardRecord.setDoorId(zhinengguiCard.getDoorId());
        zhinengguiCardRecord.setState("3");
        zhinengguiCardRecordService.save(zhinengguiCardRecord);
        ResultUtils.successMode(model);
    }

    /**
     * 查看所有柜门
     * @param request
     * @param model
     */
    @RequestMapping(value = "selectDoors")
    public void selectDoors(HttpServletRequest request, Model model){
        String deviceId = request.getParameter("deviceId");
        if (StringUtils.isBlank(deviceId)){
            ResultUtils.inputNull(model);
            return;
        }
        ZngDevice zhinengguiDevice = zhinengguiDeviceService.getByDeviceId(deviceId);
        if (zhinengguiDevice == null){
            ResultUtils.noDevice(model);
            return;
        }
        List<ZngCabinets> list = zhinengguiCabinetsService.getAllListByDeviceId(deviceId);
        ResultUtils.successMode(model, list);
    }

    /**
     * 查看未分配柜门
     * @param request
     * @param model
     */
    @RequestMapping(value = "selectCanUseDoors")
    public void selectCanUseDoors(HttpServletRequest request, Model model){
        String deviceId = request.getParameter("deviceId");
        if (StringUtils.isBlank(deviceId)){
            ResultUtils.inputNull(model);
            return;
        }
        ZngDevice zhinengguiDevice = zhinengguiDeviceService.getByDeviceId(deviceId);
        if (zhinengguiDevice == null){
            ResultUtils.noDevice(model);
            return;
        }
        List<ZngCabinets> list = zhinengguiCabinetsService.getListByDeviceId(deviceId);
        ResultUtils.successMode(model, list);
    }
}
