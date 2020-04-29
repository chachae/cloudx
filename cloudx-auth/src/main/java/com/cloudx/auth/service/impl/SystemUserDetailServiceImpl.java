package com.cloudx.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudx.auth.constant.SystemUserConstant;
import com.cloudx.auth.mapper.MenuMapper;
import com.cloudx.auth.mapper.UserMapper;
import com.cloudx.common.entity.auth.AuthUser;
import com.cloudx.common.entity.system.Menu;
import com.cloudx.common.entity.system.SystemUser;
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
 * @author chachae
 * @since 2020/4/29 22:08
 */
@Service
@RequiredArgsConstructor
public class SystemUserDetailServiceImpl extends ServiceImpl<UserMapper, SystemUser> implements
    UserDetailsService {

  private final MenuMapper menuMapper;

  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    SystemUser systemUser = findByUserName(userName);
    if (systemUser != null) {
      String expression = selectExpressionByUserId(systemUser.getUserId());
      boolean notLocked = false;
      if (SystemUserConstant.STATUS_VALID.equals(systemUser.getStatus())) {
        notLocked = true;
      }
      AuthUser authUser = new AuthUser(systemUser.getUserName(), systemUser.getPassword(), true,
          true, true,
          notLocked,
          AuthorityUtils.commaSeparatedStringToAuthorityList(expression));
      BeanUtils.copyProperties(systemUser, authUser);
      return authUser;
    } else {
      throw new UsernameNotFoundException("");
    }
  }

  private SystemUser findByUserName(String userName) {
    LambdaQueryWrapper<SystemUser> qw = new LambdaQueryWrapper<>();
    qw.eq(SystemUser::getUserName, userName);
    return this.getOne(qw);
  }

  private String selectExpressionByUserId(Long userId) {
    List<Menu> menus = menuMapper.selectMenusByUserId(userId);
    return menus.stream().map(Menu::getExpression).collect(Collectors.joining(","));
  }
}
