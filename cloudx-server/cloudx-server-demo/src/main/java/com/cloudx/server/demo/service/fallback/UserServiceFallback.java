package com.cloudx.server.demo.service.fallback;

import com.cloudx.common.core.annotation.Fallback;
import com.cloudx.server.demo.service.IUserService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * Feign 异常回滚
 *
 * @author chachae
 * @since 2020/05/01 11:57
 */
@Slf4j
@Fallback
public class UserServiceFallback implements FallbackFactory<IUserService> {

  @Override
  public IUserService create(Throwable cause) {
    return (queryParam, user) -> {
      log.error("获取用户信息失败", cause);
      return null;
    };
  }
}