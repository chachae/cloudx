package com.cloudx.common.entity.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author chachae
 * @since 2020/4/15 20:33
 */

@Data
public class UserDTO {

  @NotBlank(message = "用户名不能为空")
  private String userName;

  @NotBlank(message = "密码不能为空")
  private String password;

  @NotBlank(message = "验证码不能为空")
  private String uuid;

  @NotBlank(message = "验证码不能为空")
  private String text;

}
