package com.cloudx.auth.filter;

import cn.hutool.core.util.StrUtil;
import com.cloudx.auth.exception.CaptchaException;
import com.cloudx.auth.service.ICaptchaService;
import com.cloudx.common.core.constant.EndpointConstant;
import com.cloudx.common.core.constant.GrantTypeConstant;
import com.cloudx.common.core.constant.ParamsConstant;
import com.cloudx.common.core.entity.R;
import com.cloudx.common.core.util.HttpUtil;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * OAUTH2 密码模式过滤器
 *
 * @author chachae
 * @since 2020/04/26 13:20
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PswGrantFilter extends OncePerRequestFilter {

  private final ICaptchaService captchaService;

  @Override
  protected void doFilterInternal(@Nonnull HttpServletRequest httpServletRequest,
      @Nonnull HttpServletResponse httpServletResponse, @Nonnull FilterChain filterChain)
      throws ServletException, IOException {
    // 获取 Basic 认证请求头数据
    String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
    // 获取客户端 ID
    String clientId = getClientId(header);
    RequestMatcher matcher = new AntPathRequestMatcher(EndpointConstant.OAUTH_TOKEN,
        HttpMethod.POST.toString());
    if (matcher.matches(httpServletRequest)
        // 密码模式
        && StrUtil.equalsIgnoreCase(httpServletRequest.getParameter(ParamsConstant.GRANT_TYPE),
        GrantTypeConstant.PASSWORD) && !StrUtil.equalsAnyIgnoreCase(clientId, "swagger")) {
      try {
        // 验证码校验
        validateCaptcha(httpServletRequest);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
      } catch (CaptchaException e) {
        HttpUtil.makeResponse(httpServletResponse, MediaType.APPLICATION_JSON_VALUE,
            HttpServletResponse.SC_INTERNAL_SERVER_ERROR, R.fail(e.getMessage()));
        log.error(e.getMessage(), e);
      }
    } else {
      filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
  }

  private void validateCaptcha(HttpServletRequest request) {
    String key = request.getParameter(ParamsConstant.CAPTCHA_KEY);
    String code = request.getParameter(ParamsConstant.CAPTCHA_CODE);
    captchaService.validateCode(key, code);
  }

  /**
   * 获取客户端 ID
   * <p>
   * 密码模式：Authorization：Basic context，context由 client_id 和 secret 通过 base64 编码构成
   *
   * @param header 请求头内容
   * @return 客户端 ID
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
