package com.cloudx.common.core.util;

import static com.alibaba.fastjson.JSON.toJSONBytes;

import cn.hutool.core.net.NetUtil;
import com.cloudx.common.core.entity.UserAgent;
import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@Slf4j
public class HttpUtil extends cn.hutool.http.HttpUtil {

  private HttpUtil() {
  }

  private static final String[] LOCALHOST = {"unknown", "127.0.0.1", "0:0:0:0:0:0:0:1"};

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
    response.getOutputStream().write(toJSONBytes(value));
  }

  /**
   * 设置JSON类型响应
   *
   * @param response HttpServletResponse
   * @param status   http状态码
   * @param value    响应内容
   * @throws IOException IOException
   */
  public static void makeJsonResponse(HttpServletResponse response, int status, Object value)
      throws IOException {
    makeResponse(response, MediaType.APPLICATION_JSON_VALUE, status, value);
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
        .wrap(toJSONBytes(value));
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
   * 获取请求头值
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
   * 获取请求地址
   *
   * @return String
   */
  public static String getRequestUrl() {
    return getHttpServletRequest().getRequestURI();
  }

  /**
   * 获取请求IP
   *
   * @return String IP
   */
  public static String getIpAddress() {
    HttpServletRequest request = getHttpServletRequest();
    String ip = request.getHeader(HttpHeaders.X_FORWARDED_FOR);
    if (ip == null || ip.length() == 0 || LOCALHOST[0].equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || LOCALHOST[0].equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || LOCALHOST[0].equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    if (LOCALHOST[1].equalsIgnoreCase(ip) || LOCALHOST[2].equalsIgnoreCase(ip)) {
      ip = NetUtil.getLocalhostStr();
    }
    return ip;
  }

  /**
   * 获取当前用户浏览器 UserAgent 信息
   *
   * @return 用户标识对象
   */
  public static UserAgent getUserAgent() {
    String userAgent = getHeader(HttpHeaders.USER_AGENT);
    return parseUserAgent(userAgent);
  }

  private static UserAgent parseUserAgent(String ua) {
    try {
      StringBuilder userAgent = new StringBuilder("[");
      userAgent.append(ua);
      userAgent.append("]");
      int indexOfMac = userAgent.indexOf("Mac OS X");
      int indexOfWindows = userAgent.indexOf("Windows NT");
      int indexOfIe = userAgent.indexOf("MSIE");
      int indexOfIe11 = userAgent.indexOf("rv:");
      int indexOfFirefox = userAgent.indexOf("Firefox");
      int indexOfSogou = userAgent.indexOf("MetaSr");
      int indexOfChrome = userAgent.indexOf("Chrome");
      int indexOfSafari = userAgent.indexOf("Safari");
      int indexOfEdge = userAgent.indexOf("Edg");
      boolean isMac = indexOfMac > 0;
      boolean isWindows = indexOfWindows > 0;
      boolean isLinux = userAgent.indexOf("Linux") > 0;
      boolean containIe = indexOfIe > 0 || (isWindows && (indexOfIe11 > 0));
      boolean containFireFox = indexOfFirefox > 0;
      boolean containSogou = indexOfSogou > 0;
      boolean containChrome = indexOfChrome > 0;
      boolean containSafari = indexOfSafari > 0;
      boolean containEdge = indexOfEdge > 0;
      String browser = "";
      if (containSogou) {
        if (containIe) {
          browser = "搜狗" + userAgent.substring(indexOfIe, indexOfIe + "IE x.x".length());
        } else if (containChrome) {
          browser = "搜狗" + userAgent.substring(indexOfChrome, indexOfChrome + "Chrome/xx".length());
        }
      } else if (containEdge) {
        browser = userAgent.substring(indexOfEdge, indexOfEdge + "Edg/xx".length());
        browser = browser.replace("Edg", "Microsoft Edge");
      } else if (containChrome) {
        browser = userAgent.substring(indexOfChrome, indexOfChrome + "Chrome/xx".length());
      } else if (containSafari) {
        int indexOfSafariVersion = userAgent.indexOf("Version");
        browser = "Safari "
            + userAgent
            .substring(indexOfSafariVersion, indexOfSafariVersion + "Version/x.x.x.x".length());
      } else if (containFireFox) {
        browser = userAgent.substring(indexOfFirefox, indexOfFirefox + "Firefox/xx".length());
      } else if (containIe) {
        if (indexOfIe11 > 0) {
          browser = "IE 11";
        } else {
          browser = userAgent.substring(indexOfIe, indexOfIe + "IE x.x".length());
        }
      }
      String os = "";
      if (isMac) {
        os = userAgent.substring(indexOfMac, indexOfMac + "MacOS X xxxxxxxx".length());
        os = os.replace("_", ".");
      } else if (isLinux) {
        os = "Linux";
      } else if (isWindows) {
        os = "Windows ";
        String version = userAgent.substring(indexOfWindows + "Windows NT".length(), indexOfWindows
            + "Windows NTx.x".length());
        version = version.trim();
        switch (version) {
          case "5.0":
            os += "2000";
            break;
          case "5.1":
            os += "XP";
            break;
          case "5.2":
            os += "2003";
            break;
          case "6.0":
            os += "Vista";
            break;
          case "6.1":
            os += "7";
            break;
          case "6.2":
            os += "8";
            break;
          case "6.3":
            os += "8.1";
            break;
          case "10":
            os += "10";
            break;
          default:
            os = os;
        }
      }
      return new UserAgent(StringUtils.replace(browser, "/", " "), os);
    } catch (Exception e) {
      final String unknown = "未知";
      log.error("获取登录信息失败：{}", e.getMessage());
      return new UserAgent(unknown, unknown);
    }
  }
}
