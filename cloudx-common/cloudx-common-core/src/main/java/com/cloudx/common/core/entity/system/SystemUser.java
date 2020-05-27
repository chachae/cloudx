package com.cloudx.common.core.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户表实体类
 *
 * @author chachae
 * @since 2020-04-29 21:25:49
 */
@Data
@TableName("t_user")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SystemUser implements Serializable {

  private static final long serialVersionUID = 5970249403350193074L;

  /**
   * 用户状态：有效
   */
  public static final String STATUS_VALID = "1";
  /**
   * 用户状态：锁定
   */
  public static final String STATUS_LOCK = "0";
  /**
   * 默认头像
   */
  public static final String DEFAULT_AVATAR = "default.jpg";
  /**
   * 默认密码
   */
  public static final String DEFAULT_PASSWORD = "123456";
  /**
   * 性别男
   */
  public static final String SEX_MALE = "0";
  /**
   * 性别女
   */
  public static final String SEX_FEMALE = "1";
  /**
   * 性别保密
   */
  public static final String SEX_UNKNOWN = "2";

  @TableId(type = IdType.AUTO)
  private Long userId;
  private String username;
  private String password;
  private Long deptId;
  private String email;
  private String mobile;
  private String status;
  private Date createTime;
  private Date updateTime;
  private Date lastLoginTime;
  private Integer gender;
  private String avatar;
  private String description;
}