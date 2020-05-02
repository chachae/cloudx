package com.cloudx.common.handler;

import com.cloudx.common.base.R;
import com.cloudx.common.util.HttpUtil;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * 认证过的用户访问无权限资源时的异常
 * <p>
 * {@link AccessDeniedHandler }
 *
 * @author chachae
 * @see <a href="https://blog.csdn.net/jkjkjkll/article/details/79975975"></a>
 * @since 2020/4/23 07:08
 */
public class CloudxAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException {
    HttpUtil.makeResponse(
        response, MediaType.APPLICATION_JSON_VALUE,
        HttpServletResponse.SC_FORBIDDEN, R.fail("没有权限访问该资源"));
  }
}
