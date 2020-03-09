package com.jeesite.tio.model;

import com.jeesite.tio.entity.MessageEntity;
import com.jeesite.tio.utils.MessageDataUtils;

public class DataAnalysis {


    public MessageEntity Express(String data) {
        switch (data.substring(0, 8)) {
            case "63737a6e":
                return ExpressCabinet(data);
            default:
                return null;
        }
    }

    /**
     * 解析后装入实体返回
     *
     * @param data
     * @return
     */
    private MessageEntity ExpressCabinet(String data) {
        MessageEntity MeEntity = new MessageEntity(
                // header
                data.substring(0, 8),
                // 帧长度
                data.substring(8, 10),
                // 板地址
                data.substring(10, 12),
                // 指令字
                data.substring(12, 14),
                // 数据域
                data.substring(14, data.length() - 2),
                // BBC校验
                data.substring(data.length() - 2, data.length()));
        return AnalysisData(MeEntity);
    }

    /**
     * 数据域KEY值解析
     *
     * @param entity
     * @return
     */
    private MessageEntity AnalysisData(MessageEntity entity) {

        switch (entity.getInstructKeywords()) {
            case "80":
                // 心跳包
                break;
            case "81":
                // 主动获取设备ID
                entity.setState(entity.getData().substring(0, 2));//状态
                entity.setDeviceId(MessageDataUtils.convertHexToString(entity.getData().substring(2)));// 设备ID
                break;
            case "82":
                // 主动开单个通道
                entity.setState(entity.getData().substring(0, 2));//状态
                entity.setChannel(entity.getData().substring(2, 4));//锁通道
                entity.setChannelState(entity.getData().substring(4, 6));//锁状态
                break;
            case "83":
                // 主动读取单个锁通道状态
                entity.setState(entity.getData().substring(0, 2)); //状态
                entity.setChannel(entity.getData().substring(2, 4)); //锁通道
                entity.setChannelState(entity.getData().substring(4, 6)); // 锁状态
                break;
            case "84":
                // 主动读取所有锁通道状态
                entity.setState(entity.getData().substring(0, 2));//状态
                entity.setChannelCount(entity.getData().substring(2, 4));//锁总数
                entity.setChannelState(entity.getData().substring(4, entity.getData().length()));//锁状态(锁总数)
                break;
            case "85":
                // 锁状态变化（当锁的状态发生变化，主板主动发送）
                entity.setChannel(entity.getData().substring(0, 2));//锁通道
                entity.setChannelState(entity.getData().substring(2, 4));//锁状态
                break;
            case "86":
                // 主动开全部锁通道
                entity.setState(entity.getData().substring(2, 4));
                break;
        }
        return entity;
    }
}
