package com.cloudx.common.core.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cloudx.common.core.constant.PageResultConstant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页工具
 *
 * @author chachae
 * @since 2019/12/21 11:01
 */
public class PageUtil {

  private PageUtil() {
  }

  /**
   * Page 数据处理，预防redis反序列化报错
   */
  public static <T> Map<String, Object> toPage(IPage<T> page) {
    Map<String, Object> map = new HashMap<>(2);
    map.put(PageResultConstant.ROWS, page.getRecords());
    map.put(PageResultConstant.TOTAL, page.getTotal());
    return map;
  }

  /**
   * Page 数据处理，预防redis反序列化报错
   */
  public static <T> Map<String, Object> toPage(T records, long total) {
    Map<String, Object> map = new HashMap<>(2);
    map.put(PageResultConstant.ROWS, records);
    map.put(PageResultConstant.TOTAL, total);
    return map;
  }

  /**
   * List<T> 分页
   */
  public static <T> List<T> toPage(int page, int size, List<T> list) {
    int start = (page - 1) * size;
    int end = start + size;
    if (start > list.size()) {
      return new ArrayList<>(0);
    } else if (end >= list.size()) {
      return list.subList(start, list.size());
    } else {
      return list.subList(start, end);
    }
  }
}
