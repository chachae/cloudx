package com.cloudx.common.validator;

import cn.hutool.core.util.StrUtil;
import com.cloudx.common.util.RegexpUtil;
import com.cloudx.common.validator.annotation.IDCard;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验是否为合法的18位身份证
 *
 * @author chachae
 * @since 2020/04/30 12:24
 */
public class IDCardValidator implements ConstraintValidator<IDCard, String> {

  @Override
  public void initialize(IDCard idCard) {
  }

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    try {
      return StrUtil.isBlank(s) || RegexpUtil.isIdCard(s);
    } catch (Exception e) {
      return false;
    }
  }
}
