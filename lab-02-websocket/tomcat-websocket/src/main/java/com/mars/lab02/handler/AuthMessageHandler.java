package com.mars.lab02.handler;

import com.mars.lab02.msg.AuthRequest;
import com.mars.lab02.msg.AuthResponse;
import com.mars.lab02.msg.UserJoinNoticeRequest;
import com.mars.lab02.util.WebSocketUtil;
import jakarta.websocket.Session;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author geyan
 * @date 2025/10/12
 */
@Component
public class AuthMessageHandler implements MessageHandler<AuthRequest> {

    @Override
    public void execute(Session session, AuthRequest msg) {
        if (StringUtils.isEmpty(msg.getAccessToken())) {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setCode(1);
            authResponse.setMsg("access token is empty");
            WebSocketUtil.send(session, AuthResponse.TYPE, authResponse);
            return;
        }
        // 这里应该使用user或者userId，这里为了简单，直接使用accessToken作为user
        WebSocketUtil.addSession(session, msg.getAccessToken());

        // 判断是否认证成功，这里假设直接成功
        AuthResponse authResponse = new AuthResponse();
        authResponse.setCode(0);
        WebSocketUtil.send(session, AuthResponse.TYPE, authResponse);

        // 这里还可以广播一下，某个人加入了
        UserJoinNoticeRequest userJoinNoticeRequest = new UserJoinNoticeRequest();
        userJoinNoticeRequest.setUsername(msg.getAccessToken());
        WebSocketUtil.broadcast(UserJoinNoticeRequest.TYPE, userJoinNoticeRequest);
    }

    @Override
    public String getType() {
        return AuthRequest.TYPE;
    }
}
