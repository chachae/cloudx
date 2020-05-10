package com.cloudx.apm.admin.config;

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
public class SpringAdminSecurityConfig extends WebSecurityConfigurerAdapter {

  /**
   * Spring-Admin 面板路径
   */
  private final String adminCtx;

  public SpringAdminSecurityConfig(AdminServerProperties adminServerProperties) {
    this.adminCtx = adminServerProperties.getContextPath();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
    successHandler.setTargetUrlParameter("redirectTo");

    http.headers().frameOptions().disable()
        .and()
        .authorizeRequests()
        .antMatchers(adminCtx + "/assets/**").permitAll()
        .antMatchers(adminCtx + "/login").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin().loginPage(adminCtx + "/login").successHandler(successHandler).and()
        .logout().logoutUrl(adminCtx + "/logout").and()
        //启用HTTP-Basic支持。这是Spring Boot Admin Client注册所必需的
        .httpBasic().and()
        .csrf().disable();
  }
}
