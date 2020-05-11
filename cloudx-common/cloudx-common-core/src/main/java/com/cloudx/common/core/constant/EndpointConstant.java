package com.cloudx.common.core.constant;

/**
 * OAUTH 2 对外暴露端点常量池
 *
 * @author chachae
 * @since 2020/4/27 22:23
 */
public class EndpointConstant {

  private EndpointConstant() {
  }

  public static final String ALL = "/**";

  public static final String OAUTH_ALL = "/oauth/**";

  public static final String OAUTH_AUTHORIZE = "/oauth/authorize";

  public static final String OAUTH_CHECK_TOKEN = "/oauth/check_token";

  public static final String OAUTH_CONFIRM_ACCESS = "/oauth/confirm_access";

  public static final String OAUTH_TOKEN = "/oauth/token";

  public static final String OAUTH_TOKEN_KEY = "/oauth/token_key";

  public static final String OAUTH_ERROR = "/oauth/error";
}
