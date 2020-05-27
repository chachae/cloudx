package com.cloudx.server.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudx.common.core.entity.QueryParam;
import com.cloudx.common.core.entity.dto.SystemUserDTO;
import com.cloudx.common.core.entity.system.SystemUser;

/**
 * 用户业务接口
 *
 * @author chachae
 * @since 2020/4/30 19:56
 */
public interface IUserService extends IService<SystemUser> {

  /**
   * 通过用户名查询用户信息
   *
   * @param username 用户名
   * @return 用户信息
   */
  SystemUser getSystemUser(String username);

  /**
   * 分页查询用户数据
   *
   * @param param 分页参数
   * @param user  模糊条件
   * @return 分页对象
   */
  IPage<SystemUserDTO> pageSystemUser(QueryParam param, SystemUser user);

  /**
   * 通过用户名更新用户登陆时间
   *
   * @param username 用户名
   */
  void updateLoginTime(String username);

  SystemUserDTO selectSystemUser(SystemUser user);

  void insert(SystemUserDTO user);

  void update(Long userId, SystemUserDTO user);

  void delete(Long userId);
}
