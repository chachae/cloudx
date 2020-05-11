package com.cloudx.gateway.common.init;

import com.cloudx.common.core.constant.ServerConstant;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author chachae
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StartedUpRunner implements ApplicationRunner {

  private final ConfigurableApplicationContext context;

  @Value("${spring.application.name:'" + ServerConstant.CLOUDX_GATEWAY + "'}")
  private String applicationName;

  @Override
  public void run(ApplicationArguments args) {
    if (context.isActive()) {
      log.info("  _   _   _   _   _   _   _   _");
      log.info(" / \\ / \\ / \\ / \\ / \\ / \\ / \\ / \\");
      log.info("( c | o | m | p | l | e | t | e )");
      log.info(" \\_/ \\_/ \\_/ \\_/ \\_/ \\_/ \\_/ \\_/");
      log.info("{} 启动完毕，时间：{}", applicationName, LocalDateTime.now());
    }
  }
}
