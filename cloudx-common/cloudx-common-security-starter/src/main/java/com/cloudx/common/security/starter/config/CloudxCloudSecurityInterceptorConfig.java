package com.cloudx.common.security.starter.config;

import com.cloudx.common.security.starter.interceptor.CloudxServerProtectInterceptor;
import com.cloudx.common.security.starter.properties.CloudxCloudSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author chachae
 */
public class CloudxCloudSecurityInterceptorConfig implements WebMvcConfigurer {

  private CloudxCloudSecurityProperties properties;

  @Autowired
  public void setProperties(CloudxCloudSecurityProperties properties) {
    this.properties = properties;
  }

  @Bean
  public HandlerInterceptor cloudxServerProtectInterceptor() {
    CloudxServerProtectInterceptor interceptor = new CloudxServerProtectInterceptor();
    interceptor.setProperties(properties);
    return interceptor;
  }

  @Override
  @SuppressWarnings("all")
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(cloudxServerProtectInterceptor());
  }
}
