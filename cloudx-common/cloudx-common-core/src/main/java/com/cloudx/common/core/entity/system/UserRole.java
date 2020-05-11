package com.cloudx.common.core.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户角色关联表实体类
 *
 * @author chachae
 * @since 2020-04-29 21:25:49
 */
@Data
@NoArgsConstructor
@TableName("t_user_role")
public class UserRole {

  /**
   * 用户ID
   */
  private Long userId;
  /**
   * 角色ID
   */
  private Long roleId;

  public UserRole(Long userId) {
    this.userId = userId;
  }


}