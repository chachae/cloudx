package com.cloudx.common.config;

import com.cloudx.common.interceptor.ServerProtectInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 微服务防护配置
 *
 * @author chachae
 */
public class ServerProtectConfig implements WebMvcConfigurer {

  private HandlerInterceptor serverProtectInterceptor;

  @Autowired
  public void setServerProtectInterceptor(HandlerInterceptor serverProtectInterceptor) {
    this.serverProtectInterceptor = serverProtectInterceptor;
  }

  /**
   * 密码加密 / 解密
   *
   * @return /
   */
  @Bean
  @ConditionalOnMissingBean(value = PasswordEncoder.class)
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * 网关 Token 拦截器
   *
   * @return /
   */
  @Bean
  public HandlerInterceptor serverProtectInterceptor() {
    return new ServerProtectInterceptor();
  }

  /**
   * 注册拦截器
   *
   * @param registry 拦截器注册
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(serverProtectInterceptor);
  }
}
