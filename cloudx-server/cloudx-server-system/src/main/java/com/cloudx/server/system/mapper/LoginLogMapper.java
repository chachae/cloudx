package com.cloudx.server.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudx.common.core.entity.system.LoginLog;
import com.cloudx.common.core.entity.system.SystemUser;
import java.util.List;
import java.util.Map;

/**
 * 登录日志数据库访问层
 *
 * @author chachae
 * @since 2020-05-27 17:11:48
 */
public interface LoginLogMapper extends BaseMapper<LoginLog> {

  /**
   * 获取表中数据总量
   *
   * @return 访问总量
   */
  Long countTotalVisit();


  /**
   * 获取今日访问量
   *
   * @return 今日访问量
   */
  Long countTodayVisit();

  /**
   * 获取今天的IP量
   *
   * @return 今日IP量
   */
  Long countTodayIp();

  /**
   * 获取系统近十天来的访问记录
   *
   * @param user 用户
   * @return 系统近十天来的访问记录
   */
  List<Map<String, Object>> selectLastTenDaysVisitCount(SystemUser user);

}