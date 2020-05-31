package com.cloudx.auth.service;

import com.cloudx.auth.entity.BindUser;
import com.cloudx.auth.entity.UserConnection;
import com.cloudx.common.core.exception.ApiException;
import java.util.List;
import java.util.Map;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * 第三方登录认证业务接口
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/30 22:50
 */
public interface ISocialLoginService {

  /**
   * 解析第三方登录请求
   *
   * @param oauthType 第三方平台类型
   * @return AuthRequest
   * @throws ApiException 异常
   */
  AuthRequest renderAuth(String oauthType);

  /**
   * 处理第三方登录（绑定页面）
   *
   * @param oauthType 第三方平台类型
   * @param callback  回调
   * @return R
   * @throws ApiException 异常
   */
  Map<String, Object> resolveBind(String oauthType, AuthCallback callback);

  /**
   * 处理第三方登录（登录页面）
   *
   * @param oauthType 第三方平台类型
   * @param callback  回调
   * @return Map<String, Object>
   * @throws ApiException 异常
   */
  Map<String, Object> resolveLogin(String oauthType, AuthCallback callback);

  /**
   * 绑定并登录
   *
   * @param bindUser 绑定用户
   * @param authUser 第三方平台对象
   * @return OAuth2AccessToken 令牌对象
   * @throws ApiException 异常
   */
  OAuth2AccessToken bindLogin(BindUser bindUser, AuthUser authUser);

  /**
   * 注册并登录
   *
   * @param registerUser 注册用户
   * @param authUser     第三方平台对象
   * @return OAuth2AccessToken 令牌对象
   * @throws ApiException 异常
   */
  OAuth2AccessToken signLogin(BindUser registerUser, AuthUser authUser);

  /**
   * 绑定
   *
   * @param bindUser 绑定对象
   * @param authUser 第三方平台对象
   * @throws ApiException 异常
   */
  void bind(BindUser bindUser, AuthUser authUser);

  /**
   * 解绑
   *
   * @param bindUser  绑定对象
   * @param oauthType 第三方平台对象
   * @throws ApiException 异常
   */
  void unbind(BindUser bindUser, String oauthType);

  /**
   * 根据用户名获取绑定关系
   *
   * @param username 用户名
   * @return 绑定关系集合
   */
  List<UserConnection> findUserConnections(String username);
}
