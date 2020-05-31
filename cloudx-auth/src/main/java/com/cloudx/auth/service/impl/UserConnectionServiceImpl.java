package com.cloudx.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudx.auth.entity.UserConnection;
import com.cloudx.auth.mapper.UserConnectionMapper;
import com.cloudx.auth.service.IUserConnectionService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 第三方登录实现
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/30 21:58
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserConnectionServiceImpl extends
    ServiceImpl<UserConnectionMapper, UserConnection> implements
    IUserConnectionService {

  @Override
  public UserConnection selectByCondition(String providerName, String providerUserId) {
    LambdaQueryWrapper<UserConnection> wrapper = new LambdaQueryWrapper<>();
    wrapper
        .eq(UserConnection::getProviderName, providerName)
        .eq(UserConnection::getProviderUserId, providerUserId);
    return this.baseMapper.selectOne(wrapper);
  }

  @Override
  public List<UserConnection> selectByCondition(String username) {
    LambdaQueryWrapper<UserConnection> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(UserConnection::getUserName, username);
    return this.baseMapper.selectList(wrapper);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createUserConnection(UserConnection userConnection) {
    System.out.println("ins：" + userConnection);
    this.baseMapper.insert(userConnection);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteByCondition(String username, String providerName) {
    LambdaQueryWrapper<UserConnection> wrapper = new LambdaQueryWrapper<>();
    wrapper
        .eq(UserConnection::getUserName, username)
        .eq(UserConnection::getProviderName, providerName);
    this.remove(wrapper);
  }

}
