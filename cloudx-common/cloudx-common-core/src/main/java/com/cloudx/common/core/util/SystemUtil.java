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
        "服务启动成功，时间：" + DateUtil
        .formatFullTime(LocalDateTime.now(), DateUtil.FULL_TIME_SPLIT_PATTERN) + "\n" +
        "服务名称：" + environment.getProperty("spring.application.name") + "\n" +
        "端口号：" + environment.getProperty("server.port") + "\n" +
        "-----------------------------------------";
    System.out.println(banner);
  }

}
