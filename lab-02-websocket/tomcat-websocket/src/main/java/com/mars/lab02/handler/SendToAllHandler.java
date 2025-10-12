package com.mars.lab02.handler;

import com.mars.lab02.msg.SendResponse;
import com.mars.lab02.msg.SendToAllRequest;
import com.mars.lab02.msg.SendToUserRequest;
import com.mars.lab02.util.WebSocketUtil;
import jakarta.websocket.Session;
import org.springframework.stereotype.Component;

/**
 * @author geyan
 * @date 2025/10/12
 */
@Component
public class SendToAllHandler implements MessageHandler<SendToAllRequest> {

    @Override
    public void execute(Session session, SendToAllRequest msg) {
        SendResponse sendResponse = new SendResponse();
        sendResponse.setCode(0);
        sendResponse.setMsgId(msg.getMsgId());
        // 回复
        WebSocketUtil.send(session, SendResponse.TYPE, sendResponse);

        // 发送给所有人
        SendToUserRequest sendToUserRequest = new SendToUserRequest();
        sendToUserRequest.setMsgId(msg.getMsgId());
        sendToUserRequest.setContent(msg.getContent());
        WebSocketUtil.broadcast(SendToUserRequest.TYPE, sendToUserRequest);
    }

    @Override
    public String getType() {
        return SendToAllRequest.TYPE;
    }
}
