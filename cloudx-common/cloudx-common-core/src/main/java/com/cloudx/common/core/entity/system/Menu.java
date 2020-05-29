package com.cloudx.common.core.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 菜单表(TMenu)表实体类
 *
 * @author chachae
 * @since 2020-05-27 10:29:30
 */
@Data
@TableName("t_menu")
public class Menu implements Serializable {

  /**
   * 菜单
   */
  public static final String TYPE_MENU = "0";
  /**
   * 按钮
   */
  public static final String TYPE_BUTTON = "1";
  public static final Long TOP_MENU_ID = 0L;

  private static final long serialVersionUID = 4308580131512119507L;

  /**
   * 菜单/按钮ID
   */
  @TableId(type = IdType.AUTO)
  private Long menuId;
  /**
   * 上级菜单ID
   */
  private Long parentId;
  /**
   * 菜单/按钮名称
   */
  private String menuName;
  /**
   * 对应路由path
   */
  private String path;
  /**
   * 对应路由组件component
   */
  private String component;
  /**
   * 权限表达式
   */
  private String expression;
  /**
   * 图标
   */
  private String icon;
  /**
   * 类型 0菜单 1按钮
   */
  private String type;
  /**
   * 排序
   */
  private Integer orderNum;
  /**
   * 创建时间
   */
  private Date createTime;
  /**
   * 修改时间
   */
  private Date updateTime;

}