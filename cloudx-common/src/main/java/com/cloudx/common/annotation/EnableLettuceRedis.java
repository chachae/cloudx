package com.cloudx.common.annotation;

import com.cloudx.common.config.LettuceRedisConfig;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * 注入 Redis Template 和 RedisService
 *
 * @author chachae
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LettuceRedisConfig.class)
public @interface EnableLettuceRedis {

}
