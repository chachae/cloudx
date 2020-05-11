package com.cloudx.common.core.entity.system;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 登录日志表(TLoginLog)表实体类
 *
 * @author chachae
 * @since 2020-04-29 21:25:49
 */
@Data
public class LoginLog implements Serializable {

  private static final long serialVersionUID = 5370224853350191738L;

  /**
   * id
   */
  private Long id;
  /**
   * 用户名ID
   */
  private Long userId;
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
  private String system;
  /**
   * 浏览器
   */
  private String browser;
}