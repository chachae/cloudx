package com.cloudx.auth.translator;

import com.cloudx.common.base.ResponseMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidScopeException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * 异常翻译
 *
 * @author MrBird
 */
@Slf4j
@Component
public class CloudxWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

  @Override
  public ResponseEntity<?> translate(Exception e) {
    ResponseEntity.BodyBuilder status = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
    ResponseMap response = new ResponseMap();
    String message = "认证失败";
    log.error(message, e);
    if (e instanceof UnsupportedGrantTypeException) {
      message = "不支持该认证类型";
      return status.body(response.message(message));
    }
    if (e instanceof InvalidTokenException
        && StringUtils.containsIgnoreCase(e.getMessage(), "Invalid refresh token (expired)")) {
      message = "刷新令牌已过期，请重新登录";
      return status.body(response.message(message));
    }
    if (e instanceof InvalidScopeException) {
      message = "不是有效的scope值";
      return status.body(response.message(message));
    }
    if (e instanceof InvalidGrantException) {
      if (StringUtils.containsIgnoreCase(e.getMessage(), "Invalid refresh token")) {
        message = "refresh token无效";
        return status.body(response.message(message));
      }
      if (StringUtils.containsIgnoreCase(e.getMessage(), "locked")) {
        message = "用户已被锁定，请联系管理员";
        return status.body(response.message(message));
      }
      message = "用户名或密码错误";
      return status.body(response.message(message));
    }
    return status.body(response.message(message));
  }
}
