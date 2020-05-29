package com.cloudx.server.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudx.common.core.entity.system.UserDataPermission;
import com.cloudx.server.system.mapper.UserDataPermissionMapper;
import com.cloudx.server.system.service.IUserDataPermissionService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户数据权限关联服务实现类
 *
 * @author chachae
 * @since 2020-05-28 12:03:43
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserDataPermissionImpl extends
    ServiceImpl<UserDataPermissionMapper, UserDataPermission> implements
    IUserDataPermissionService {

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteByDeptIds(List<String> deptIds) {
    baseMapper.delete(
        new LambdaQueryWrapper<UserDataPermission>().in(UserDataPermission::getDeptId, deptIds));
  }

  @Override
  public String getByUserId(Long userId) {
    LambdaQueryWrapper<UserDataPermission> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(UserDataPermission::getUserId, userId);
    return baseMapper.selectList(wrapper).stream()
        .map(permission -> String.valueOf(permission.getDeptId())).collect(
            Collectors.joining(StrUtil.COMMA));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteByUserIds(String[] userIds) {
    List<String> list = Arrays.asList(userIds);
    baseMapper.delete(
        new LambdaQueryWrapper<UserDataPermission>().in(UserDataPermission::getUserId, list));
  }


}