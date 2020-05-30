package com.cloudx.auth.controller;

import com.cloudx.auth.service.ICaptchaService;
import com.cloudx.common.core.entity.R;
import java.security.Principal;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chachae
 * @since 2020/4/25 17:42
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class AuthController {

  private final ICaptchaService captchaService;

  @GetMapping("user")
  public Principal currentUser(Principal principal) {
    return principal;
  }

  @GetMapping("captcha")
  public R<String> getCaptcha(HttpServletResponse response) {
    return R.ok(captchaService.create(response));
  }

}
