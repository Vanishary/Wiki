package com.epra.wiki.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author: Guotao Li
 * @DateTime: 2023/6/17 9:58 下午
 * @Description: WebSocket 配置项
 */
@Configuration
public class WebSocketConfig {

    // 声明本应用暴露、使用WebSocket
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
