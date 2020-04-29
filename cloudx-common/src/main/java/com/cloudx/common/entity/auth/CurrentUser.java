package com.cloudx.common.entity.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author chachae
 * @since 2020/4/14 20:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUser implements Serializable {

  private static long serialVersionUID = 764748087824726463L;

  @JsonIgnore
  private String password;
  @JsonIgnore
  private Set<GrantedAuthority> authorities;
  private boolean accountNonExpired;
  private boolean accountNonLocked;
  private boolean credentialsNonExpired;
  private boolean enabled;
  private Long userId;
  private String userName;
  private Long deptId;
  private String email;
  private String mobile;
  private Object status;
  private Date createTime;
  private Date updateTime;
  private Date lastLoginTime;
  private Integer gender;
  private String avatar;
  private String description;
}
