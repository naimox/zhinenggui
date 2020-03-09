package com.jeesite.modules.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by Administrator on 2018/9/5 0005.
 */
public class QRCodeUtils {

    public static String httpPost(String url, JSONObject jsonParam, boolean noNeedResponse){
        //post请求返回结果
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String result = "";
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
                    if (noNeedResponse) {
                        return null;
                    }
                    result = str;
                } catch (Exception e) {

                }
            }
        } catch (IOException e) {

        }
        return result;
    }

    public static String doPostMethod(String orderId, String money){
        String url = "http://dongdongkeji.cn/wx_gi/home/index/rs_code?order_number="+orderId+"&money="+money;
        return httpPost(url,null,false);
    }
	/**
	 * 生成二维码
	 * @param deviceId
	 * @param userId
	 * @param money
	 * @return
	 */
    public static String recharge(String deviceId, String userId, String money){
        String url = "http://dongdongkeji.cn/php_api/api/api/rs_code?deviceId="+deviceId+"&money="+money+"&userId="+userId;
        return httpPost(url,null,false);
    }

    public static String rechargeCode(String userId, Double money){
        String url = "http://dongdongkeji.cn/php_api/api/api/d_code?money="+money+"&user_id="+userId;
        return httpPost(url,null,false);
    }
///tx_money?money=money&openid=openid
    public static String withdrawDeposit(String openid, Double money,String userId){
        String url = "http://dongdongkeji.cn/php_api/api/api/tx_money?money="+money+"&openid="+openid+"&user_id="+userId;
        return httpPost(url, null, false);
    }
    
    public static void main(String[] args) {
    	String text = recharge("00001009","1087599445760462848","0.01");
    	System.out.println(text);
	}
}
