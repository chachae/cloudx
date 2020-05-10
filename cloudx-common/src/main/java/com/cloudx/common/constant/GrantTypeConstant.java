package com.cloudx.common.constant;

/**
 * OAUTH 2 认证模式常量池
 *
 * @author chachae
 * @since 2020/4/27 22:24
 */
public class GrantTypeConstant {

  private GrantTypeConstant() {
  }

  /**
   * 刷新模式
   */
  public static final String REFRESH_TOKEN = "refresh_token";
  /**
   * 授权码模式
   */
  public static final String AUTHORIZATION_CODE = "authorization_code";
  /**
   * 客户端模式
   */
  public static final String CLIENT_CREDENTIALS = "client_credentials";
  /**
   * 密码模式
   */
  public static final String PASSWORD = "password";
  /**
   * 简化模式
   */
  public static final String IMPLICIT = "implicit";
}
