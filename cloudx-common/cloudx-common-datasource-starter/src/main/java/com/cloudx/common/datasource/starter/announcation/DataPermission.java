package com.cloudx.common.datasource.starter.announcation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 过滤数据权限注解
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/29 16:38
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataPermission {

  /**
   * mapper层需要数据权限过滤的方法名集合
   *
   * @return 方法名数组
   */
  String[] methods() default {};

  /**
   * mapper层需要数据过滤的方法名前缀， 比如指定为find，表示所有以find开头的方法 都会进行数据权限过滤
   *
   * @return 方法名前缀
   */
  String methodPrefix() default "";

  /**
   * 数据权限关联字段 目前系统数据权限通过dept_id关联
   *
   * @return String
   */
  String field() default "dept_id";
}

