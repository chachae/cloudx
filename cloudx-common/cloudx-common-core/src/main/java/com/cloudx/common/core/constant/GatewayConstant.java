package com.cloudx.common.core.constant;

/**
 * Gateway 网关常量池
 *
 * @author chachae
 * @since 2020/4/27 22:20
 */
public interface GatewayConstant {

  /**
   * Gateway请求头TOKEN名称（不要有空格）
   */
  String TOKEN_HEADER = "GatewayToken";

  /**
   * Gateway请求头TOKEN值
   */
  String TOKEN_VALUE = "cloudx:gateway:accessToken";
}
