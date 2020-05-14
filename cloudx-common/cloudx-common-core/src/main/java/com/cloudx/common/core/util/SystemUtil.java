package com.cloudx.common.core.util;

import java.time.LocalDateTime;
import org.springframework.core.env.Environment;

/**
 * @author chachae
 * @since 2020/5/12 15:21
 */
public class SystemUtil {

  private SystemUtil() {
  }

  public static void printServerUpBanner(Environment environment) {
    String banner = "-----------------------------------------\n" +
        "server up success,current datetime is：" + LocalDateTime.now() + "\n" +
        "server application name：" + environment.getProperty("spring.application.name") + "\n" +
        "server port：" + environment.getProperty("server.port") + "\n" +
        "-----------------------------------------";
    System.out.println(banner);
  }

}
