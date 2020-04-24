package com.cloudx.common.entity.system;

import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;

/**
 * 权限表实体类
 *
 * @author chachae
 * @since 2020-04-24 23:13:28
 */
@Data
public class Permission implements Serializable {

  private static final long serialVersionUID = -5452977288794165453L;

  @TableId
  private Long id;

  private String name;

  private String expression;
}