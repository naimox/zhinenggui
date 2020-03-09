package com.jeesite.mobileMessage.sendingZtMsgUtil;

import com.jeesite.common.codec.Md5Utils;
import com.jeesite.mobileMessage.sendingUtil.SmsSendUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author tianyh 
 * @Description:普通短信发送
 */
public class ZtSendingMsgUtil {

	private final static String link_url = "http://api.zthysms.com/sendSms.do";
	private final static String userName = "dongdonghy";
	private final static String passWord = "cWV7wL";

	public static Boolean sendMsg(String phone, String content){
		//post请求返回结果
		if(StringUtils.isBlank(phone)||StringUtils.isBlank(content)){
			return false;
		}
		String timeTemp = getTimeTemp();
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String url = link_url + "?username=" + userName + "&password="
				+ md5Pwd(timeTemp) + "&tkey=" + timeTemp
				+ "&mobile=" + phone + "&content=" + content;
		HttpGet method = new HttpGet(url);
		try {
			HttpResponse response = httpClient.execute(method);
			url = URLDecoder.decode(url, "UTF-8");
			/**请求发送成功，并得到响应**/
			if (response.getStatusLine().getStatusCode() == 200) {
				String str = "";
				try {
					/**读取服务器返回过来的json字符串数据**/
					str = EntityUtils.toString(response.getEntity());
					System.out.println(str);
				} catch (Exception e) {
					return false;
				}
			}
		} catch (IOException e) {

		}
		return true;
	}

//	public static Boolean sendMsg(String phone, String content){
//		boolean flag = false;
//		String str = "";
//		try {
//			str = SmsSendUtil.sendMessage(content,phone);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		if (str.equalsIgnoreCase("0")){
//			flag = true;
//		}
//		return flag;
//	}

	private static String getTimeTemp(){
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	private static String md5Pwd(String timeTemp){
		return Md5Utils.md5(Md5Utils.md5(passWord) + timeTemp);
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
//		System.out.println(getTimeTemp());
		sendMsg("15538316963","【智能柜】您的快件已到达,请凭取件码:123456");
	}


}
