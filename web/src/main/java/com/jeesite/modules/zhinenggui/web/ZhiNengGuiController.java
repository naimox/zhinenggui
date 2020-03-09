/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.zhinenggui.web;

import com.jeesite.common.web.BaseController;
import com.jeesite.modules.common.ResultMsg;
import com.jeesite.modules.zhinenggui.entity.*;
import com.jeesite.modules.zhinenggui.search.CabinetsAndOrder;
import com.jeesite.modules.zhinenggui.search.DoorPriceSearch;
import com.jeesite.modules.zhinenggui.search.OrderSearch;
import com.jeesite.modules.zhinenggui.service.*;
import com.jeesite.modules.file.entity.FileUpload;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.jeesite.modules.utils.AddNewDeviceUtils;
import com.jeesite.modules.utils.CommonUtil;
import com.jeesite.modules.utils.OpenDoorUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户寄件接口
 *
 * @author lzy
 * @version 2018-08-28
 */
@Controller
@RequestMapping(value = "${frontPath}/zhinenggui")
public class ZhiNengGuiController extends BaseController {

    @Autowired
    private ZngUserService zhinengguiUserService;
    @Autowired
    private ZngUserSendOrderController zhinengguiUserSendOrderController;
    @Autowired
    private ZngDeviceService zhinengguiDeviceService;
    @Autowired
    private ZngCabinetsService zhinengguiCabinetsService;
    @Autowired
    private ZngCodeService zhinengguiCodeService;
    @Autowired
    private ZngOrderService zhinengguiOrderService;
    @Autowired
    private ZngWalletService zhinengguiWalletService;
    @Autowired
    private ZngMsgService zhinengguiMsgService;
    @Autowired
    private ZngPointNominateService zhinengguiPointNominateServer;
    @Autowired
    private ZngHelpService helpService;
    @Autowired
    private ZngFunctionImagesService zhinengguiFunctionImagesService;


    //	appid:this.data.user.appId,
//	secret:this.data.user.secretId,
//	js_code:res.code,
//	grant_type:"authorization_code"

