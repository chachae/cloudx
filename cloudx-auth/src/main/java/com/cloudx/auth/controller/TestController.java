package com.cloudx.auth.controller;

import com.cloudx.common.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chachae
 * @since 2020/4/25 17:42
 */

@Slf4j
@RestController
public class TestController {

  @GetMapping("/authInfo")
  public Object getAuthInfo() {

    return SecurityUtil.getCurrentUser();
  }

}
