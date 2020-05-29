package com.cloudx.server.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudx.common.core.entity.system.UserRole;
import java.util.List;

/**
 * @author chachae
 * @since 2020/4/30 21:52
 */
public interface IUserRoleService extends IService<UserRole> {

  /**
   * 删除角色用户管理关系
   *
   * @param userIds 用户id数组
   */
  void deleteUserRolesByUserId(String[] userIds);

  /**
   * 删除角色用户管理关系
   *
   * @param roleIds 角色id数组
   */
  void deleteUserRolesByRoleId(String[] roleIds);

  /**
   * 通过角色id查找对应的用户id
   *
   * @param roleIds 角色id
   * @return 用户id集
   */
  List<String> getUserIdsByRoleId(String[] roleIds);
}
