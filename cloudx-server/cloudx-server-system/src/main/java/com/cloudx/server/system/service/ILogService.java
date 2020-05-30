package com.cloudx.server.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudx.common.core.constant.SystemConstant;
import com.cloudx.common.core.entity.QueryParam;
import com.cloudx.common.core.entity.system.Log;
import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

/**
 * 系统日志业务接口
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/30 12:09
 */
public interface ILogService extends IService<Log> {

  /**
   * 查询操作日志分页
   *
   * @param log   日志
   * @param param QueryParam
   * @return IPage<SystemLog>
   */
  IPage<Log> findLogs(Log log, QueryParam param);

  /**
   * 删除操作日志
   *
   * @param logIds 日志 ID集合
   */
  void deleteLogs(String[] logIds);

  /**
   * 异步保存操作日志
   *
   * @param point     切点
   * @param method    Method
   * @param ip        ip
   * @param operation 操作内容
   * @param username  操作用户
   * @param start     开始时间
   */
  @Async(SystemConstant.ASYNC_POOL)
  void saveLog(ProceedingJoinPoint point, Method method, String ip, String operation,
      String username, long start);
}
