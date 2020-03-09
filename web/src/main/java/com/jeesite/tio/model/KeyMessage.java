package com.jeesite.tio.model;

import com.jeesite.tio.common.TioConstants;
import com.jeesite.tio.utils.MessageDataUtils;

/**
 * 设备操作秘钥生产工具类
 *
 * @author Administrator
 */
public final class KeyMessage {


    /**
     * 生产心跳包协议秘钥
     */
    public static final String getDeviceHeartbeat() {
        String text = MessageDataUtils.convertStringToHex(TioConstants.HEADER_STRING) +//头部
                "09" +//帧长度
                "00" +//板地址
                "80" +//指令字
                "00";//状态
        return text + MessageDataUtils.get_BBC(text);
    }

    /**
     * 生产获取设备ID秘钥
     *
     * @return 案例:"64 64 6b 6a 01 81 00 80	"
     */
    public static final String getDeviceIdString() {
        String text = MessageDataUtils.convertStringToHex(TioConstants.HEADER_STRING) +//头部
                "08" +//帧长度
                "00" +//板地址
                "81";//指令字
        return text + MessageDataUtils.get_BBC(text);
    }

    /**
     * 生产获取开单个锁秘钥
     *
     * @param boardAddress
     * @param channel
     * @return
     */
    public static final String getOpenOneLock(String boardAddress, String channel) {
        String text = MessageDataUtils.convertStringToHex(TioConstants.HEADER_STRING) +//头部
                "09" +//帧长度
                MessageDataUtils.getCoverDataString(Integer.toHexString(Integer.valueOf(boardAddress))) +//板地址
                "82" +//指令字
                MessageDataUtils.getCoverDataString(Integer.toHexString(Integer.valueOf(channel)));//通道号,锁地址
        return text + MessageDataUtils.get_BBC(text);
    }

    /**
     * 生产获取单个锁状态秘钥
     *
     * @param boardAddress
     * @param channel
     * @return
     */
    public static final String getReadState(String boardAddress, String channel) {
        String text = MessageDataUtils.convertStringToHex(TioConstants.HEADER_STRING) +//头部
                "09" +//帧长度
                MessageDataUtils.getCoverDataString(Integer.toHexString(Integer.valueOf(boardAddress))) +//板地址
                "83" +//指令字
                MessageDataUtils.getCoverDataString(Integer.toHexString(Integer.valueOf(channel)));//通道号
        return text + MessageDataUtils.get_BBC(text);
    }

    /**
     * 生产获取所有锁状态秘钥
     *
     * @param boardAddress
     * @return
     */
    public static final String getReadStateALL(String boardAddress) {
        String text = MessageDataUtils.convertStringToHex(TioConstants.HEADER_STRING) +//头部
                "08" +//帧长度
                MessageDataUtils.getCoverDataString(Integer.toHexString(Integer.valueOf(boardAddress))) +//板地址
                "84";//命令符
        return text + MessageDataUtils.get_BBC(text);
    }


    /**
     * 生产获取开启所有锁通道秘钥
     *
     * @param boardAddress
     * @return
     */
    public static final String getOpenLockAll(String boardAddress) {
        String text = MessageDataUtils.convertStringToHex(TioConstants.HEADER_STRING) +//头部
                "08" +//帧长度
                MessageDataUtils.getCoverDataString(Integer.toHexString(Integer.valueOf(boardAddress))) +//板号
                "86";//指令字
        return text + MessageDataUtils.get_BBC(text);
    }


    /**
     * 生产支付成功返回开锁秘钥
     *
     * @param cabinetId
     * @param doorId
     * @return
     */
    public static final String getPayOpenLockOne(String cabinetId, String doorId, String orderId) {
        String text = MessageDataUtils.convertStringToHex(TioConstants.HEADER_STRING) +//头部
                "01" +//设备符
                "96" +//命令符
                "0" +//占位符
                MessageDataUtils.getCoverDataString(Integer.toHexString(Integer.valueOf(cabinetId))) +//数据域柜号
                MessageDataUtils.getCoverDataString(Integer.toHexString(Integer.valueOf(doorId)))//数据域门号
                + orderId;//订单号
        return text + MessageDataUtils.get_BBC(text);
    }


    /**
     * 生产支付成功返回开锁秘钥
     *
     * @param userId
     * @return
     */
    public static final String getPayMoney(String userId) {
        String text = MessageDataUtils.convertStringToHex(TioConstants.HEADER_STRING) +//头部
                "01" +//设备符
                "97" +//命令符
                "0" +//占位符
                userId;//订单号
        return text + MessageDataUtils.get_BBC(text);
    }


    /**
     * 生产更新推送秘钥
     */
    public static final String getEditionUpdate() {
        String text = MessageDataUtils.convertStringToHex(TioConstants.HEADER_STRING) +//头部
                "01" +//设备符
                "18" +//命令符
                "00";//数据域
        return text + MessageDataUtils.get_BBC(text);
    }
}
