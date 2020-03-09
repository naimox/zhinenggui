package com.jeesite.tio.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.server.ServerGroupContext;
import org.tio.server.TioServer;
import org.tio.server.intf.ServerAioHandler;
import org.tio.server.intf.ServerAioListener;

import com.jeesite.tio.common.TioConstants;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * TIO服务类
 *
 * @author BBF
 * @see <a href="http://www.cnblogs.com/panzi/p/7814062.html">通讯框架 t-io 学习</a>
 */
public class TioServerGo {
    private static final Logger LOGGER = LoggerFactory.getLogger(TioServer.class);

    /**
     * aioServer对象
     */
    private final TioServer tioServer;

    /**
     * 服务器IP，不需要绑定，设置为null
     */
    private final String serverIp;

    /**
     * 监听的端口
     */
    private final int serverPort;
    /**
     * handler, 包括编码、解码、消息处理
     */
    public static ServerAioHandler aioHandler;
    /**
     * 事件监听器，可以为null，但建议自己实现该接口，可以参考showcase了解接口
     */
    public static ServerAioListener aioListener;
    /**
     * 一组连接共用的上下文对象
     */
    public static ServerGroupContext serverGroupContext;

    public TioServerGo() {
        this(null, TioConstants.DEFAULT_PORT);
    }

    /**
     * TIO服务
     *
     * @param serverIp 绑定IP，如果不需要则为null
     * @param port     指定端口
     */
    public TioServerGo(String serverIp, int port) {
        this.serverIp = serverIp;
        this.serverPort = port;

        aioHandler = new TioServerAioHandler();

        aioListener = new ServerAioListenerAchieve() {
        };

        serverGroupContext = new
                ServerGroupContext(aioHandler, aioListener);
        tioServer = new TioServer(serverGroupContext);
    }

    /**
     * 初始化Bean的时候执行的初始化方法
     */
    @PostConstruct
    public void init() {
        try {
            //启动Tio服务
            tioServer.start(serverIp, serverPort);
            LOGGER.info("[TioServer.init]tio启动成功，端口{}", serverPort);
        } catch (Exception e) {
            LOGGER.error("[TioServer.init]", e);
        }
    }

    @PreDestroy
    public void destroy() {
        tioServer.stop();
    }
}