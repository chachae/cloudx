package com.cloudx.auth.entity;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/5/30 21:43
 */
@Data
public class BindUser implements Serializable {

  private static final long serialVersionUID = -6639691217526196732L;

  @NotBlank(message = "{required}")
  private String bindUsername;

  @NotBlank(message = "{required}")
  private String bindPassword;
}

