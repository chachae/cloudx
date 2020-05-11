package com.cloudx.common.security.starter.config;

import com.cloudx.common.core.constant.GatewayConstant;
import com.cloudx.common.core.constant.SystemConstant;
import com.cloudx.common.core.util.SecurityUtil;
import com.cloudx.common.security.starter.handler.CloudxAccessDeniedHandler;
import com.cloudx.common.security.starter.handler.CloudxAuthExceptionEntryPoint;
import com.cloudx.common.security.starter.properties.CloudxCloudSecurityProperties;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Base64Utils;

/**
 * @author chachae
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(CloudxCloudSecurityProperties.class)
@ConditionalOnProperty(value = "cloudx.cloud.security.enable", havingValue = "true", matchIfMissing = true)
public class CloudxCloudSecurityAutoConfig {

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

  @Bean
  @ConditionalOnMissingBean(value = PasswordEncoder.class)
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CloudxCloudSecurityInterceptorConfig cloudxCloudSecurityInterceptorConfigure() {
    return new CloudxCloudSecurityInterceptorConfig();
  }


  @Bean
  public RequestInterceptor oauth2FeignRequestInterceptor() {
    return requestTemplate -> {
      String gatewayToken = new String(Base64Utils.encode(GatewayConstant.TOKEN_VALUE.getBytes()));
      requestTemplate.header(GatewayConstant.TOKEN_HEADER, gatewayToken);
      String authorizationToken = SecurityUtil.getCurrentTokenValue();
      requestTemplate
          .header(HttpHeaders.AUTHORIZATION, SystemConstant.OAUTH2_TOKEN_TYPE + authorizationToken);
    };
  }
}
