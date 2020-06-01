package com.cloudx.server.system;

import com.cloudx.common.security.starter.annotation.EnableCloudxCloudResourceServer;
import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author chachae
 * @since 2020/4/29 17:53
 */
@EnableAsync
@SpringBootApplication
@EnableTransactionManagement
@EnableDistributedTransaction
@EnableCloudxCloudResourceServer
@MapperScan("com.cloudx.server.system.mapper")
public class ServerSystemApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(ServerSystemApplication.class)
        .web(WebApplicationType.SERVLET)
        .run(args);
  }
}

