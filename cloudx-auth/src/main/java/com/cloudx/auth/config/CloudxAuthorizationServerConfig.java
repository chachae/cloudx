package com.cloudx.auth.config;


import cn.hutool.core.util.IdUtil;
import com.cloudx.auth.properties.AuthProperties;
import com.cloudx.auth.service.impl.RedisClientDetailsService;
import com.cloudx.auth.translator.CloudxWebResponseExceptionTranslator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 认证服务器配置
 *
 * @author chachae
 * @since 2020/04/25
 */
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class CloudxAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailService;
  private final CloudxWebResponseExceptionTranslator exceptionTranslator;
  private final AuthProperties properties;
  private final RedisClientDetailsService redisClientDetailsService;
  private final RedisConnectionFactory redisConnectionFactory;

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.withClientDetails(redisClientDetailsService);
  }

  @Override
  @SuppressWarnings("unchecked")
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    endpoints
        .tokenStore(tokenStore())
        .userDetailsService(userDetailService)
        .authenticationManager(authenticationManager)
        .exceptionTranslator(exceptionTranslator);
    if (properties.getEnableJwt()) {
      endpoints.accessTokenConverter(jwtAccessTokenConverter());
    }
  }

  @Bean
  public TokenStore tokenStore() {
    if (properties.getEnableJwt()) {
      return new JwtTokenStore(jwtAccessTokenConverter());
    } else {
      RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
      // 解决每次生成的 token都一样的问题
      redisTokenStore.setAuthenticationKeyGenerator(
          oAuth2Authentication -> IdUtil.fastUUID());
      return redisTokenStore;
    }
  }

  @Bean
  @Primary
  public DefaultTokenServices defaultTokenServices() {
    DefaultTokenServices tokenServices = new DefaultTokenServices();

    tokenServices.setTokenStore(tokenStore());
    tokenServices.setSupportRefreshToken(true);
    tokenServices.setClientDetailsService(redisClientDetailsService);
    return tokenServices;
  }

  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
    DefaultAccessTokenConverter defaultAccessTokenConverter =
        (DefaultAccessTokenConverter) accessTokenConverter.getAccessTokenConverter();
    DefaultUserAuthenticationConverter userAuthenticationConverter =
        new DefaultUserAuthenticationConverter();
    userAuthenticationConverter.setUserDetailsService(userDetailService);
    defaultAccessTokenConverter.setUserTokenConverter(userAuthenticationConverter);
    accessTokenConverter.setSigningKey(properties.getJwtAccessKey());
    return accessTokenConverter;
  }

  @Bean
  public ResourceOwnerPasswordTokenGranter resourceOwnerPasswordTokenGranter(
      AuthenticationManager authenticationManager, OAuth2RequestFactory oAuth2RequestFactory) {
    DefaultTokenServices defaultTokenServices = defaultTokenServices();
    if (properties.getEnableJwt()) {
      defaultTokenServices.setTokenEnhancer(jwtAccessTokenConverter());
    }
    return new ResourceOwnerPasswordTokenGranter(
        authenticationManager,
        defaultTokenServices,
        redisClientDetailsService,
        oAuth2RequestFactory);
  }

  @Bean
  public DefaultOAuth2RequestFactory oAuth2RequestFactory() {
    return new DefaultOAuth2RequestFactory(redisClientDetailsService);
  }
}
