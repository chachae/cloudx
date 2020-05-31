package com.cloudx.server.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudx.common.core.entity.QueryParam;
import com.cloudx.common.core.entity.system.SystemUser;
import java.util.List;

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
  IPage<SystemUser> pageSystemUser(QueryParam param, SystemUser user);

  /**
   * 通过用户名更新用户登陆时间
   *
   * @param username 用户名
   */
  void updateLoginTime(String username);

  /**
   * 新增用户
   *
   * @param user user
   */
  void createUser(SystemUser user);

  /**
   * 删除用户
   *
   * @param userIds 用户 id数组
   */
  void deleteUsers(String[] userIds);

  /**
   * 修改用户
   *
   * @param user user
   */
  void updateUser(SystemUser user);

  /**
   * 通过部门id查询用户id
   *
   * @param deptIds 部门id
   * @return List<String>
   */
  List<String> getUserIdByDeptIds(String[] deptIds);


  /**
   * 更新个人信息
   *
   * @param user 个人信息
   */
  void updateProfile(SystemUser user);

  /**
   * 更新用户头像
   *
   * @param avatar 用户头像
   */
  void updateAvatar(String avatar);

  /**
   * 更新用户密码
   *
   * @param password 新密码
   */
  void updatePassword(String password);

  /**
   * 重置密码
   *
   * @param usernames 用户集合
   */
  void resetPassword(String[] usernames);

}
