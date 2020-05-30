package com.cloudx.server.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudx.common.core.constant.SystemConstant;
import com.cloudx.common.core.entity.QueryParam;
import com.cloudx.common.core.entity.system.Log;
import com.cloudx.common.core.util.SortUtil;
import com.cloudx.server.system.mapper.LogMapper;
import com.cloudx.server.system.service.ILogService;
import com.cloudx.server.system.util.AddressUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 系统日志业务实现
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/30 12:33
 */
@Service
@RequiredArgsConstructor
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {

  private final ObjectMapper objectMapper;

  @Override
  public IPage<Log> findLogs(Log log, QueryParam request) {
    QueryWrapper<Log> queryWrapper = new QueryWrapper<>();

    if (StrUtil.isNotBlank(log.getUsername())) {
      queryWrapper.lambda().eq(Log::getUsername, log.getUsername().toLowerCase());
    }
    if (StrUtil.isNotBlank(log.getOperation())) {
      queryWrapper.lambda().like(Log::getOperation, log.getOperation());
    }
    if (StrUtil.isNotBlank(log.getLocation())) {
      queryWrapper.lambda().like(Log::getLocation, log.getLocation());
    }
    if (StrUtil.isNotBlank(log.getCreateTimeFrom()) && StrUtil
        .isNotBlank(log.getCreateTimeTo())) {
      queryWrapper.lambda()
          .ge(Log::getCreateTime, log.getCreateTimeFrom())
          .le(Log::getCreateTime, log.getCreateTimeTo());
    }

    Page<Log> page = new Page<>(request.getPageNum(), request.getPageSize());
    SortUtil.handlePageSort(request, page, "create_time", SystemConstant.ORDER_DESC, false);

    return this.page(page, queryWrapper);
  }

  @Override
  public void deleteLogs(String[] logIds) {
    List<String> list = Arrays.asList(logIds);
    baseMapper.deleteBatchIds(list);
  }

  @Override
  public void saveLog(ProceedingJoinPoint point, Method method, String ip, String operation,
      String username, long start) {
    Log log = new Log();
    log.setIp(ip);

    log.setUsername(username);
    log.setTime(System.currentTimeMillis() - start);
    log.setOperation(operation);

    String className = point.getTarget().getClass().getName();
    String methodName = method.getName();
    log.setMethod(className + "." + methodName + "()");

    Object[] args = point.getArgs();
    LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
    String[] paramNames = u.getParameterNames(method);
    if (args != null && paramNames != null) {
      StringBuilder params = new StringBuilder();
      params = handleParams(params, args, Arrays.asList(paramNames));
      log.setParams(params.toString());
    }
    log.setCreateTime(new Date());
    log.setLocation(AddressUtil.getCityInfo(ip));
    // 保存系统日志
    save(log);
  }

  @SuppressWarnings("all")
  private StringBuilder handleParams(StringBuilder params, Object[] args, List paramNames) {
    try {
      for (int i = 0; i < args.length; i++) {
        if (args[i] instanceof Map) {
          Set set = ((Map) args[i]).keySet();
          List<Object> list = new ArrayList<>();
          List<Object> paramList = new ArrayList<>();
          for (Object key : set) {
            list.add(((Map) args[i]).get(key));
            paramList.add(key);
          }
          return handleParams(params, list.toArray(), paramList);
        } else {
          if (args[i] instanceof Serializable) {
            Class<?> aClass = args[i].getClass();
            try {
              aClass.getDeclaredMethod("toString", new Class[]{null});
              // 如果不抛出 NoSuchMethodException 异常则存在 toString 方法 ，安全的 writeValueAsString ，否则 走 Object的 toString方法
              params.append(" ").append(paramNames.get(i)).append(": ")
                  .append(objectMapper.writeValueAsString(args[i]));
            } catch (NoSuchMethodException e) {
              params.append(" ").append(paramNames.get(i)).append(": ")
                  .append(objectMapper.writeValueAsString(args[i].toString()));
            }
          } else if (args[i] instanceof MultipartFile) {
            MultipartFile file = (MultipartFile) args[i];
            params.append(" ").append(paramNames.get(i)).append(": ").append(file.getName());
          } else {
            params.append(" ").append(paramNames.get(i)).append(": ").append(args[i]);
          }
        }
      }
    } catch (Exception ignore) {
      params.append("参数解析失败");
    }
    return params;
  }
}
