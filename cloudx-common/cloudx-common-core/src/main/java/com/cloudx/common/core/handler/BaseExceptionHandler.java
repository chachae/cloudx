package com.cloudx.common.core.handler;

import cn.hutool.core.util.StrUtil;
import com.cloudx.common.core.entity.R;
import com.cloudx.common.core.exception.ApiException;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局异常处理基类
 *
 * @author chachae
 * @since 2020/4/20 20:20
 */

@Slf4j
public abstract class BaseExceptionHandler {

  @ExceptionHandler(value = Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected R<String> handleException(Exception e) {
    log.error("系统内部异常，异常信息", e);
    return R.fail("系统内部异常");
  }

  @ExceptionHandler(value = ApiException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected R<String> handleApiException(ApiException e) {
    log.error("系统错误", e);
    return R.fail(e.getMessage());
  }

  /**
   * 统一处理请求参数校验(实体对象传参)
   *
   * @param e BindException
   * @return R<String>
   */
  @ExceptionHandler(BindException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected R<String> handleBindException(BindException e) {
    StringBuilder message = new StringBuilder();
    List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
    for (FieldError error : fieldErrors) {
      message.append(error.getField()).append(error.getDefaultMessage()).append(",");
    }
    message = new StringBuilder(message.substring(0, message.length() - 1));
    return R.fail(message.toString());
  }

  /**
   * 统一处理请求参数校验(普通传参)
   *
   * @param e ConstraintViolationException
   * @return R<String>
   */
  @ExceptionHandler(value = ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected R<String> handleConstraintViolationException(ConstraintViolationException e) {
    StringBuilder message = new StringBuilder();
    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
    for (ConstraintViolation<?> violation : violations) {
      Path path = violation.getPropertyPath();
      String[] pathArr = StrUtil.splitToArray(path.toString(), '.');
      message.append(pathArr[1]).append(violation.getMessage()).append(',');
    }
    message = new StringBuilder(message.substring(0, message.length() - 1));
    return R.fail(message.toString());
  }

  /**
   * 统一处理请求参数校验(json)
   *
   * @param e ConstraintViolationException
   * @return R<String>
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected R<String> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    StringBuilder message = new StringBuilder();
    for (FieldError error : e.getBindingResult().getFieldErrors()) {
      message.append(error.getField()).append(error.getDefaultMessage()).append(",");
    }
    message = new StringBuilder(message.substring(0, message.length() - 1));
    log.error(message.toString(), e);
    return R.fail(message.toString());
  }

  @ExceptionHandler(value = AccessDeniedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  protected R<String> handleAccessDeniedException() {
    return R.fail("没有权限访问该资源");
  }

  @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected R<String> handleHttpMediaTypeNotSupportedException(
      HttpMediaTypeNotSupportedException e) {
    return R.fail("该方法不支持" + StrUtil.subBetween(e.getMessage(), "'", "'") + "媒体类型");
  }

  @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected R<String> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException e) {
    return R.fail("该方法不支持" + StrUtil.subBetween(e.getMessage(), "'", "'") + "请求方法");
  }

}
