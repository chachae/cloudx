package com.cloudx.common.security.starter.annotation;

import com.cloudx.common.security.starter.config.CloudxCloudResourceServerConfig;
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
@Import(CloudxCloudResourceServerConfig.class)
public @interface EnableCloudxCloudResourceServer {

}
