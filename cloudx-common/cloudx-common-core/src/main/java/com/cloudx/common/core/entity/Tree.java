package com.cloudx.common.core.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * menu tree
 *
 * @author chachae
 * @since 2020/4/13 21:23
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tree<T> {

  private String id;

  private String label;

  private List<Tree<T>> children;

  private String parentId;

  private boolean hasParent = false;

  private boolean hasChildren = false;

  public void initChildren() {
    this.children = new ArrayList<>();
  }

}
