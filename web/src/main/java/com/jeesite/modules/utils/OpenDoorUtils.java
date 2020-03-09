package com.jeesite.modules.utils;

import com.jeesite.tio.common.TioConstants;
import com.jeesite.tio.common.TioPacket;
import com.jeesite.tio.model.KeyMessage;
import com.jeesite.tio.server.TioServerGo;

import org.tio.core.ChannelContext;
import org.tio.core.Tio;

/**
 * Created by Administrator on 2018/8/31 0031.
 * Soket命令封装工具类
 */
public class OpenDoorUtils {
	/**
	 * 管理员远程开门
	 * @param deviceId
	 * @param plateId
	 * @param doorId
	 * @return
	 */
    public static Boolean openTheDoor(String deviceId, String plateId, String doorId){
        boolean flag = false;
        TioPacket TP = new TioPacket();
        TP.setBody(KeyMessage.getOpenOneLock(plateId,doorId).getBytes(TioConstants.CHARSET_UTF8));
        flag = Tio.sendToBsId(TioServerGo.serverGroupContext, deviceId, TP);
        return flag;
    }
    /**
     * 支付成功远程开门
     * @param deviceId
     * @param plateId
     * @param doorId
     * @param orderId
     * @return
     */
    public static Boolean openTheDoorPay(String deviceId, String plateId, String doorId, String orderId){
        boolean flag = false;
        TioPacket TP = new TioPacket();
        System.out.println(KeyMessage.getPayOpenLockOne(plateId,doorId,orderId));
        TP.setBody(KeyMessage.getPayOpenLockOne(plateId,doorId,orderId).getBytes(TioConstants.CHARSET_UTF8));
        flag = Tio.sendToBsId(TioServerGo.serverGroupContext, deviceId, TP);
        return flag;
    }
    /**
     * 获取支付金额
     * @param deviceId
     * @param userId
     * @return
     */
    public static Boolean getPayMoney(String deviceId, String userId){
        boolean flag = false;
        TioPacket TP = new TioPacket();
        TP.setBody(KeyMessage.getPayMoney(userId).getBytes(TioConstants.CHARSET_UTF8));
        flag = Tio.sendToBsId(TioServerGo.serverGroupContext, deviceId, TP);
        return flag;
    }
    
    /**
     * 远程推送更新
     * @param deviceId
     * @return
     */
    public static Boolean sendEditionUpdate(String deviceId){
        boolean flag = false;
        TioPacket TP = new TioPacket();
        TP.setBody(KeyMessage.getEditionUpdate().getBytes(TioConstants.CHARSET_UTF8));
        flag = Tio.sendToBsId(TioServerGo.serverGroupContext, deviceId, TP);
        return flag;
    }
    /**
     * 获取设备是否在线
     * @param deviceId
     * @return true在线 false不在线
     */
    public static Boolean getDeviceOnlinState(String deviceId) {
    	boolean flag = false;
    	ChannelContext cc = 	Tio.getChannelContextByBsId(TioServerGo.serverGroupContext, deviceId);
    	if(cc!=null) {
    		flag = true;
    	}
    	return flag;
    }
    

}
