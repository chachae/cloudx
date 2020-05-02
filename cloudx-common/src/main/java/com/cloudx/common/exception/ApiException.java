package com.cloudx.common.exception;

/**
 * 统一的业务异常
 *
 * @author chachae
 * @since 2020/4/13 20:40
 */

public class ApiException extends RuntimeException {

  private static final long serialVersionUID = -6916154462432027437L;

  public ApiException(String message) {
    super(message);
  }
}
