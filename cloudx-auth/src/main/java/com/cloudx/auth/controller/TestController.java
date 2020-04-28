package com.cloudx.auth.controller;

import com.cloudx.auth.service.ICaptchaService;
import com.cloudx.common.util.SecurityUtil;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chachae
 * @since 2020/4/25 17:42
 */

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

  private final ICaptchaService captchaService;

  @GetMapping("/authInfo")
  public Object getAuthInfo() {

    return SecurityUtil.getCurrentUser();
  }

  @GetMapping("/captcha")
  @PreAuthorize("hasAuthority('employee:list')")
  public void getCaptcha(HttpServletResponse response)
      throws IOException, CacheException {
    captchaService.create(response);
  }

}
