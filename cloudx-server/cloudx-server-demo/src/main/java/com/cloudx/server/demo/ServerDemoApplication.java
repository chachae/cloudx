package com.cloudx.server.demo;

import com.cloudx.common.annotation.CloudxApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @author chachae
 * @since 2020/5/1 11:31
 */

@CloudxApplication
@EnableFeignClients
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ServerDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServerDemoApplication.class, args);
  }
}
