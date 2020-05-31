package com.cloudx.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/5/30 21:43
 */
@Data
@TableName("t_user_connection")
public class UserConnection {

  @NotBlank(message = "{required}")
  private String userName;

  @NotBlank(message = "{required}")
  private String providerName;
  
  @NotBlank(message = "{required}")
  private String providerUserId;

  private String providerUserName;

  private String nickName;

  private String imageUrl;

  private String location;

  private String remark;

}