package com.cloudx.common.security.starter.properties;

import com.cloudx.common.core.constant.EndpointConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chachae
 */
@ConfigurationProperties(prefix = "cloudx.cloud.security")
public class CloudxCloudSecurityProperties {

  /**
   * 是否开启安全配置
   */
  private Boolean enable;
  /**
   * 配置需要认证的uri，默认为所有/**
   */
  private String authUri = EndpointConstant.ALL;
  /**
   * 免认证资源路径，支持通配符 多个值时使用逗号分隔
   */
  private String anonUris;
  /**
   * 是否只能通过网关获取资源
   */
  private Boolean onlyFetchByGateway = Boolean.TRUE;

  public Boolean getEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }

  public String getAuthUri() {
    return authUri;
  }

  public void setAuthUri(String authUri) {
    this.authUri = authUri;
  }

  public String getAnonUris() {
    return anonUris;
  }

  public void setAnonUris(String anonUris) {
    this.anonUris = anonUris;
  }

  public Boolean getOnlyFetchByGateway() {
    return onlyFetchByGateway;
  }

  public void setOnlyFetchByGateway(Boolean onlyFetchByGateway) {
    this.onlyFetchByGateway = onlyFetchByGateway;
  }

  @Override
  public String toString() {
    return "CloudxCloudSecurityProperties{" +
        "enable=" + enable +
        ", authUri='" + authUri + '\'' +
        ", anonUris='" + anonUris + '\'' +
        ", onlyFetchByGateway=" + onlyFetchByGateway +
        '}';
  }
}
