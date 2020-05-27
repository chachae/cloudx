package com.cloudx.server.system.controller;

import cn.hutool.core.util.StrUtil;
import com.cloudx.common.core.entity.R;
import com.cloudx.common.core.entity.router.VueRouter;
import com.cloudx.common.core.entity.system.Menu;
import com.cloudx.server.system.service.IMenuService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/5/27 11:15
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {

  private final IMenuService menuService;

  @GetMapping("/{username}")
  public R<Map<String, Object>> getUserRouters(
      @NotBlank(message = "{required}") @PathVariable String username) {
    Map<String, Object> result = new HashMap<>(2);
    List<VueRouter<Menu>> userRouters = this.menuService.getUserRouters(username);
    String userPermissions = this.menuService.findUserPermissions(username);
    String[] permissionArray = new String[0];
    if (StrUtil.isNotBlank(userPermissions)) {
      permissionArray = StrUtil.splitToArray(userPermissions, ',');
    }
    result.put("routes", userRouters);
    result.put("permissions", permissionArray);
    return R.ok(result);
  }

  @GetMapping
  public R<Map<String, Object>> menuList(Menu menu) {
    Map<String, Object> menus = this.menuService.findMenus(menu);
    return R.ok(menus);
  }

  @GetMapping("/permissions")
  public String findUserPermissions(String username) {
    return this.menuService.findUserPermissions(username);
  }

}
