package com.cloudx.server.demo.service;

import com.cloudx.common.core.constant.ServerConstant;
import com.cloudx.common.core.entity.QueryParam;
import com.cloudx.common.core.entity.R;
import com.cloudx.common.core.entity.system.SystemUser;
import com.cloudx.server.demo.service.fallback.UserServiceFallback;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign客户端
 *
 * @author chachae
 * @since 2020/05/01 12:00
 */
@FeignClient(value = ServerConstant.CLOUDX_SERVER_SYSTEM, contextId = "userServiceClient", fallbackFactory = UserServiceFallback.class)
public interface IUserService {

  /**
   * 远程调用
   *
   * @param queryParam queryRequest
   * @param user       user
   * @return R<Map < String, Object>>
   */
  @GetMapping("user/page")
  R<Map<String, Object>> pageUser(@RequestParam("queryParam") QueryParam queryParam,
      @RequestParam("user") SystemUser user);
}
