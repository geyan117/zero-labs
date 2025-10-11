package com.mars.lab02.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author geyan
 * @date 2025/10/12
 */
@Configuration
public class WebSocketConfiguration {

    /**
     * 该Bean的作用，扫描添加有 @ServerEndpoint注解的Bean
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
