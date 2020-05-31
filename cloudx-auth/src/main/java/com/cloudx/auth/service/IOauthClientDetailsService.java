package com.cloudx.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudx.auth.entity.OauthClientDetails;
import com.cloudx.common.core.entity.QueryParam;

/**
 * 客户端业务接口
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/30 18:01
 */
public interface IOauthClientDetailsService extends IService<OauthClientDetails> {

  /**
   * 查询（分页）
   *
   * @param param              param
   * @param oauthClientDetails oauthClientDetails
   * @return IPage<OauthClientDetails>
   */
  IPage<OauthClientDetails> findOauthClientDetails(QueryParam param,
      OauthClientDetails oauthClientDetails);

  /**
   * 新增
   *
   * @param oauthClientDetails oauthClientDetails
   */
  void createOauthClientDetails(OauthClientDetails oauthClientDetails);

  /**
   * 修改
   *
   * @param oauthClientDetails oauthClientDetails
   */
  void updateOauthClientDetails(OauthClientDetails oauthClientDetails);

  /**
   * 删除
   *
   * @param clientIds clientIds
   */
  void deleteOauthClientDetails(String clientIds);
}

