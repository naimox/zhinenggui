package com.jeesite.tio.server;

import com.jeesite.modules.zhinenggui.dao.SpringUtil;
import com.jeesite.modules.zhinenggui.entity.ZngDevice;
import com.jeesite.modules.zhinenggui.service.ZngDeviceService;
import com.jeesite.tio.common.TioConstants;
import com.jeesite.tio.common.TioPacket;
import com.jeesite.tio.model.KeyMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioListener;

@Component
public abstract class ServerAioListenerAchieve implements ServerAioListener {

    @Autowired
    private ZngDeviceService DDservice;

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerAioListenerAchieve.class);

    /**
     * 建立连接触发
     */
    @Override
    public void onAfterConnected(ChannelContext channelContext, boolean isConnected, boolean isReconnect) throws Exception {
        TioPacket respPacket = new TioPacket();
        respPacket.setBody(KeyMessage.getDeviceIdString().getBytes(TioConstants.CHARSET_UTF8));
        LOGGER.info("获取设备ID时发送：" + KeyMessage.getDeviceIdString());
        Tio.send(channelContext, respPacket);
        LOGGER.info("新设备建立连接");
    }

    /**
     * 解码成功触发
     */
    @Override
    public void onAfterDecoded(ChannelContext channelContext, Packet packet, int packetSize) throws Exception {
        LOGGER.info("提示:解码成功，触发接口。");
    }

    /**
     * 接收到TCP层传过来的数据后触发
     */
    @Override
    public void onAfterReceivedBytes(ChannelContext channelContext, int receivedBytes) throws Exception {
        LOGGER.info("提示:接收到TCP层数据包，触发接口。");
    }

    /**
     * 消息发送后触发
     */
    @Override
    public void onAfterSent(ChannelContext channelContext, Packet packet, boolean isSentSuccess) throws Exception {
        LOGGER.info("提示:消息发送成功，触发接口。");
    }


    /**
     * 处理一个消息包后
     *
     * @param channelContext
     * @param packet
     * @param cost           本次处理消息耗时，单位：毫秒
     * @throws Exception
     */
    @Autowired
    public void onAfterHandled(ChannelContext channelContext, Packet packet, long cost) throws Exception {
        LOGGER.info("提示:已处理一个消息包，触发接口。");
    }

    /**
     * 连接关闭后触发
     */
    @Override
    public void onBeforeClose(ChannelContext channelContext, Throwable throwable, String remark, boolean isRemove) throws Exception {
        if (channelContext.getBsId() != null) {
            LOGGER.info("提示:" + channelContext.getBsId() + "设备已离线.");
            DDservice = (ZngDeviceService) SpringUtil.getApplicationContext().getBean("zhinengguiDeviceService");
            ZngDevice DD = DDservice.getByDeviceId(channelContext.getBsId());
            DD.setOnLine("0");
            DDservice.update(DD);
        } else {
            LOGGER.info("提示:删除超时冗余连接与无效媒介通道:" + channelContext);
        }
    }
}
