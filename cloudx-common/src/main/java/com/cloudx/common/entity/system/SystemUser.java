package com.cloudx.common.entity.system;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户表实体类
 *
 * @author chachae
 * @since 2020-04-24 22:49:53
 */
@Data
@TableName("user")
public class SystemUser implements Serializable {

  private static final long serialVersionUID = -4352930128794165121L;

  /**
   * 自增ID
   */
  @TableId
  private Long id;
  /**
   * 用户数据ID
   */
  private String userInfoId;
  /**
   * 账号
   */
  private String userName;
  /**
   * 真实姓名
   */
  private String fullName;
  /**
   * 密码
   */
  private String password;
  /**
   * 性别
   */
  private String sex;
  /**
   * 头像地址
   */
  private String avatar;
  /**
   * 邮箱地址
   */
  private String email;
  /**
   * 移动电话
   */
  private String mobile;
  /**
   * qq号码
   */
  private String qqNumber;
  /**
   * 个人介绍
   */
  private String profile;
  /**
   * 生日
   */
  private Date birthday;
  /**
   * 博客地址
   */
  private String blogAddress;
  /**
   * 创建时间
   */
  private Date createTime;
  /**
   * 更新时间
   */
  private Date updateTime;
  /**
   * 上次登录时间
   */
  private Date lastLoginTime;
  /**
   * 上次登录ip
   */
  private String lastLoginIp;
  /**
   * 部门id
   */
  private Integer deptId;

}