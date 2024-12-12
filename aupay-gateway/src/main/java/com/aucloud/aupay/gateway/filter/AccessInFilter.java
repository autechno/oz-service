package com.aucloud.aupay.gateway.filter;

import com.aucloud.aupay.gateway.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AccessInFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String ip = IpUtils.getRealIpAddress(exchange.getRequest());
        // 该步骤可选。可以传递给下游服务器，用于业务处理
        log.info("filter IP: {}", ip);
        ServerHttpRequest request = exchange.getRequest().mutate()
                .header("Aupay-Nginx-Forwarded-IP", new String[]{ip})
                .build();
        return chain.filter(exchange.mutate().request(request).build());
    }


    @Override
    public int getOrder() {
        return -3;
    }
}