package com.cloudx.common.core.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 统一响应信息
 *
 * @author chachae
 * @since 2020/4/13 21:23
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class R<E> implements Serializable {

  private static final long serialVersionUID = -8713837163942965721L;

  /**
   * 响应信息
   */
  private String message;

  /**
   * 响应数据
   */
  private E data;

  /**
   * 时间戳
   */
  private Date timestamp;

  private R() {
  }

  /**
   * 全参构造
   */
  private R(String message, E data, Date timestamp) {
    this.message = message;
    this.data = data;
    this.timestamp = timestamp;
  }

  private R(String message, E data) {
    this(message, data, null);
  }

  public R(String message) {
    this(message, null, null);
  }

  public R(E data) {
    this(null, data, null);
  }

  /**
   * 默认 [成功] 响应
   *
   * @return /
   */
  public static R<Object> ok() {
    return new R<>("ok", null);
  }

  /**
   * 携带响应数据信息的 [成功] 响应
   *
   * @return /
   */
  public static <E> R<E> ok(E data) {
    return new R<>("ok", data);
  }

  /**
   * 携带信息的 [失败] 响应
   *
   * @return /
   */
  public static R<String> fail(String message) {
    return new R<>(message, null, new Date());
  }

  /**
   * 自定义响应信息的 [失败] 响应
   *
   * @return /
   */
  public static R<String> fail() {
    return fail("fail");
  }
}
