package com.cloudx.common.core.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 部门实体类
 *
 * @author chachae
 * @since 2020-05-27 22:10:41
 */
@Data
@TableName("t_dept")
public class Dept implements Serializable {

  public static final Long TOP_DEPT_ID = 0L;
  private static final long serialVersionUID = -7790334862410409053L;

  /**
   * 部门ID
   */
  @TableId(type = IdType.AUTO)
  private Long deptId;
  /**
   * 上级部门ID
   */
  private Long parentId;
  /**
   * 部门名称
   */
  private String deptName;
  /**
   * 排序
   */
  private Integer orderNum;
  /**
   * 创建时间
   */
  private Date createTime;
  /**
   * 修改时间
   */
  private Date updateTime;

  private transient String createTimeFrom;

  private transient String createTimeTo;
}