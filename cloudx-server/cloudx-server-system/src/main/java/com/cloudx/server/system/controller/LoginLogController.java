package com.cloudx.server.system.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cloudx.common.core.entity.QueryParam;
import com.cloudx.common.core.entity.R;
import com.cloudx.common.core.entity.system.LoginLog;
import com.cloudx.common.core.util.PageUtil;
import com.cloudx.common.core.util.SecurityUtil;
import com.cloudx.server.system.service.ILoginLogService;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志控制层
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/30 12:03
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("loginLog")
public class LoginLogController {


  private final ILoginLogService loginLogService;

  @GetMapping
  public R<Map<String, Object>> loginLogList(LoginLog loginLog, QueryParam param) {
    IPage<LoginLog> result = this.loginLogService.pageLoginLogs(loginLog, param);
    return R.ok(PageUtil.toPage(result));
  }

  @GetMapping("currentUser")
  public R<List<LoginLog>> getUserLastSevenLoginLogs() {
    String currentUsername = SecurityUtil.getCurrentUsername();
    List<LoginLog> userLastSevenLoginLogs = this.loginLogService
        .getUserLastSevenLoginLogs(currentUsername);
    return R.ok(userLastSevenLoginLogs);
  }

  @DeleteMapping("{ids}")
  @PreAuthorize("hasAuthority('loginlog:delete')")
  public void deleteLogs(@NotBlank(message = "{required}") @PathVariable String ids) {
    String[] loginLogIds = ids.split(StrUtil.COMMA);
    this.loginLogService.deleteLoginLogs(loginLogIds);
  }

}
