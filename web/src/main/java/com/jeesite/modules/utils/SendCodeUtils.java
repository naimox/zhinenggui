package com.jeesite.modules.utils;

import com.jeesite.mobileMessage.sendingUtil.SmsSendUtil;
import com.jeesite.modules.zhinenggui.entity.ZngCode;
import org.springframework.ui.Model;

import java.io.UnsupportedEncodingException;

public class SendCodeUtils {
    public static String reSendCode(String type, String phone, String code, ZngCode zhinengguiCode){
        String result = "-1";
        String message = "";
        switch (type){
            case "0" ://普通验证码
                message += "【智能柜短信】您的验证码为:"+code;
                //message += "【PsarTaoOnline】 Your verification code is:"+code;
                break;
            case "1" :
                message += "【智能柜短信】已有快件到达快递箱，需要您联系用户进行寄送快件操作" + zhinengguiCode.getContent();
                //message += "【PsarTaoOnline】 Your verification code is:"+code;
                break;
            case "2" ://收件验证码
                //message +="【PsarTaoOnline】HURRAH! Your order is ready in our 'Luck & Lock' smart rt locker."
                //	+zhinengguiCode.getContent()+ " and  use the code "+code+" to verify & "
                //	+ "experience the easiest pickup method now. "
                //	+ "If you have any question,please call ll 086986868."
                //	+ "Valid for for 5 days then back in our office! "
                //	+ "More 'Luck & Lock' will be placed in Phnom Penh soon! Here to discover right now (map): https://goo.gl/jZ6yNw";
                message += "【智能柜短信】您的快件取件码:" + code + zhinengguiCode.getContent()==null?"":zhinengguiCode.getContent();
                break;
            case "3" :
                message += "【智能柜短信】，您的验证码为:" + code;
                //message += "【PsarTaoOnline】 Your verification code is:"+code;
                break;
            default:
                break;
        }
        try {
            result = SmsSendUtil.sendMessage(message,phone);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }



}
