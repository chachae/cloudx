package com.cloudx.common.constant;

/**
 * 系统用户默认数据常量
 *
 * @author chachae
 * @since 2020/4/29 22:41
 */
public class SystemUserConstant {

  private SystemUserConstant() {
  }

  /**
   * 用户状态：有效
   */
  public static final Boolean STATUS_VALID = true;
  /**
   * 用户状态：锁定
   */
  public static final Boolean STATUS_LOCK = false;
  /**
   * 默认头像
   */
  public static final String DEFAULT_AVATAR = "default.jpg";
  /**
   * 默认密码
   */
  public static final String DEFAULT_PASSWORD = "123456";
  /**
   * 性别男
   */
  public static final String GENDER_MALE = "0";
  /**
   * 性别女
   */
  public static final String GENDER_FEMALE = "1";
  /**
   * 性别保密
   */
  public static final String GENDER_UNKNOWN = "2";

}
