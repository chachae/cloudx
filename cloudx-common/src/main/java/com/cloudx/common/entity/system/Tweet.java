package com.cloudx.common.entity.system;

import com.cloudx.common.base.BaseEntity;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chachae
 * @since 2020-04-13 18:50:47
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class Tweet extends BaseEntity {

  /**
   * 所属用户id
   */
  private Integer userId;
  /**
   * 微博内容
   */
  private String context;
  /**
   * 创建时间
   */
  private Date createTime;

}