package com.cloudx.common.entity.system;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户表实体类
 *
 * @author chachae
 * @since 2020-04-29 21:25:49
 */
@Data
@TableName("t_user")
public class SystemUser implements Serializable {

  private static final long serialVersionUID = 5970249403350193074L;
  @TableId
  private Long userId;
  private String userName;
  private String password;
  private Long deptId;
  private String email;
  private String mobile;
  private Boolean status;
  private Date createTime;
  private Date updateTime;
  private Date lastLoginTime;
  private Integer gender;
  private String avatar;
  private String description;
}