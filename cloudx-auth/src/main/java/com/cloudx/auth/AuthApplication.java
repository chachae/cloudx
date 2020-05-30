package com.cloudx.auth;

import com.cloudx.common.security.starter.annotation.EnableCloudxCloudResourceServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动器
 *
 * @author chachae
 * @since 2020/4/24 18:45
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableCloudxCloudResourceServer
@MapperScan("com.cloudx.auth.mapper")
public class AuthApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(AuthApplication.class)
        .web(WebApplicationType.SERVLET)
        .run(args);
  }
}
