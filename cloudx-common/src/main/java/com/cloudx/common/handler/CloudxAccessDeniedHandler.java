package com.cloudx.common.handler;

import com.cloudx.common.base.ResponseMap;
import com.cloudx.common.util.HttpUtil;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * 资源无权响应
 *
 * @author chachae
 * @since 2020/4/19 10:02
 */
public class CloudxAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException {
    ResponseMap responseMap = new ResponseMap();
    HttpUtil.makeResponse(
        response, MediaType.APPLICATION_JSON_VALUE,
        HttpServletResponse.SC_FORBIDDEN, responseMap.message("没有权限访问该资源"));
  }
}
