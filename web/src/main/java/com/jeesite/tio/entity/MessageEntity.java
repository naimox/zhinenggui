package com.jeesite.tio.entity;

public class MessageEntity extends MessageDataEntity {

    /**
     * 起始符
     */
    private String header;
    /**
     * 帧长度
     */
    private String frameLength;
    /**
     * 指令字
     */
    private String instructKeywords;
    /**
     * 数据域
     */
    private String data;

    /**
     * BBC校验
     */
    private String BBC;

    public MessageEntity() {

    }

    public MessageEntity(String header, String frameLength, String boardAddress, String instructKeywords, String data, String BBC) {
        this.header = header;
        this.frameLength = frameLength;
        super.setBoardAddress(boardAddress);
        this.instructKeywords = instructKeywords;
        this.data = data;
        this.BBC = BBC;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFrameLength() {
        return frameLength;
    }

    public void setFrameLength(String frameLength) {
        this.frameLength = frameLength;
    }

    public String getInstructKeywords() {
        return instructKeywords;
    }

    public void setInstructKeywords(String instructKeywords) {
        this.instructKeywords = instructKeywords;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getBBC() {
        return BBC;
    }

    public void setBBC(String BBC) {
        this.BBC = BBC;
    }
}
