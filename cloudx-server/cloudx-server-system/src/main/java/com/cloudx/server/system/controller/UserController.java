package com.cloudx.server.system.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cloudx.common.core.entity.QueryParam;
import com.cloudx.common.core.entity.R;
import com.cloudx.common.core.entity.system.LoginLog;
import com.cloudx.common.core.entity.system.SystemUser;
import com.cloudx.common.core.util.PageUtil;
import com.cloudx.common.core.util.SecurityUtil;
import com.cloudx.server.system.service.ILoginLogService;
import com.cloudx.server.system.service.IUserDataPermissionService;
import com.cloudx.server.system.service.IUserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息控制层
 *
 * @author chachae
 * @since 2020/4/30 19:56
 */
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

  private final IUserService userService;
  private final IUserDataPermissionService userDeptService;
  private final ILoginLogService loginLogService;

  /**
   * 登录成功存储登录日志
   */
  @GetMapping("success")
  public void loginSuccess() {
    String currentUsername = SecurityUtil.getCurrentUsername();
    // 更新登录时间
    this.userService.updateLoginTime(currentUsername);
    LoginLog loginLog = new LoginLog();
    loginLog.setUsername(currentUsername);
    this.loginLogService.saveLoginLog(loginLog);
  }

  /**
   * Dashboard 数据统计
   *
   * @return R<Map < String, Object>>
   */
  @GetMapping("index")
  public R<Map<String, Object>> index() {
    Map<String, Object> data = new HashMap<>(5);
    // 获取系统总访问记录
    Long totalVisitCount = loginLogService.getTotalVisitCount();
    data.put("totalVisitCount", totalVisitCount);
    // 获取系统今日访问记录
    Long todayVisitCount = loginLogService.getTodayVisitCount();
    data.put("todayVisitCount", todayVisitCount);
    // 获取系统进入访问IP量
    Long todayIp = loginLogService.getTodayIp();
    data.put("todayIp", todayIp);
    // 获取近期系统访问记录
    List<Map<String, Object>> lastTenVisitCount = loginLogService.getLastTenDaysVisitCount(null);
    data.put("lastTenVisitCount", lastTenVisitCount);
    // 当前用户近七天访问记录
    SystemUser systemUser = new SystemUser();
    systemUser.setUsername(SecurityUtil.getCurrentUsername());
    List<Map<String, Object>> lastTenUserVisitCount = loginLogService
        .getLastTenDaysVisitCount(systemUser);
    data.put("lastTenUserVisitCount", lastTenUserVisitCount);
    return R.ok(data);
  }

  /**
   * 用户名检测
   *
   * @param username 用户名
   * @return boolean
   */
  @GetMapping("check/{username}")
  public boolean checkUserName(@NotBlank(message = "{required}") @PathVariable String username) {
    SystemUser result = userService.getSystemUser(username);
    return result == null;
  }

  @GetMapping
  @PreAuthorize("hasAuthority('user:view')")
  public R<Map<String, Object>> userList(QueryParam param, SystemUser user) {
    IPage<SystemUser> result = userService.pageSystemUser(param, user);
    return R.ok(PageUtil.toPage(result));
  }

  @PostMapping
  @PreAuthorize("hasAuthority('user:add')")
  public void addUser(@Valid SystemUser user) {
    this.userService.createUser(user);
  }

  @GetMapping("{userId}")
  @PreAuthorize("hasAuthority('user:update')")
  public R<String> findUserDataPermissions(
      @NotNull(message = "{required}") @PathVariable Long userId) {
    String dataPermissions = userDeptService.getByUserId(userId);
    return R.ok(dataPermissions);
  }

  @PutMapping
  @PreAuthorize("hasAuthority('user:update')")
  public void updateUser(@Valid SystemUser user) {
    this.userService.updateUser(user);
  }

  @DeleteMapping("{userIds}")
  @PreAuthorize("hasAuthority('user:delete')")
  public void deleteUsers(@NotBlank(message = "{required}") @PathVariable String userIds) {
    String[] ids = userIds.split(StrUtil.COMMA);
    this.userService.deleteUsers(ids);
  }

}
