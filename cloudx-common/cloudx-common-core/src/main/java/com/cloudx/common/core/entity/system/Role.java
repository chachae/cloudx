package com.cloudx.common.core.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 角色表表实体类
 *
 * @author chachae
 * @since 2020-04-29 21:39:54
 */
@Data
@TableName("t_role")
public class Role implements Serializable {

  private static final long serialVersionUID = -2870817897310784072L;

  @TableId(type = IdType.AUTO)
  private Long roleId;
  private String roleName;
  private String remark;
  private Date createTime;
  private Date updateTime;

  private transient String menuIds;

}