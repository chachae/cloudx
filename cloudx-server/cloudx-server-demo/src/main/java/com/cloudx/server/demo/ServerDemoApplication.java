package com.cloudx.server.demo;

import com.cloudx.common.security.starter.annotation.EnableCloudxCloudResourceServer;
import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author chachae
 * @since 2020/5/1 11:31
 */


@EnableFeignClients
@SpringBootApplication
@EnableTransactionManagement
@EnableDistributedTransaction
@EnableCloudxCloudResourceServer
public class ServerDemoApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(ServerDemoApplication.class)
        .web(WebApplicationType.SERVLET)
        .run(args);
  }
}