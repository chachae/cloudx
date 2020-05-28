package com.cloudx.common.core.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloudx.common.core.validator.annotation.Mobile;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
  private static final long serialVersionUID = -4352868070794165001L;
  /**
   * 用户 ID
   */
  @TableId(type = IdType.AUTO)
  private Long userId;

  /**
   * 用户名
   */
  @Size(min = 4, max = 10, message = "{range}")
  private String username;

  /**
   * 密码
   */
  private String password;

  /**
   * 部门 ID
   */
  private Long deptId;

  /**
   * 邮箱
   */
  @Size(max = 50, message = "{noMoreThan}")
  @Email(message = "{email}")
  private String email;

  /**
   * 联系电话
   */
  @Mobile(message = "{mobile}")
  private String mobile;

  /**
   * 状态 0锁定 1有效
   */
  @NotBlank(message = "{required}")
  private String status;

  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 修改时间
   */
  private Date updateTime;

  /**
   * 最近访问时间
   */
  private Date lastLoginTime;

  /**
   * 性别 0男 1女 2 保密
   */
  @TableField("ssex")
  @NotBlank(message = "{required}")
  private String sex;

  /**
   * 头像
   */
  private String avatar;

  /**
   * 描述
   */
  @Size(max = 100, message = "{noMoreThan}")
  private String description;

  /**
   * 部门名称
   */
  @TableField(exist = false)
  private String deptName;

  @TableField(exist = false)
  private String createTimeFrom;
  @TableField(exist = false)
  private String createTimeTo;
  /**
   * 角色 ID
   */
  @NotBlank(message = "{required}")
  @TableField(exist = false)
  private String roleId;

  @TableField(exist = false)
  private String roleName;

  @TableField(exist = false)
  private String deptIds;
}