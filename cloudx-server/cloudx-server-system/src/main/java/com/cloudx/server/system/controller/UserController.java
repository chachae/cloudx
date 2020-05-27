package com.cloudx.server.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cloudx.common.core.entity.QueryParam;
import com.cloudx.common.core.entity.R;
import com.cloudx.common.core.entity.dto.SystemUserDTO;
import com.cloudx.common.core.entity.system.LoginLog;
import com.cloudx.common.core.entity.system.SystemUser;
import com.cloudx.common.core.util.PageUtil;
import com.cloudx.common.core.util.SecurityUtil;
import com.cloudx.server.system.service.ILoginLogService;
import com.cloudx.server.system.service.IUserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
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
  private final ILoginLogService loginLogService;

  /**
   * 分页查询用户信息
   *
   * @param param 分页条件
   * @param user  模糊搜索条件
   * @return 用户数据
   */
  @GetMapping
  @PreAuthorize("hasAuthority('user:view')")
  public R<Map<String, Object>> userList(QueryParam param, SystemUser user) {
    IPage<SystemUserDTO> result = userService.pageSystemUser(param, user);
    return R.ok(PageUtil.toPage(result));
  }


  /**
   * 登录成功存储登录日志
   *
   * @param request 请求对象
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


  @GetMapping("check/{userName}")
  public boolean checkUserName(@PathVariable("userName") String userName) {
    SystemUser result = userService.getSystemUser(userName);
    return result != null;
  }

  @PostMapping
  @PreAuthorize("hasAuthority('user:add')")
  public void add(@Valid SystemUserDTO user) {
    userService.insert(user);
  }

  @PutMapping("{userId}")
  @PreAuthorize("hasAuthority('user:update')")
  public void update(@PathVariable("userId") Long id, @Valid SystemUserDTO user) {
    userService.update(id, user);
  }

  @DeleteMapping("{userId}")
  @PreAuthorize("hasAuthority('user:delete')")
  public void delete(@PathVariable("userId") Long id) {
    userService.delete(id);
  }

}
