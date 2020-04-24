package com.cloudx.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudx.common.entity.system.SystemUser;
import java.util.List;

/**
 * @author chachae
 * @since 2020/4/21 21:36
 */
public interface IUserService extends IService<SystemUser> {

  SystemUser findByUsername(String username);

  List<String> findByEmployeeId(Long employeeId);

}
