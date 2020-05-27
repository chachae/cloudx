package com.cloudx.server.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudx.common.core.entity.QueryParam;
import com.cloudx.common.core.entity.dto.SystemUserDTO;
import com.cloudx.common.core.entity.system.SystemUser;

/**
 * @author chachae
 * @since 2020/4/30 19:56
 */
public interface IUserService extends IService<SystemUser> {

  SystemUser selectByUsername(String userName);

  IPage<SystemUserDTO> pageSystemUser(QueryParam param, SystemUserDTO user);

  SystemUserDTO selectSystemUser(SystemUserDTO user);

  void insert(SystemUserDTO user);

  void update(Long userId, SystemUserDTO user);

  void delete(Long userId);
}
