package com.jeesite.tio.server;

import com.jeesite.tio.common.AbstractTioHandler;
import com.jeesite.tio.common.TioConstants;
import com.jeesite.tio.common.TioPacket;
import com.jeesite.tio.model.DataAnalysis;
import com.jeesite.tio.model.MessageHandling;
import com.jeesite.tio.utils.MessageDataUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioHandler;

/**
 * 消息处理类
 *
 * @author BBF
 */
public class TioServerAioHandler extends AbstractTioHandler
        implements ServerAioHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TioServerAioHandler.class);

    /**
     * 处理消息
     */
    @Override
    public void handler(Packet packet,
                        ChannelContext channelContext) {
        TioPacket tioPacket = (TioPacket) packet;
        byte[] body = tioPacket.getBody();
        if (body != null) {
            String resutString = MessageDataUtils.bytesToHexString(body);
            LOGGER.info("[TioServerAioHandler.handler]收到消息：--{}--", resutString);
            TioPacket messageHandingRealizeDoorType = MessageHandling.MessageHandingRealizeDoorType(new DataAnalysis().Express(resutString), channelContext);
            byte[] textBody = messageHandingRealizeDoorType.getBody();
            String text = new String(textBody, TioConstants.CHARSET_UTF8);
            LOGGER.info("[TioServerAioHandler.handler]发送数据:--{}--", text);
            Tio.send(channelContext, messageHandingRealizeDoorType);
        }
    }

    @Override
    public void showPacket(TioPacket packet) {
        LOGGER.info("[TioServerAioHandler.showPacket]接收数据：{}", packet.toString());
    }
}
