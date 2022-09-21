package com.example.webflux.config.interceptors;

import lombok.extern.java.Log;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.RequestPath;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Log
public class LogRequestInterceptor implements WebFilter {
    private static final String logTemplate = "Request with path: %s and method: %s, responded with status %d";

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        return webFilterChain.filter(serverWebExchange).doFinally(signalType -> {
            RequestPath path = serverWebExchange.getRequest().getPath();
            HttpMethod method = serverWebExchange.getRequest().getMethod();
            Integer statusCode = serverWebExchange.getResponse().getRawStatusCode();

            log.info(String.format(logTemplate, path, method, statusCode));
        });
    }
}
