package com.cloudx.auth.exception;

/**
 * 验证码异常
 *
 * @author chachae
 * @since 2020/4/25 23:05
 */
public class CaptchaException extends RuntimeException {

  public CaptchaException(String message) {
    super(message);
  }
}
