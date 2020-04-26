package com.cloudx.common.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reactor.core.publisher.Mono;

/**
 * HuTool HttpUtil 拓展
 *
 * @author chachae
 * @since 2020/4/24 18:21
 */
public class HttpUtil extends cn.hutool.http.HttpUtil {

  private HttpUtil() {
  }

  private static final String UNKNOW = "unknown";

  /**
   * 设置响应
   *
   * @param response    HttpServletResponse
   * @param contentType content-type
   * @param status      http状态码
   * @param value       响应内容
   * @throws IOException IOException
   */
  public static void makeResponse(HttpServletResponse response, String contentType,
      int status, Object value) throws IOException {
    response.setContentType(contentType);
    response.setStatus(status);
    response.getOutputStream().write(JSONObject.toJSONString(value).getBytes());
  }

  /**
   * 设置webflux模型响应
   *
   * @param response    ServerHttpResponse
   * @param contentType content-type
   * @param status      http状态码
   * @param value       响应内容
   * @return Mono<Void>
   */
  public static Mono<Void> makeWebFluxResponse(ServerHttpResponse response, String contentType,
      HttpStatus status, Object value) {
    response.setStatusCode(status);
    response.getHeaders().add(HttpHeaders.CONTENT_TYPE, contentType);
    DataBuffer dataBuffer = response.bufferFactory()
        .wrap(JSONObject.toJSONString(value).getBytes());
    return response.writeWith(Mono.just(dataBuffer));
  }

  /**
   * 获取HttpServletRequest
   *
   * @return HttpServletRequest
   */
  public static HttpServletRequest getHttpServletRequest() {
    return ((ServletRequestAttributes) Objects
        .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
  }

  /**
   * 获取请求头数据
   *
   * @return String
   */
  public static String getHeader(String key) {
    return getHttpServletRequest().getHeader(key);
  }

  /**
   * 获取请求数据
   *
   * @return String
   */
  public static String getParam(String key) {
    return getHttpServletRequest().getParameter(key);
  }

  /**
   * 获取请求IP
   *
   * @return String IP
   */
  public static String getHttpServletRequestIpAddress() {
    HttpServletRequest request = getHttpServletRequest();
    String ip = request.getHeader("x-forwarded-for");
    if (StrUtil.isBlank(ip) || UNKNOW.equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || UNKNOW.equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || UNKNOW.equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
  }
}
