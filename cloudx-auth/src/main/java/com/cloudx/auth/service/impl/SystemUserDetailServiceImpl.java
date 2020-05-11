package com.cloudx.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudx.auth.mapper.MenuMapper;
import com.cloudx.auth.mapper.UserMapper;
import com.cloudx.common.core.constant.SystemUserConstant;
import com.cloudx.common.core.entity.auth.AuthUser;
import com.cloudx.common.core.entity.system.Menu;
import com.cloudx.common.core.entity.system.SystemUser;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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

  private final MenuMapper menuMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    SystemUser systemUser = findByUserName(username);
    if (systemUser != null) {
      String expression = selectExpressionByUserId(systemUser.getUserId());
      boolean notLocked = false;
      if (SystemUserConstant.STATUS_VALID.equals(systemUser.getStatus())) {
        notLocked = true;
      }
      AuthUser authUser = new AuthUser(systemUser.getUsername(), systemUser.getPassword(), true,
          true, true,
          notLocked,
          AuthorityUtils.commaSeparatedStringToAuthorityList(expression));
      BeanUtils.copyProperties(systemUser, authUser);
      return authUser;
    } else {
      throw new UsernameNotFoundException("");
    }
  }

  private SystemUser findByUserName(String username) {
    LambdaQueryWrapper<SystemUser> query = new LambdaQueryWrapper<>();
    query.eq(SystemUser::getUsername, username);
    return getOne(query);
  }

  private String selectExpressionByUserId(Long userId) {
    List<Menu> menus = menuMapper.selectMenusByUserId(userId);
    return menus.stream().map(Menu::getExpression).collect(Collectors.joining(","));
  }
}
