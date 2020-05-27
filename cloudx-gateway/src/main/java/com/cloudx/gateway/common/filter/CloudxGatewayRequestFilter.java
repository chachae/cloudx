package com.cloudx.gateway.common.filter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.cloudx.common.core.constant.GatewayConstant;
import com.cloudx.common.core.entity.R;
import com.cloudx.common.core.util.HttpUtil;
import com.cloudx.gateway.common.properties.CloudxGatewayProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Base64Utils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Gateway 网关全局过滤器
 * <p>
 * Gateway 网关接入 Sentinel 作为微服务的流量控制组件，其 {@link SentinelGatewayFilter} 内实现 了{@link
 * org.springframework.cloud.gateway.filter.GlobalFilter} 接口，本网关采用自定义 的 Gateway Token 作为微服务调用 /
 * 远程调用的请求来自网关转发的凭证，需要依赖全局过滤器，同时需要 Sentinel 的过滤器来实现簇点链路流控，因此需通过继承 Sentinel 的过滤器以此获得其所有实现，并重写 {@link
 * SentinelGatewayFilter#filter} 方法实现自定义请求头的规则，方法内调用用父级的实现完成 请求在网关内的过滤和禁止访问路径的拦截。（后面使用 MongoDB
 * 实现黑白名单控制）
 *
 * @author chachae
 * @since 2020/04/21 13:30
 */
@Slf4j
@Configuration
@Order(0)
@RequiredArgsConstructor
public class CloudxGatewayRequestFilter extends SentinelGatewayFilter {

  private final CloudxGatewayProperties properties;
  private final AntPathMatcher pathMatcher = new AntPathMatcher();

  /**
   * 将 Gateway Token 经过base64 编码后加入请求头中
   *
   * @param exchange 请求响应交互的契约，提供对HTTP请求和响应的访问， 并公开额外服务器端处理相关属性
   * @param chain    /
   * @return /
   */
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    if (isForbid(exchange)) {
      return HttpUtil
          .makeWebFluxResponse(exchange.getResponse(), MediaType.APPLICATION_JSON_VALUE,
              HttpStatus.FORBIDDEN, R.fail("禁止访问"));
    }
    ServerHttpRequest build = exchange.getRequest().mutate()
        .header(GatewayConstant.TOKEN_HEADER,
            new String(Base64Utils.encode((GatewayConstant.TOKEN_VALUE).getBytes()))).build();
    ServerWebExchange newExchange = exchange.mutate().request(build).build();
    if (properties.getEnableFlow()) {
      return super.filter(newExchange, chain);
    } else {
      return chain.filter(newExchange);
    }
  }

  private boolean isForbid(ServerWebExchange exchange) {
    String[] forbidUri = StrUtil.splitToArray(properties.getForbidRequestUri(), ',');
    for (String pattern : forbidUri) {
      if (pathMatcher.match(pattern, exchange.getRequest().getPath().value())) {
        return true;
      }
    }
    return false;
  }
}
