package com.example.apigateway.filter;

import java.time.OffsetDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class RequestLoggingGlobalFilter implements GlobalFilter, Ordered {
    private static final Logger log =
            LoggerFactory.getLogger(RequestLoggingGlobalFilter.class);


    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {
        log.info("Time={} Method={} URI={}", OffsetDateTime.now(),
                exchange.getRequest().getMethod(),
                exchange.getRequest().getURI());
        return chain.filter(exchange);
    }
    @Override
    public int getOrder() { return Ordered.HIGHEST_PRECEDENCE; }
}