package com.cloudx.server.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudx.common.core.constant.SystemConstant;
import com.cloudx.common.core.entity.QueryParam;
import com.cloudx.common.core.entity.auth.CurrentUser;
import com.cloudx.common.core.entity.system.SystemUser;
import com.cloudx.common.core.entity.system.UserDataPermission;
import com.cloudx.common.core.entity.system.UserRole;
import com.cloudx.common.core.exception.ApiException;
import com.cloudx.common.core.util.SecurityUtil;
import com.cloudx.common.core.util.SortUtil;
import com.cloudx.server.system.mapper.UserMapper;
import com.cloudx.server.system.service.IUserDataPermissionService;
import com.cloudx.server.system.service.IUserRoleService;
import com.cloudx.server.system.service.IUserService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统用户业务接口
 *
 * @author chachae
 * @since 2020/4/30 19:56
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl extends ServiceImpl<UserMapper, SystemUser> implements IUserService {

  private final IUserRoleService userRoleService;
  private final PasswordEncoder passwordEncoder;
  private final IUserDataPermissionService userDataPermissionService;

  @Override
  public SystemUser getSystemUser(String username) {
    LambdaQueryWrapper<SystemUser> qw = new LambdaQueryWrapper<>();
    qw.eq(SystemUser::getUsername, username);
    return getOne(qw);
  }

  @Override
  public IPage<SystemUser> pageSystemUser(QueryParam param, SystemUser user) {
    Page<SystemUser> page = new Page<>(param.getPageNum(), param.getPageSize());
    SortUtil.handlePageSort(param, page, "user_id", SystemConstant.ORDER_ASC, false);
    return baseMapper.pageSystemUserDetail(page, user);
  }

  @Override
  public void updateLoginTime(String username) {
    SystemUser user = new SystemUser();
    user.setLastLoginTime(new Date());
    LambdaQueryWrapper<SystemUser> qw = new LambdaQueryWrapper<>();
    qw.eq(SystemUser::getUsername, username);
    baseMapper.update(user, qw);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createUser(SystemUser user) {
    // 创建用户
    user.setCreateTime(new Date());
    user.setAvatar(SystemUser.DEFAULT_AVATAR);
    user.setPassword(passwordEncoder.encode(SystemUser.DEFAULT_PASSWORD));
    save(user);
    // 保存用户角色
    String[] roles = StrUtil.splitToArray(user.getRoleId(), StrUtil.C_COMMA);
    setUserRoles(user, roles);
    // 保存用户数据权限关联关系
    String[] deptIds = StrUtil.splitToArray(user.getDeptIds(), StrUtil.C_COMMA);
    setUserDataPermissions(user, deptIds);
  }

  @Override
  public void deleteUsers(String[] userIds) {
    // 判断是否删除数据内包含当前用户
    if (hasCurrentUser(userIds)) {
      throw new ApiException("不能删除当前用户");
    }
    List<String> list = Arrays.asList(userIds);
    removeByIds(list);
    // 删除用户角色
    this.userRoleService.deleteUserRolesByUserId(userIds);
    this.userDataPermissionService.deleteByUserIds(userIds);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateUser(SystemUser user) {
    // 更新用户
    user.setPassword(null);
    user.setUsername(null);
    user.setCreateTime(null);
    user.setUpdateTime(new Date());
    updateById(user);
    // 维护用户角色信息
    String[] userIds = {String.valueOf(user.getUserId())};
    userRoleService.deleteUserRolesByUserId(userIds);
    String[] roles = StrUtil.split(user.getRoleId(), StrUtil.COMMA);
    setUserRoles(user, roles);
    // 维护用户数据权限信息
    userDataPermissionService.deleteByUserIds(userIds);
    String[] deptIds = StrUtil.split(user.getDeptIds(), StrUtil.COMMA);
    setUserDataPermissions(user, deptIds);
  }

  @Override
  public List<String> getUserIdByDeptIds(String[] deptIds) {
    return baseMapper.selectList(
        new LambdaQueryWrapper<SystemUser>()
            .in(SystemUser::getDeptId, String.join(",", deptIds)))
        .stream().map(user -> String.valueOf(user.getUserId()))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateProfile(SystemUser user) {
    user.setPassword(null).setUsername(null).setStatus(null);
    if (hasCurrentUser(new String[]{String.valueOf(user.getUserId())})) {
      updateById(user);
    } else {
      throw new ApiException("您无权修改别人的账号信息！");
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateAvatar(String avatar) {
    SystemUser user = new SystemUser().setAvatar(avatar);
    String curUsername = SecurityUtil.getCurrentUsername();
    this.baseMapper.update(user,
        new LambdaQueryWrapper<SystemUser>().eq(SystemUser::getUsername, curUsername));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updatePassword(String password) {
    SystemUser user = new SystemUser().setPassword(passwordEncoder.encode(password));
    String curUsername = SecurityUtil.getCurrentUsername();
    baseMapper.update(user,
        new LambdaQueryWrapper<SystemUser>().eq(SystemUser::getUsername, curUsername));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void resetPassword(String[] usernames) {
    SystemUser params = new SystemUser();
    params.setPassword(passwordEncoder.encode(SystemUser.DEFAULT_PASSWORD));

    List<String> list = Arrays.asList(usernames);
    baseMapper
        .update(params, new LambdaQueryWrapper<SystemUser>().in(SystemUser::getUsername, list));

  }

  private void setUserRoles(SystemUser user, String[] roles) {
    List<UserRole> userRoles = new ArrayList<>();
    UserRole userRole = new UserRole();
    userRole.setUserId(user.getUserId());
    for (String roleId : roles) {
      userRole.setRoleId(Long.valueOf(roleId));
      userRoles.add(userRole);
    }
    userRoleService.saveBatch(userRoles);
  }

  private void setUserDataPermissions(SystemUser user, String[] deptIds) {
    List<UserDataPermission> userDataPermissions = new ArrayList<>();
    UserDataPermission permission = new UserDataPermission();
    permission.setUserId(user.getUserId());
    for (String deptId : deptIds) {
      permission.setDeptId(Long.valueOf(deptId));
      userDataPermissions.add(permission);
    }
    userDataPermissionService.saveBatch(userDataPermissions);
  }

  private boolean hasCurrentUser(String[] userIds) {
    CurrentUser cur = SecurityUtil.getCurrentUser();
    if (cur != null) {
      for (String userId : userIds) {
        if (String.valueOf(cur.getUserId()).equals(userId)) {
          return true;
        }
      }
    }
    return false;
  }
}
