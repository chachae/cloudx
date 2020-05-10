package com.cloudx.auth.service;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码接口
 *
 * @author chachae
 * @since 2020/4/25 23:04
 */
public interface ICaptchaService {

  /**
   * 创建验证码
   */
  void create(HttpServletResponse response) throws IOException;

  /**
   * 校验验证码
   *
   * @param key   验证码key
   * @param value 用户输入值
   */
  void validateCode(String key, String value);
}
