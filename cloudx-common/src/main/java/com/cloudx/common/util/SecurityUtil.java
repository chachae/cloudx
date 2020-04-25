package com.cloudx.common.util;

import com.alibaba.fastjson.JSONObject;
import com.cloudx.common.entity.auth.AuthUser;
import com.cloudx.common.entity.auth.CurrentUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

/**
 * 系统信息安全工具类
 *
 * @author chachae
 * @since 2020/4/24 21:56
 */
@Slf4j
public class SecurityUtil {

  private SecurityUtil() {
  }

  private static final ObjectMapper MAPPER = new ObjectMapper();

  /**
   * 获取在线用户信息
   *
   * @return CurrentUser 当前用户信息
   */
  public static CurrentUser getCurrentUser() {
    try {
      Object principal = getAuthenticationDetails().getPrincipal();
      return JSONObject.parseObject(MAPPER.writeValueAsString(principal), CurrentUser.class);
    } catch (Exception e) {
      log.error("获取当前用户信息失败", e);
      return null;
    }
  }

  /**
   * 获取当前用户名称
   *
   * @return String 用户名
   */
  public static String getCurrentUsername() {
    Object principal = getOauth2Authentication().getPrincipal();
    if (principal instanceof AuthUser) {
      return ((AuthUser) principal).getUsername();
    }
    return (String) getOauth2Authentication().getPrincipal();
  }

  /**
   * 获取当前用户权限集
   *
   * @return Collection<GrantedAuthority>权限集
   */
  public static Collection<GrantedAuthority> getCurrentUserAuthority() {
    return getOauth2Authentication().getAuthorities();
  }

  /**
   * 获取当前令牌内容
   *
   * @return String 令牌内容
   */
  public static String getCurrentTokenValue() {
    OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) getOauth2Authentication()
        .getDetails();
    return details.getTokenValue();
  }

  private static OAuth2Authentication getOauth2Authentication() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return (OAuth2Authentication) authentication;
  }

  private static Authentication getAuthenticationDetails() {
    return getOauth2Authentication().getUserAuthentication();
  }
}
