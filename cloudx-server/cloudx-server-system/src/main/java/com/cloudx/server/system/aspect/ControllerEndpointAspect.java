package com.cloudx.server.system.aspect;

import cn.hutool.core.util.StrUtil;
import com.cloudx.common.core.exception.ApiException;
import com.cloudx.common.core.util.HttpUtil;
import com.cloudx.common.core.util.RegexpUtil;
import com.cloudx.common.core.util.SecurityUtil;
import com.cloudx.server.system.annotation.ControllerEndpoint;
import com.cloudx.server.system.service.ILogService;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 系统日志切面
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/30 16:50
 */
@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class ControllerEndpointAspect extends BaseAspectSupport {

  private final ILogService logService;

  @Pointcut("@annotation(com.cloudx.server.system.annotation.ControllerEndpoint)")
  private void pointcut() {
    // do nothing......
  }

  /**
   * 环绕增强
   *
   * @param point 切入点
   * @return /
   */
  @Around("pointcut()")
  public Object around(ProceedingJoinPoint point) {
    Object result;
    Method targetMethod = resolveMethod(point);
    ControllerEndpoint annotation = targetMethod.getAnnotation(ControllerEndpoint.class);
    String operation = annotation.operation();
    long start = System.currentTimeMillis();
    try {
      result = point.proceed();
      if (StrUtil.isNotBlank(operation)) {
        String username = SecurityUtil.getCurrentUsername();
        String ip = HttpUtil.getIpAddress();
        logService.saveLog(point, targetMethod, ip, operation, username, start);
      }
      return result;
    } catch (Throwable throwable) {
      log.error(throwable.getMessage(), throwable);
      String reason = annotation.exceptionMessage();
      String msg = throwable.getMessage();
      String error = RegexpUtil.hasChinese(msg) ? reason + " : " + msg : reason;
      throw new ApiException(error);
    }
  }
}

