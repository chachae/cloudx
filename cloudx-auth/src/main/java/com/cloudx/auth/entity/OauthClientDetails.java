package com.cloudx.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/5/30 18:00
 */
@Data
@TableName("oauth_client_details")
public class OauthClientDetails implements Serializable {

  private static final long serialVersionUID = -628496816153693846L;

  @TableId(value = "client_id")
  @NotBlank(message = "{required}")
  @Size(max = 255, message = "{noMoreThan}")
  private String clientId;

  @TableField("resource_ids")
  @Size(max = 255, message = "{noMoreThan}")
  private String resourceIds;

  @TableField("client_secret")
  @NotBlank(message = "{required}")
  @Size(max = 255, message = "{noMoreThan}")
  private String clientSecret;

  @TableField("scope")
  @NotBlank(message = "{required}")
  @Size(max = 255, message = "{noMoreThan}")
  private String scope;

  @TableField("authorized_grant_types")
  @NotBlank(message = "{required}")
  @Size(max = 255, message = "{noMoreThan}")
  private String authorizedGrantTypes;

  @TableField("web_server_redirect_uri")
  @Size(max = 255, message = "{noMoreThan}")
  private String webServerRedirectUri;

  @TableField("authorities")
  @Size(max = 255, message = "{noMoreThan}")
  private String authorities;

  @TableField("access_token_validity")
  @NotNull(message = "{required}")
  private Integer accessTokenValidity;

  @TableField("refresh_token_validity")
  private Integer refreshTokenValidity;

  @TableField("autoapprove")
  private Byte autoapprove;

  @TableField("origin_secret")
  private String originSecret;

}

