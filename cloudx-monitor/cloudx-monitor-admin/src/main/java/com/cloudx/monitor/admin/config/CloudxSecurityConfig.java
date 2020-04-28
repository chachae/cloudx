package com.cloudx.monitor.admin.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * WebSecurity配置
 *
 * @author chachae
 * @since 2020/04/27 14:26
 */
@EnableWebSecurity
public class CloudxSecurityConfig extends WebSecurityConfigurerAdapter {

  private final String ctx;

  public CloudxSecurityConfig(AdminServerProperties adminServerProperties) {
    this.ctx = adminServerProperties.getContextPath();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
    successHandler.setTargetUrlParameter("redirectTo");

    http.headers().frameOptions().disable()
        .and()
        .authorizeRequests()
        .antMatchers(ctx + "/assets/**").permitAll()
        .antMatchers(ctx + "/login").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin().loginPage(ctx + "/login").successHandler(successHandler).and()
        .logout().logoutUrl(ctx + "/logout").and()
        .httpBasic().and()
        .csrf().disable();
  }
}
