package com.cloudx.auth.service;

import com.cloudx.auth.exception.CaptchaException;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码接口 1
 *
 * @author chachae
 * @since 2020/4/25 23:04
 */
public interface ICaptchaService {

  /**
   * 创建验证码
   *
   * @return 验证码 Base64 编码
   */
  void create(HttpServletResponse response) throws IOException, CaptchaException;

  /**
   * 校验验证码
   *
   * @param key   验证码key
   * @param value 用户输入值
   */
  void validateCode(String key, String value) throws CaptchaException;
}
