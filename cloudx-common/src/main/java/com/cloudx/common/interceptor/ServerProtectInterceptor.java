package com.cloudx.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.cloudx.common.base.HttpStatus;
import com.cloudx.common.base.Response;
import com.cloudx.common.constant.SystemConstant.GatewayConstant;
import com.cloudx.common.util.HttpUtil;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 微服务请求拦截器
 *
 * @author chachae
 * @since 2020/4/16
 */
public class ServerProtectInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull Object handler)
      throws IOException {
    // 从请求头中获取 Gateway token
    String token = request.getHeader(GatewayConstant.TOKEN_HEADER);
    // Base64 编码正确的网关 token
    String rightToken = new String(Base64Utils.encode(GatewayConstant.TOKEN_VALUE.getBytes()));
    // 校验 Gateway Token 的正确性
    if (StrUtil.equals(rightToken, token)) {
      return true;
    } else {
      HttpUtil.makeResponse(response, MediaType.APPLICATION_JSON_VALUE,
          HttpServletResponse.SC_FORBIDDEN, Response.fail(HttpStatus.FORBIDDEN.val(), "请通过网关获取资源"));
      return false;
    }
  }
}
