package com.mars.lab02.util;

import jakarta.websocket.Session;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 工具类，提供客户端连接的管理等功能
 *
 * @author geyan
 * @date 2025/10/12
 */
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


}
