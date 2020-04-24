package com.cloudx.common.entity.system;

import com.cloudx.common.base.BaseEntity;
import java.sql.Timestamp;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户表
 *
 * @author chachae
 * @since 2020/4/13 18:50
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

  //用户ID
  private Integer userId;
  //昵称
  @NotBlank(message = "昵称不能为空")
  @Size(min = 2, max = 20, message = "昵称长度应在2~20个字符之间")
  private String nickName;
  //账号
  private String userName;
  //密码
  @NotBlank(message = "密码不能为空")
  private String password;
  //性别
  private String sex;
  //头像地址
  private String avatar;
  //邮箱地址
  @Email(message = "请输入格式正确的邮箱地址")
  private String email;
  //移动电话
  private String mobile;
  //qq号码
  private String qqNumber;
  //个人介绍
  private String profile;
  //生日
  private Timestamp birthday;
  //博客地址
  private String blogAddress;
  //创建时间
  private Timestamp createTime;
  //更新时间
  private Timestamp updateTime;
  //上次登录时间
  private Timestamp lastLoginTime;
  //上次登录ip
  private String lastLoginIp;
}
