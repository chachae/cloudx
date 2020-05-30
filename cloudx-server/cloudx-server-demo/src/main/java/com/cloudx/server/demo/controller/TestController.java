package com.cloudx.server.demo.controller;

import com.cloudx.common.core.entity.QueryParam;
import com.cloudx.common.core.entity.R;
import com.cloudx.common.core.entity.system.SystemUser;
import com.cloudx.common.core.exception.ApiException;
import com.cloudx.common.core.util.HttpUtil;
import com.cloudx.common.core.util.SecurityUtil;
import com.cloudx.server.demo.service.IUserService;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试用控制层
 *
 * @author chachae
 * @since 2020/5/1 11:57
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
public class TestController {

  private final IUserService userService;

  private final AtomicBoolean cut = new AtomicBoolean(false);

  /**
   * 用于演示 Feign调用受保护的远程方法
   */
  @GetMapping("user/page")
  public R<Map<String, Object>> pageRemoteUsers(QueryParam queryParam, SystemUser user) {
    if (!cut.get()) {
      cut.compareAndSet(false, true);
      throw new ApiException("测试 Feign 异常回滚");
    } else {
      cut.compareAndSet(true, false);
    }
    return userService.pageUser(queryParam, user);
  }

  /**
   * 获取当前用户信息
   */
  @GetMapping("user")
  public Map<String, Object> currentUser(HttpServletRequest request) {
    Map<String, Object> map = new HashMap<>(5);
    map.put("currentUser", SecurityUtil.getCurrentUser());
    map.put("currentUsername", SecurityUtil.getCurrentUsername());
    map.put("currentUserAuthority", SecurityUtil.getCurrentUserAuthority());
    map.put("currentTokenValue", SecurityUtil.getCurrentTokenValue());
    map.put("currentRequestIpAddress", HttpUtil.getIpAddress());
    return map;
  }
}
