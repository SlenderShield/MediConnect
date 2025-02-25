package com.hungrycoders.filter;

import com.hungrycoders.config.JwtUtil;
import com.hungrycoders.config.RouterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RefreshScope // Allows dynamic reloading of properties for this component
@Component // Marks this filter as a Spring-managed component
public class AuthenticationFilter implements GatewayFilter {

    private final RouterValidator routerValidator;
    private final JwtUtil jwtUtil;

    @Autowired // Constructor-based dependency injection
    public AuthenticationFilter(RouterValidator routerValidator, JwtUtil jwtUtil) {
        this.routerValidator = routerValidator;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Filters incoming requests, validating JWT tokens for secured routes.
     *
     * @param exchange the current server exchange containing request and response.
     * @param chain    the filter chain to forward requests after processing.
     * @return a Mono<Void> indicating filter completion.
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        System.out.println(request);
        // Check if the request path requires authentication
        if (routerValidator.isSecured.test(request)) {
            if (isAuthMissing(request)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED, "Authorization header is missing.");
            }

            final String token = getAuthHeader(request);

            if (jwtUtil.isInvalid(token)) {
                return onError(exchange, HttpStatus.FORBIDDEN, "Invalid or expired token.");
            }

            updateRequest(exchange, token);
        }

        System.out.println("forwarding the request");
        return chain.filter(exchange); // Forward the request if valid
    }

    /**
     * Sends an error response with the specified HTTP status and message.
     *
     * @param exchange   the server exchange containing the response.
     * @param httpStatus the HTTP status to set.
     * @param message    the error message to include.
     * @return a Mono<Void> indicating error handling completion.
     */
    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        response.getHeaders().add("Error-Message", message);
        return response.setComplete();
    }

    /**
     * Retrieves the Authorization header from the request.
     *
     * @param request the incoming request.
     * @return the JWT token as a string.
     */
    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Missing Authorization header"));
    }

    /**
     * Checks if the Authorization header is missing.
     *
     * @param request the incoming request.
     * @return true if the Authorization header is missing, false otherwise.
     */
    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    /**
     * Updates the request with additional headers extracted from the token.
     *
     * @param exchange the current server exchange.
     * @param token    the JWT token.
     */
    private void updateRequest(ServerWebExchange exchange, String token) {
        String email = jwtUtil.getAllClaimsFromToken(token).get("email", String.class);
        exchange.getRequest().mutate()
                .header("email", email) // Add the email claim to the request headers
                .build();
    }
}
