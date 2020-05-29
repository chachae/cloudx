package com.cloudx.server.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudx.common.core.entity.system.UserDataPermission;
import java.util.List;

/**
 * 用户数据权限关联服务接口
 *
 * @author chachae
 * @since 2020-05-28 12:03:40
 */
public interface IUserDataPermissionService extends IService<UserDataPermission> {

  /**
   * 通过用户id 查询用户权限表达式
   *
   * @param userId 用户id
   * @return 权限表达式
   */
  String getByUserId(Long userId);

  /**
   * 通过用户ID删除关联关系
   *
   * @param userIds 用户id
   */
  void deleteByUserIds(String[] userIds);

  /**
   * 通过部门ID删除关联关系
   *
   * @param deptIds 部门id
   */
  void deleteByDeptIds(List<String> deptIds);
}