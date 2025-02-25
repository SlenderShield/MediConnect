package com.hungrycoders.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.function.Predicate;

@Component // Marks this class as a Spring-managed component for dependency injection
public class RouterValidator {

    /**
     * A set of endpoints that are publicly accessible and do not require authentication.
     */
    public static final Set<String> openApiEndpoints = Set.of(
            "api/auth/register",
            "api/auth/signin"// Add more public endpoints as needed
    );

    /**
     * Predicate to check if a given request is secured (requires authentication).
     *
     * @param request the incoming request.
     * @return true if the request is secured, false if it matches any open endpoint.
     */
    public Predicate<ServerHttpRequest> isSecured = request ->
            openApiEndpoints.stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
