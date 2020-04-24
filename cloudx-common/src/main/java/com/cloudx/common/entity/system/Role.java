package com.cloudx.common.entity.system;

import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 角色表实体类
 *
 * @author chachae
 * @since 2020-04-24 22:57:01
 */
@Data
public class Role implements Serializable {

  private static final long serialVersionUID = -4862930128722065121L;

  @TableId
  private Long id;
  /**
   * 角色名称
   */
  private String name;
  /**
   * 角色介绍
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