package com.cloudx.server.system;

import com.cloudx.common.annotation.EnableCloudxLettuceRedis;
import com.cloudx.common.annotation.EnableCloudxServerProtect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author chachae
 * @since 2020/4/29 17:53
 */

@EnableCloudxLettuceRedis
@EnableCloudxServerProtect
@SpringBootApplication
@EnableDiscoveryClient
public class ServerSystemApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServerSystemApplication.class, args);
  }

}
