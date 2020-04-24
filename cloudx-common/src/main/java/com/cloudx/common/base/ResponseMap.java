package com.cloudx.common.base;

import java.util.HashMap;

/**
 * 自定义数据响应
 *
 * @author chachae
 * @since 2020/4/23 06:58
 */
public class ResponseMap extends HashMap<String, Object> {

  private static final long serialVersionUID = -8713837118340960775L;

  public ResponseMap message(String message) {
    this.put("message", message);
    return this;
  }

  public ResponseMap data(Object data) {
    this.put("data", data);
    return this;
  }

  @Override
  public ResponseMap put(String key, Object value) {
    super.put(key, value);
    return this;
  }

  public String getMessage() {
    return String.valueOf(get("message"));
  }

  public Object getData() {
    return get("data");
  }
}
