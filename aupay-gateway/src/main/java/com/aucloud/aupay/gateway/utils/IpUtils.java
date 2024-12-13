package com.aucloud.aupay.gateway.utils;


import org.springframework.http.server.reactive.ServerHttpRequest;

public class IpUtils {

    /**
     * 获取真实客户端IP
     * @param serverHttpRequest
     * @return
     */
    public static String getRealIpAddress(ServerHttpRequest serverHttpRequest) {
        String ip = serverHttpRequest.getHeaders().getFirst("Aupay-Nginx-Forwarded-IP");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if(ip.contains(",")){
                ip = ip.split(",")[0];
            }
        } else {
            ip = serverHttpRequest.getRemoteAddress().getAddress().getHostAddress();
        }
        return ip;
    }

}