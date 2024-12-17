package com.aucloud.aupay.operate.websockets;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.aucloud.commons.constant.RedisCacheKeys;
import com.aucloud.commons.pojo.bo.HuobiTicker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@RefreshScope
@Service
public class BinanceWebSocketClientService extends TextWebSocketHandler {

    private final AtomicBoolean running = new AtomicBoolean(false);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private WebSocketClient client;

    private String websocketUrl;
    private WebSocketSession session;

    @Value("${binance.wsstream.addr}")
    public void setWebsocketUrl(String websocketUrl) {
        this.websocketUrl = websocketUrl;
        if (this.session != null) {
            try {
                this.session.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        running.compareAndSet(true, false);
    }

    @Scheduled(fixedRate = 60000)
    public void keepalive() {
        if (!running.get()) {
            connect(websocketUrl);
        }
    }

    public void connect(String uri) {
        WebSocketConnectionManager manager = new WebSocketConnectionManager(client, this, uri);
        manager.start();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("binance websocket afterConnectionEstablished");
        running.compareAndSet(false, true);

        String msg = "{\n" +
                "  \"method\": \"SUBSCRIBE\",\n" +
                "  \"params\": [\n" +
                "    \"ethusdt@ticker\",\n" +
                "    \"btcusdt@ticker\",\n" +
                "    \"trxusdt@ticker\"\n" +
                "  ],\n" +
                "  \"id\": 10086\n" +
                "}";
        session.sendMessage(new TextMessage(msg));
        this.session = session;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.debug("Received: {}", message.getPayload());
        try {
            JSONObject jsonObject = JSON.parseObject(message.getPayload());
            String e = jsonObject.getString("e");
            if (StringUtils.equalsIgnoreCase(e, "24hrTicker")) {
                HuobiTicker ticker = new HuobiTicker();
                ticker.setSymbol(jsonObject.getString("s").toLowerCase());
                ticker.setBid(new BigDecimal(jsonObject.getString("c")));
                ticker.setHigh(new BigDecimal(jsonObject.getString("b")));
                ticker.setLow(new BigDecimal(jsonObject.getString("a")));
                ticker.setAsk(new BigDecimal(jsonObject.getString("w")));
                ticker.setChange(new BigDecimal(jsonObject.getString("p")));
                ticker.setPoint(new BigDecimal(StringUtils.defaultIfBlank(jsonObject.getString("P"),"0")).divide(new BigDecimal(100),6, RoundingMode.HALF_UP));
    //            tokenTickersWebSocketServer.sendMessage2All(JSON.toJSONString(ticker));
                redisTemplate.opsForHash().put(RedisCacheKeys.USDT_TICKET, ticker.getSymbol(), ticker);
                TickersWebSocketHandler.sendMessage2All(JSON.toJSONString(ticker));
            } else {
                log.info("binance websocket handleTextMessage.: {}", message.getPayload());
            }
        } catch (Exception ex) {
            log.error("binance websocket handleTextMessage", ex);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.error("binance websocket afterConnectionClosed");
        running.compareAndSet(true, false);
        super.afterConnectionClosed(session, status);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("binance websocket handleTransportError", exception);
        running.compareAndSet(true, false);
        super.handleTransportError(session, exception);
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        session.sendMessage(new PingMessage(message.getPayload()));
    }

    protected void handlePingMessage(WebSocketSession session, PingMessage message) throws Exception {
        session.sendMessage(new PongMessage(message.getPayload()));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message instanceof TextMessage) {
            handleTextMessage(session, (TextMessage) message);
        } else if (message instanceof PingMessage) {
            handlePingMessage(session, (PingMessage) message);
        } else {
            super.handleMessage(session, message);
        }
    }
}
