package com.hungrycoders.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class RemoveDuplicateHeadersFilter implements GatewayFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Remove the duplicate Access-Control-Allow-Origin header
        exchange.getResponse().getHeaders().remove(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN);
        return chain.filter(exchange);
    }
}
