package com.cloudx.auth.config;

import com.cloudx.auth.filter.CaptchaFilter;
import com.cloudx.common.constant.Oauth2Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * WebSecurity 配置
 *
 * @author chachae
 * @since 2020/04/21
 */
@Order(2)
@EnableWebSecurity
@RequiredArgsConstructor
public class CloudxSecurityConfig extends WebSecurityConfigurerAdapter {

  private final CaptchaFilter captchaFilter;
  private final UserDetailsService userDetailService;
  private final PasswordEncoder passwordEncoder;

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        //访问资源之前过滤密码模式的认证授权
        .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
        .requestMatchers()
        .antMatchers(Oauth2Constant.Endpoint.OAUTH_ALL)
        .and()
        .authorizeRequests()
        // oAuth 对外暴露接口全部需要认证
        .antMatchers(Oauth2Constant.Endpoint.OAUTH_ALL).authenticated()
        .and()
        .csrf().disable();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
  }
}
