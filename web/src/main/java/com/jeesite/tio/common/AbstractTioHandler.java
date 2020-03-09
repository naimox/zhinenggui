package com.jeesite.tio.common;

import com.jeesite.tio.server.ServerAioListenerAchieve;
import com.jeesite.tio.utils.MessageDataUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.Tio;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioHandler;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * 消息处理抽象类
 *
 * @author BBF
 */
public abstract class AbstractTioHandler
        implements ServerAioHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerAioListenerAchieve.class);

    /**
     * 数据显示AbstractTioHandler
     *
     * @param packet
     */
    public abstract void showPacket(TioPacket packet);

    /**
     * 解码：把接收到的ByteBuffer，解码成应用可以识别的业务消息包
     * 总的消息结构：消息头 + 消息体
     * 消息头结构：    4个字节，存储消息体的长度
     * 消息体结构：   对象的json串的byte[]
     */
    @Override
    public TioPacket decode(ByteBuffer buffer, int limit, int position, int readableLength, ChannelContext channelContext) throws AioDecodeException {

        TioPacket imPacket = new TioPacket();
        if (limit - position == readableLength) {
            byte[] dst = new byte[readableLength];
            buffer.get(dst);
            imPacket.setBody(dst);
            return imPacket;
        } else {
            return null;
        }
    }

    /**
     * 编码：
     */
    @Override
    public ByteBuffer encode(Packet packet, GroupContext groupContext, ChannelContext channelContext) {
        TioPacket tioPacket = (TioPacket) packet;
        byte[] body = tioPacket.getBody();
        LOGGER.info(("发送前编码为：" + new String(body)));
        return ByteBuffer.wrap(MessageDataUtils.hexStr2Byte(new String(body)));
    }
}
