package com.cloudx.gateway.common.controller;

import com.cloudx.common.core.entity.R;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 统一的网关异常回滚控制层
 *
 * @author chachae
 * @since 2020/26 22:27
 */
@RestController
public class FallbackController {

  @GetMapping("fallback/{name}")
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Mono<R<String>> systemFallback(@PathVariable("name") String name) {
    return Mono.just(R.fail(String.format("访问 %s 超时或者服务不可用", name)));
  }

}
