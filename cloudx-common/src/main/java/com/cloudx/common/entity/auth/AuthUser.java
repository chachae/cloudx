package com.cloudx.common.entity.auth;

import java.util.Collection;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
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
  private String password;
  private Long deptId;
  private String email;
  private String mobile;
  private Boolean status;
  private Date lastLoginTime;
  private Integer gender;
  private String avatar;
  private String description;

  public AuthUser(String username, String password,
      Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
  }

  public AuthUser(String username, String password, boolean enabled, boolean accountNonExpired,
      boolean credentialsNonExpired, boolean accountNonLocked,
      Collection<? extends GrantedAuthority> authorities) {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
        authorities);
  }
}
