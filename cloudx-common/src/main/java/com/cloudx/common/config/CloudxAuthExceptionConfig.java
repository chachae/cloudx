package com.cloudx.common.config;

import com.cloudx.common.handler.CloudxAccessDeniedHandler;
import com.cloudx.common.handler.CloudxAuthExceptionEntryPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * OAUTH 2 资源访问异常翻译配置
 *
 * @author chachae
 * @since 2020/4/13 20:40
 */
public class CloudxAuthExceptionConfig {

  @Bean
  @ConditionalOnMissingBean(name = "accessDeniedHandler")
  public CloudxAccessDeniedHandler accessDeniedHandler() {
    return new CloudxAccessDeniedHandler();
  }

  @Bean
  @ConditionalOnMissingBean(name = "authenticationEntryPoint")
  public CloudxAuthExceptionEntryPoint authenticationEntryPoint() {
    return new CloudxAuthExceptionEntryPoint();
  }
}
