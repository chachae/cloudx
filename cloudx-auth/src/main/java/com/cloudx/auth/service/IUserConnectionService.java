package com.cloudx.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudx.auth.entity.UserConnection;
import java.util.List;

/**
 * 第三方登录数据操作阢接口
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/30 21:45
 */
public interface IUserConnectionService extends IService<UserConnection> {

  /**
   * 根据条件查询关联关系
   *
   * @param providerName   平台名称
   * @param providerUserId 平台用户ID
   * @return 关联关系
   */
  UserConnection selectByCondition(String providerName, String providerUserId);

  /**
   * 根据条件查询关联关系
   *
   * @param username 用户名
   * @return 关联关系
   */
  List<UserConnection> selectByCondition(String username);

  /**
   * 新增
   *
   * @param userConnection userConnection
   */
  void createUserConnection(UserConnection userConnection);

  /**
   * 删除
   *
   * @param username     username 用户名
   * @param providerName providerName 平台名称
   */
  void deleteByCondition(String username, String providerName);
}

