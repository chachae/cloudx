package com.cloudx.server.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudx.common.core.entity.QueryParam;
import com.cloudx.common.core.entity.system.LoginLog;
import com.cloudx.common.core.entity.system.SystemUser;
import java.util.List;
import java.util.Map;

/**
 * 登录日志服务接口
 *
 * @author chachae
 * @since 2020-05-27 17:11:48
 */
public interface ILoginLogService extends IService<LoginLog> {

  /**
   * 获取登录日志分页信息
   *
   * @param loginLog 传参
   * @param param    分页数据
   * @return IPage<LoginLog>
   */
  IPage<LoginLog> pageLoginLogs(LoginLog loginLog, QueryParam param);

  /**
   * 保存用户登录日志
   *
   * @param loginLog 用户日志信息
   */
  void saveLoginLog(LoginLog loginLog);

  /**
   * 获取系统总访问次数
   *
   * @return Long
   */
  Long getTotalVisitCount();

  /**
   * 获取系统今日访问次数
   *
   * @return Long
   */
  Long getTodayVisitCount();


  /**
   * 获取系统今日访问 IP数
   *
   * @return Long
   */
  Long getTodayIp();

  /**
   * 获取系统近十天来的访问记录
   *
   * @param user 用户
   * @return 系统近十天来的访问记录
   */
  List<Map<String, Object>> getLastTenDaysVisitCount(SystemUser user);

  /**
   * 通过用户名获取该用户最近7次登录日志
   *
   * @param username 用户名
   * @return 登录日志集合
   */
  List<LoginLog> getUserLastSevenLoginLogs(String username);

  /**
   * 删除登录日志
   *
   * @param ids 日志 id集合
   */
  void deleteLoginLogs(String[] ids);
}