package com.cloudx.auth.mapper;

import com.cloudx.common.core.entity.system.Menu;
import java.util.List;

/**
 * @author chachae
 * @since 2020/4/29 22:33
 */
public interface MenuMapper {

  /**
   * 通过用户ID获取用户的菜单集合
   *
   * @param userId 用户ID
   * @return 菜单集合
   */
  List<Menu> selectListByUserId(Long userId);

  /**
   * 获取用户权限集
   *
   * @param username 用户名
   * @return 权限集合
   */
  List<Menu> selectUserPermissions(String username);
}
