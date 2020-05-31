package com.cloudx.auth.manager;

import cn.hutool.core.util.StrUtil;
import com.cloudx.auth.mapper.MenuMapper;
import com.cloudx.auth.mapper.UserMapper;
import com.cloudx.auth.mapper.UserRoleMapper;
import com.cloudx.common.core.constant.SystemConstant;
import com.cloudx.common.core.entity.system.Menu;
import com.cloudx.common.core.entity.system.SystemUser;
import com.cloudx.common.core.entity.system.UserDataPermission;
import com.cloudx.common.core.entity.system.UserRole;
import java.util.Date;
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

  /**
   * 注册用户
   *
   * @param username username
   * @param password password
   * @return SystemUser SystemUser
   */
  @Transactional(rollbackFor = Exception.class)
  public SystemUser registUser(String username, String password) {
    SystemUser systemUser = new SystemUser();
    systemUser.setUsername(username);
    systemUser.setPassword(password);
    systemUser.setCreateTime(new Date());
    systemUser.setStatus(SystemUser.STATUS_VALID);
    systemUser.setSex(SystemUser.SEX_UNKNOWN);
    systemUser.setAvatar(SystemUser.DEFAULT_AVATAR);
    systemUser.setDescription("注册用户");
    this.userMapper.insert(systemUser);

    UserRole userRole = new UserRole();
    userRole.setUserId(systemUser.getUserId());
    // 注册用户角色 ID
    userRole.setRoleId(SystemConstant.REGISTER_ROLE_ID);
    this.userRoleMapper.insert(userRole);
    return systemUser;
  }

}
