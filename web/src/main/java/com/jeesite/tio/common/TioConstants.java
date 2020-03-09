package com.jeesite.tio.common;

import java.nio.charset.Charset;

/**
 * 常量定义
 *
 * @author BBF
 */
public final class TioConstants {
    private TioConstants() {
    }

    /**
     * 数据头部识别字符串
     */
    public static String HEADER_STRING = "zhinenggui";

    /**
     * 消息头的长度
     */
    public static final int HEADER_LENGTH = 4;

    /**
     * 默认端口
     */
    public static final int DEFAULT_PORT = 12000;

    /**
     * UTF8字符集
     */
    public static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");
}