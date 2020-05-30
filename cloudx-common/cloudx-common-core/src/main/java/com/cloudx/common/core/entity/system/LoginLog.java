package com.cloudx.common.core.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 登录日志表实体类
 *
 * @author chachae
 * @since 2020-05-27 17:11:48
 */
@Data
@TableName("t_login_log")
public class LoginLog implements Serializable {

  private static final long serialVersionUID = -4838997402864414894L;

  /**
   * id
   */
  @TableId(type = IdType.AUTO)
  private Long id;
  /**
   * 用户名
   */
  private String username;
  /**
   * 登录时间
   */
  private Date loginTime;
  /**
   * 登录地点
   */
  private String location;
  /**
   * IP地址
   */
  private String ip;
  /**
   * 操作系统
   */
  @TableField("`system`")
  private String system;
  /**
   * 浏览器
   */
  private String browser;

  /**
   * 开始时间
   */
  private transient String loginTimeFrom;

  /**
   * 结束时间
   */
  private transient String loginTimeTo;
}