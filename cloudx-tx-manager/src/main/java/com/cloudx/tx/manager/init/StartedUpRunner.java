package com.cloudx.tx.manager.init;

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
        "server up success,current datetime is：" + LocalDateTime.now() + "\n" +
        "server application name：" + environment.getProperty("spring.application.name") + "\n" +
        "server port：" + environment.getProperty("server.port") + "\n" +
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
