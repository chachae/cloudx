package com.cloudx.auth.init;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author chachae
 * @since 2020/04/25
 */
@Component
public class StartedUpRunner implements ApplicationRunner {

  private final ConfigurableApplicationContext context;
  private final Environment environment;

  @Autowired
  public StartedUpRunner(ConfigurableApplicationContext context, Environment environment) {
    this.context = context;
    this.environment = environment;
  }

  private static void printSystemUpBanner(Environment environment) {
    String banner = "-----------------------------------------\n" +
        "服务启动成功，时间：" + LocalDateTime.now() + "\n" +
        "服务名称：" + environment.getProperty("spring.application.name") + "\n" +
        "端口号：" + environment.getProperty("server.port") + "\n" +
        "-----------------------------------------";
    System.out.println(banner);
  }

  @Override
  public void run(ApplicationArguments args) {
    if (context.isActive()) {
      printSystemUpBanner(environment);
    }
  }
}
