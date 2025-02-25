package com.hungrycoders.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Log the incoming request details
        logger.info("Request Method: {}, Request URI: {}",
                exchange.getRequest().getMethod(),
                exchange.getRequest().getURI());

        // Continue the filter chain and log the outgoing response details
        return chain.filter(exchange).doFinally(signalType ->
                logger.info("Response Status Code: {}", exchange.getResponse().getStatusCode()));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE; // Ensures this filter runs last
    }
}
