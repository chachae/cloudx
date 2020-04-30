package com.cloudx.common.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author chachae
 * @since 2020/4/30 20:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SystemUserDTO implements Serializable {

  private static final long serialVersionUID = 5970249403350193074L;
  private Long userId;
  private String userName;
  @JsonIgnore
  private String password;
  private Long deptId;
  private String deptName;
  private String roleId;
  private String roleName;
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
