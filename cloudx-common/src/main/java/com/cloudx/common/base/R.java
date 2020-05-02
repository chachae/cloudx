package com.cloudx.common.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.http.HttpStatus;

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
   * 响应代码
   */
  private Integer code;

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
  private R(Integer code, String message, E data, Date timestamp) {
    this.code = code;
    this.message = message;
    this.data = data;
    this.timestamp = timestamp;
  }

  private R(Integer code, String message, E data) {
    this(code, message, data, null);
  }

  public R(String message) {
    this(null, message, null);
  }

  public R(E data) {
    this(null, null, data);
  }

  public R(int code, String message) {
    this(code, message, null);
  }

  /**
   * 默认 [成功] 响应
   *
   * @return /
   */
  public static R<Object> ok() {
    return new R<>(HttpStatus.OK.value(), HttpStatus.OK.name(), null);
  }

  /**
   * 携带响应数据信息的 [成功] 响应
   *
   * @return /
   */
  public static <E> R<E> ok(E data) {
    return new R<>(HttpStatus.OK.value(), HttpStatus.OK.name(), data);
  }

  /**
   * 携带信息的 [失败] 响应
   *
   * @return /
   */
  public static R<String> fail(String message) {
    return new R<>(null, message, null, new Date());
  }

  /**
   * 自定义响应信息的 [失败] 响应
   *
   * @return /
   */
  public static R<Object> fail(int code, String message) {
    return new R<>(code, message, null, new Date());
  }
}
