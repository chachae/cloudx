package com.cloudx.server.system.config;

import cn.hutool.core.util.StrUtil;
import com.cloudx.common.constant.EndpointConstant;
import com.cloudx.common.handler.CloudxAccessDeniedHandler;
import com.cloudx.common.handler.CloudxAuthExceptionEntryPoint;
import com.cloudx.server.system.properties.ServerSystemProperties;
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
public class ServerSystemResourceServerConfig extends ResourceServerConfigurerAdapter {

  private final CloudxAccessDeniedHandler accessDeniedHandler;
  private final CloudxAuthExceptionEntryPoint exceptionEntryPoint;
  private final ServerSystemProperties properties;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    // 匿名接口
    String[] anonUrls = StrUtil.splitToArray(properties.getAnonUrl(), ',');

    http.headers().frameOptions().disable()
        .and().csrf().disable()
        .requestMatchers().antMatchers(EndpointConstant.ALL)
        .and()
        .authorizeRequests()
        .antMatchers(anonUrls).permitAll()
        .antMatchers(EndpointConstant.ALL).authenticated()
        .and().httpBasic();
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    resources.authenticationEntryPoint(exceptionEntryPoint)
        .accessDeniedHandler(accessDeniedHandler);
  }
}