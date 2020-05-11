package com.cloudx.common.redis.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chachae
 * @since 2020/05/11 22:31
 */
@ConfigurationProperties(prefix = "cloudx.lettuce.redis")
public class CloudxLettuceRedisProperties {

  /**
   * 是否开启Lettuce Redis
   */
  private Boolean enable = true;

  public Boolean getEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }

  @Override
  public String toString() {
    return "CloudxLettuceRedisProperties{" +
        "enable=" + enable +
        '}';
  }
}
