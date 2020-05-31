package com.cloudx.auth.controller;

import com.cloudx.auth.entity.OauthClientDetails;
import com.cloudx.auth.service.IOauthClientDetailsService;
import com.cloudx.common.core.entity.QueryParam;
import com.cloudx.common.core.entity.R;
import com.cloudx.common.core.exception.ApiException;
import com.cloudx.common.core.util.PageUtil;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户端管理控制层
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/30 18:04
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("client")
public class OauthClientDetailsController {

  private final IOauthClientDetailsService oauthClientDetailsService;

  @GetMapping("check/{clientId}")
  public boolean checkUserName(@NotBlank(message = "{required}") @PathVariable String clientId) {
    OauthClientDetails client = this.oauthClientDetailsService.getById(clientId);
    return client == null;
  }

  @GetMapping("secret/{clientId}")
  @PreAuthorize("hasAuthority('client:decrypt')")
  public R<String> getOriginClientSecret(
      @NotBlank(message = "{required}") @PathVariable String clientId) {
    OauthClientDetails client = this.oauthClientDetailsService.getById(clientId);
    String origin = client != null ? client.getOriginSecret() : "";
    return R.ok(origin);
  }

  @GetMapping
  @PreAuthorize("hasAuthority('client:view')")
  public R<Map<String, Object>> oauthClientDetailsList(QueryParam param,
      OauthClientDetails oAuthClientDetails) {
    Map<String, Object> map = PageUtil
        .toPage(this.oauthClientDetailsService.findOauthClientDetails(param, oAuthClientDetails));
    return R.ok(map);
  }

  @PostMapping
  @PreAuthorize("hasAuthority('client:add')")
  public void addOauthClientDetails(@Valid OauthClientDetails oAuthClientDetails) {
    try {
      this.oauthClientDetailsService.createOauthClientDetails(oAuthClientDetails);
    } catch (Exception e) {
      String message = "新增客户端失败";
      log.error(message, e);
      throw new ApiException(message);
    }
  }

  @DeleteMapping
  @PreAuthorize("hasAuthority('client:delete')")
  public void deleteOauthClientDetails(@NotBlank(message = "{required}") String clientIds) {
    try {
      this.oauthClientDetailsService.deleteOauthClientDetails(clientIds);
    } catch (Exception e) {
      String message = "删除客户端失败";
      log.error(message, e);
      throw new ApiException(message);
    }
  }

  @PutMapping
  @PreAuthorize("hasAuthority('client:update')")
  public void updateOauthClientDetails(@Valid OauthClientDetails oAuthClientDetails) {
    try {
      this.oauthClientDetailsService.updateOauthClientDetails(oAuthClientDetails);
    } catch (Exception e) {
      String message = "修改客户端失败";
      log.error(message, e);
      throw new ApiException(message);
    }
  }
}