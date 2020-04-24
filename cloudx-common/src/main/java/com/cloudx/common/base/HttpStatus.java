package com.cloudx.common.base;

import lombok.AllArgsConstructor;

/**
 * 网络响应信息枚举
 *
 * @author chachae
 * @since 2020/4/13 20:57
 */
@AllArgsConstructor
public enum HttpStatus {

  // 请求成功
  OK(200, "请求成功"),
  // 请求异常
  BAD_REQUEST(400, "请求失败"),
  // 没有权限
  UNAUTHORIZED(401, "没有权限"),
  // 资源禁止访问
  FORBIDDEN(403, "禁止访问的资源"),
  // 资源不存在
  NOT_FOUND(404, "不存在的资源"),
  // 系统异常
  INTERNAL_SERVER_ERROR(500, "系统异常");

  private final int status;

  private final String message;

  public int val() {
    return this.status;
  }

  public String desc() {
    return this.message;
  }
}
