package com.cloudx.common.core.constant;

/**
 * 系统用户默认数据常量
 *
 * @author chachae
 * @since 2020/4/29 22:41
 */
public interface SystemUserConstant {

  /**
   * 用户状态：有效
   */
  String STATUS_VALID = "1";
  /**
   * 用户状态：锁定
   */
  String STATUS_LOCK = "0";
  /**
   * 默认头像
   */
  String DEFAULT_AVATAR = "default.jpg";
  /**
   * 默认密码
   */
  String DEFAULT_PASSWORD = "123456";
  /**
   * 性别男
   */
  String GENDER_MALE = "0";
  /**
   * 性别女
   */
  String GENDER_FEMALE = "1";
  /**
   * 性别保密
   */
  String GENDER_UNKNOWN = "2";

}
