package com.cloudx.common.core.validator;

import cn.hutool.core.util.StrUtil;
import com.cloudx.common.core.util.RegexpUtil;
import com.cloudx.common.core.validator.annotation.Mobile;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验是否为合法的手机号码
 *
 * @author chachae
 * @since 2020/04/30 12:24
 */
public class MobileValidator implements ConstraintValidator<Mobile, String> {

  @Override
  public void initialize(Mobile mobile) {
  }

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    try {
      return StrUtil.isBlank(s) || RegexpUtil.isMobile(s);
    } catch (Exception e) {
      return false;
    }
  }
}
