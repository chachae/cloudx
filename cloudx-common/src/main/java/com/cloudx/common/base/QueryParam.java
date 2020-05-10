package com.cloudx.common.base;

import java.io.Serializable;
import lombok.Data;

/**
 * 分页参数
 *
 * @author chachae
 * @since 2020/4/30 20:15
 */
@Data
public class QueryParam implements Serializable {

  private static final long serialVersionUID = -4219594085276188358L;

  /**
   * 页号，默认第1页
   */
  private Long pageNum = 1L;

  /**
   * 每页数据量，默认10条
   */
  private Long pageSize = 10L;

}
