package com.cloudx.server.system.controller;

import cn.hutool.core.util.StrUtil;
import com.cloudx.common.core.entity.QueryParam;
import com.cloudx.common.core.entity.R;
import com.cloudx.common.core.entity.system.Dept;
import com.cloudx.server.system.annotation.ControllerEndpoint;
import com.cloudx.server.system.service.IDeptService;
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
 * 部门控制层
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/27 22:08
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dept")
@RequiredArgsConstructor
public class DeptController {

  private final IDeptService deptService;

  /**
   * 部门分页信息
   *
   * @param param 分页数据
   * @param dept  模糊搜索
   * @return 分页结果集
   */
  @GetMapping
  public R<Map<String, Object>> deptList(QueryParam param, Dept dept) {
    return R.ok(this.deptService.getDepts(param, dept));
  }

  @PostMapping
  @PreAuthorize("hasAuthority('dept:add')")
  @ControllerEndpoint(operation = "新增部门", exceptionMessage = "新增部门失败")
  public void addDept(@Valid Dept dept) {
    this.deptService.createDept(dept);
  }

  @DeleteMapping("{deptIds}")
  @PreAuthorize("hasAuthority('dept:delete')")
  @ControllerEndpoint(operation = "删除部门", exceptionMessage = "删除部门失败")
  public void deleteDepts(@NotBlank(message = "{required}") @PathVariable String deptIds) {
    String[] ids = deptIds.split(StrUtil.COMMA);
    this.deptService.deleteDepts(ids);
  }

  @PutMapping
  @PreAuthorize("hasAuthority('dept:update')")
  @ControllerEndpoint(operation = "更新部门", exceptionMessage = "更新部门失败")
  public void updateDept(@Valid Dept dept) {
    this.deptService.updateDept(dept);
  }

}
