package com.mars.lab02.handler;

import com.mars.lab02.msg.Message;
import jakarta.websocket.Session;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author geyan
 * @date 2025/10/12
 */
public interface MessageHandler<T extends Message> {

    void execute(WebSocketSession session, T msg);

    String getType();
}
