package com.cloudx.common.core.util;

import cn.hutool.core.util.StrUtil;
import java.time.LocalDateTime;
import java.util.stream.IntStream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;

/**
 * 系统常用工具
 *
 * @author chachae
 * @since 2020/5/12 15:21
 */
public class SystemUtil {

  private SystemUtil() {
  }

  /**
   * 系统启动成功信息答应
   *
   * @param environment /
   */
  public static void printServerUpBanner(Environment environment) {
    String banner = "-----------------------------------------\n" +
        "服务启动成功，时间：" + DateUtil
        .formatFullTime(LocalDateTime.now(), DateUtil.FULL_TIME_SPLIT_PATTERN) + "\n" +
        "服务名称：" + environment.getProperty("spring.application.name") + "\n" +
        "端口号：" + environment.getProperty("server.port") + "\n" +
        "-----------------------------------------";
    System.out.println(banner);
  }

  /**
   * 驼峰转下划线
   *
   * @param value 待转换值
   * @return 结果
   */
  public static String camelToUnderscore(String value) {
    if (StringUtils.isBlank(value)) {
      return value;
    }
    String[] arr = StringUtils.splitByCharacterTypeCamelCase(value);
    if (arr.length == 0) {
      return value;
    }
    StringBuilder result = new StringBuilder();
    IntStream.range(0, arr.length).forEach(i -> {
      if (i != arr.length - 1) {
        result.append(arr[i]).append("_");
      } else {
        result.append(arr[i]);
      }
    });
    return StringUtils.lowerCase(result.toString());
  }

  /**
   * 下划线转驼峰
   *
   * @param value 待转换值
   * @return 结果
   */
  public static String underscoreToCamel(String value) {
    StringBuilder result = new StringBuilder();
    String[] arr = value.split(StrUtil.UNDERLINE);
    for (String s : arr) {
      result.append((String.valueOf(s.charAt(0))).toUpperCase()).append(s.substring(1));
    }
    return result.toString();
  }

}
