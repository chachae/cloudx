package com.cloudx.server.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudx.common.core.constant.PageResultConstant;
import com.cloudx.common.core.entity.MenuTree;
import com.cloudx.common.core.entity.Tree;
import com.cloudx.common.core.entity.router.RouterMeta;
import com.cloudx.common.core.entity.router.VueRouter;
import com.cloudx.common.core.entity.system.Menu;
import com.cloudx.common.core.exception.ApiException;
import com.cloudx.common.core.util.TreeUtil;
import com.cloudx.server.system.mapper.MenuMapper;
import com.cloudx.server.system.service.IMenuService;
import com.cloudx.server.system.service.IRoleMenuService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户菜单业务实现类
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/27 11:08
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

  private final IRoleMenuService roleMenuService;

  @Override
  public String getUserPermissions(String username) {
    List<Menu> menus = baseMapper.selectUserPermissions(username);
    return menus.stream().map(Menu::getExpression).collect(Collectors.joining(","));
  }

  @Override
  public List<Menu> getUserMenus(String username) {
    return this.baseMapper.selectListByUsername(username);
  }

  @Override
  public Map<String, Object> findMenus(Menu menu) {
    Map<String, Object> result = new HashMap<>(2);
    try {
      LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.orderByAsc(Menu::getOrderNum);
      List<Menu> menus = baseMapper.selectList(queryWrapper);

      List<MenuTree> trees = new ArrayList<>();
      buildTrees(trees, menus);
      if (StrUtil.equals(menu.getType(), Menu.TYPE_BUTTON)) {
        result.put(PageResultConstant.ROWS, trees);
      } else {
        List<? extends Tree<?>> menuTree = TreeUtil.build(trees);
        result.put(PageResultConstant.ROWS, menuTree);
      }

      result.put(PageResultConstant.TOTAL, menus.size());
    } catch (NumberFormatException e) {
      log.error("查询菜单失败", e);
      result.put(PageResultConstant.ROWS, null);
      result.put(PageResultConstant.TOTAL, 0);
    }
    return result;
  }

  @Override
  public List<VueRouter<Menu>> getUserRouters(String username) {
    List<VueRouter<Menu>> routes = new ArrayList<>();
    List<Menu> menus = this.getUserMenus(username);
    menus.forEach(menu -> {
      VueRouter<Menu> route = new VueRouter<>();
      route.setId(menu.getMenuId().toString());
      route.setParentId(menu.getParentId().toString());
      route.setPath(menu.getPath());
      route.setComponent(menu.getComponent());
      route.setName(menu.getMenuName());
      route.setMeta(new RouterMeta(menu.getMenuName(), menu.getIcon(), true));
      routes.add(route);
    });
    return TreeUtil.buildVueRouter(routes);
  }


  @Override
  public List<Menu> findMenuList(Menu menu) {
    LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
    if (StrUtil.isNotBlank(menu.getMenuName())) {
      queryWrapper.like(Menu::getMenuName, menu.getMenuName());
    }
    queryWrapper.orderByAsc(Menu::getMenuId);
    return baseMapper.selectList(queryWrapper);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createMenu(Menu menu) {
    menu.setCreateTime(new Date());
    setMenu(menu);
    this.save(menu);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateMenu(Menu menu) {
    menu.setUpdateTime(new Date());
    setMenu(menu);
    baseMapper.updateById(menu);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteMeuns(String[] menuIds) {
    List<String> result = roleMenuService.getRoleIdsByMenuId(menuIds);
    if (CollUtil.isNotEmpty(result)) {
      throw new ApiException("菜单存在角色关联，请移除相关角色后重试");
    }
    this.delete(Arrays.asList(menuIds));
  }

  private void buildTrees(List<MenuTree> trees, List<Menu> menus) {
    menus.forEach(menu -> {
      MenuTree tree = new MenuTree();
      tree.setId(menu.getMenuId().toString());
      tree.setParentId(menu.getParentId().toString());
      tree.setLabel(menu.getMenuName());
      tree.setComponent(menu.getComponent());
      tree.setIcon(menu.getIcon());
      tree.setOrderNum(menu.getOrderNum());
      tree.setPath(menu.getPath());
      tree.setType(menu.getType());
      tree.setExpression(menu.getExpression());
      trees.add(tree);
    });
  }

  private void setMenu(Menu menu) {
    if (menu.getParentId() == null) {
      menu.setParentId(Menu.TOP_MENU_ID);
    }
    if (Menu.TYPE_BUTTON.equals(menu.getType())) {
      menu.setPath(null);
      menu.setIcon(null);
      menu.setComponent(null);
      menu.setOrderNum(null);
    }
  }

  private void delete(List<String> menuIds) {
    removeByIds(menuIds);

    LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.in(Menu::getParentId, menuIds);
    List<Menu> menus = baseMapper.selectList(queryWrapper);
    if (CollUtil.isNotEmpty(menus)) {
      List<String> menuIdList = new ArrayList<>();
      menus.forEach(m -> menuIdList.add(String.valueOf(m.getMenuId())));
      this.delete(menuIdList);
    }
  }

}
