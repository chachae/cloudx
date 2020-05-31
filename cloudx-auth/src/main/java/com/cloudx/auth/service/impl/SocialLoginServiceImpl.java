package com.cloudx.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.cloudx.auth.entity.BindUser;
import com.cloudx.auth.entity.UserConnection;
import com.cloudx.auth.manager.UserManager;
import com.cloudx.auth.properties.AuthProperties;
import com.cloudx.auth.service.ISocialLoginService;
import com.cloudx.auth.service.IUserConnectionService;
import com.cloudx.common.core.constant.GrantTypeConstant;
import com.cloudx.common.core.constant.ParamsConstant;
import com.cloudx.common.core.constant.SocialConstant;
import com.cloudx.common.core.constant.StringConstant;
import com.cloudx.common.core.entity.system.SystemUser;
import com.cloudx.common.core.exception.ApiException;
import com.cloudx.common.core.util.HttpUtil;
import com.cloudx.common.core.util.SecurityUtil;
import com.xkcoding.justauth.AuthRequestFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.zhyd.oauth.config.AuthDefaultSource;
import me.zhyd.oauth.config.AuthSource;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.stereotype.Service;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/5/30 22:58
 */
@Service
@RequiredArgsConstructor
public class SocialLoginServiceImpl implements ISocialLoginService {

  private static final String USERNAME = "username";
  private static final String PASSWORD = "password";

  private static final String NOT_BIND = "not_bind";
  private static final String SOCIAL_LOGIN_SUCCESS = "social_login_success";

  private final UserManager userManager;
  private final AuthProperties properties;
  private final AuthRequestFactory factory;
  private final PasswordEncoder passwordEncoder;
  private final ResourceOwnerPasswordTokenGranter granter;
  private final IUserConnectionService userConnectionService;
  private final RedisClientDetailsService redisClientDetailsService;

  @Override
  public AuthRequest renderAuth(String oauthType) {
    return getAuthRequest(getAuthSource(oauthType));
  }

  @Override
  public Map<String, Object> resolveBind(String oauthType, AuthCallback callback) {
    Map<String, Object> res = new HashMap<>(1);
    AuthRequest authRequest = getAuthRequest(getAuthSource(oauthType));
    AuthResponse<?> response = authRequest.login(resolveAuthCallback(callback));
    if (response.ok()) {
      res.put("data", response.getData());
    } else {
      throw new ApiException(String.format("第三方登录失败，%s", response.getMsg()));
    }
    return res;
  }

  @Override
  public Map<String, Object> resolveLogin(String oauthType, AuthCallback callback) {
    Map<String, Object> res = new HashMap<>(3);
    AuthRequest authRequest = getAuthRequest(getAuthSource(oauthType));
    AuthResponse<?> response = authRequest.login(resolveAuthCallback(callback));
    if (response.ok()) {
      AuthUser authUser = (AuthUser) response.getData();
      UserConnection userConnection = userConnectionService
          .selectByCondition(authUser.getSource(), authUser.getUuid());
      if (userConnection == null) {
        res.put("message", NOT_BIND);
        res.put("data", authUser);
      } else {
        SystemUser user = userManager.findByName(userConnection.getUserName());
        if (user == null) {
          throw new ApiException("系统中未找到与第三方账号对应的账户");
        }
        OAuth2AccessToken oAuth2AccessToken = getOauth2AccessToken(user);
        res.put("message", SOCIAL_LOGIN_SUCCESS);
        res.put("data", oAuth2AccessToken);
        res.put(USERNAME, user.getUsername());
      }
    } else {
      throw new ApiException(String.format("第三方登录失败，%s", response.getMsg()));
    }
    return res;
  }

  @Override
  public OAuth2AccessToken bindLogin(BindUser bindUser, AuthUser authUser) {
    SystemUser systemUser = userManager.findByName(bindUser.getBindUsername());
    if (systemUser == null || !passwordEncoder
        .matches(bindUser.getBindPassword(), systemUser.getPassword())) {
      throw new ApiException("绑定系统账号失败，用户名或密码错误！");
    }
    this.createConnection(systemUser, authUser);
    return this.getOauth2AccessToken(systemUser);
  }

  @Override
  public OAuth2AccessToken signLogin(BindUser registerUser, AuthUser authUser) {
    SystemUser user = this.userManager.findByName(registerUser.getBindUsername());
    if (user != null) {
      throw new ApiException("该用户名已存在！");
    }
    String encryptPassword = passwordEncoder.encode(registerUser.getBindPassword());
    SystemUser systemUser = this.userManager
        .registUser(registerUser.getBindUsername(), encryptPassword);
    this.createConnection(systemUser, authUser);
    return this.getOauth2AccessToken(systemUser);
  }

