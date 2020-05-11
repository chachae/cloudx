package com.cloudx.common.core.entity.system;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 菜单表实体类
 *
 * @author chachae
 * @since 2020-04-29 21:25:49
 */
@Data
@TableName("t_menu")
public class Menu implements Serializable {

  private static final long serialVersionUID = 3717240193284289714L;
  @TableId
  private Long menuId;
  private Long parentId;
  private String menuName;
  private String path;
  private String expression;
  private String icon;
  private Integer type;
  private Integer orderNum;
  private Date createTime;
  private Date updateTime;

}