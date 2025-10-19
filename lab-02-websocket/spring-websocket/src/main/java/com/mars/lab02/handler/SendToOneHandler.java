package com.mars.lab02.handler;

import com.mars.lab02.msg.SendResponse;
import com.mars.lab02.msg.SendToOneRequest;
import com.mars.lab02.msg.SendToUserRequest;
import com.mars.lab02.util.WebSocketUtil;
import jakarta.websocket.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author geyan
 * @date 2025/10/12
 */
@Component
public class SendToOneHandler implements MessageHandler<SendToOneRequest> {

    @Override
    public void execute(WebSocketSession session, SendToOneRequest msg) {
        // 假设直接成功了
        SendResponse sendResponse = new SendResponse();
        sendResponse.setMsgId(msg.getMsgId());
        sendResponse.setCode(0);

        // 回复给发送的信息，表示发送成功
        WebSocketUtil.send(session, SendResponse.TYPE, sendResponse);

        // 发送给对应的用户
        SendToUserRequest sendToUserRequest = new SendToUserRequest();
        sendToUserRequest.setMsgId(msg.getMsgId());
        sendToUserRequest.setContent(msg.getContent());
        WebSocketUtil.send(msg.getToUser(), SendToUserRequest.TYPE, sendToUserRequest);
    }

    @Override
    public String getType() {
        return SendToOneRequest.TYPE;
    }
}
