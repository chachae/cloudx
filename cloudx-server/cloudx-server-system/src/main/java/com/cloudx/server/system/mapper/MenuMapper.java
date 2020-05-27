package com.cloudx.server.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudx.common.core.entity.system.Menu;
import java.util.List;

/**
 * @author chachae
 * @since 2020/4/29 22:33
 */
public interface MenuMapper extends BaseMapper<Menu> {

  /**
   * 获取用户权限集
   *
   * @param username 用户名
   * @return 用户权限集
   */
  List<Menu> selectUserPermissions(String username);

  /**
   * 通过用户ID获取用户的菜单集合
   *
   * @param username 用户名
   * @return 菜单集合
   */
  List<Menu> selectListByUsername(String username);
}
