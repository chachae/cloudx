package com.cloudx.auth.config;

import cn.hutool.core.util.StrUtil;
import com.cloudx.auth.properties.AuthProperties;
import com.cloudx.common.constant.EndpointConstant;
import com.cloudx.common.handler.CloudxAccessDeniedHandler;
import com.cloudx.common.handler.CloudxAuthExceptionEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * OAUTH 2 资源服务器配置
 *
 * @author chachae
 * @since 2020/04/25
 */
@Configuration
@EnableResourceServer
@RequiredArgsConstructor
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

  private final AuthProperties properties;
  private final CloudxAccessDeniedHandler accessDeniedHandler;
  private final CloudxAuthExceptionEntryPoint exceptionEntryPoint;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    // 匿名路径白名单
    String[] anonUrls = StrUtil.splitToArray(properties.getAnonUrl(), ',');
    http.csrf().disable()
        .requestMatchers().antMatchers(EndpointConstant.ALL)
        .and()
        .authorizeRequests()
        // 匿名访问路径放行
        .antMatchers(anonUrls).permitAll()
        // 全部需要经过认证
        .antMatchers(EndpointConstant.ALL).authenticated()
        .and().httpBasic();
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    resources.authenticationEntryPoint(exceptionEntryPoint)
        .accessDeniedHandler(accessDeniedHandler);
  }
}
