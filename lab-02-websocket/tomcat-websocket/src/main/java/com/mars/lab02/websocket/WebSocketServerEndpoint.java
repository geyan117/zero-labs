package com.mars.lab02.websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

/**
 * @author geyan
 * @date 2025/10/12
 */
@Controller
@ServerEndpoint("/")
@Slf4j
public class WebSocketServerEndpoint {

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        log.info("[onOpen] session({}) 接入", session.getId());
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        log.info("[onMessage] session({}) 接收到一条消息 {}", session.getId(), message);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        log.info("[onClose] session({}) 连接关闭，原因 {}", session.getId(), closeReason);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.info("[onError] session({}) 发生异常", session.getId(), throwable);
    }
}
