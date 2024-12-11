package com.aucloud.aupay.operate.config;

import com.aucloud.aupay.operate.websockets.TickersWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Bean
    public WebSocketClient webSocketClient() {
        return new StandardWebSocketClient();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // webSocket通道,指定处理器和路径
        // 直连当前业务模块的连接地址：ws://192.168.2.137:9403/websocket?user=张三
        // 通过网关模块的连接地址  ws://192.168.2.137:8080/ws/websocket?user=张三
        // 测试发送消息格式：{"content":"内容","targetId":"0"}
        registry
                .addHandler(new TickersWebSocketHandler(), "/websocket/tickers/**")
                // 指定自定义拦截器
                .addInterceptors(new TickersWebSocketInterceptor())
                // 允许跨域
                .setAllowedOrigins("*");
        // sockjs通道
        registry
                .addHandler(new TickersWebSocketHandler(), "/sock-js")
                .addInterceptors(new TickersWebSocketInterceptor())
                .setAllowedOrigins("*")
                // 开启sockJs支持
                .withSockJS();
    }
}
