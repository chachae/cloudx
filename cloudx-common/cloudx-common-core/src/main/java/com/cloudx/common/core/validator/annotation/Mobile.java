package com.cloudx.common.core.validator.annotation;

import com.cloudx.common.core.validator.MobileValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 移动电话校验注解
 *
 * @author chachae
 * @since 2020/04/30 12:25
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MobileValidator.class)
public @interface Mobile {

  String message();

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
