package com.cloudx.server.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudx.common.core.entity.system.UserRole;

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

}
