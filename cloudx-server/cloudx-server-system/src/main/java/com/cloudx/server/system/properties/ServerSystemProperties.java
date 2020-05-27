package com.cloudx.server.system.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author chachae
 * @since 2020/4/30 20:12
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:cloudx-server-system.properties"})
@ConfigurationProperties(prefix = "cloudx.server.system")
public class ServerSystemProperties {

  /**
   * 免认证 URI，多个值的话以逗号分隔
   */
  private String anonUrls;
  /**
   * 批量插入当批次可插入的最大值
   */
  private Integer batchInsertMaxNum = 1000;
}
