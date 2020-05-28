package com.cloudx.auth.manager;

import cn.hutool.core.util.StrUtil;
import com.cloudx.auth.mapper.MenuMapper;
import com.cloudx.auth.mapper.UserMapper;
import com.cloudx.auth.mapper.UserRoleMapper;
import com.cloudx.common.core.entity.system.Menu;
import com.cloudx.common.core.entity.system.SystemUser;
import com.cloudx.common.core.entity.system.UserDataPermission;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户业务逻辑
 *
 * @author MrBird
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserManager {

  private final UserMapper userMapper;
  private final MenuMapper menuMapper;
  private final UserRoleMapper userRoleMapper;

  /**
   * 通过用户名查询用户信息
   *
   * @param username 用户名
   * @return 用户
   */
  public SystemUser findByName(String username) {
    SystemUser user = userMapper.selectByName(username);
    if (user != null) {
      List<UserDataPermission> permissions = userMapper.selectUserDataPermissions(user.getUserId());
      String deptIds = permissions.stream().map(p -> String.valueOf(p.getDeptId()))
          .collect(Collectors.joining(
              StrUtil.COMMA));
      user.setDeptIds(deptIds);
    }
    return user;
  }

  /**
   * 通过用户名查询用户权限串
   *
   * @param username 用户名
   * @return 权限
   */
  public String findUserPermissions(String username) {
    List<Menu> userPermissions = menuMapper.selectUserPermissions(username);
    return userPermissions.stream().map(Menu::getExpression).collect(Collectors.joining(","));
  }

}
