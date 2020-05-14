package com.cloudx.gateway.common.controller;

import com.cloudx.common.core.base.R;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Gateway 网关欢迎控制层
 *
 * @author chachae
 * @since 2020/5/14 16:10
 */

@RestController
public class GatewayController {

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Mono<R<String>> systemFallback() {
    return Mono.just(R.ok("欢迎访问 Gateway 网关 :)"));
  }

}
