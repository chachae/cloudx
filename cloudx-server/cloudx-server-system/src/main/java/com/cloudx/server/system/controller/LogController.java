package com.cloudx.server.system.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cloudx.common.core.entity.QueryParam;
import com.cloudx.common.core.entity.R;
import com.cloudx.common.core.entity.system.Log;
import com.cloudx.common.core.util.PageUtil;
import com.cloudx.server.system.service.ILogService;
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
 * 系统日志控制层
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/30 11:50
 */
@Slf4j
@RestController
@RequestMapping("log")
@RequiredArgsConstructor
public class LogController {

  private final ILogService logService;

  @GetMapping
  public R<Map<String, Object>> logList(Log log, QueryParam param) {
    IPage<Log> result = this.logService.findLogs(log, param);
    return R.ok(PageUtil.toPage(result));
  }

  @DeleteMapping("{ids}")
  @PreAuthorize("hasAuthority('log:delete')")
  public void deleteLogs(@NotBlank(message = "{required}") @PathVariable String ids) {
    String[] logIds = ids.split(StrUtil.COMMA);
    this.logService.deleteLogs(logIds);
  }
}