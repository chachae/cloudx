package com.cloudx.auth.filter;

import com.cloudx.common.constant.Oauth2Constant;
import com.cloudx.common.constant.ParamsConstant;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 验证码过滤器
 *
 * @author chachae
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CaptchaFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(@Nonnull HttpServletRequest httpServletRequest,
      @Nonnull HttpServletResponse httpServletResponse, @Nonnull FilterChain filterChain)
      throws ServletException, IOException {
    // 获取 Basic 认证请求头数据
    String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
    // 获取客户端 ID
    String clientId = getClientId(header);
    RequestMatcher matcher = new AntPathRequestMatcher(Oauth2Constant.Endpoint.OAUTH_TOKEN,
        HttpMethod.POST.toString());
    if (matcher.matches(httpServletRequest)
        && StringUtils
        .equalsIgnoreCase(httpServletRequest.getParameter(ParamsConstant.GRANT_TYPE),
            Oauth2Constant.GrantType.PASSWORD)
        && !StringUtils.equalsAnyIgnoreCase(clientId, "swagger")) {
      // todo 验证码校验
      filterChain.doFilter(httpServletRequest, httpServletResponse);
    } else {
      filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
  }

  /**
   * 获取客户端 ID
   * <p>
   * 密码模式：Authorization：Basic context，context由 client_id 和 secret 通过 base64 编码构成
   *
   * @param header 请求头内容
   * @return 客户端I D
   */
  private String getClientId(String header) {
    String clientId = null;
    try {
      byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
      byte[] decoded = Base64Utils.decode(base64Token);
      String token = new String(decoded, StandardCharsets.UTF_8);
      int colon = token.indexOf(':');
      clientId = (colon > 0 && colon < token.length()) ? token.substring(0, colon) : "";
    } catch (Exception ignore) {
    }
    return clientId;
  }
}
