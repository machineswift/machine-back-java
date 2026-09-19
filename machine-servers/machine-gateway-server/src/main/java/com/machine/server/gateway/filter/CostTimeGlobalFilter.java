package com.machine.server.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * 网关请求耗时过滤器
 */
@Slf4j
@Component
public class CostTimeGlobalFilter implements GlobalFilter, Ordered {

    private static final long SLOW_THRESHOLD_MS = 2_000L;

    @Override
    public @NonNull Mono<Void> filter(@NonNull ServerWebExchange exchange,
                                      @NonNull GatewayFilterChain chain) {
        long startTime = System.nanoTime();
        return chain.filter(exchange)
                .doFinally(signalType -> {
                    long costMs = TimeUnit.NANOSECONDS.toMillis(
                            System.nanoTime() - startTime
                    );
                    logRequest(exchange, costMs, signalType);
                });
    }

    private void logRequest(ServerWebExchange exchange,
                            long costMs,
                            reactor.core.publisher.SignalType signalType) {
        ServerHttpRequest request = exchange.getRequest();
        String method = request.getMethod().name();

        String path = request.getURI().getRawPath();
        String routeId = getRouteId(exchange);
        int status = getStatus(exchange);

        if (signalType == reactor.core.publisher.SignalType.ON_ERROR) {
            log.error(
                    "[gateway] 请求异常 method={} path={} route={} status={} cost={}ms",
                    method,
                    path,
                    routeId,
                    status,
                    costMs
            );
            return;
        }

        if (costMs >= SLOW_THRESHOLD_MS) {
            log.warn(
                    "[gateway] 慢请求 method={} path={} route={} status={} cost={}ms",
                    method,
                    path,
                    routeId,
                    status,
                    costMs
            );
        } else {
            log.info(
                    "[gateway] 请求 method={} path={} route={} status={} cost={}ms",
                    method,
                    path,
                    routeId,
                    status,
                    costMs
            );
        }
    }

    private String getRouteId(ServerWebExchange exchange) {
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        return route == null ? "-" : route.getId();
    }

    private int getStatus(ServerWebExchange exchange) {
        HttpStatusCode statusCode = exchange.getResponse().getStatusCode();
        return statusCode == null
                ? -1
                : statusCode.value();
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}