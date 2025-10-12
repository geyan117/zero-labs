package com.mars.lab02.util;

import com.alibaba.fastjson.JSONObject;
import com.mars.lab02.msg.Message;
import jakarta.websocket.RemoteEndpoint;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

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

    private static final Map<Session, String> SESSION_USER_MAP = new ConcurrentHashMap<>();

    private static final Map<String, Session> USER_SESSION_MAP = new ConcurrentHashMap<>();

    public static void addSession(Session session, String userId) {
        SESSION_USER_MAP.put(session, userId);
        USER_SESSION_MAP.put(userId, session);
    }

    public static void removeSession(Session session) {
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
        String messageText = buildTextMessage(type, message);
        for (Session session : SESSION_USER_MAP.keySet()) {
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
    public static <T extends Message> void send(Session session, String type, T message) {
        String messageText = buildTextMessage(type, message);
        sendTextMessage(session, messageText);
    }

    public static <T extends Message> boolean send(String user, String type, T message) {
        Session session = USER_SESSION_MAP.get(user);
        if (session == null) {
            log.error("session is null, user: {}", user);
            return false;
        }
        send(session, type, message);
        return true;
    }

    private static void sendTextMessage(Session session, String messageText) {
        if (session == null) {
            log.error("session is null");
            return;
        }
        RemoteEndpoint.Basic basic = session.getBasicRemote();
        if (basic == null) {
            log.error("basic is null");
            return;
        }
        try {
            basic.sendText(messageText);
        } catch (Exception e) {
            log.error("send message error", e);
        }
    }


    private static <T extends Message> String buildTextMessage(String type, T message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", type);
        jsonObject.put("body", message);
        return jsonObject.toJSONString();
    }


}