    /**
     * 获取openID
     *
     * @param model
     * @param request
     */
    @RequestMapping(value = "getOpenId")
    public void getOpenId(Model model, HttpServletRequest request) {
        String result = null;
        String link_url = "https://api.weixin.qq.com/sns/jscode2session";
        String appId = request.getParameter("appid");
        String secret = request.getParameter("secret");
        String jsCode = request.getParameter("js_code");
        String grantType = request.getParameter("grant_type");
        String str = "";
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("appid", appId));
        str += "?appid=" + appId;
        urlParameters.add(new BasicNameValuePair("secret", secret));
        str += "&secret=" + secret;
        urlParameters.add(new BasicNameValuePair("js_code", jsCode));
        str += "&js_code=" + jsCode;
        urlParameters.add(new BasicNameValuePair("grant_type", grantType));
        str += "&grant_type=" + grantType;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpPost post = new HttpPost(link_url);
        try {
            post.setEntity(new UrlEncodedFormEntity(urlParameters, HTTP.UTF_8));
            try {
                response = httpclient.execute(post);
                // 判断网络连接状态码是否正常(0--200都数正常)
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    result = EntityUtils.toString(response.getEntity(), "UTF-8");
                    System.out.println(result);
                    System.out.println(str);
                }
                EntityUtils.consume(response.getEntity());//完全消耗
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != response) response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            //释放链接
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("result", result);
    }

    /**
     * 用户openId判断是否存在
     */
    @RequestMapping(value = "checkUser")
    public void checkUser(Model model, HttpServletRequest request) {
        String wxId = request.getParameter("wxId");
        if (!checkOpenId(wxId, model)) return;
        ZngUser zhinengguiUser = zhinengguiUserService.findOneByWxId(wxId);
        if (zhinengguiUser == null) {
            model.addAttribute("data", "0");
            model.addAttribute("flag", ResultMsg.MODEL_NULL);
            model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
        } else {
            model.addAttribute("data", "1");
            model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
            model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
        }
    }

    /**
     * 用户注册
     */
    @RequestMapping(value = "registUser")
    public void registUser(Model model, ZngUser zhinengguiUser, HttpServletRequest request) {
        String validCode = request.getParameter("code");
        if (!checkUser(zhinengguiUser, model, validCode)) return;
        zhinengguiUser.setLoginPwd("123456");
        zhinengguiUser.setIsManager("0");//0为普通用户
        //TODO COMPANYID 写死的
        zhinengguiUser.setByCompany("ZNG");
        zhinengguiUser.setByOffice("ZNG");
        zhinengguiUser.setByUser("18595411153_bx33");
        zhinengguiUserService.save(zhinengguiUser);
        model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
        model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
        model.addAttribute("data", new HashMap<String, String>());
    }

    /**
     * 获得验证码
     */
    @RequestMapping(value = "getValidCode")
    public void getValidCode(Model model, HttpServletRequest request) {
        //TODO deviceId 写死的
        String deviceId = "00000000";
        ZngDevice device = zhinengguiDeviceService.getByDeviceId(deviceId);
        if (zhinengguiUserSendOrderController.checkDevice(model, device)) {
            return;
        }
        String phone = request.getParameter("phone");
        String type = "0";
        ZngCode zhinengguiCode = new ZngCode();
        if (zhinengguiUserSendOrderController.sendCode(model, deviceId, phone, type, zhinengguiCode)) {
            return;
        }
        model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
        model.addAttribute("msg", ResultMsg.MSG_VALID_ZN);
        model.addAttribute("data", zhinengguiCode);
    }

    /**
     * 根据设备id获取所有柜门信息
     */
    @RequestMapping(value = "getDoorList")
    public void getDoorList(Model model, HttpServletRequest request) {
        String deviceId = request.getParameter("deviceId");
        String doorSize = request.getParameter("doorSize");
        ZngDevice device = zhinengguiDeviceService.getByDeviceId(deviceId);
        if (zhinengguiUserSendOrderController.checkDevice(model, device)) {
            return;
        }
        List<DoorPriceSearch> doorPriceList = zhinengguiCabinetsService.getDoorPriceList(deviceId, doorSize);
        model.addAttribute("data", doorPriceList);
        model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
        model.addAttribute("msg", ResultMsg.MSG_VALID_ZN);
    }

    /**
     * 根据设备id获取所有柜门信息
     */
    @RequestMapping(value = "getDoorDetailById")
    public void getDoorDetailById(Model model, HttpServletRequest request) {
        String doorId = request.getParameter("doorId");
        DoorPriceSearch doorPrice = zhinengguiCabinetsService.getDoorDetailById(doorId);
        model.addAttribute("data", doorPrice);
        model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
        model.addAttribute("msg", ResultMsg.MSG_VALID_ZN);
    }

    @RequestMapping(value = "checkHasOrder")
    public void checkHasOrder(HttpServletRequest request, Model model) {
        String deviceId = request.getParameter("deviceId");
        String wxId = request.getParameter("wxId");
        if (StringUtils.isBlank(deviceId) && StringUtils.isBlank(wxId)) {
            model.addAttribute("flag", ResultMsg.INPUT_NULL);
            model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
            model.addAttribute("data", new HashMap<>());
            return;
        }
        ZngDevice zhinengguiDevice = zhinengguiDeviceService.getByDeviceId(deviceId);
        ZngUser zhinengguiUser = zhinengguiUserService.findOneByWxId(wxId);
        if (zhinengguiDevice == null || zhinengguiUser == null) {
            model.addAttribute("flag", ResultMsg.INPUT_NULL);
            model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
            model.addAttribute("data", new HashMap<>());
            return;
        }
        List<OrderSearch> orderList = zhinengguiOrderService.getOrderListByPhone(deviceId, zhinengguiUser.getPhone());
        Map<String, Object> map = new HashMap<>();
        if (orderList.size() > 0) {
            map.put("has", "true");
        } else {
            map.put("has", "false");
        }
        model.addAttribute("data", map);
        model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
        model.addAttribute("msg", ResultMsg.MSG_VALID_ZN);
    }


    /**
     * 支付订单
     * @param request
     * @param model
     */
    @RequestMapping(value = "/submitOrder")
    public void submitOrder(HttpServletRequest request, Model model) {
        String openid = request.getParameter("openid");
        String doorId = request.getParameter("doorId");
        String money = request.getParameter("money");
        String orderId = request.getParameter("orderId");
        if (money == null || "".equals(money)) {
            // 根据柜子的ID获得柜子的价格
            money = zhinengguiCabinetsService.getDoorDetailById(doorId).getMoney();
        }
        // 获得柜子
        ZngCabinets cabinets = zhinengguiCabinetsService.get(doorId);
        ZngUser zhinengguiUser = zhinengguiUserService.findOneByWxId(openid);
        if (cabinets == null || zhinengguiUser == null || !"1".equalsIgnoreCase(cabinets.getState())) {
            model.addAttribute("flag", ResultMsg.INPUT_NULL);
            model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
            model.addAttribute("data", new HashMap<>());
            return;
        }
        ZngOrder order = null;
        if (orderId == null || "".equals(orderId)) {
            order = new ZngOrder();
        } else {
            order = zhinengguiOrderService.get(orderId);
        }

        order.setState("200");
        order.setGetterPhone(zhinengguiUser.getPhone());
        order.setSendDeviceId(cabinets.getDeviceId());
        order.setSendDoorId(cabinets.getId());
        order.setOrderSn(CommonUtil.generateOrderNumber());
        order.setPrepaidAmount(money);
        zhinengguiOrderService.save(order);

        PayController payController = new PayController();
        Map resultObj = payController.payPrepay(request, model, openid, money, order.getOrderSn());
        if (resultObj != null) {
            resultObj.put("orderId", order.getId());
            // 业务处理
            order.setPrepayId(resultObj.get("prepay_id").toString());
            // 付款中
            order.setState("300");
            zhinengguiOrderService.update(order);
            model.addAttribute("data", resultObj);
            model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
            model.addAttribute("msg", ResultMsg.MSG_VALID_ZN);
            return;
        }

        model.addAttribute("flag", ResultMsg.INPUT_NULL);
        model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
        model.addAttribute("data", new HashMap<>());
        return;
    }

    @RequestMapping(value = "/finishedOrder")
    public void finishedOrder(HttpServletRequest request, Model model) {
        String openid = request.getParameter("openid");
        String doorId = request.getParameter("doorId");
        String money = request.getParameter("money");
        String orderId = request.getParameter("orderId");

        ZngOrder order = zhinengguiOrderService.get(orderId);

        PayController payController = new PayController();
        String orderSn = CommonUtil.generateOrderNumber();
        Map resultObj = payController.payPrepay(request, model, openid, money, orderSn);
        if (resultObj != null) {
            order.setFinishOrderSn(orderSn);
            order.setFinishPrepayId(resultObj.get("prepay_id").toString());
            order.setPickUpAmount(money);
            zhinengguiOrderService.update(order);
            model.addAttribute("data", resultObj);
            model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
            model.addAttribute("msg", ResultMsg.MSG_VALID_ZN);
            return;
        }
    }

    /**
     * 暂存 支付成功后选择空门信息
     */
    @RequestMapping(value = "zanCun")
    public void zanCun(HttpServletRequest request, Model model) {
        //1.根据柜门型号计算价钱
        String doorId = request.getParameter("doorId");
        String wxId = request.getParameter("wxId");
        String money = request.getParameter("money");
        String orderId = request.getParameter("orderId");
        if (StringUtils.isBlank(doorId) && StringUtils.isBlank(wxId)) {
            model.addAttribute("flag", ResultMsg.INPUT_NULL);
            model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
            model.addAttribute("data", new HashMap<>());
            return;
        }
        ZngCabinets cabinets = zhinengguiCabinetsService.get(doorId);
        ZngUser zhinengguiUser = zhinengguiUserService.findOneByWxId(wxId);
        if (cabinets == null || zhinengguiUser == null || !"1".equalsIgnoreCase(cabinets.getState())) {
            model.addAttribute("flag", ResultMsg.INPUT_NULL);
            model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
            model.addAttribute("data", new HashMap<>());
            return;
        }
        ZngOrder order = zhinengguiOrderService.get(orderId);
        if (order == null) {
            return;
        }
        order.setState("100");
        order.setGetterPhone(zhinengguiUser.getPhone());
        order.setSendDeviceId(cabinets.getDeviceId());
        order.setSendDoorId(cabinets.getId());
        zhinengguiOrderService.save(order);

        boolean flag = OpenDoorUtils.openTheDoor(cabinets.getDeviceId(), cabinets.getPlateId(), cabinets.getDoorId());
        if (flag) {
//			处理订单，发送消息提示用户取件。
            if (!"0".equalsIgnoreCase(cabinets.getStatus())) {
                model.addAttribute("flag", ResultMsg.DEVICE_NULL_ERROR);
                model.addAttribute("msg", ResultMsg.ERR_NULL_DEVICE_ZN);
                model.addAttribute("data", new HashMap<>());
                return;
            }
            order.setState("400");
            order.setSenderTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            order.setSendTotalMoney(money);
            zhinengguiOrderService.update(order);
            //修改柜门状态
            cabinets.setState("2");
            zhinengguiCabinetsService.update(cabinets);
            ZngDevice zhinengguiDevice = zhinengguiDeviceService.getByDeviceId(cabinets.getDeviceId());
            String userId = zhinengguiDevice.getCompanyUserId();
            ZngWallet wallet = zhinengguiWalletService.getWalletByUserId(userId);
            wallet.setBalance(wallet.getBalance() + Double.parseDouble(money));
            zhinengguiWalletService.update(wallet);
        } else {
            model.addAttribute("flag", ResultMsg.INTERFACE_ERROR);
            model.addAttribute("msg", ResultMsg.ERR_OPEN_DOOR_ZN);
            model.addAttribute("data", new HashMap<>());
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("orderId", order.getId());
        model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
        model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
        model.addAttribute("data", map);
        return;
    }

    @RequestMapping(value = "againOpenDoor")
    public void againOpenDoor(HttpServletRequest request, Model model) {
        String orderId = request.getParameter("orderId");
        String wxId = request.getParameter("wxId");
        if (StringUtils.isBlank(orderId) && StringUtils.isBlank(wxId)) {
            model.addAttribute("flag", ResultMsg.INPUT_NULL);
            model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
            model.addAttribute("data", new HashMap<>());
            return;
        }
        ZngOrder zhinengguiOrder = zhinengguiOrderService.get(orderId);
        ZngUser zhinengguiUser = zhinengguiUserService.findOneByWxId(wxId);
        if (zhinengguiOrder == null || zhinengguiUser == null) {
            model.addAttribute("flag", ResultMsg.INPUT_NULL);
            model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
            model.addAttribute("data", new HashMap<>());
            return;
        }

        CabinetsAndOrder cabinetsAndOrder = zhinengguiOrderService.getDoorByOrderIdAndPhone(zhinengguiUser.getPhone(), orderId);
        if (cabinetsAndOrder == null) {
            model.addAttribute("flag", ResultMsg.MODEL_NULL);
            model.addAttribute("msg", ResultMsg.ERR_ORDER_ZN);
            model.addAttribute("data", new HashMap<>());
            return;
        }
        if (OpenDoorUtils.openTheDoor(zhinengguiOrder.getSendDeviceId(), cabinetsAndOrder.getPlateId(), cabinetsAndOrder.getDoorId())) {
            model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
            model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
            model.addAttribute("data", new HashMap<>());
        } else {
            model.addAttribute("flag", ResultMsg.INTERFACE_ERROR);
            model.addAttribute("msg", ResultMsg.ERR_OPEN_DOOR_ZN);
            model.addAttribute("data", new HashMap<>());
        }
        return;
    }

    /**
     * 获取订单金额
     *
     * @param request
     * @param model
     */
    @RequestMapping(value = "getOrderEndMoney")
    public void getOrderEndMoney(HttpServletRequest request, Model model) {
        String orderId = request.getParameter("orderId");
        String wxId = request.getParameter("wxId");
        ZngOrder zhinengguiOrder = zhinengguiOrderService.get(orderId);
        ZngUser zhinengguiUser = zhinengguiUserService.findOneByWxId(wxId);
        if (zhinengguiOrder == null || zhinengguiUser == null || !"400".equalsIgnoreCase(zhinengguiOrder.getState())) {
            model.addAttribute("flag", ResultMsg.INPUT_NULL);
            model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
            model.addAttribute("data", new HashMap<>());
            return;
        }

        CabinetsAndOrder cabinetsAndOrder = zhinengguiOrderService.getDoorByOrderIdAndPhone(zhinengguiUser.getPhone(), orderId);
        if (cabinetsAndOrder == null) {
            model.addAttribute("flag", ResultMsg.MODEL_NULL);
            model.addAttribute("msg", ResultMsg.ERR_ORDER_ZN);
            model.addAttribute("data", new HashMap<>());
            return;
        }
        Float money = zhinengguiOrderService.getOrderMoney1(cabinetsAndOrder);
        Map<String, Object> map = new HashMap<>();
        map.put("money", money);
        model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
        model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
        model.addAttribute("data", map);
        return;
    }


    /**
     * 根据订单号开门
     *
     * @param request
     * @param model
     */
    @RequestMapping(value = "openDoor")
    public void openDoor(HttpServletRequest request, Model model) {
        String orderId = request.getParameter("orderId");
        String wxId = request.getParameter("wxId");
        if (StringUtils.isBlank(orderId) && StringUtils.isBlank(wxId)) {
            model.addAttribute("flag", ResultMsg.INPUT_NULL);
            model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
            model.addAttribute("data", new HashMap<>());
            return;
        }
        ZngOrder zhinengguiOrder = zhinengguiOrderService.get(orderId);
        ZngUser zhinengguiUser = zhinengguiUserService.findOneByWxId(wxId);
        if (zhinengguiOrder == null || zhinengguiUser == null || !"400".equalsIgnoreCase(zhinengguiOrder.getState())) {
            model.addAttribute("flag", ResultMsg.INPUT_NULL);
            model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
            model.addAttribute("data", new HashMap<>());
            return;
        }

        CabinetsAndOrder cabinetsAndOrder = zhinengguiOrderService.getDoorByOrderIdAndPhone(zhinengguiUser.getPhone(), orderId);
        if (cabinetsAndOrder == null) {
            model.addAttribute("flag", ResultMsg.MODEL_NULL);
            model.addAttribute("msg", ResultMsg.ERR_ORDER_ZN);
            model.addAttribute("data", new HashMap<>());
            return;
        }
        Float money = zhinengguiOrderService.getOrderMoney1(cabinetsAndOrder);
//		if(money > 0f){
//			String result = QRCodeUtils.doPostMethod(cabinetsAndOrder.getOrderId(),money-Float.parseFloat(zhinengguiOrder.getSendTotalMoney()) + "");
//			zhinengguiOrder.setSendTotalMoney(money.toString());
//			zhinengguiOrderService.update(zhinengguiOrder);
//			Map<String ,String> map = new HashMap<>();
//			map.put("code", result.trim());
//			map.put("orderId", cabinetsAndOrder.getOrderId());
//			map.put("money", money.toString());
//			map.put("totalDoorId", cabinetsAndOrder.getTotalDoorId());
//			model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
//			model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
//			model.addAttribute("data", map);
//			return;
//		}else{
        if (OpenDoorUtils.openTheDoor(zhinengguiOrder.getSendDeviceId(), cabinetsAndOrder.getPlateId(), cabinetsAndOrder.getDoorId())) {
            zhinengguiOrder.setState("600");
            zhinengguiOrder.setSendTotalMoney(money.toString());
            zhinengguiOrder.setGetterTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            zhinengguiOrderService.update(zhinengguiOrder);
            ZngCabinets cabinets = zhinengguiCabinetsService.get(cabinetsAndOrder.getId());
            cabinets.setState("1");
            zhinengguiCabinetsService.update(cabinets);
            model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
            model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
            Map<String, String> map = new HashMap<>();
            map.put("code", "noPay");
            map.put("orderId", cabinetsAndOrder.getOrderId());
            map.put("plateId", cabinetsAndOrder.getPlateId());
            map.put("doorId", cabinetsAndOrder.getDoorId());
            map.put("totalDoorId", cabinets.getTotalDoorId());
            model.addAttribute("data", map);
        } else {
            model.addAttribute("flag", ResultMsg.INTERFACE_ERROR);
            model.addAttribute("msg", ResultMsg.ERR_OPEN_DOOR_ZN);
            model.addAttribute("data", new HashMap<>());
        }
        return;
//		}
    }

    /**
     * 根据设备ID与openID获取订单列表或未完成订单
     *
     * @param model
     * @param request
     */
    @RequestMapping(value = "getOrderListByWxId")
    public void getOrderListByWxId(Model model, HttpServletRequest request) {
        String wxId = request.getParameter("wxId");
        String deviceId = request.getParameter("deviceId");
        String state = request.getParameter("state");
        if (StringUtils.isBlank(wxId) || StringUtils.isBlank(state)) {
            model.addAttribute("flag", ResultMsg.INPUT_NULL);
            model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
            model.addAttribute("data", new HashMap<>());
            return;
        }
        ZngUser zhinengguiUser = zhinengguiUserService.findOneByWxId(wxId);
        if (zhinengguiUser == null) {
            model.addAttribute("flag", ResultMsg.INPUT_NULL);
            model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
            model.addAttribute("data", new HashMap<>());
            return;
        }
        List<OrderSearch> list;
        if (state.equals("1")) {//state==1返回该设备所有订单包括完成与未完成
            list = zhinengguiOrderService.getOrderListByPhoneNoState(deviceId, zhinengguiUser.getPhone());
        } else if (state.equals("0")) {//返回该设备或该用户未完成订单<理论返回只有一条>
            list = zhinengguiOrderService.getOrderListByPhoneNoOver(deviceId, zhinengguiUser.getPhone());
        } else if (state.equals("2")) {//返回已完成的所有订单<不根据设备筛选>
            list = zhinengguiOrderService.getOverOrderListByPhone(zhinengguiUser.getPhone());
        } else {
            list = null;
        }

        model.addAttribute("data", list);
        model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
        model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
    }

    @RequestMapping(value = "saveMsg")
    public void saveMsg(Model model, HttpServletRequest request) {
        String wxId = request.getParameter("wxId");
        String image = request.getParameter("image");
        String content = request.getParameter("content");
        if (StringUtils.isBlank(wxId) || StringUtils.isBlank(image) || StringUtils.isBlank(content)) {
            model.addAttribute("flag", ResultMsg.INPUT_NULL);
            model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
            model.addAttribute("data", new HashMap<>());
            return;
        }
        ZngUser zhinengguiUser = zhinengguiUserService.findOneByWxId(wxId);
        if (zhinengguiUser == null) {
            model.addAttribute("flag", ResultMsg.INPUT_NULL);
            model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
            model.addAttribute("data", new HashMap<>());
            return;
        }
        ZngMsg zhinengguiMsg = new ZngMsg();
        zhinengguiMsg.setContent(content);
        zhinengguiMsg.setName(zhinengguiUser.getName());
        zhinengguiMsg.setImage(image);
        zhinengguiMsg.setWxId(wxId);
        zhinengguiMsg.setStatus("2");
        zhinengguiMsgService.save(zhinengguiMsg);
        model.addAttribute("data", new HashMap<>());
        model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
        model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
    }

    @RequestMapping(value = "getMsgList")
    public void getMsgList(Model model, HttpServletRequest request) {
        ZngMsg zhinengguiMsg = new ZngMsg();
        List<ZngMsg> list = zhinengguiMsgService.findList(zhinengguiMsg);
        model.addAttribute("data", list);
        model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
        model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
    }


    private boolean checkOpenId(String openId, Model model) {
        boolean flag = true;
        if (StringUtils.isBlank(openId)) {
            model.addAttribute("data", new HashMap<String, String>());
            model.addAttribute("flag", ResultMsg.INPUT_NULL);
            model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
            flag = false;
        }
        return flag;
    }

    private boolean checkUser(ZngUser zhinengguiUser, Model model, String validCode) {
        boolean flag = true;
        if (StringUtils.isBlank(zhinengguiUser.getLoginName()) || StringUtils.isBlank(zhinengguiUser.getName())
                || StringUtils.isBlank(zhinengguiUser.getPhone()) || StringUtils.isBlank(zhinengguiUser.getWxId())) {
            model.addAttribute("data", new HashMap<String, String>());
            model.addAttribute("flag", ResultMsg.INPUT_NULL);
            model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
            flag = false;
        } else {
            ZngCode zhinengguiCode = new ZngCode();
            zhinengguiCode.setPhone(zhinengguiUser.getPhone());
            zhinengguiCode.setCode(validCode);
            zhinengguiCode.setType("0");
            zhinengguiCode = zhinengguiCodeService.getByPhoneAndCode(zhinengguiCode);
            if (zhinengguiCode == null) {
                flag = false;
            } else {
                ZngUser user = zhinengguiUserService.findByLoginName(zhinengguiUser.getLoginName());
                if (user != null) flag = false;
            }
        }
        return flag;
    }

    /**
     * 添加设备
     *
     * @param model
     * @param request
     */
    @RequestMapping(value = "addDevice")
    public void addDevice(Model model, HttpServletRequest request) {
        String deviceId = request.getParameter("deviceId");
        String plateId = request.getParameter("plateId");
        Integer count = Integer.parseInt(request.getParameter("count"));
        Integer totalIdStart = Integer.parseInt(request.getParameter("totalIdStart"));
        AddNewDeviceUtils service = new AddNewDeviceUtils();
        service.add(deviceId, count, plateId, totalIdStart);
        model.addAttribute("success");
    }

    /**
     * 返回微信柜所有设备
     *
     * @param model
     * @param request
     */
    @RequestMapping(value = "getDeviceBy")
    public void getDeviceBy(Model model, HttpServletRequest request) {
        ZngDevice zhinengguiDevice = new ZngDevice();
        zhinengguiDevice.setByCompany("ZNG");//所属公司编码
        zhinengguiDevice.setByOffice("ZNG");//所述部门编码
        List<ZngDevice> list = zhinengguiDeviceService.findList(zhinengguiDevice);
        model.addAttribute("data", list);
        model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
        model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
    }

    /**
     * 添加推荐点位
     *
     * @param model
     * @param request
     */
    @RequestMapping(value = "addPointNominate")
    public void addPointNominate(Model model, HttpServletRequest request) {
        ZngPointNominate zhinengguiPointNominate = new ZngPointNominate();

        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        if (StringUtils.isBlank(name)
                || StringUtils.isBlank(phone)
                || StringUtils.isBlank(address)) {
            model.addAttribute("flag", ResultMsg.INPUT_NULL);
            model.addAttribute("msg", ResultMsg.ERR_NULL_ZN);
            model.addAttribute("data", new HashMap<>());
            return;
        } else {
            zhinengguiPointNominate.setName(name);
            zhinengguiPointNominate.setPhone(phone);
            zhinengguiPointNominate.setAddress(address);
            zhinengguiPointNominateServer.save(zhinengguiPointNominate);
        }
        model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
        model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
        model.addAttribute("data", new HashMap<>());
    }


    /**
     * 返回帮助信息
     *
     * @param model
     * @param request
     */
    @RequestMapping(value = "resHelp")
    public void resHelp(Model model, HttpServletRequest request) {
        ZngHelp zhinengguiHelp = new ZngHelp();
        zhinengguiHelp.setByCompany("ZNG");//查找所属数据
        List<ZngHelp> list = helpService.findList(zhinengguiHelp);

        List<Object> lists = new ArrayList<>();
        for (ZngHelp lsHelp : list) {
            Map<String, Object> map1 = new HashMap<>();
            Map<String, Object> map2 = new HashMap<>();
            List<Object> listss = new ArrayList<>();

            map2.put("itemName", lsHelp.getContent());//二重数组
            map1.put("listName", lsHelp.getTitle());    //一重数组
            listss.add(map2);
            map1.put("item", listss);
            lists.add(map1);
//			list:[{			保持这种数据结构
//			 listName:""
//				 item:[{
//					 itemName:""
//				 }]
//			}]
        }
        model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
        model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
        model.addAttribute("data", lists);
    }


    /**
     * 返回某设备轮播图片信息
     *
     * @param model
     * @param request !当前未使用!
     */
    @RequestMapping(value = "resDeviceRollImages")
    public void resDeviceRollImages(Model model, HttpServletRequest request) {
        ZngHelp zhinengguiHelp = new ZngHelp();
        String deviceId = request.getParameter("deviceId");
        zhinengguiHelp.setByCompany("ZNG");//查找所属数据
        ZngDevice zhinengguiDevice = zhinengguiDeviceService.getByDeviceId(deviceId);
        List<FileUpload> listImage = FileUploadUtils.findFileUpload(zhinengguiDevice.getId(), "zhinengguiDevice_image");
        List<String> listUrl = new ArrayList<>();
        for (FileUpload item : listImage) {
            listUrl.add(item.getFileUrl());
        }
        model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
        model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
        model.addAttribute("data", listUrl);
    }

    /**
     * 返回小程序轮播图片信息
     *
     * @param model
     * @param request
     */
    @RequestMapping(value = "resRollImages")
    public void resRollImages(Model model, HttpServletRequest request) {

        String userId = request.getParameter("userId");
        ZngFunctionImages zhinengguiFunctionImages = zhinengguiFunctionImagesService.getByUserIdAndType(userId, "1");
        List<FileUpload> listImage = FileUploadUtils.findFileUpload(zhinengguiFunctionImages.getId(), "zhinengguiFunctionImages_image");

        System.out.println(request.getSession().getServletContext().getRealPath("/"));
        String url = request.getScheme() + "://";
        url += request.getServerName();
        if (request.getServerPort() != 80) {
            url += ":" + request.getServerPort();
        }
        url = url + request.getContextPath();
        System.out.println(url);
        List<String> listUrl = new ArrayList<>();
        for (FileUpload item : listImage) {
            listUrl.add(url + item.getFileUrl());
        }
        model.addAttribute("flag", ResultMsg.SUCCESS_FLAG);
        model.addAttribute("msg", ResultMsg.MSG_SUCCESS_ZN);
        model.addAttribute("data", listUrl);
    }
}