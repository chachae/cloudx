package com.cloudx.common.core.entity;

import lombok.Data;

/**
 * 分页参数
 *
 * @author chachae
 * @since 2020/4/30 20:15
 */
@Data
public class QueryParam {

  /**
   * 页号，默认第1页
   */
  private Long pageNum = 1L;

  /**
   * 每页数据量，默认10条
   */
  private Long pageSize = 10L;

}
