package com.jeesite.tio.entity;

public class MessageDataEntity {

    private String state;//状态

    private String deviceId;//设备ID

    private String boardAddress;//版地址

    private String channel;//通道号,锁地址

    private String channelState; //锁状态

    private String channelCount;//通道总数

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getBoardAddress() {
        return boardAddress;
    }

    public void setBoardAddress(String boardAddress) {
        this.boardAddress = boardAddress;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannelState() {
        return channelState;
    }

    public void setChannelState(String channelState) {
        this.channelState = channelState;
    }

    public String getChannelCount() {
        return channelCount;
    }

    public void setChannelCount(String channelCount) {
        this.channelCount = channelCount;
    }
}
