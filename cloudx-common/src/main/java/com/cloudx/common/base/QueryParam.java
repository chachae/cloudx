package com.cloudx.common.base;

import java.io.Serializable;
import lombok.Data;

/**
 * @author chachae
 * @since 2020/4/30 20:15
 */
@Data
public class QueryParam implements Serializable {

  private static final long serialVersionUID = -4219594085276188358L;

  private Long pageNum = 1L;

  private Long pageSize = 10L;

}
