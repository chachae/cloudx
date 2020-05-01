package com.cloudx.common.entity.system;

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
  @TableId
  private Long userId;
  private String username;
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