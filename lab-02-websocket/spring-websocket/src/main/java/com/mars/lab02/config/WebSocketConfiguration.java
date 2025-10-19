package com.mars.lab02.config;

import com.mars.lab02.websocket.DemoWebSocketHandler;
import com.mars.lab02.websocket.DemoWebSocketShakeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author geyan
 * @date 2025/10/12
 */
@Configuration
@EnableWebSocket  // 开启Spring WebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/")
                .addInterceptors(new DemoWebSocketShakeInterceptor())
                .setAllowedOrigins("*");  // 解决跨域问题
    }


    @Bean
    public DemoWebSocketHandler webSocketHandler(){
        return new DemoWebSocketHandler();
    }

    @Bean
    public DemoWebSocketShakeInterceptor webSocketShakeInterceptor(){
        return new DemoWebSocketShakeInterceptor();
    }
}
