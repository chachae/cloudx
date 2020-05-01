package com.cloudx.common.annotation;

import com.cloudx.common.config.Oauth2FeignConfig;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * @author chachae
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(Oauth2FeignConfig.class)
public @interface EnableCloudxOauth2FeignClient {

}
