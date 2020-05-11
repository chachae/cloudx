package com.cloudx.common.security.starter.config;

import cn.hutool.core.util.StrUtil;
import com.cloudx.common.security.starter.handler.CloudxAccessDeniedHandler;
import com.cloudx.common.security.starter.handler.CloudxAuthExceptionEntryPoint;
import com.cloudx.common.security.starter.properties.CloudxCloudSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author chachae
 */
@EnableResourceServer
@EnableAutoConfiguration(exclude = UserDetailsServiceAutoConfiguration.class)
public class CloudxCloudResourceServerConfig extends ResourceServerConfigurerAdapter {

  private CloudxCloudSecurityProperties properties;
  private CloudxAccessDeniedHandler accessDeniedHandler;
  private CloudxAuthExceptionEntryPoint exceptionEntryPoint;

  @Autowired
  public void setProperties(CloudxCloudSecurityProperties properties) {
    this.properties = properties;
  }

  @Autowired
  public void setAccessDeniedHandler(CloudxAccessDeniedHandler accessDeniedHandler) {
    this.accessDeniedHandler = accessDeniedHandler;
  }

  @Autowired
  public void setExceptionEntryPoint(CloudxAuthExceptionEntryPoint exceptionEntryPoint) {
    this.exceptionEntryPoint = exceptionEntryPoint;
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    String[] anonUrls = StrUtil.splitToArray(properties.getAnonUris(), ',');
    if (anonUrls.length == 0) {
      anonUrls = new String[]{};
    }

    http.csrf().disable()
        .requestMatchers().antMatchers(properties.getAuthUri())
        .and()
        .authorizeRequests()
        .antMatchers(anonUrls).permitAll()
        .antMatchers(properties.getAuthUri()).authenticated()
        .and()
        .httpBasic();
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    resources.authenticationEntryPoint(exceptionEntryPoint)
        .accessDeniedHandler(accessDeniedHandler);
  }
}
