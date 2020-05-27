package com.cloudx.server.system.controller;

import com.cloudx.common.core.entity.R;
import com.cloudx.common.core.entity.system.Role;
import com.cloudx.server.system.service.IRoleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping("options")
  public R<List<Role>> roles() {
    List<Role> roles = roleService.getAllRoles();
    return R.ok(roles);
  }

}
