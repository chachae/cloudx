package com.cloudx.auth.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Web 配置
 *
 * @author chachae
 * @since 2020/04/20
 */
@Configuration
public class CloudxWebConfig {

  /**
   * MyBatisPlus 分页
   *
   * @return /
   */
  @Bean
  public PaginationInterceptor paginationInterceptor() {
    PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
    // 恶意 SQL 阻断器
    List<ISqlParser> sqlParserList = new ArrayList<>();
    sqlParserList.add(new BlockAttackSqlParser());
    paginationInterceptor.setSqlParserList(sqlParserList);
    return paginationInterceptor;
  }
}
