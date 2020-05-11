package com.cloudx.server.demo;

import com.cloudx.common.security.starter.annotation.EnableCloudxCloudResourceServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author chachae
 * @since 2020/5/1 11:31
 */


@EnableAsync
@EnableFeignClients
@SpringBootApplication
@EnableCloudxCloudResourceServer
@EnableTransactionManagement
@MapperScan("com.cloudx.server.system.mapper")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ServerDemoApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(ServerDemoApplication.class)
        .web(WebApplicationType.SERVLET)
        .run(args);
  }
}