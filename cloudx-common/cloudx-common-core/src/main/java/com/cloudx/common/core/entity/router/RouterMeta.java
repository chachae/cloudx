package com.cloudx.common.core.entity.router;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Vue路由 Meta
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/27 10:47
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouterMeta implements Serializable {

  private static final long serialVersionUID = 5499925008927195914L;

  private String title;
  private String icon;
  private Boolean breadcrumb = true;

}
