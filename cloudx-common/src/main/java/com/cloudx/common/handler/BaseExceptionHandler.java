package com.cloudx.common.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.cloudx.common.base.Result;
import com.cloudx.common.exception.ApiException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常处理基类
 *
 * @author chachae
 * @since 2020/4/20 20:20
 */

@Slf4j
public abstract class BaseExceptionHandler {

  /**
   * 数据校验异常
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Result<Object> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    String[] str =
        Objects.requireNonNull(e.getBindingResult().getAllErrors().get(0).getCodes())[1].split(
            "\\.");
    String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    String msg = "不能为空";
    if (msg.equals(message)) {
      message = str[1] + ":" + message;
    }
    return Result.fail(message);
  }

  /**
   * 自定义ApiException
   */
  @ExceptionHandler(ApiException.class)
  public Result<Object> badRequestException(ApiException e) {
    return Result.fail(e.getStatus(), e.getMsg());
  }

  /**
   * 处理所有不可知的异常
   */
  @ExceptionHandler(Exception.class)
  public Result<Object> handleException(Exception e) {
    // 打印堆栈信息
    log.error(ExceptionUtil.stacktraceToString(e));
    return Result.fail(e.getMessage());
  }
}
