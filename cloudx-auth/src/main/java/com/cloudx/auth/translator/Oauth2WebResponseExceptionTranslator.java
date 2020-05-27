package com.cloudx.auth.translator;

import cn.hutool.core.util.StrUtil;
import com.cloudx.common.core.entity.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidScopeException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * OAUTH 2 异常翻译
 * <p>
 * 对 OAuth2Exception 异常的响应数据进行统一处理，默认是 xml 响应，不友好且不统一。 具体是实现{@link WebResponseExceptionTranslator}接口的方法，
 * 可以自定义返回的数据格式， 这里使用 Spring 提供的{@link ResponseEntity}构建响应数据体
 * </p>
 *
 * @author chachae
 * @see <a href="https://blog.csdn.net/qq_31063463/article/details/83752459">参考资料</a>
 * @since 2020/04/15 01:28
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class Oauth2WebResponseExceptionTranslator implements
    WebResponseExceptionTranslator {

  @Override
  public ResponseEntity<R<String>> translate(Exception e) {
    ResponseEntity.BodyBuilder status = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
    String message = "认证失败";
    log.error(message, e);
    if (e instanceof UnsupportedGrantTypeException) {
      message = "不支持该认证类型";
      return status.body(R.fail(message));
    }
    if (e instanceof InvalidTokenException
        && StrUtil.containsIgnoreCase(e.getMessage(), "Invalid refresh token (expired)")) {
      message = "刷新令牌已过期，请重新登录";
      return status.body(R.fail(message));
    }
    if (e instanceof InvalidScopeException) {
      message = "不是有效的scope值";
      return status.body(R.fail(message));
    }
    if (e instanceof InvalidGrantException) {
      if (StrUtil.containsIgnoreCase(e.getMessage(), "Invalid refresh token")) {
        message = "refresh token无效";
        return status.body(R.fail(message));
      }
      if (StrUtil.containsIgnoreCase(e.getMessage(), "locked")) {
        message = "用户已被锁定，请联系管理员";
        return status.body(R.fail(message));
      }
      message = "用户名或密码错误";
      return status.body(R.fail(message));
    }
    return status.body(R.fail(message));
  }
}
