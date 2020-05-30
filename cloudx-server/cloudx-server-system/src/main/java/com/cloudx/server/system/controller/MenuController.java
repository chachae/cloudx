package com.cloudx.server.system.controller;

import cn.hutool.core.util.StrUtil;
import com.cloudx.common.core.entity.R;
import com.cloudx.common.core.entity.router.VueRouter;
import com.cloudx.common.core.entity.system.Menu;
import com.cloudx.server.system.annotation.ControllerEndpoint;
import com.cloudx.server.system.service.IMenuService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单路由控制层
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/27 11:15
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("menu")
public class MenuController {

  private final IMenuService menuService;

  @GetMapping("{username}")
  public R<Map<String, Object>> getUserRouters(
      @NotBlank(message = "{required}") @PathVariable String username) {
    Map<String, Object> result = new HashMap<>(2);
    List<VueRouter<Menu>> userRouters = this.menuService.getUserRouters(username);
    String userPermissions = this.menuService.getUserPermissions(username);
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

  @GetMapping("permissions")
  public String findUserPermissions(String username) {
    return this.menuService.getUserPermissions(username);
  }

  @PostMapping
  @PreAuthorize("hasAuthority('menu:add')")
  @ControllerEndpoint(operation = "新增菜单", exceptionMessage = "新增菜单失败")
  public void addMenu(@Valid Menu menu) {
    this.menuService.createMenu(menu);
  }

  @DeleteMapping("{menuIds}")
  @PreAuthorize("hasAuthority('menu:delete')")
  @ControllerEndpoint(operation = "删除角色", exceptionMessage = "删除菜单失败")
  public void deleteMenus(@NotBlank(message = "{required}") @PathVariable String menuIds) {
    String[] ids = menuIds.split(StrUtil.COMMA);
    this.menuService.deleteMeuns(ids);
  }

  @PutMapping
  @PreAuthorize("hasAuthority('menu:update')")
  @ControllerEndpoint(operation = "更新角色", exceptionMessage = "更新菜单失败")
  public void updateMenu(@Valid Menu menu) {
    this.menuService.updateMenu(menu);
  }

}
