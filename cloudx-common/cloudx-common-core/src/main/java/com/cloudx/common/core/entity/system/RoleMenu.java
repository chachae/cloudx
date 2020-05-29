package com.cloudx.common.core.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @author chachae
 */
@TableName("t_role_menu")
@Data
public class RoleMenu implements Serializable {

  private static final long serialVersionUID = -7573904024872252113L;

  private Long roleId;

  private Long menuId;
}