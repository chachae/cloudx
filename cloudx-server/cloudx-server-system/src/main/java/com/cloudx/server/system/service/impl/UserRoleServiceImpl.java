package com.cloudx.server.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudx.common.core.entity.system.UserRole;
import com.cloudx.server.system.mapper.UserRoleMapper;
import com.cloudx.server.system.service.IUserRoleService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 * @since 2020/4/30 21:52
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements
    IUserRoleService {

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteUserRolesByRoleId(String[] roleIds) {
    List<String> list = Arrays.asList(roleIds);
    this.baseMapper.delete(new LambdaQueryWrapper<UserRole>().in(UserRole::getRoleId, list));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteUserRolesByUserId(String[] userIds) {
    List<String> list = Arrays.asList(userIds);
    baseMapper.delete(new LambdaQueryWrapper<UserRole>().in(UserRole::getUserId, list));
  }

  @Override
  public List<String> getUserIdsByRoleId(String[] roleIds) {
    List<UserRole> list = baseMapper.selectList(
        new LambdaQueryWrapper<UserRole>().in(UserRole::getRoleId, String.join(",", roleIds)));
    return list.stream().map(userRole -> String.valueOf(userRole.getUserId()))
        .collect(Collectors.toList());
  }

}
