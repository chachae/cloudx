package com.cloudx.server.system;

import com.cloudx.common.annotation.CloudxApplication;
import com.cloudx.common.annotation.EnableCloudxServerProtect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author chachae
 * @since 2020/4/29 17:53
 */
@EnableAsync
@CloudxApplication
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.cloudx.server.system.mapper")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ServerSystemApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServerSystemApplication.class, args);
  }

}
