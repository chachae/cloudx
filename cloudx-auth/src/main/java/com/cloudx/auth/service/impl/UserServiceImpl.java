package com.cloudx.auth.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudx.auth.dao.PermissionDAO;
import com.cloudx.auth.dao.UserDAO;
import com.cloudx.auth.service.IUserService;
import com.cloudx.common.entity.auth.AuthUser;
import com.cloudx.common.entity.system.Permission;
import com.cloudx.common.entity.system.SystemUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author chachae
 * @since 2020/4/21 21:36
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserDAO, SystemUser> implements
    IUserService, UserDetailsService {

  private final UserDAO userDAO;
  private final PermissionDAO permissionDAO;

  /**
   * Spring Security 认证
   *
   * @param username 用户名
   * @return /
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    SystemUser systemUser = this.findByUsername(username);
    if (!ObjectUtils.isEmpty(systemUser)) {
      // 角色
      List<String> roleIds = findByEmployeeId(systemUser.getId());
      // 权限
      String[] expressions = new String[0];
      if (CollUtil.isNotEmpty(roleIds)) {
        List<String> permissionIds = permissionDAO.selectPermissionIdByRoleId(roleIds);
        List<Permission> permissions = permissionDAO.selectBatchIds(permissionIds);
        expressions = permissions.stream().map(Permission::getExpression)
            .toArray(String[]::new);
      }
      // 构建用户信息
      AuthUser authUser = new AuthUser(systemUser.getUserName(), systemUser.getPassword(), true,
          true, true,
          true,
          AuthorityUtils.createAuthorityList(expressions));
      BeanUtils.copyProperties(systemUser, authUser);
      return authUser;
    } else {
      throw new UsernameNotFoundException("");
    }
  }

  @Override
  public SystemUser findByUsername(String username) {
    LambdaQueryWrapper<SystemUser> qw = new LambdaQueryWrapper<>();
    qw.eq(SystemUser::getUserName, username);
    return userDAO.selectOne(qw);
  }

  @Override
  public List<String> findByEmployeeId(Long userId) {
    return userDAO.selectRoleIdByUserId(userId);
  }
}
