package com.cloudx.server.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudx.common.core.entity.QueryParam;
import com.cloudx.common.core.entity.system.Role;
import java.util.List;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/5/27 22:39
 */
public interface IRoleService extends IService<Role> {


  /**
   * 获取所有角色
   *
   * @return 角色列表
   */
  List<Role> getAllRoles();

  /**
   * 查找角色分页数据
   *
   * @param role  role
   * @param param param
   * @return 角色分页数据
   */
  IPage<Role> pageRoles(QueryParam param, Role role);

  /**
   * 通过名称获取角色
   *
   * @param roleName 角色名称
   * @return 角色
   */
  Role getByName(String roleName);

  /**
   * 创建角色
   *
   * @param role role
   */
  void createRole(Role role);

  /**
   * 删除角色
   *
   * @param roleIds 角色id数组
   */
  void deleteRoles(String[] roleIds);

  /**
   * 更新角色
   *
   * @param role role
   */
  void updateRole(Role role);
}
