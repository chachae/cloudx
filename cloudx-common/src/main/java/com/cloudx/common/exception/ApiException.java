package com.cloudx.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * 统一的业务异常
 *
 * @author chachae
 * @since 2020/4/13 20:40
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class ApiException extends RuntimeException {

  private static final long serialVersionUID = 5970149403350193074L;

  /**
   * 异常响应代码
   */
  private Integer status;

  /**
   * 异常信息
   */
  private String msg;

  private ApiException(Integer status, String msg) {
    this.status = status;
    this.msg = msg;
  }

  /**
   * 系统异常（500）
   *
   * @param msg 异常消息
   * @return /
   */
  public static ApiException systemError(String msg) {
    return new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
  }

  /**
   * 参数 / 业务异常（400）
   *
   * @param msg 异常消息
   * @return /
   */
  public static ApiException serviceError(String msg) {
    return new ApiException(org.springframework.http.HttpStatus.BAD_REQUEST.value(), msg);
  }

  /**
   * 自定义异常
   *
   * @param status 异常代码
   * @param msg    异常消息
   * @return /
   */
  public static ApiException error(int status, String msg) {
    return new ApiException(status, msg);
  }

}
