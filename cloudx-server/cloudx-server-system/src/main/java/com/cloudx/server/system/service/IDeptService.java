package com.cloudx.server.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudx.common.core.entity.QueryParam;
import com.cloudx.common.core.entity.system.Dept;
import java.util.List;
import java.util.Map;

/**
 * 部门业务接口
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/27 22:09
 */
public interface IDeptService extends IService<Dept> {

  /**
   * 获取部门信息
   *
   * @param param param
   * @param dept  dept
   * @return 部门信息
   */
  Map<String, Object> getDepts(QueryParam param, Dept dept);

  /**
   * 获取部门列表
   *
   * @param dept  dept
   * @param param param
   * @return 部门列表
   */
  List<Dept> getDepts(Dept dept, QueryParam param);

  /**
   * 创建部门
   *
   * @param dept dept
   */
  void createDept(Dept dept);

  /**
   * 更新部门
   *
   * @param dept dept
   */
  void updateDept(Dept dept);

  /**
   * 删除部门
   *
   * @param deptIds 部门id数组
   */
  void deleteDepts(String[] deptIds);
}
