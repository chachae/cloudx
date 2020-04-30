package com.cloudx.common.annotation;

import com.cloudx.common.selector.CloudxApplicationSelector;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * @author chachae
 * @since 2020/04/30 18:07
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(CloudxApplicationSelector.class)
public @interface CloudxApplication {

}