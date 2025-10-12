package com.mars.lab02.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mars.lab02.handler.MessageHandler;
import com.mars.lab02.msg.AuthRequest;
import com.mars.lab02.msg.Message;
import com.mars.lab02.util.WebSocketUtil;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author geyan
 * @date 2025/10/12
 */
@Controller
@ServerEndpoint("/")
@Slf4j
public class WebSocketServerEndpoint implements InitializingBean {

    private static final Map<String, MessageHandler> HANDLERS = new ConcurrentHashMap<>();

    @Autowired
    private ApplicationContext applicationContext;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        log.info("[onOpen] session({}) 接入", session.getId());

        List<String> tokens = session.getRequestParameterMap().get("accessToken");
        String accessToken = CollectionUtils.isEmpty(tokens) ? null : tokens.get(0);

        AuthRequest authRequest = new AuthRequest();
        authRequest.setAccessToken(accessToken);

        MessageHandler messageHandler = HANDLERS.get(AuthRequest.TYPE);
        if (messageHandler == null) {
            log.error("[onOpen] 不存在对应的消息处理器");
            return;
        }
        messageHandler.execute(session, authRequest);
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        log.info("[onMessage] session({}) 接收到一条消息 {}", session.getId(), message);

        try {
            JSONObject jsonObject = JSON.parseObject(message);
            String type = jsonObject.getString("type");
            MessageHandler messageHandler = HANDLERS.get(type);
            if (messageHandler == null) {
                log.error("[onMessage] 不存在对应的消息处理器");
                return;
            }

            // 如果是业务需求的话，就不用这个，直接转对应的类型就行；
            // 如果是框架的话，肯定是不知道对应类型的，就需要这个
            Class<? extends Message> messageClass = getMessageClass(messageHandler);
            Message body = JSON.parseObject(jsonObject.getString("body"), messageClass);
            messageHandler.execute(session, body);
        } catch (Exception e) {
            log.error("[onMessage] session({}) message({}) 发生异常", session.getId(), message, e);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        log.info("[onClose] session({}) 连接关闭，原因 {}", session.getId(), closeReason);
        WebSocketUtil.removeSession(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.info("[onError] session({}) 发生异常", session.getId(), throwable);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        applicationContext.getBeansOfType(MessageHandler.class).values()
                .forEach(handler -> HANDLERS.put(handler.getType(), handler));
        log.info("[afterPropertiesSet] 消息处理器数量: {}", HANDLERS.size());
    }


    private Class<? extends Message> getMessageClass(MessageHandler handler) {
        // 获得 Bean 对应的 Class 类名。因为有可能被 AOP 代理过。
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(handler);
        // 获得接口的 Type 数组
        Type[] interfaces = targetClass.getGenericInterfaces();
        Class<?> superclass = targetClass.getSuperclass();
        while ((Objects.isNull(interfaces) || 0 == interfaces.length) && Objects.nonNull(superclass)) { // 此处，是以父类的接口为准
            interfaces = superclass.getGenericInterfaces();
            superclass = targetClass.getSuperclass();
        }
        if (Objects.nonNull(interfaces)) {
            // 遍历 interfaces 数组
            for (Type type : interfaces) {
                // 要求 type 是泛型参数
                if (type instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) type;
                    // 要求是 MessageHandler 接口
                    if (Objects.equals(parameterizedType.getRawType(), MessageHandler.class)) {
                        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                        // 取首个元素
                        if (Objects.nonNull(actualTypeArguments) && actualTypeArguments.length > 0) {
                            return (Class<Message>) actualTypeArguments[0];
                        } else {
                            throw new IllegalStateException(String.format("类型(%s) 获得不到消息类型", handler));
                        }
                    }
                }
            }
        }
        throw new IllegalStateException(String.format("类型(%s) 获得不到消息类型", handler));
    }
}
