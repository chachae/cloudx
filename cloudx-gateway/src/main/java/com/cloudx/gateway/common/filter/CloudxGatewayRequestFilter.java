package com.cloudx.gateway.common.filter;

import com.cloudx.common.constant.GatewayConstant;
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
 * Gateway 网关全局过滤器
 *
 * @author chachae
 * @since 2020/04/21 13:30
 */
@Slf4j
@Order(0)
@Component
@RequiredArgsConstructor
public class CloudxGatewayRequestFilter implements GlobalFilter {

  /**
   * 将 Gateway Token 经过base64 编码后加入请求头中
   *
   * @param exchange 请求响应交互的契约，提供对HTTP请求和响应的访问， 并公开额外服务器端处理相关属性
   * @param chain    /
   * @return /
   */
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    byte[] token = Base64Utils.encode((GatewayConstant.TOKEN_VALUE).getBytes());
    ServerHttpRequest build = exchange.getRequest().mutate()
        .header(GatewayConstant.TOKEN_HEADER, new String(token)).build();
    ServerWebExchange newExchange = exchange.mutate().request(build).build();
    return chain.filter(newExchange);
  }
}