  @Override
  public void bind(BindUser bindUser, AuthUser authUser) {
    String username = bindUser.getBindUsername();
    if (isCurrentUser(username)) {
      UserConnection userConnection = userConnectionService
          .selectByCondition(authUser.getSource(), authUser.getUuid());
      if (userConnection != null) {
        throw new ApiException("绑定失败，该第三方账号已绑定" + userConnection.getUserName() + "系统账户");
      }
      SystemUser systemUser = new SystemUser();
      systemUser.setUsername(username);
      this.createConnection(systemUser, authUser);
    } else {
      throw new ApiException("绑定失败，您无权绑定别人的账号");
    }
  }

  @Override
  public void unbind(BindUser bindUser, String oauthType) {
    String username = bindUser.getBindUsername();
    if (isCurrentUser(username)) {
      this.userConnectionService.deleteByCondition(username, oauthType);
    } else {
      throw new ApiException("解绑失败，您无权解绑别人的账号");
    }
  }

  @Override
  public List<UserConnection> findUserConnections(String username) {
    return this.userConnectionService.selectByCondition(username);
  }

  private void createConnection(SystemUser systemUser, AuthUser authUser) {
    UserConnection userConnection = new UserConnection();
    userConnection.setUserName(systemUser.getUsername());
    userConnection.setProviderName(authUser.getSource());
    userConnection.setProviderUserId(authUser.getUuid());
    userConnection.setProviderUserName(authUser.getUsername());
    userConnection.setImageUrl(authUser.getAvatar());
    userConnection.setNickName(authUser.getNickname());
    userConnection.setLocation(authUser.getLocation());
    userConnection.setRemark(authUser.getRemark());
    this.userConnectionService.createUserConnection(userConnection);
  }

  private AuthCallback resolveAuthCallback(AuthCallback callback) {
    int stateLength = 3;
    String state = callback.getState();
    String[] strings = StrUtil.split(state, StringConstant.DOUBLE_COLON);
    if (strings.length == stateLength) {
      callback.setState(strings[0] + StringConstant.DOUBLE_COLON + strings[1]);
    }
    return callback;
  }

  private AuthSource getAuthSource(String type) {
    if (StrUtil.isNotBlank(type)) {
      return AuthDefaultSource.valueOf((type.toUpperCase()));
    } else {
      throw new ApiException(String.format("暂不支持 %s 第三方登录", type));
    }
  }

  private AuthRequest getAuthRequest(AuthSource authSource) {
    try {
      return factory.get(authSource.toString());
    } catch (NullPointerException e) {
      throw new ApiException(String.format("暂不支持 %s 第三方登录", authSource.toString()));
    }
  }

  private boolean isCurrentUser(String username) {
    String currentUsername = SecurityUtil.getCurrentUsername();
    return StrUtil.equalsIgnoreCase(username, currentUsername);
  }

  private OAuth2AccessToken getOauth2AccessToken(SystemUser user) {
    final HttpServletRequest httpServletRequest = HttpUtil.getHttpServletRequest();
    httpServletRequest.setAttribute(ParamsConstant.LOGIN_TYPE, SocialConstant.SOCIAL_LOGIN);
    String socialLoginClientId = properties.getSocialLoginClientId();
    ClientDetails clientDetails;
    try {
      clientDetails = redisClientDetailsService.loadClientByClientId(socialLoginClientId);
    } catch (Exception e) {
      throw new ApiException("获取第三方登录可用的Client失败");
    }
    if (clientDetails == null) {
      throw new ApiException("未找到第三方登录可用的Client");
    }
    Map<String, String> requestParameters = new HashMap<>(5);
    requestParameters.put(ParamsConstant.GRANT_TYPE, GrantTypeConstant.PASSWORD);
    requestParameters.put(USERNAME, user.getUsername());
    requestParameters.put(PASSWORD, SocialConstant.SOCIAL_LOGIN_PASSWORD);

    String grantTypes = String.join(StrUtil.COMMA, clientDetails.getAuthorizedGrantTypes());
    TokenRequest tokenRequest = new TokenRequest(requestParameters, clientDetails.getClientId(),
        clientDetails.getScope(), grantTypes);
    return granter.grant(GrantTypeConstant.PASSWORD, tokenRequest);
  }
}

