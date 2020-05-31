package com.cloudx.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudx.auth.entity.OauthClientDetails;
import com.cloudx.auth.mapper.OauthClientDetailsMapper;
import com.cloudx.auth.service.IOauthClientDetailsService;
import com.cloudx.common.core.entity.QueryParam;
import com.cloudx.common.core.exception.ApiException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 客户端业务接口实现
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/30 18:02
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OauthClientDetailsServiceImpl extends
    ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements
    IOauthClientDetailsService {

  private final PasswordEncoder passwordEncoder;
  private final RedisClientDetailsService redisClientDetailsService;

  @Override
  public IPage<OauthClientDetails> findOauthClientDetails(QueryParam param,
      OauthClientDetails oauthClientDetails) {
    LambdaQueryWrapper<OauthClientDetails> wrapper = new LambdaQueryWrapper<>();
    if (StrUtil.isNotBlank(oauthClientDetails.getClientId())) {
      wrapper.like(OauthClientDetails::getClientId, oauthClientDetails.getClientId());
    }
    Page<OauthClientDetails> page = new Page<>(param.getPageNum(), param.getPageSize());
    IPage<OauthClientDetails> result = page(page, wrapper);

    List<OauthClientDetails> records = new ArrayList<>(result.getRecords().size());
    for (OauthClientDetails ocd : result.getRecords()) {
      ocd.setOriginSecret(null);
      ocd.setClientSecret(null);
      records.add(ocd);
    }
    result.setRecords(records);
    return result;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createOauthClientDetails(OauthClientDetails oauthClientDetails) {
    OauthClientDetails details = getById(oauthClientDetails.getClientId());
    if (details != null) {
      throw new ApiException("该Client已存在");
    }
    oauthClientDetails.setOriginSecret(oauthClientDetails.getClientSecret());
    oauthClientDetails
        .setClientSecret(passwordEncoder.encode(oauthClientDetails.getClientSecret()));
    boolean saved = save(oauthClientDetails);
    if (saved) {
      this.redisClientDetailsService.loadClientByClientId(oauthClientDetails.getClientId());
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateOauthClientDetails(OauthClientDetails oauthClientDetails) {
    String clientId = oauthClientDetails.getClientId();

    LambdaQueryWrapper<OauthClientDetails> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(OauthClientDetails::getClientId, oauthClientDetails.getClientId());

    oauthClientDetails.setClientId(null);
    oauthClientDetails.setClientSecret(null);
    boolean updated = update(oauthClientDetails, wrapper);
    if (updated) {
      redisClientDetailsService.removeRedisCache(clientId);
      redisClientDetailsService.loadClientByClientId(clientId);
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteOauthClientDetails(String clientIds) {
    List<String> clientIdList = StrUtil.split(clientIds, StrUtil.C_COMMA);
    LambdaQueryWrapper<OauthClientDetails> wrapper = new LambdaQueryWrapper<>();
    wrapper.in(OauthClientDetails::getClientId, clientIdList);
    boolean removed = this.remove(wrapper);
    if (removed) {
      clientIdList.forEach(c -> redisClientDetailsService.removeRedisCache(String.valueOf(c)));
    }
  }
}
