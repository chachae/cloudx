package com.cloudx.common.core.constant;

/**
 * OAUTH 2 对外暴露端点常量池
 *
 * @author chachae
 * @since 2020/4/27 22:23
 */
public interface EndpointConstant {

  String ALL = "/**";

  String OAUTH_ALL = "/oauth/**";

  String OAUTH_AUTHORIZE = "/oauth/authorize";

  String OAUTH_CHECK_TOKEN = "/oauth/check_token";

  String OAUTH_CONFIRM_ACCESS = "/oauth/confirm_access";

  String OAUTH_TOKEN = "/oauth/token";

  String OAUTH_TOKEN_KEY = "/oauth/token_key";

  String OAUTH_ERROR = "/oauth/error";
}
