package com.cloudx.server.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudx.common.core.entity.system.SystemUser;
import com.cloudx.common.datasource.starter.announcation.DataPermission;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 用户Mapper
 *
 * @author chachae
 * @since 2020/4/30 19:56
 */
@DataPermission(methods = {"pageSystemUserDetail", "selectSystemUserDetail"})
public interface UserMapper extends BaseMapper<SystemUser> {


  /**
   * 查找用户详细信息
   *
   * @param page 分页对象
   * @param user 用户对象，用于传递查询条件
   * @param <T>  type
   * @return IPage<SystemUser>
   */
  <T> IPage<SystemUser> pageSystemUserDetail(Page<T> page, @Param("user") SystemUser user);

  /**
   * 查找用户详细信息
   *
   * @param user 用户对象，用于传递查询条件
   * @return List<SystemUser>
   */
  List<SystemUser> selectSystemUserDetail(@Param("user") SystemUser user);

}
