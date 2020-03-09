package com.jeesite.mobileMessage.sendingUtil;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.jeesite.modules.zhinenggui.service.ZngCodeService;
/**
 * 
 * @author tianyh 
 * @Description:普通短信发送
 */
public class SmsSendUtil {

	public static final String charset = "utf-8";
	
	// 请登录zz.253.com 获取创蓝API账号(非登录账号,示例:N1234567)
//	public static String account = "N7530500";

	// 请登录zz.253.com 获取创蓝API密码(非登录密码)
//	public static String pswd = "a3IcNxgUuJe40a";

	public static String account = "N6477274";
	public static String pswd = "WZy1YQjfvT9388";

	public static String sendMessage(String message,String phone) throws UnsupportedEncodingException {

		//短信发送的URL 请登录zz.253.com 获取完整的URL接口信息
		String smsSingleRequestServerUrl = "https://smssh1.253.com/msg/send/json";
		
		String code = "";
		
		
		// 设置您要发送的内容：其中“【】”中括号为运营商签名符号，多签名内容前置添加提交
		//要和短信模板相同eg:"【智能柜短信】您好,您的验证码为:"9876
	    String msg = message;
		//状态报告
		String report= "true";
		
		SmsSendRequest smsSingleRequest = new SmsSendRequest(account, pswd, msg, phone, report);
		
		String requestJson = JSON.toJSONString(smsSingleRequest);
		
		System.out.println("before request string is: " + requestJson);
		
		String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);
		
		System.out.println("response after request result is :" + response);
		
		SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
		
		System.out.println("response  toString is :" + smsSingleResponse);

		code = smsSingleResponse.getCode();
		 
		return code;
	
	}
	
	/**
	 * 随机生成六位验证码数字
	 * @return
	 */
	public static String getCode(){
		int num = (int)(Math.random()*1000000);
		if (num < 100000) {
			return getCode();
		}else {
			return String.valueOf(num);
		}
	}

	public static void main(String[] args) {
		String msg = "【智能柜短信】这是一个测试的短信验证码！";
		String code = "";
		try {
			code = sendMessage(msg,"18704060405");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if("0".equalsIgnoreCase(code)){
			System.out.println("成功！");
		}else{
			System.out.println("失败！"+ code);
		}
	}



}
