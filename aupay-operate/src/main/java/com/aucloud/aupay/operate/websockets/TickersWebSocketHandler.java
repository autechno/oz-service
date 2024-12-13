package com.aucloud.aupay.operate.websockets;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class TickersWebSocketHandler extends TextWebSocketHandler {

    public TickersWebSocketHandler() {
        //定时清理掉线的session 长期无心跳的session
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    keepalived();
                } catch (Exception e) {
                    log.error("keepalived error", e);
                }
            }
        }, 60000, 60000);
    }

    /**
     * 存储sessionId和webSocketSession
     * 需要注意的是，webSocketSession没有提供无参构造，不能进行序列化，也就不能通过redis存储
     * 在分布式系统中，要想别的办法实现webSocketSession共享
     */
    private static final Map<String, SessionWrapper> sessionMap = new ConcurrentHashMap<>();

    private static final Queue<SessionWrapper> queue = new PriorityQueue<>(Comparator.comparing(SessionWrapper::getUpateTime));


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 获取参数
        String sid = String.valueOf(session.getAttributes().get("sid"));
        SessionWrapper sessionWrapper = new SessionWrapper(session, session.getId(), System.currentTimeMillis());
        sessionMap.put(session.getId(), sessionWrapper);
        queue.add(sessionWrapper);
        log.info("###############  [ws : 连接成功, sid:{}]  ###############", sid);
        super.afterConnectionEstablished(session);
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        log.debug("接收到客户端消息2: {}", message.getPayload());
        refleshSession(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.debug("接收到客户端消息: {}", message.getPayload());
        refleshSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.error("链接已断开，sid:{}", session.getId());
        removeSession(session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("链接已断开2，sid:{}", session.getId() ,exception);
        removeSession(session);
    }

    private void refleshSession(WebSocketSession session) {
        String id = session.getId();
        if (!sessionMap.containsKey(id)) {
            SessionWrapper sessionWrapper = new SessionWrapper(session,session.getId(), System.currentTimeMillis());
            sessionMap.put(session.getId(), sessionWrapper);
            queue.add(sessionWrapper);
        } else {
            SessionWrapper sessionWrapper = sessionMap.get(id);
            sessionWrapper.setUpateTime(System.currentTimeMillis());
        }
    }
    private static void removeSession(WebSocketSession session) {
        try {
            SessionWrapper remove = sessionMap.remove(session.getId());
            queue.remove(remove);
            session.close();
        } catch (IOException e) {
            log.error("",e);
        }
    }

    public static void sendMessage2All(String message) {
        try {
            sessionMap.values().forEach(wrapper -> {
                WebSocketSession session1 = wrapper.getSession();
                if (session1.isOpen()) {
                    try {
                        session1.sendMessage(new TextMessage(message));
                    } catch (IOException e) {
                        log.error("发送消息异常", e);
                    }
                } else {
                    removeSession(session1);
                }
            });
        } catch (Exception e) {
            log.error("发送消息异常2", e);
        }
    }

//    @Scheduled(fixedDelay = 60000)
    private void keepalived(){
        while (!queue.isEmpty()) {
            SessionWrapper peek = queue.peek();
            long upateTime = peek.getUpateTime();
            if (upateTime < (System.currentTimeMillis() - 5*60*1000)) {
                //过期
                queue.remove(peek);
                sessionMap.remove(peek.getSid());
                try {
                    peek.getSession().close();
                } catch (IOException e) {
                    log.error("关闭链接异常", e);
                }
            } else {
                break;
            }
        }
    }

    @Data
    protected static class SessionWrapper {
        private WebSocketSession session;
        private String sid;
        private Long upateTime;

        public SessionWrapper(WebSocketSession session, String sid, long upateTime) {
            this.session = session;
            this.sid = sid;
            this.upateTime = upateTime;
        }
    }
}
