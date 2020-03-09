package com.jeesite.mobileMessage.sendingZtMsgUtil;

import com.jeesite.common.codec.Md5Utils;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author tianyh 
 * @Description:普通短信发送
 */
public class ZtForeignSendingMsgUtil {

	private final static String link_url = "http://intl.zthysms.com/intSendSms.do";
	private final static String userName = "dongdonggjhy";
	private final static String passWord = "VnWd50";
	private final static String code = "855";
//	private final static String code = "86";

	public static Boolean sendMsg(String phone, String content){
		//post请求返回结果
		if(StringUtils.isBlank(phone)||StringUtils.isBlank(content)){
			return false;
		}
		String str = "";
		String timeTemp = getTimeTemp();
		List<NameValuePair> urlParameters = new ArrayList<>();
		urlParameters.add(new BasicNameValuePair("username", userName));
		str += "?username="+userName;
		String md5 = md5Pwd(timeTemp);
		urlParameters.add(new BasicNameValuePair("password", md5));
		str += "&password="+md5;
//		urlParameters.add(new BasicNameValuePair("tkey", timeTemp));
//		str += "&tkey="+timeTemp;
		urlParameters.add(new BasicNameValuePair("mobile", phone));
		str += "&mobile="+phone;
		urlParameters.add(new BasicNameValuePair("content", content));
		str += "&content="+content;
		urlParameters.add(new BasicNameValuePair("code", code));
		str += "&code="+code;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		HttpPost post = new HttpPost(link_url);
		try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters, HTTP.UTF_8));
			try {
				response = httpclient.execute(post);
				// 判断网络连接状态码是否正常(0--200都数正常)
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String c = EntityUtils.toString(response.getEntity(), "UTF-8");
					System.out.println(c);
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
		return true;
	}

	private static String getTimeTemp(){
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	private static String md5Pwd(String timeTemp){
		return Md5Utils.md5(passWord);
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
		sendMsg("78852887","【បច្ចេកវិទ្យា】this is TextDate");
	}


}
