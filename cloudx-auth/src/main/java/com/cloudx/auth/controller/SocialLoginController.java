package com.cloudx.auth.controller;

import cn.hutool.core.util.StrUtil;
import com.cloudx.auth.entity.BindUser;
import com.cloudx.auth.entity.UserConnection;
import com.cloudx.auth.service.ISocialLoginService;
import com.cloudx.common.core.constant.StringConstant;
import com.cloudx.common.core.entity.R;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 第三方登录控制层
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/30 20:33
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("social")
public class SocialLoginController {

  private static final String TYPE_LOGIN = "login";
  private static final String TYPE_BIND = "bind";

  private final ISocialLoginService socialLoginService;

  @Value("${cloudx.frontUrl}")
  private String frontUrl;

  /**
   * 登录
   *
   * @param oauthType 第三方登录类型
   * @param response  response
   */
  @ResponseBody
  @GetMapping("/login/{oauthType}/{type}")
  public void renderAuth(@PathVariable String oauthType, @PathVariable String type,
      HttpServletResponse response) throws IOException {
    AuthRequest authRequest = socialLoginService.renderAuth(oauthType);
    response.sendRedirect(authRequest
        .authorize(oauthType + StringConstant.DOUBLE_COLON + AuthStateUtils.createState())
        + StringConstant.DOUBLE_COLON
        + type);
  }

  /**
   * 登录成功后的回调
   *
   * @param oauthType 第三方登录类型
   * @param callback  携带返回的信息
   * @return String
   */
  @GetMapping("/{oauthType}/callback")
  public String login(@PathVariable String oauthType, AuthCallback callback, String state,
      Model model) {
    try {
      Map<String, Object> res;
      String type = StringUtils.substringAfterLast(state, StringConstant.DOUBLE_COLON);
      if (StrUtil.equals(type, TYPE_BIND)) {
        res = socialLoginService.resolveBind(oauthType, callback);
      } else {
        res = socialLoginService.resolveLogin(oauthType, callback);
      }
      model.addAttribute("response", res);
      model.addAttribute("frontUrl", frontUrl);
      return "result";
    } catch (Exception e) {
      model.addAttribute("error", e.getMessage());
      return "fail";
    }
  }

  /**
   * 绑定并登录
   *
   * @param bindUser bindUser
   * @param authUser authUser
   * @return R<OAuth2AccessToken>
   */
  @ResponseBody
  @PostMapping("bind/login")
  public R<OAuth2AccessToken> bindLogin(@Valid BindUser bindUser, AuthUser authUser) {
    OAuth2AccessToken oAuth2AccessToken = this.socialLoginService.bindLogin(bindUser, authUser);
    return R.ok(oAuth2AccessToken);
  }

  /**
   * 注册并登录
   *
   * @param registerUser registerUser
   * @param authUser     authUser
   * @return R<OAuth2AccessToken>
   */
  @ResponseBody
  @PostMapping("sign/login")
  public R<OAuth2AccessToken> signLogin(@Valid BindUser registerUser, AuthUser authUser) {
    OAuth2AccessToken oAuth2AccessToken = this.socialLoginService.signLogin(registerUser, authUser);
    return R.ok(oAuth2AccessToken);
  }

  /**
   * 绑定
   *
   * @param bindUser bindUser
   * @param authUser authUser
   */
  @ResponseBody
  @PostMapping("bind")
  public void bind(BindUser bindUser, AuthUser authUser) {
    this.socialLoginService.bind(bindUser, authUser);
  }

  /**
   * 解绑
   *
   * @param bindUser  bindUser
   * @param oauthType oauthType
   */
  @ResponseBody
  @DeleteMapping("unbind")
  public void unbind(BindUser bindUser, String oauthType) {
    this.socialLoginService.unbind(bindUser, oauthType);
  }

  /**
   * 根据用户名获取绑定关系
   *
   * @param username 用户名
   * @return FebsResponse
   */
  @ResponseBody
  @GetMapping("connections/{username}")
  public R<List<UserConnection>> findUserConnections(
      @NotBlank(message = "{required}") @PathVariable String username) {
    List<UserConnection> userConnections = this.socialLoginService.findUserConnections(username);
    return R.ok(userConnections);
  }
}
