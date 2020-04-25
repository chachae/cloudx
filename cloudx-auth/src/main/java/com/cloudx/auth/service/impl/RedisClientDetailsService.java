package com.cloudx.auth.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.cloudx.common.service.RedisService;
import java.util.List;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

/**
 * Redis 下的 oauth2.0 客户端信息服务
 *
 * @author chachae
 * @since 2020/04/25 17:11
 */
@Slf4j
@Service
public class RedisClientDetailsService extends JdbcClientDetailsService {

  /**
   * 缓存 client的 redis key，这里是 hash结构存储
   */
  private static final String CACHE_CLIENT_KEY = "client_details";

  private final RedisService redisService;

  public RedisClientDetailsService(DataSource dataSource, RedisService redisService) {
    super(dataSource);
    this.redisService = redisService;
  }

  @Override
  public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
    String value = (String) redisService.hget(CACHE_CLIENT_KEY, clientId);
    return StringUtils.isBlank(value) ? cacheAndGetClient(clientId)
        : JSONObject.parseObject(value, BaseClientDetails.class);
  }

  /**
   * 缓存 client并返回 client
   *
   * @param clientId clientId
   */
  public ClientDetails cacheAndGetClient(String clientId) {
    ClientDetails clientDetails;
    // 父级通过 JDBC 获取客户端信息，将数据缓存到 Redis
    clientDetails = super.loadClientByClientId(clientId);
    if (clientDetails != null) {
      redisService.hset(CACHE_CLIENT_KEY, clientId, JSONObject.toJSONString(clientDetails));
    }
    return clientDetails;
  }

  /**
   * 删除 redis缓存
   *
   * @param clientId clientId
   */
  public void removeRedisCache(String clientId) {
    redisService.hdel(CACHE_CLIENT_KEY, clientId);
  }

  /**
   * 将 oauth_client_details全表刷入 redis
   */
  public void loadAllClientToCache() {
    if (redisService.hasKey(CACHE_CLIENT_KEY)) {
      return;
    }
    log.info("将oauth_client_details全表刷入redis");

    List<ClientDetails> list = super.listClientDetails();
    if (CollUtil.isEmpty(list)) {
      log.error("oauth_client_details表数据为空，请检查");
      return;
    }
    list.forEach(client -> redisService
        .hset(CACHE_CLIENT_KEY, client.getClientId(), JSONObject.toJSONString(client)));
  }
}
