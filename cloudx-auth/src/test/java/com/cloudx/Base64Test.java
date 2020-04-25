package com.cloudx;

import java.nio.charset.StandardCharsets;
import org.springframework.util.Base64Utils;

/**
 * @author chachae
 * @since 2020/4/25 11:33
 */
public class Base64Test {

  public static void main(String[] args) {
    System.out.println(de());
  }

  public static String en() {
    return Base64Utils.encodeToString(":fefwgewg".getBytes());
  }

  public static String de() {
    String ct = en();
    byte[] decoded = Base64Utils.decode(ct.getBytes(StandardCharsets.UTF_8));
    String token = new String(decoded, StandardCharsets.UTF_8);
    int split = token.indexOf(':');
    return split > 0 && split < token.length() ? token.substring(0, split) : null;
  }

}
