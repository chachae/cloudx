package com.cloudx.auth.config;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.IdUtil;
import com.cloudx.auth.properties.AuthProperties;
import com.cloudx.auth.service.impl.RedisClientDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * Token 配置
 *
 * @author chachae
 * @since 2020/4/25 16:42
 */
@Configuration
@RequiredArgsConstructor
public class TokenConfig {

  private final AuthProperties properties;
  private final UserDetailsService userDetailService;
  private final RedisConnectionFactory redisConnectionFactory;
  private final RedisClientDetailsService redisClientDetailsService;

  /**
   * Token 存储策略 JWT / Redis
   *
   * @return /
   */
  @Bean
  public TokenStore tokenStore() {
    if (BooleanUtil.isTrue(properties.getEnableJwt())) {
      return new JwtTokenStore(jwtAccessTokenConverter());
    } else {
      RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
      redisTokenStore.setAuthenticationKeyGenerator(
          oAuth2Authentication -> IdUtil.fastUUID());
      return redisTokenStore;
    }
  }

  /**
   * Token 服务
   *
   * @return /
   */
  @Bean
  @Primary
  public DefaultTokenServices defaultTokenServices() {
    DefaultTokenServices tokenServices = new DefaultTokenServices();
    // token 存储策略
    tokenServices.setTokenStore(tokenStore());
    // token 刷新
    tokenServices.setSupportRefreshToken(true);
    // 客户端信息服务
    tokenServices.setClientDetailsService(redisClientDetailsService);
    return tokenServices;
  }

  /**
   * JWT Token 配置
   *
   * @return /
   */
  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
    DefaultAccessTokenConverter tokenConverter =
        (DefaultAccessTokenConverter) jwtAccessTokenConverter.getAccessTokenConverter();
    DefaultUserAuthenticationConverter userAuthenticationConverter =
        new DefaultUserAuthenticationConverter();
    userAuthenticationConverter.setUserDetailsService(userDetailService);
    tokenConverter.setUserTokenConverter(userAuthenticationConverter);
    jwtAccessTokenConverter.setSigningKey(properties.getJwtAccessKey());
    return jwtAccessTokenConverter;
  }

  /**
   * 密码模式下的 TokenGranter
   *
   * @param authenticationManager /
   * @param oAuth2RequestFactory  /
   * @return /
   */
  @Bean
  public ResourceOwnerPasswordTokenGranter resourceOwnerPasswordTokenGranter(
      AuthenticationManager authenticationManager, OAuth2RequestFactory oAuth2RequestFactory) {
    DefaultTokenServices defaultTokenServices = defaultTokenServices();
    // 检查是否开启 JWT Token
    if (BooleanUtil.isTrue(properties.getEnableJwt())) {
      defaultTokenServices.setTokenEnhancer(jwtAccessTokenConverter());
    }
    return new ResourceOwnerPasswordTokenGranter(
        authenticationManager,
        defaultTokenServices,
        redisClientDetailsService,
        oAuth2RequestFactory);
  }
}
