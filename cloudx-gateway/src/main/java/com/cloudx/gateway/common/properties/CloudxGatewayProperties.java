package com.cloudx.gateway.common.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author MrBird
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:cloudx-gateway.properties"})
@ConfigurationProperties(prefix = "cloudx.gateway")
public class CloudxGatewayProperties {
    /**
     * 禁止外部访问的 URI，多个值的话以逗号分隔
     */
    private String forbidRequestUri;

    private Boolean enableFlow;
}
