package com.cloudx.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudx.common.entity.system.SystemUser;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author chachae
 * @since 2020/4/21 21:35
 */
public interface UserDAO extends BaseMapper<SystemUser> {

  /**
   * 通过用户 ID 获取角色 ID
   *
   * @param userId 用户ID
   * @return 角色值集合
   */
  @Select("select role_id from user_role where user_id = #{userId}")
  List<String> selectRoleIdByUserId(@Param("userId") Long userId);

}
