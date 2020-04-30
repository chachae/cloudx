package com.cloudx.server.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudx.common.base.QueryParam;
import com.cloudx.common.entity.dto.SystemUserDTO;
import com.cloudx.common.entity.system.SystemUser;

/**
 * @author chachae
 * @since 2020/4/30 19:56
 */
public interface IUserService extends IService<SystemUser> {

  SystemUser selectByUserName(String userName);

  IPage<SystemUserDTO> pageSystemUser(QueryParam param, SystemUserDTO user);

  SystemUserDTO selectSystemUser(SystemUserDTO user);

  void insert(SystemUserDTO user);

  void update(Long userId, SystemUserDTO user);
}
