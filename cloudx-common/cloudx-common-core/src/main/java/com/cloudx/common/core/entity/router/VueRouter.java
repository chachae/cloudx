package com.cloudx.common.core.entity.router;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * 构建 Vue路由
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/27 10:47
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VueRouter<T> implements Serializable {

  private static final long serialVersionUID = -3327478146308500708L;

  @JsonIgnore
  private String id;
  @JsonIgnore
  private String parentId;

  private String path;
  private String name;
  private String component;
  private String redirect;
  private RouterMeta meta;
  private Boolean hidden = false;
  private Boolean alwaysShow = false;
  private List<VueRouter<T>> children;

  @JsonIgnore
  private Boolean hasParent = false;

  @JsonIgnore
  private Boolean hasChildren = false;

  public void initChildren() {
    this.children = new ArrayList<>();
  }

}
