package com.cloudx.server.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudx.common.core.entity.system.RoleMenu;
import com.cloudx.server.system.mapper.RoleMenuMapper;
import com.cloudx.server.system.service.IRoleMenuService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/5/29 22:15
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements
    IRoleMenuService {

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteRoleMenusByRoleId(String[] roleIds) {
    List<String> list = Arrays.asList(roleIds);
    baseMapper.delete(new LambdaQueryWrapper<RoleMenu>().in(RoleMenu::getRoleId, list));
  }

  @Override
  public List<String> getRoleIdsByMenuId(String[] menuIds) {
    return this.baseMapper.selectList(
        new LambdaQueryWrapper<RoleMenu>().in(RoleMenu::getMenuId, String.join(",", menuIds)))
        .stream().map(roleMenu -> String.valueOf(roleMenu.getRoleId())).collect(
            Collectors.toList());
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteRoleMenusByMenuId(String[] menuIds) {
    List<String> list = Arrays.asList(menuIds);
    baseMapper.delete(new LambdaQueryWrapper<RoleMenu>().in(RoleMenu::getMenuId, list));
  }

  @Override
  public List<RoleMenu> getRoleMenusByRoleId(String roleId) {
    return baseMapper
        .selectList(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleId));
  }

}
