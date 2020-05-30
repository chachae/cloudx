package com.cloudx.server.system.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cloudx.common.core.entity.QueryParam;
import com.cloudx.common.core.entity.R;
import com.cloudx.common.core.entity.system.Role;
import com.cloudx.common.core.util.PageUtil;
import com.cloudx.server.system.annotation.ControllerEndpoint;
import com.cloudx.server.system.service.IRoleService;
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
 * @author chachae
 * @version v1.0
 * @date 2020/5/27 22:38
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("role")
public class RoleController {

  private final IRoleService roleService;

  @GetMapping
  public R<Map<String, Object>> roleList(QueryParam param, Role role) {
    IPage<Role> roles = roleService.pageRoles(param, role);
    return R.ok(PageUtil.toPage(roles));
  }

  @GetMapping("options")
  public R<List<Role>> roles() {
    List<Role> roles = roleService.getAllRoles();
    return R.ok(roles);
  }

  @GetMapping("check/{roleName}")
  public boolean checkRoleName(@NotBlank(message = "{required}") @PathVariable String roleName) {
    Role result = this.roleService.getByName(roleName);
    return result == null;
  }

  @PostMapping
  @PreAuthorize("hasAuthority('role:add')")
  @ControllerEndpoint(operation = "新增角色", exceptionMessage = "新增角色失败")
  public void addRole(@Valid Role role) {
    this.roleService.createRole(role);
  }

  @DeleteMapping("{roleIds}")
  @PreAuthorize("hasAuthority('role:delete')")
  @ControllerEndpoint(operation = "删除角色", exceptionMessage = "删除角色失败")
  public void deleteRoles(@NotBlank(message = "{required}") @PathVariable String roleIds) {
    String[] ids = roleIds.split(StrUtil.COMMA);
    this.roleService.deleteRoles(ids);
  }

  @PutMapping
  @PreAuthorize("hasAuthority('role:update')")
  @ControllerEndpoint(operation = "更新角色", exceptionMessage = "更新角色失败")
  public void updateRole(@Valid Role role) {
    this.roleService.updateRole(role);
  }

}
