package com.cloudx.common.core.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 系统日志
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/30 12:10
 */
@Data
@TableName("t_log")
public class Log implements Serializable {

  private static final long serialVersionUID = -3601829075465884164L;
  /**
   * 日志ID
   */
  @TableId(type = IdType.AUTO)
  private Long id;

  /**
   * 操作用户
   */
  private String username;

  /**
   * 操作内容
   */
  private String operation;

  /**
   * 耗时
   */
  private Long time;

  /**
   * 操作方法
   */
  private String method;

  /**
   * 方法参数
   */
  private String params;

  /**
   * 操作者IP
   */
  private String ip;

  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 操作地点
   */
  private String location;

  private transient String createTimeFrom;
  private transient String createTimeTo;
}
