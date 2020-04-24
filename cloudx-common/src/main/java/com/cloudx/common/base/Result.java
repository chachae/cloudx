package com.cloudx.common.base;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

/**
 * 统一响应信息
 *
 * @author chachae
 * @since 2020/4/13 21:23
 */
@Data
@Accessors(chain = true)
public class Result<E> implements Serializable {

  private static final long serialVersionUID = -8713837000040960771L;

  private Result() {
  }

  /**
   * 响应代码
   */
  private Integer status;

  /**
   * 响应信息
   */
  private String msg;

  /**
   * 时间戳
   */
  private Long timestamp;

  /**
   * 响应数据
   */
  private E data;

  /**
   * 默认 [成功] 响应
   *
   * @return /
   */
  public static Result<Object> ok() {
    return new Result<>()
        .setStatus(HttpStatus.OK.value())
        .setMsg(HttpStatus.OK.name())
        .setTimestamp(System.currentTimeMillis());
  }

  /**
   * 携带响应数据信息的 [成功] 响应
   *
   * @return /
   */
  public static <E> Result<E> ok(E data) {
    return new Result<E>()
        .setData(data)
        .setStatus(HttpStatus.OK.value())
        .setMsg(HttpStatus.OK.name())
        .setTimestamp(System.currentTimeMillis());
  }

  /**
   * 自定义响应信息的 [成功] 响应
   *
   * @return /
   */
  public static <E> Result<E> ok(int status, String msg, E data) {
    return new Result<E>()
        .setStatus(status)
        .setMsg(msg)
        .setData(data)
        .setTimestamp(System.currentTimeMillis());
  }

  /**
   * 默认 [失败] 响应
   *
   * @return /
   */
  public static Result<Object> fail() {
    return new Result<>()
        .setStatus(HttpStatus.BAD_REQUEST.value())
        .setMsg(HttpStatus.BAD_REQUEST.name())
        .setTimestamp(System.currentTimeMillis());
  }

  /**
   * 携带信息的 [失败] 响应
   *
   * @return /
   */
  public static Result<Object> fail(String msg) {
    return new Result<>()
        .setStatus(HttpStatus.BAD_REQUEST.value())
        .setMsg(msg)
        .setTimestamp(System.currentTimeMillis());
  }

  /**
   * 自定义响应信息的 [失败] 响应
   *
   * @return /
   */
  public static Result<Object> fail(int status, String msg) {
    return new Result<>()
        .setStatus(status)
        .setMsg(msg)
        .setTimestamp(System.currentTimeMillis());
  }
}
