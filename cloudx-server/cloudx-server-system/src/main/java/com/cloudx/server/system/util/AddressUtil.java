package com.cloudx.server.system.util;

import com.cloudx.common.core.constant.SystemConstant;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;

/**
 * 根据 IP 获取地址
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/27 17:34
 */
@Slf4j
public class AddressUtil {

  private AddressUtil() {

  }

  public static String getCityInfo(String ip) {
    DbSearcher searcher = null;
    try {
      String dbPath = AddressUtil.class.getResource("/ip2region/ip2region.db").getPath();
      File file = new File(dbPath);
      if (!file.exists()) {
        String tmpDir = System.getProperties().getProperty(SystemConstant.JAVA_TEMP_DIR);
        dbPath = tmpDir + "ip.db";
        file = new File(dbPath);
        InputStream resourceAsStream = AddressUtil.class.getClassLoader()
            .getResourceAsStream("classpath:ip2region/ip2region.db");
        if (resourceAsStream != null) {
          FileUtils.copyInputStreamToFile(resourceAsStream, file);
        }
      }
      DbConfig config = new DbConfig();
      searcher = new DbSearcher(config, file.getPath());
      Method method = searcher.getClass().getMethod("btreeSearch", String.class);
      DataBlock dataBlock = (DataBlock) method.invoke(searcher, ip);
      return dataBlock.getRegion();
    } catch (Exception e) {
      log.warn("获取地址信息异常,{}", e.getMessage());
      return StringUtils.EMPTY;
    } finally {
      if (searcher != null) {
        try {
          searcher.close();
        } catch (IOException e) {
          log.error("ip2region 数据库异常", e);
        }
      }
    }
  }
}