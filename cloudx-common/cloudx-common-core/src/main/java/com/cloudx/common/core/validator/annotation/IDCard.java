package com.cloudx.common.core.validator.annotation;

import com.cloudx.common.core.validator.IDCardValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 18位身份证校验注解
 *
 * @author chachae
 * @since 2020/4/30 13:33
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IDCardValidator.class)
public @interface IDCard {

  String message();

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
