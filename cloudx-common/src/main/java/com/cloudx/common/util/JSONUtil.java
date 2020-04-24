package com.cloudx.common.util;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chachae
 * @since 2020/1/16 17:23
 */
@Slf4j
public class JSONUtil {

  private JSONUtil() {
  }

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  // 加载Jackson配置
  static {
    // 在反序列化时忽略在 json 中存在但 Java 对象不存在的属性
    OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    // 在序列化时日期格式默认为 yyyy-MM-dd'T'HH:mm:ss
    OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    // 在序列化时忽略值为 null 的属性
    OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    // 忽略值为默认值的属性
    OBJECT_MAPPER.setDefaultPropertyInclusion(JsonInclude.Include.NON_DEFAULT);
  }

  /**
   * 序列化
   *
   * @param src 传入参数
   * @param <T> 类型
   * @return 返回String
   */
  public static <T> String obj2String(T src) {
    if (src == null) {
      return null;
    } else {
      try {
        return src instanceof String ? (String) src : OBJECT_MAPPER.writeValueAsString(src);
      } catch (JsonProcessingException e) {
        log.error(ExceptionUtil.stacktraceToString(e));
        return null;
      }
    }
  }

  /**
   * 反序列化
   *
   * @param src 传入参数
   * @param tz  类
   * @param <T> 类型
   * @return obj
   */
  public static <T> T toBean(Object src, Class<T> tz) {
    if (src == null || tz == null) {
      return null;
    } else {
      try {
        return OBJECT_MAPPER.readValue(OBJECT_MAPPER.writeValueAsString(src), tz);
      } catch (JsonProcessingException e) {
        log.error(ExceptionUtil.stacktraceToString(e));
        return null;
      }
    }
  }
}
