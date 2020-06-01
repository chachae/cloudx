package com.cloudx.tx.manager;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author chachae
 * @since 2020/5/1 11:31
 */
@SpringBootApplication
@EnableTransactionManagerServer
public class TxManagerApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(TxManagerApplication.class)
        .web(WebApplicationType.SERVLET)
        .run(args);
  }
}