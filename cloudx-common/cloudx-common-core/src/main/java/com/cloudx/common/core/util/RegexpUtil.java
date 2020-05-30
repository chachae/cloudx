package com.cloudx.common.core.util;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ReUtil;
import com.cloudx.common.core.constant.RegexpConstant;
import java.util.regex.Pattern;

/**
 * 正则匹配工具类（拓展 ReUtil 正则工具类）
 *
 * @author chachae
 * @since 2020/4/30 14:27
 */
public class RegexpUtil extends ReUtil {

  private RegexpUtil() {
  }

  public static boolean match(String regex, String value) {
    Pattern pattern = Pattern.compile(regex);
    return pattern.matcher(value).matches();
  }

  public static boolean isMobile(String mobileStr) {
    return match(RegexpConstant.MOBILE_REG, mobileStr);
  }

  public static boolean isIdCard(String idCardStr) {
    return match(RegexpConstant.ID_CARD_REG, idCardStr);
  }

  public static boolean hasChinese(String value) {
    return Validator.hasChinese(value);
  }

}
