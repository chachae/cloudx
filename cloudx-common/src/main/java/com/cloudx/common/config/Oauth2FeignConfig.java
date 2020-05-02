package com.cloudx.common.config;

import com.cloudx.common.constant.GatewayConstant;
import com.cloudx.common.constant.SystemConstant;
import com.cloudx.common.util.SecurityUtil;
import com.google.common.net.HttpHeaders;
import feign.RequestInterceptor;
import java.nio.charset.StandardCharsets;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Base64Utils;

/**
 * OAuth2.0 Feign配置
 *
 * @author chachae
 * @since 2020/4/18 17:11
 */
public class Oauth2FeignConfig {

  @Bean
  public RequestInterceptor oauth2FeignRequestInterceptor() {
    return template -> {
      // 请求头中添加 Gateway Token
      String gatewayToken = new String(Base64Utils.encode(GatewayConstant.TOKEN_VALUE.getBytes(
          StandardCharsets.UTF_8)));
      template.header(GatewayConstant.TOKEN_HEADER, gatewayToken);
      // 请求头中添加原请求头中的 token
      String jwtToken = SecurityUtil.getCurrentTokenValue();
      // bearer 前缀连接 token 可以不加空格
      template.header(HttpHeaders.AUTHORIZATION, SystemConstant.OAUTH2_TOKEN_TYPE + jwtToken);
    };
  }
}
