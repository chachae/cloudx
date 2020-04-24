package com.cloudx.auth;

import com.cloudx.common.annotation.EnableCloudxAuthExceptionHandler;
import com.cloudx.common.annotation.EnableCloudxLettuceRedis;
import com.cloudx.common.annotation.EnableCloudxServerProtect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * 启动器
 *
 * @author chachae
 * @since 2020/4/24 18:45
 */

@EnableDiscoveryClient
@EnableCloudxLettuceRedis
@EnableCloudxAuthExceptionHandler
@EnableCloudxServerProtect
@SpringBootApplication
@MapperScan("com.cloudx.auth.dao")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthApplication.class, args);
  }

}
