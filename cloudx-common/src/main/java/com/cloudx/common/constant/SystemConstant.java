package com.cloudx.common.constant;

/**
 * @author chachae
 * @since 2020/4/24 18:18
 */
public class SystemConstant {

  /**
   * OAUTH2 令牌类型 https://oauth.net/2/bearer-tokens/
   */
  public static final String OAUTH2_TOKEN_TYPE = "bearer";

  /**
   * Gateway 常量
   */
  public static class GatewayConstant {

    /**
     * Gateway请求头TOKEN名称（不要有空格）
     */
    public static final String TOKEN_HEADER = "GatewayToken";

    /**
     * Gateway请求头TOKEN值
     */
    public static final String TOKEN_VALUE = "cloudx:gateway:tokenValue";
  }

}
