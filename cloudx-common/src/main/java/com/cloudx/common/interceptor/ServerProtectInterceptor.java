package com.cloudx.common.interceptor;

import com.cloudx.common.base.R;
import com.cloudx.common.constant.GatewayConstant;
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
 * <p>
 * 详见 Gateway 网关中 GlobalFilter 对通过网关转发请求的请求头处理
 *
 * @author chachae
 * @since 2020/4/16 21:33
 */
public class ServerProtectInterceptor implements HandlerInterceptor {

  /**
   * Gateway Token 拦截处理方法
   *
   * @param request  /
   * @param response /
   * @param handler  /
   * @return /
   * @throws IOException /
   */
  @Override
  public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull Object handler)
      throws IOException {
    String token = request.getHeader(GatewayConstant.TOKEN_HEADER);
    String rightToken = new String(Base64Utils.encode(GatewayConstant.TOKEN_VALUE.getBytes()));
    return rightToken.equals(token) || banRequest(response);
  }

  private boolean banRequest(HttpServletResponse response) throws IOException {
    HttpUtil.makeResponse(response, MediaType.APPLICATION_JSON_VALUE,
        HttpServletResponse.SC_FORBIDDEN, R.fail("请通过网关获取资源"));
    return false;
  }
}
