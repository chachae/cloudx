package com.cloudx.server.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudx.common.core.entity.dto.SystemUserDTO;
import com.cloudx.common.core.entity.system.SystemUser;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author chachae
 * @since 2020/4/30 19:56
 */

public interface UserMapper extends BaseMapper<SystemUser> {


  /**
   * 查找用户详细信息
   *
   * @param page 分页对象
   * @param user 用户对象，用于传递查询条件
   * @param <T>  type
   * @return Ipage<SystemUserDTO>
   */
  <T> IPage<SystemUserDTO> pageSystemUserDetail(Page<T> page, @Param("user") SystemUserDTO user);

  /**
   * 查找用户详细信息
   *
   * @param user 用户对象，用于传递查询条件
   * @return List<SystemUserDTO>
   */
  List<SystemUserDTO> selectSystemUserDetail(@Param("user") SystemUserDTO user);

}
