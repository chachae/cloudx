package com.cloudx.auth.service.impl;

import com.cloudx.auth.exception.CaptchaException;
import com.cloudx.auth.properties.AuthProperties;
import com.cloudx.auth.properties.CaptchaProperties;
import com.cloudx.auth.service.ICaptchaService;
import com.cloudx.common.constant.ImageTypeConstant;
import com.cloudx.common.constant.ParamsConstant;
import com.cloudx.common.constant.SystemConstant;
import com.cloudx.common.service.RedisService;
import com.cloudx.common.util.HttpUtil;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 验证码业务实现
 *
 * @author chachae
 * @since 2020/4/25 23:15
 */
@Service
@RequiredArgsConstructor
public class CaptchaServiceImpl implements ICaptchaService {

  private final RedisService redisService;
  private final AuthProperties properties;

  @Override
  public String create() {
    String key = HttpUtil.get(ParamsConstant.CAPTCHA_KEY);
    if (StringUtils.isBlank(key)) {
      throw new CaptchaException("验证码key不能为空");
    }
    CaptchaProperties code = properties.getCode();
    Captcha captcha = createCaptcha(code);
    redisService.set(SystemConstant.CAPTCHA_PREFIX + key, StringUtils.lowerCase(captcha.text()),
        code.getTime());
    return captcha.toBase64();
  }

  @Override
  public void validateCode(String key, String value) {
    Object rightCaptchaText = redisService.get(SystemConstant.CAPTCHA_PREFIX + key);
    if (StringUtils.isBlank(value)) {
      throw new CaptchaException("请输入验证码");
    }
    if (rightCaptchaText == null) {
      throw new CaptchaException("验证码已过期");
    }
    if (!StringUtils.equalsIgnoreCase(value, String.valueOf(rightCaptchaText))) {
      throw new CaptchaException("验证码不正确");
    }
  }

  private Captcha createCaptcha(CaptchaProperties code) {
    Captcha captcha;
    if (StringUtils.equalsIgnoreCase(code.getType(), ImageTypeConstant.GIF)) {
      captcha = new GifCaptcha(code.getWidth(), code.getHeight(), code.getLength());
    } else {
      captcha = new SpecCaptcha(code.getWidth(), code.getHeight(), code.getLength());
    }
    captcha.setCharType(code.getCharType());
    return captcha;
  }

}
