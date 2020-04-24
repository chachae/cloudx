package com.cloudx.auth.config;

import com.cloudx.auth.filter.ValidateCodeFilter;
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
 * WebSecurity配置
 *
 * @author chachae
 * @since 2020/04/21
 */
@Order(2)
@EnableWebSecurity
@RequiredArgsConstructor
public class CloudxSecurityConfig extends WebSecurityConfigurerAdapter {

  private final ValidateCodeFilter validateCodeFilter;
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
        .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
        .requestMatchers()
        .antMatchers(Oauth2Constant.Endpoint.OAUTH_ALL)
        .and()
        .authorizeRequests()
        .antMatchers(Oauth2Constant.Endpoint.OAUTH_ALL).authenticated()
        .and()
        .csrf().disable();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
  }
}
