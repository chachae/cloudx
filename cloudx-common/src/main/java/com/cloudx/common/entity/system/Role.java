package com.cloudx.common.entity.system;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class Role {

  @TableId
  private Long roleId;
  private String rileName;
  private String remark;
  private Date createTime;
  private Date updateTime;

}