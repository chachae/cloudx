package com.cloudx.common.core.constant;

/**
 * OAUTH 2 认证模式常量池
 *
 * @author chachae
 * @since 2020/4/27 22:24
 */
public interface GrantTypeConstant {


  /**
   * 刷新模式
   */
  String REFRESH_TOKEN = "refresh_token";
  /**
   * 授权码模式
   */
  String AUTHORIZATION_CODE = "authorization_code";
  /**
   * 客户端模式
   */
  String CLIENT_CREDENTIALS = "client_credentials";
  /**
   * 密码模式
   */
  String PASSWORD = "password";
  /**
   * 简化模式
   */
  String IMPLICIT = "implicit";
}
