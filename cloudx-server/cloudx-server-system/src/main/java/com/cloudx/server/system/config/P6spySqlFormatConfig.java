package com.cloudx.server.system.config;

import cn.hutool.core.util.StrUtil;
import com.cloudx.common.core.util.DateUtil;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import java.time.LocalDateTime;

/**
 * 自定义 p6spy sql输出格式
 *
 * @author chachae
 */
public class P6spySqlFormatConfig implements MessageFormattingStrategy {

  @Override
  public String formatMessage(int connectionId, String now, long elapsed, String category,
      String prepared, String sql, String url) {
    return StrUtil.isNotBlank(sql) ?
        DateUtil.formatFullTime(LocalDateTime.now(), DateUtil.FULL_TIME_SPLIT_PATTERN)
            + " | 耗时 " + elapsed + " ms | SQL 语句：" + StrUtil.LF + sql
            .replaceAll("[\\s]+", StrUtil.SPACE) + ";" : StrUtil.EMPTY;
  }
}
