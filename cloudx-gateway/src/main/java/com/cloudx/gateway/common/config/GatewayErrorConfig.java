package com.cloudx.gateway.common.config;

import com.cloudx.gateway.common.handler.CloudxGatewayExceptionHandler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

/**
 * 网关异常配置
 *
 * @author chachae
 * @since 2020/04/26 18:02
 */
@Configuration
@RequiredArgsConstructor
public class GatewayErrorConfig {

  private final ServerProperties serverProperties;
  private final ApplicationContext applicationContext;
  private final ResourceProperties resourceProperties;
  private final List<ViewResolver> viewResolvers;
  private final ServerCodecConfigurer serverCodecConfigurer;

  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public ErrorWebExceptionHandler errorWebExceptionHandler(ErrorAttributes errorAttributes) {
    CloudxGatewayExceptionHandler exceptionHandler = new CloudxGatewayExceptionHandler(
        errorAttributes, resourceProperties, serverProperties.getError(), applicationContext);
    exceptionHandler.setViewResolvers(viewResolvers);
    exceptionHandler.setMessageWriters(serverCodecConfigurer.getWriters());
    exceptionHandler.setMessageReaders(serverCodecConfigurer.getReaders());
    return exceptionHandler;
  }
}
