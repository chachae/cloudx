package com.cloudx.server.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudx.common.core.entity.system.Role;
import java.util.List;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/5/27 22:39
 */
public interface IRoleService extends IService<Role> {


  /**
   * 获取所有角色
   *
   * @return 角色列表
   */
  List<Role> getAllRoles();

}
