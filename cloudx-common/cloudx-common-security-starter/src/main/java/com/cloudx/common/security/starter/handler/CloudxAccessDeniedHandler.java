package com.cloudx.common.security.starter.handler;

import com.cloudx.common.core.entity.R;
import com.cloudx.common.core.util.HttpUtil;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * @author chachae
 */
public class CloudxAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException {
    HttpUtil.makeJsonResponse(response, HttpServletResponse.SC_FORBIDDEN,
        R.fail("没有权限访问该资源"));
  }
}
