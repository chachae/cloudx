package com.cloudx.auth.controller;

import com.cloudx.auth.service.ICaptchaService;
import java.io.IOException;
import java.security.Principal;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chachae
 * @since 2020/4/25 17:42
 */

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

  private final ICaptchaService captchaService;

  @GetMapping("user")
  public Principal currentUser(Principal principal) {
    return principal;
  }

  @GetMapping("/captcha")
  public void getCaptcha(HttpServletResponse response)
      throws IOException, CacheException {
    captchaService.create(response);
  }

}
