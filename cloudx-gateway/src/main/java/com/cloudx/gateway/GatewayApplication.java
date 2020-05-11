package com.cloudx.gateway;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Gateway 网关启动器
 * <p>
 * Gateway 网关使用 WebFlux 编程，不需要引入 spring web 依赖，本系统的「cloudx-common」 通用依赖存在该依赖，引用时需要进行依赖排除。除此之外，本网关使用
 * Sentinel 服务流量控制组 件，全程需以 WebFlux 编程作为实践基础，不熟悉的请阅读提供的参考文章。
 *
 * @author chachae
 * @see <a href="https://juejin.im/post/5dd1f6fdf265da0bb854bc5d">🔥外行人都能看懂的WebFlux</a>
 * @since 2020/4/26 13:33
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(GatewayApplication.class)
        .web(WebApplicationType.REACTIVE)
        .run(args);
  }
}
