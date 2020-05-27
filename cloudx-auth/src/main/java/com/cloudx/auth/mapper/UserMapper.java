package com.cloudx.auth.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudx.common.core.entity.dto.SystemUserDTO;
import com.cloudx.common.core.entity.system.SystemUser;

/**
 * @author chachae
 * @since 2020/4/21 21:35
 */
public interface UserMapper extends BaseMapper<SystemUser> {

  /**
   * 通过用户名获取用户信息
   *
   * @param username 用户名称
   * @return 用户信息：SystemUserDTO
   */
  SystemUserDTO findUserDto(String username);

}
