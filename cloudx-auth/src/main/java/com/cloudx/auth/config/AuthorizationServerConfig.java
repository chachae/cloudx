package com.cloudx.auth.config;

import com.cloudx.auth.properties.AuthProperties;
import com.cloudx.auth.service.impl.RedisClientDetailsService;
import com.cloudx.auth.translator.Oauth2WebResponseExceptionTranslator;
import com.cloudx.common.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * OAUTH 2 认证服务器配置
 *
 * @author chachae
 * @since 2020/04/25
 */
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  private final TokenStore tokenStore;
  private final AuthProperties properties;
  private final UserDetailsService userDetailService;
  private final AuthenticationManager authenticationManager;
  private final JwtAccessTokenConverter jwtAccessTokenConverter;
  private final RedisClientDetailsService redisClientDetailsService;
  private final Oauth2WebResponseExceptionTranslator exceptionTranslator;

  /**
   * 客户端详细信息配置
   *
   * @param clients /
   * @throws Exception /
   */
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.withClientDetails(redisClientDetailsService);
  }

  /**
   * 令牌访问端点
   *
   * @param endpoints /
   */
  @Override
  @SuppressWarnings("unchecked")
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    endpoints
        // token 存储方式
        .tokenStore(tokenStore)
        // 用户信息
        .userDetailsService(userDetailService)
        // 认证信息管理器
        .authenticationManager(authenticationManager)
        // OAUTH2 认证授权异常翻译
        .exceptionTranslator(exceptionTranslator)
        // 支持的请求方式
        .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    // 判断是否使用 JWT Token
    if (properties.getEnableJwt()) {
      endpoints.accessTokenConverter(jwtAccessTokenConverter);
    }
  }

  @Bean
  public DefaultOAuth2RequestFactory oAuth2RequestFactory() {
    return new DefaultOAuth2RequestFactory(redisClientDetailsService);
  }
}
