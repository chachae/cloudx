package com.cloudx.server.system.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志记录注解
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/30 16:20
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerEndpoint {

  String operation() default "";

  String exceptionMessage() default "系统内部异常";
}
