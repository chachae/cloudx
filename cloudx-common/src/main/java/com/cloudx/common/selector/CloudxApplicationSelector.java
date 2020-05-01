package com.cloudx.common.selector;

import com.cloudx.common.config.AuthExceptionConfig;
import com.cloudx.common.config.Oauth2FeignConfig;
import com.cloudx.common.config.ServerProtectConfig;
import javax.annotation.Nonnull;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 微服务外部配置收集配置类
 *
 * @author chachae
 * @since 2020/04/30 18:30
 */
public class CloudxApplicationSelector implements ImportSelector {

  @Override
  @SuppressWarnings("all")
  public String[] selectImports(@Nonnull AnnotationMetadata annotationMetadata) {
    return new String[]{
        AuthExceptionConfig.class.getName(),
        Oauth2FeignConfig.class.getName(),
        ServerProtectConfig.class.getName()
    };
  }
}
