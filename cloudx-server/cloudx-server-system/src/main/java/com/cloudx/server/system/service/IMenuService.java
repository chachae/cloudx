package com.cloudx.server.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudx.common.core.entity.router.VueRouter;
import com.cloudx.common.core.entity.system.Menu;
import java.util.List;
import java.util.Map;

/**
 * 菜单业务接口
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/27 11:08
 */
public interface IMenuService extends IService<Menu> {

  /**
   * 获取用户权限
   *
   * @param username 用户名
   * @return 用户权限
   */
  String getUserPermissions(String username);

  /**
   * 获取用户菜单
   *
   * @param username 用户名
   * @return 用户菜单
   */
  List<Menu> getUserMenus(String username);

  /**
   * 获取用户菜单
   *
   * @param menu menu
   * @return 用户菜单
   */
  Map<String, Object> findMenus(Menu menu);

  /**
   * 获取用户路由
   *
   * @param username 用户名
   * @return 用户路由
   */
  List<VueRouter<Menu>> getUserRouters(String username);

  /**
   * 获取菜单列表
   *
   * @param menu menu
   * @return 菜单列表
   */
  List<Menu> findMenuList(Menu menu);


  /**
   * 创建菜单
   *
   * @param menu menu
   */
  void createMenu(Menu menu);

  /**
   * 更新菜单
   *
   * @param menu menu
   */
  void updateMenu(Menu menu);

  /**
   * 递归删除菜单/按钮
   *
   * @param menuIds menuIds
   */
  void deleteMeuns(String[] menuIds);
}
