package com.cloudx.server.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudx.common.core.entity.system.RoleMenu;
import java.util.List;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/5/29 22:14
 */
public interface IRoleMenuService extends IService<RoleMenu> {

  /**
   * 删除角色菜单关联数据
   *
   * @param roleIds 角色id
   */
  void deleteRoleMenusByRoleId(String[] roleIds);

  /**
   * 通过菜单id获取角色信息
   *
   * @param menuIds 角菜单id
   * @return 角色id
   */
  List<String> getRoleIdsByMenuId(String[] menuIds);

  /**
   * 删除角色菜单关联数据
   *
   * @param menuIds 菜单id
   */
  void deleteRoleMenusByMenuId(String[] menuIds);

  /**
   * 获取角色对应的菜单列表
   *
   * @param roleId 角色id
   * @return 菜单列表
   */
  List<RoleMenu> getRoleMenusByRoleId(String roleId);
}
