package com.cloudx.server.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudx.common.core.constant.SystemConstant;
import com.cloudx.common.core.entity.QueryParam;
import com.cloudx.common.core.entity.system.Role;
import com.cloudx.common.core.entity.system.RoleMenu;
import com.cloudx.common.core.exception.ApiException;
import com.cloudx.common.core.util.SortUtil;
import com.cloudx.server.system.mapper.RoleMapper;
import com.cloudx.server.system.service.IRoleMenuService;
import com.cloudx.server.system.service.IRoleService;
import com.cloudx.server.system.service.IUserRoleService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/5/27 22:39
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

  private final IRoleMenuService roleMenuService;
  private final IUserRoleService userRoleService;

  @Override
  public List<Role> getAllRoles() {
    LambdaQueryWrapper<Role> qw = new LambdaQueryWrapper<>();
    qw.orderByAsc(Role::getRoleId);
    return baseMapper.selectList(qw);
  }

  @Override
  public IPage<Role> pageRoles(QueryParam param, Role role) {
    Page<Role> page = new Page<>(param.getPageNum(), param.getPageSize());
    SortUtil.handlePageSort(param, page, "createTime", SystemConstant.ORDER_DESC, false);
    return this.baseMapper.pageRoles(page, role);
  }

  @Override
  public Role getByName(String roleName) {
    return baseMapper.selectOne(new LambdaQueryWrapper<Role>().eq(Role::getRoleName, roleName));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createRole(Role role) {
    role.setCreateTime(new Date());
    this.save(role);

    if (StrUtil.isNotBlank(role.getMenuIds())) {
      String[] menuIds = StrUtil.splitToArray(role.getMenuIds(), ',');
      setRoleMenus(role, menuIds);
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteRoles(String[] roleIds) {
    // 判断是否存在用户关联
    List<String> result = userRoleService.getUserIdsByRoleId(roleIds);
    if (CollUtil.isNotEmpty(result)) {
      throw new ApiException("角色存在用户关联，请移除相关用户后重试");
    }

    List<String> list = Arrays.asList(roleIds);
    baseMapper.deleteBatchIds(list);

    this.roleMenuService.deleteRoleMenusByRoleId(roleIds);
    this.userRoleService.deleteUserRolesByRoleId(roleIds);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateRole(Role role) {
    role.setRoleName(null);
    role.setUpdateTime(new Date());
    baseMapper.updateById(role);

    roleMenuService
        .remove(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, role.getRoleId()));
    if (StrUtil.isNotBlank(role.getMenuIds())) {
      String[] menuIds = StrUtil.splitToArray(role.getMenuIds(), ',');
      setRoleMenus(role, menuIds);
    }
  }

  private void setRoleMenus(Role role, String[] menuIds) {
    List<RoleMenu> roleMenus = new ArrayList<>();
    for (String menuId : menuIds) {
      RoleMenu roleMenu = new RoleMenu();
      if (StrUtil.isNotBlank(menuId)) {
        roleMenu.setMenuId(Long.valueOf(menuId));
      }
      roleMenu.setRoleId(role.getRoleId());
      roleMenus.add(roleMenu);
    }
    this.roleMenuService.saveBatch(roleMenus);
  }


}
