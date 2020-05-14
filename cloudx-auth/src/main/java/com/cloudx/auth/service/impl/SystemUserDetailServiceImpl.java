package com.cloudx.auth.service.impl;

import cn.hutool.core.util.StrUtil;
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

  private final MenuMapper menuMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    SystemUser systemUser = findByUserName(username);
    if (systemUser != null) {
      // 判断用户状态
      if (SystemUserConstant.STATUS_VALID.equals(systemUser.getStatus())) {
        // 设置用户权限信息
        List<GrantedAuthority> authorities;
        String expression = selectExpressionByUserId(systemUser.getUserId());
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

  /**
   * 通过用户名查询用户信息
   *
   * @param username 用户名
   * @return 用户信息：SystemUser
   */
  private SystemUser findByUserName(String username) {
    LambdaQueryWrapper<SystemUser> query = new LambdaQueryWrapper<>();
    query.eq(SystemUser::getUsername, username);
    return getOne(query);
  }

  /**
   * 通过用户id获取用户权限表达式
   *
   * @param userId 用户id
   * @return 用户权限表达式：String
   */
  private String selectExpressionByUserId(Long userId) {
    List<Menu> menus = menuMapper.selectMenusByUserId(userId);
    return menus.stream().map(Menu::getExpression).collect(Collectors.joining(","));
  }
}
