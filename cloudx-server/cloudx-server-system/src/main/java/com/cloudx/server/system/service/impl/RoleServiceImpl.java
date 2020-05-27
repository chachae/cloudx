package com.cloudx.server.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudx.common.core.entity.system.Role;
import com.cloudx.server.system.mapper.RoleMapper;
import com.cloudx.server.system.service.IRoleService;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/5/27 22:39
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

  @Override
  public List<Role> getAllRoles() {
    LambdaQueryWrapper<Role> qw = new LambdaQueryWrapper<>();
    qw.orderByAsc(Role::getRoleId);
    return baseMapper.selectList(qw);
  }
}
