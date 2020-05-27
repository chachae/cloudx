package com.cloudx.gateway.common.controller;

import com.cloudx.common.core.entity.R;
import org.springframework.web.bind.annotation.GetMapping;
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
  public Mono<R<String>> gatewayIndex() {
    return Mono.just(R.ok("欢迎访问 Gateway 网关 :)"));
  }

}
