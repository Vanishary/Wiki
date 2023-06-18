package com.epra.wiki.service;

import com.epra.wiki.WebSocket.WebSocketServer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: Guotao Li
 * @DateTime: 2023/6/18 2:17 下午
 * @Description: TODO
 */
@Service
public class WsService {

    @Resource
    public WebSocketServer webSocketServer;

    @Async
    public void sendInfo(String message) {
        webSocketServer.sendInfo(message);
    }
}
