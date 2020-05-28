package com.cloudx.common.core.entity.auth;

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
  private String username;
  private String email;
  private String mobile;
  private String status;
  private Long deptId;
  private String deptName;
  private String roleId;
  private String roleName;
  @JsonIgnore
  private Date lastLoginTime;
  private String sex;
  private String avatar;
  private String description;
  private String deptIds;
}
