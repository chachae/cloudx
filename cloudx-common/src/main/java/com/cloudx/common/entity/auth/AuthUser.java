package com.cloudx.common.entity.auth;

import java.util.Collection;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
  private String userInfoId;
  private String userName;
  private String fullName;
  private String sex;
  private String avatar;
  private String email;
  private String mobile;
  private String qqNumber;
  private String profile;
  private Date birthday;
  private String blogAddress;
  private Date createTime;
  private Date updateTime;
  private Date lastLoginTime;
  private String lastLoginIp;
  private Integer deptId;

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
