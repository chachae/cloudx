package com.cloudx.server.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudx.common.base.QueryParam;
import com.cloudx.common.constant.SystemUserConstant;
import com.cloudx.common.entity.auth.CurrentUser;
import com.cloudx.common.entity.dto.SystemUserDTO;
import com.cloudx.common.entity.system.SystemUser;
import com.cloudx.common.entity.system.UserRole;
import com.cloudx.common.util.SecurityUtil;
import com.cloudx.server.system.mapper.UserMapper;
import com.cloudx.server.system.service.IUserRoleService;
import com.cloudx.server.system.service.IUserService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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

  @Override
  public SystemUser selectByUserName(String userName) {
    LambdaQueryWrapper<SystemUser> qw = new LambdaQueryWrapper<>();
    qw.eq(SystemUser::getUsername, userName);
    return getOne(qw);
  }

  @Override
  public IPage<SystemUserDTO> pageSystemUser(QueryParam param, SystemUserDTO user) {
    Page<SystemUserDTO> page = new Page<>(param.getPageNum(), param.getPageSize());
    return baseMapper.pageSystemUserDetail(page, user);
  }

  @Override
  public SystemUserDTO selectSystemUser(SystemUserDTO user) {
    List<SystemUserDTO> result = baseMapper.selectSystemUserDetail(user);
    return CollUtil.isNotEmpty(result) ? result.get(0) : new SystemUserDTO();
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void insert(SystemUserDTO user) {
    SystemUser insertUser = new SystemUser();
    BeanUtils.copyProperties(user, insertUser);
    insertUser.setPassword(passwordEncoder.encode(SystemUserConstant.DEFAULT_PASSWORD))
        .setAvatar(SystemUserConstant.DEFAULT_AVATAR)
        .setStatus(SystemUserConstant.STATUS_VALID)
        .setAvatar(SystemUserConstant.DEFAULT_AVATAR)
        .setCreateTime(new Date());
    save(insertUser);
    // 设置角色信息
    String[] roles = user.getRoleId().split(StringPool.COMMA);
    setUserRoles(insertUser.getUserId(), roles);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void update(Long userId, SystemUserDTO user) {
    // 更新用户
    user.setPassword(null).setUsername(null).setCreateTime(null).setUpdateTime(new Date());
    SystemUser updateUser = new SystemUser();
    BeanUtils.copyProperties(user, updateUser);
    updateById(updateUser);

    // 如果有更新用户角色信息则进行
    if (user.getRoleId() != null) {
      LambdaQueryWrapper<UserRole> qw = new LambdaQueryWrapper<>();
      qw.eq(UserRole::getUserId, user.getUserId());
      userRoleService.remove(qw);
      String[] roles = user.getRoleId().split(StringPool.COMMA);
      setUserRoles(user.getUserId(), roles);
    }
  }

  @Override
  public void delete(Long userId) {
    baseMapper.deleteById(userId);
    LambdaQueryWrapper<UserRole> qw = new LambdaQueryWrapper<>();
    qw.eq(UserRole::getUserId, userId);
    userRoleService.remove(qw);
  }

  /**
   * 设置用户角色信息
   *
   * @param userId 用户ID
   * @param roles  角色 ID 数组
   */
  private void setUserRoles(Long userId, String[] roles) {
    List<UserRole> userRoles = new ArrayList<>(roles.length);
    UserRole userRole = new UserRole(userId);
    Stream.of(roles).forEach(roleId -> {
      userRole.setRoleId(Long.valueOf(roleId));
      userRoles.add(userRole);
    });
    userRoleService.saveBatch(userRoles);
  }

  /**
   * 判断是否为当前用户
   *
   * @param id 用户ID
   * @return true / false
   */
  private boolean isCurrentUser(Long id) {
    CurrentUser cur = SecurityUtil.getCurrentUser();
    return cur != null && cur.getUserId().equals(id);
  }
}
