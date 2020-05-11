package com.cloudx.server.demo.config;

import com.cloudx.common.core.constant.EndpointConstant;
import com.cloudx.common.security.starter.handler.CloudxAccessDeniedHandler;
import com.cloudx.common.security.starter.handler.CloudxAuthExceptionEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 微服务资源服务配置
 *
 * @author chachae
 * @since 2020/4/29 17:54
 */
@Configuration
@EnableResourceServer
@RequiredArgsConstructor
public class ServerDemoResourceServerConfig extends ResourceServerConfigurerAdapter {

  private final CloudxAccessDeniedHandler accessDeniedHandler;
  private final CloudxAuthExceptionEntryPoint exceptionEntryPoint;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .requestMatchers().antMatchers(EndpointConstant.ALL)
        .and()
        .authorizeRequests()
        .antMatchers("/actuator/**").permitAll()
        .antMatchers(EndpointConstant.ALL).authenticated()
        .and()
        .httpBasic();
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    resources.authenticationEntryPoint(exceptionEntryPoint)
        .accessDeniedHandler(accessDeniedHandler);
  }
}