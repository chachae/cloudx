package com.cloudx.server.system.controller;

import com.cloudx.common.core.entity.QueryParam;
import com.cloudx.common.core.entity.R;
import com.cloudx.common.core.entity.system.Dept;
import com.cloudx.server.system.service.IDeptService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
}
