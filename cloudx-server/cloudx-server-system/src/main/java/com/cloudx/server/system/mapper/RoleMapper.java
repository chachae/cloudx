package com.cloudx.server.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudx.common.core.entity.system.Role;
import org.apache.ibatis.annotations.Param;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/5/27 22:39
 */
public interface RoleMapper extends BaseMapper<Role> {


  /**
   * 查找角色详情
   *
   * @param page 分页
   * @param role 角色
   * @param <T>  type
   * @return IPage<User>
   */
  <T> IPage<Role> pageRoles(Page<T> page, @Param("role") Role role);
}
