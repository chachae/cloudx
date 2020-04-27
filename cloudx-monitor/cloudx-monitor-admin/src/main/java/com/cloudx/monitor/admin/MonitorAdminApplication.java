package com.cloudx.monitor.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Spring-Admin 微服务监控启动器
 *
 * @author chachae
 * @since 2020/4/27 14:22
 */

@EnableAdminServer
@SpringBootApplication
@EnableDiscoveryClient
public class MonitorAdminApplication {

  public static void main(String[] args) {
    SpringApplication.run(MonitorAdminApplication.class, args);
  }

}
