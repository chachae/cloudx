package com.cloudx.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudx.auth.manager.UserManager;
import com.cloudx.auth.mapper.UserMapper;
import com.cloudx.common.core.constant.SystemUserConstant;
import com.cloudx.common.core.entity.auth.AuthUser;
import com.cloudx.common.core.entity.system.SystemUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Spring Security 密码认证实现类
 *
 * @author chachae
 * @since 2020/4/29 22:08
 */
@Service
@RequiredArgsConstructor
public class SystemUserDetailServiceImpl extends ServiceImpl<UserMapper, SystemUser> implements
    UserDetailsService {

  private final UserManager userManager;

  @Override
  public UserDetails loadUserByUsername(String username) {
    SystemUser systemUser = userManager.findByName(username);
    if (systemUser != null) {
      // 判断用户状态
      if (SystemUserConstant.STATUS_VALID.equals(systemUser.getStatus())) {
        // 设置用户权限信息
        List<GrantedAuthority> authorities;
        String expression = userManager.findUserPermissions(systemUser.getUsername());
        authorities = StrUtil.isNotBlank(expression) ? AuthorityUtils
            .commaSeparatedStringToAuthorityList(expression) : AuthorityUtils.NO_AUTHORITIES;
        // 设置认证用户属性
        AuthUser authUser = new AuthUser(systemUser.getUsername(), systemUser.getPassword(), true,
            true, true, true, authorities);
        // 拷贝认证用户属性
        BeanUtils.copyProperties(systemUser, authUser);
        return authUser;
      } else {
        return new AuthUser(systemUser.getUsername(), systemUser.getPassword(), false);
      }
    } else {
      throw new UsernameNotFoundException("");
    }
  }
}
