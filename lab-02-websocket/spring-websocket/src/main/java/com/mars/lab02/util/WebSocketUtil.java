package com.mars.lab02.util;

import com.alibaba.fastjson.JSONObject;
import com.mars.lab02.msg.Message;
import jakarta.websocket.RemoteEndpoint;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 工具类，提供客户端连接的管理等功能
 *
 * @author geyan
 * @date 2025/10/12
 */
@Slf4j
public class WebSocketUtil {

    private static final Map<WebSocketSession, String> SESSION_USER_MAP = new ConcurrentHashMap<>();

    private static final Map<String, WebSocketSession> USER_SESSION_MAP = new ConcurrentHashMap<>();

    public static void addSession(WebSocketSession session, String userId) {
        SESSION_USER_MAP.put(session, userId);
        USER_SESSION_MAP.put(userId, session);
    }

    public static void removeSession(WebSocketSession session) {
        String user = SESSION_USER_MAP.remove(session);
        if (!StringUtils.isEmpty(user)) {
            USER_SESSION_MAP.remove(user);
        }
    }


    /**
     * 广播发送消息给所有在线用户
     * @param type
     * @param message
     * @param <T>
     */
    public static <T extends Message> void broadcast(String type, T message) {
        TextMessage messageText = buildTextMessage(type, message);
        for (WebSocketSession session : SESSION_USER_MAP.keySet()) {
            sendTextMessage(session, messageText);
        }
    }

    /**
     * 发送给单个用户
     * @param session
     * @param type
     * @param message
     * @param <T>
     */
    public static <T extends Message> void send(WebSocketSession session, String type, T message) {
        TextMessage messageText = buildTextMessage(type, message);
        sendTextMessage(session, messageText);
    }

    public static <T extends Message> boolean send(String user, String type, T message) {
        WebSocketSession session = USER_SESSION_MAP.get(user);
        if (session == null) {
            log.error("session is null, user: {}", user);
            return false;
        }
        send(session, type, message);
        return true;
    }

    private static void sendTextMessage(WebSocketSession session, TextMessage messageText) {
        if (session == null) {
            log.error("session is null");
            return;
        }
        try {
            session.sendMessage(messageText);
        } catch (Exception e) {
            log.error("send message error", e);
        }
    }


    private static <T extends Message> TextMessage buildTextMessage(String type, T message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", type);
        jsonObject.put("body", message);
        return new TextMessage(jsonObject.toJSONString());
    }


}
