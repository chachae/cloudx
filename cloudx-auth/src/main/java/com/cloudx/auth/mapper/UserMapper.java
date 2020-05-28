package com.cloudx.auth.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudx.common.core.entity.system.SystemUser;
import com.cloudx.common.core.entity.system.UserDataPermission;
import java.util.List;

/**
 * @author chachae
 * @since 2020/4/21 21:35
 */
public interface UserMapper extends BaseMapper<SystemUser> {

  /**
   * 通过用户名获取用户信息
   *
   * @param username 用户名称
   * @return 用户信息：SystemUserDTO
   */
  SystemUser selectByName(String username);

  /**
   * 获取用户数据权限
   *
   * @param userId 用户id
   * @return 数据权限
   */
  List<UserDataPermission> selectUserDataPermissions(Long userId);

}
