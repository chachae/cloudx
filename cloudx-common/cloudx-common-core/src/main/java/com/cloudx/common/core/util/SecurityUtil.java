package com.cloudx.common.core.util;

import com.cloudx.common.core.entity.auth.AuthUser;
import com.cloudx.common.core.entity.auth.CurrentUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collection;
import java.util.LinkedHashMap;
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
      LinkedHashMap<String, Object> authenticationDetails = getAuthenticationDetails();
      Object principal = authenticationDetails.get("principal");
      return MAPPER.readValue(MAPPER.writeValueAsString(principal), CurrentUser.class);
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
    try {
      OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) getOauth2Authentication()
          .getDetails();
      return details.getTokenValue();
    } catch (Exception ignore) {
      return null;
    }
  }

  private static OAuth2Authentication getOauth2Authentication() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return (OAuth2Authentication) authentication;
  }

  @SuppressWarnings("all")
  private static LinkedHashMap<String, Object> getAuthenticationDetails() {
    return (LinkedHashMap<String, Object>) getOauth2Authentication().getUserAuthentication()
        .getDetails();
  }
}
