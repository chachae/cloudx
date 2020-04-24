package com.cloudx.common.entity.auth;

import java.util.Collection;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthUser extends User {

  private static final long serialVersionUID = 5970249403350193074L;

  private Long userId;

  private String avatar;

  private String email;

  private String mobile;

  private String sex;

  private Long deptId;

  private String deptName;

  private String roleId;

  private String roleName;

  private Date lastLoginTime;

  private String description;

  private String status;

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
