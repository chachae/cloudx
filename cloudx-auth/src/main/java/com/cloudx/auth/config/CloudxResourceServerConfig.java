package com.cloudx.auth.config;

import com.cloudx.auth.properties.AuthProperties;
import com.cloudx.common.constant.Oauth2Constant;
import com.cloudx.common.handler.CloudxAccessDeniedHandler;
import com.cloudx.common.handler.CloudxAuthExceptionEntryPoint;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 资源服务器配置
 *
 * @author chachae
 * @since 2020/04/25
 */
@Configuration
@EnableResourceServer
@RequiredArgsConstructor
public class CloudxResourceServerConfig extends ResourceServerConfigurerAdapter {

  private final CloudxAccessDeniedHandler accessDeniedHandler;
  private final CloudxAuthExceptionEntryPoint exceptionEntryPoint;
  private final AuthProperties properties;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    String[] anonUrls = StringUtils
        .splitByWholeSeparatorPreserveAllTokens(properties.getAnonUrl(), ",");

    http.csrf().disable()
        .requestMatchers().antMatchers(Oauth2Constant.Endpoint.ALL)
        .and()
        .authorizeRequests()
        .antMatchers(anonUrls).permitAll()
        .antMatchers(Oauth2Constant.Endpoint.ALL).authenticated()
        .and().httpBasic();
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    resources.authenticationEntryPoint(exceptionEntryPoint)
        .accessDeniedHandler(accessDeniedHandler);
  }
}
