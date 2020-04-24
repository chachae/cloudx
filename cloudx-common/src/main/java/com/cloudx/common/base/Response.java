package com.cloudx.common.base;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 统一响应信息
 *
 * @author chachae
 * @since 2020/4/13 21:23
 */
@Data
@Accessors(chain = true)
public class Response<E> {

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
  public static Response<Object> ok() {
    return new Response<>()
        .setMsg(HttpStatus.OK.desc())
        .setStatus(HttpStatus.OK.val())
        .setTimestamp(System.currentTimeMillis());
  }

  /**
   * 携带响应数据信息的 [成功] 响应
   *
   * @return /
   */
  public static <E> Response<E> ok(E data) {
    return new Response<E>()
        .setData(data)
        .setStatus(HttpStatus.OK.val())
        .setMsg(HttpStatus.OK.desc())
        .setTimestamp(System.currentTimeMillis());
  }

  /**
   * 自定义响应信息的 [成功] 响应
   *
   * @return /
   */
  public static <E> Response<E> ok(int status, String msg, E data) {
    return new Response<E>()
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
  public static Response<Object> fail() {
    return new Response<>()
        .setStatus(HttpStatus.BAD_REQUEST.val())
        .setMsg(HttpStatus.BAD_REQUEST.desc())
        .setTimestamp(System.currentTimeMillis());
  }

  /**
   * 携带信息的 [失败] 响应
   *
   * @return /
   */
  public static Response<Object> fail(String msg) {
    return new Response<>()
        .setStatus(HttpStatus.BAD_REQUEST.val())
        .setMsg(msg)
        .setTimestamp(System.currentTimeMillis());
  }

  /**
   * 自定义响应信息的 [失败] 响应
   *
   * @return /
   */
  public static Response<Object> fail(int status, String msg) {
    return new Response<>()
        .setStatus(status)
        .setMsg(msg)
        .setTimestamp(System.currentTimeMillis());
  }
}
