package com.cloudx.auth.filter;

import com.cloudx.common.base.ResponseMap;
import com.cloudx.common.constant.Oauth2Constant;
import com.cloudx.common.util.HttpUtil;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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
import org.springframework.http.MediaType;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 验证码过滤器
 *
 * @author chachae
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ValidateCodeFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(@Nonnull HttpServletRequest httpServletRequest,
      @Nonnull HttpServletResponse httpServletResponse, @Nonnull FilterChain filterChain)
      throws ServletException, IOException {
    String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
    String clientId = getClientId(header, httpServletRequest);

    RequestMatcher matcher = new AntPathRequestMatcher(Oauth2Constant.Endpoint.OAUTH_TOKEN,
        HttpMethod.POST.toString());
    if (matcher.matches(httpServletRequest)
        && StringUtils
        .equalsIgnoreCase(httpServletRequest.getParameter(Oauth2Constant.Params.GRANT_TYPE),
            Oauth2Constant.GrantType.PASSWORD)
        && !StringUtils.equalsAnyIgnoreCase(clientId, "swagger")) {
      try {
        filterChain.doFilter(httpServletRequest, httpServletResponse);
      } catch (Exception e) {
        ResponseMap febsResponse = new ResponseMap();
        HttpUtil.makeResponse(httpServletResponse, MediaType.APPLICATION_JSON_VALUE,
            HttpServletResponse.SC_INTERNAL_SERVER_ERROR, febsResponse.message(e.getMessage()));
        log.error(e.getMessage(), e);
      }
    } else {
      filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
  }

  private String getClientId(String header, HttpServletRequest request) {
    String clientId = "";
    try {
      byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
      byte[] decoded;
      decoded = Base64.getDecoder().decode(base64Token);
      String token = new String(decoded, StandardCharsets.UTF_8);
      int delim = token.indexOf(":");
      if (delim != -1) {
        clientId = new String[]{token.substring(0, delim), token.substring(delim + 1)}[0];
      }
    } catch (Exception ignore) {
    }
    return clientId;
  }
}
