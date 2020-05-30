package com.cloudx.server.system.aspect;

import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * 切面基类
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/30 16:01
 */
public abstract class BaseAspectSupport {

  Method resolveMethod(ProceedingJoinPoint point) {
    MethodSignature signature = (MethodSignature) point.getSignature();
    Class<?> targetClass = point.getTarget().getClass();

    Method method = getDeclaredMethod(targetClass, signature.getName(),
        signature.getMethod().getParameterTypes());
    if (method == null) {
      throw new IllegalStateException("无法解析目标方法: " + signature.getMethod().getName());
    }
    return method;
  }

  private Method getDeclaredMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
    try {
      return clazz.getDeclaredMethod(name, parameterTypes);
    } catch (NoSuchMethodException e) {
      Class<?> superClass = clazz.getSuperclass();
      if (superClass != null) {
        return getDeclaredMethod(superClass, name, parameterTypes);
      }
    }
    return null;
  }
}