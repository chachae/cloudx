package com.cloudx.common.interceptor;

import com.cloudx.common.constant.SystemConstant.GatewayConstant;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
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
    if (StringUtils.equals(rightToken, token)) {
      return true;
    } else {
      // todo 完成网关后开启
//      HttpUtil.makeResponse(response, MediaType.APPLICATION_JSON_VALUE,
//          HttpServletResponse.SC_FORBIDDEN, Result.fail(HttpStatus.FORBIDDEN.value(), "请通过网关获取资源"));
//      return false;
      return true;
    }
  }
}
