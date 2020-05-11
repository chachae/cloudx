package com.cloudx.gateway.common.controller;

import com.cloudx.common.core.base.R;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

  @RequestMapping("fallback/{name}")
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Mono<R<String>> systemFallback(@PathVariable("name") String name) {
    String response = String.format("访问%s超时或者服务不可用", name);
    return Mono.just(R.fail(response));
  }

}
