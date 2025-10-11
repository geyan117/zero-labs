package com.mars.lab02.handler;

import com.mars.lab02.msg.AuthRequest;
import jakarta.websocket.Session;
import org.springframework.stereotype.Component;

/**
 * @author geyan
 * @date 2025/10/12
 */
@Component
public class AuthMsgHandler implements MessageHandler<AuthRequest> {

    @Override
    public void execute(Session session, AuthRequest msg) {

    }

    @Override
    public String getType() {
        return AuthRequest.TYPE;
    }
}
