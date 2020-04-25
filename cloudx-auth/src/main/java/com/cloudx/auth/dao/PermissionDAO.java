package com.cloudx.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudx.common.entity.system.Permission;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author chachae
 * @since 2020/4/22 12:32
 */
public interface PermissionDAO extends BaseMapper<Permission> {

  /**
   * 通过角色 ID 集合获取所属权限信息
   *
   * @param roleId 角色ID
   * @return 权限表达式集合
   */
  List<String> selectPermissionIdByRoleId(@Param("roleId") List<String> roleId);

}
