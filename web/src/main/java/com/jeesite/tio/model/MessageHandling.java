package com.jeesite.tio.model;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;

import com.jeesite.modules.zhinenggui.dao.SpringUtil;
import com.jeesite.modules.zhinenggui.entity.ZngDevice;
import com.jeesite.modules.zhinenggui.service.ZngDeviceService;
import com.jeesite.tio.common.TioConstants;
import com.jeesite.tio.common.TioPacket;
import com.jeesite.tio.entity.MessageEntity;
import com.jeesite.tio.server.TioServerAioHandler;

public class MessageHandling extends TioServerAioHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TioServerAioHandler.class);
    @Autowired
    private static ZngDeviceService DDService;

    /**
     * 快递柜消息处理方法
     */
    public static TioPacket MessageHandingRealizeDoorType(MessageEntity ME, ChannelContext channelContext) {
        TioPacket respPacket = new TioPacket();
        respPacket.setBody(KeyMessage.getDeviceHeartbeat().getBytes(TioConstants.CHARSET_UTF8));
        switch (ME.getInstructKeywords()) {
            case "80":
                return respPacket;
            case "81":
                Tio.bindBsId(channelContext, ME.getDeviceId());
                DDService = (ZngDeviceService) SpringUtil.getApplicationContext().getBean("zhinengguiDeviceService");
                ZngDevice DD = DDService.getByDeviceId(ME.getDeviceId());
                if (DD != null) {
                    DD.setOnLine("1");
                    DDService.update(DD);
                } else {
                    DD = new ZngDevice();
                    DD.setDeviceId(ME.getDeviceId());
                    DD.setDeviceName("新连接");
                    DD.setDeviceRemark("新连接");
                    DD.setEnable("1");
                    DD.setOnLine("1");
                    DD.setState("1");
                    DD.setCreateDate(new Date());
                    DD.setCreateByName("System");
                    DDService.save(DD);
                }
                LOGGER.info("{}:号设备已上线...", ME.getDeviceId());
                return respPacket;
            case "82":

                break;
            case "83":

                break;
            case "84":

                break;
            case "85":
                LOGGER.info("{}:号设备；{}:号柜；{}:号锁；{}:", channelContext.getBsId(), ME.getChannel()
                        , ME.getData().substring(0, 2)
                        , "00".equals(ME.getData().substring(2, 4)) ? "关。" : "开。");
                break;
            case "86":

                break;
        }
        return respPacket;
    }
}
