package com.cloudx.common.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.cloudx.common.base.Response;
import com.cloudx.common.exception.ApiException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常处理基类
 *
 * @author chachae
 * @since 2020/4/13 20:40
 */

@Slf4j
public abstract class BaseExceptionHandler {

  /**
   * 数据校验异常
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Response<Object> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    String[] str =
        Objects.requireNonNull(e.getBindingResult().getAllErrors().get(0).getCodes())[1].split(
            "\\.");
    String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    String msg = "不能为空";
    if (msg.equals(message)) {
      message = str[1] + ":" + message;
    }
    return Response.fail(message);
  }

  /**
   * 自定义ApiException
   */
  @ExceptionHandler(ApiException.class)
  public Response<Object> badRequestException(ApiException e) {
    return Response.fail(e.getStatus(), e.getMsg());
  }

  /**
   * 处理所有不可知的异常
   */
  @ExceptionHandler(Throwable.class)
  public Response<Object> handleException(Throwable e) {
    // 打印堆栈信息
    log.error(ExceptionUtil.stacktraceToString(e));
    return Response.fail(e.getMessage());
  }
}
