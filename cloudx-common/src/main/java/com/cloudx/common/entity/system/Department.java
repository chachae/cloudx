package com.cloudx.common.entity.system;

import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 部门表实体类
 *
 * @author chachae
 * @since 2020-04-24 22:56:44
 */
@Data
public class Department implements Serializable {

  private static final long serialVersionUID = -4352930128794165121L;

  /**
   * 自增id
   */
  @TableId
  private Long id;
  /**
   * 部门名称
   */
  private String name;
  /**
   * 部门简介
   */
  private String profile;
  /**
   * 创建时间
   */
  private Date createTime;
  /**
   * 更新时间
   */
  private Date updateTime;

}