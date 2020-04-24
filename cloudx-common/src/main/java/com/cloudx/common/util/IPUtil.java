package com.cloudx.common.util;

import com.google.common.net.HttpHeaders;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chachae
 * @since 2019/12/21 11:01
 */
@Slf4j
public class IPUtil {

  private IPUtil() {
  }

  private static final String UNKNOWN = "unknown";

  /**
   * 获取IP
   */
  public static String getIpAddr(HttpServletRequest request) {
    String ip = request.getHeader(HttpHeaders.X_FORWARDED_FOR);
    if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
  }
}
