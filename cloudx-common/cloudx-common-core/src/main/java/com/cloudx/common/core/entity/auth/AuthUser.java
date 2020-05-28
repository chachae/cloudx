package com.cloudx.common.core.entity.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

/**
 * 自定义 Spring Security UserDetail
 * <p>
 * {@link User}
 *
 * @author chachae
 * @since 2020/4/24 22:25
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class AuthUser extends User {

  private static final long serialVersionUID = 5970249403350193074L;

  private Long userId;
  private String username;
  @JsonIgnore
  private String password;
  private Long deptId;
  private String deptName;
  private String roleId;
  private String roleName;
  private String email;
  private String mobile;
  private String status;
  private Date lastLoginTime;
  private String sex;
  private String avatar;
  private String description;
  private String deptIds;

  public AuthUser(String username, String password, boolean enabled, boolean accountNonExpired,
      boolean credentialsNonExpired, boolean accountNonLocked,
      Collection<? extends GrantedAuthority> authorities) {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
        authorities);
  }

  public AuthUser(String username, String password,
      Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
  }

  public AuthUser(String username, String password, boolean accountNonLocked) {
    this(username, password, true, true, true, accountNonLocked, AuthorityUtils.NO_AUTHORITIES);
  }

}
