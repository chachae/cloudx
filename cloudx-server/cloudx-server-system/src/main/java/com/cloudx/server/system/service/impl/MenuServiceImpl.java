package com.cloudx.server.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudx.common.core.constant.PageResultConstant;
import com.cloudx.common.core.entity.MenuTree;
import com.cloudx.common.core.entity.Tree;
import com.cloudx.common.core.entity.router.RouterMeta;
import com.cloudx.common.core.entity.router.VueRouter;
import com.cloudx.common.core.entity.system.Menu;
import com.cloudx.common.core.util.TreeUtil;
import com.cloudx.server.system.mapper.MenuMapper;
import com.cloudx.server.system.service.IMenuService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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

      if (StringUtils.equals(menu.getType(), Menu.TYPE_BUTTON)) {
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
    if (StringUtils.isNotBlank(menu.getMenuName())) {
      queryWrapper.like(Menu::getMenuName, menu.getMenuName());
    }
    queryWrapper.orderByAsc(Menu::getMenuId);
    return this.baseMapper.selectList(queryWrapper);
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

}
