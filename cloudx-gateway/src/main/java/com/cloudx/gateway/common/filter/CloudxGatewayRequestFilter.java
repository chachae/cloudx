package com.cloudx.gateway.common.filter;

import com.cloudx.common.constant.SystemConstant.GatewayConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author chachae
 */
@Slf4j
@Component
@Order(0)
@RequiredArgsConstructor
public class CloudxGatewayRequestFilter implements GlobalFilter {

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    byte[] token = Base64Utils.encode((GatewayConstant.TOKEN_VALUE).getBytes());
    String[] headerValues = {new String(token)};
    ServerHttpRequest build = exchange.getRequest().mutate()
        .header(GatewayConstant.TOKEN_HEADER, headerValues).build();
    ServerWebExchange newExchange = exchange.mutate().request(build).build();
    return chain.filter(newExchange);
  }
}
