package com.cloudx.common.core.util;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudx.common.core.constant.SystemConstant;
import com.cloudx.common.core.entity.QueryParam;

/**
 * 处理排序工具类
 *
 * @author MrBird
 */
public class SortUtil {

  private SortUtil() {
  }

  /**
   * 处理排序（分页情况下） for mybatis-plus
   *
   * @param request           QueryRequest
   * @param page              Page
   * @param defaultSort       默认排序的字段
   * @param defaultOrder      默认排序规则
   * @param camelToUnderscore 是否开启驼峰转下划线
   */
  public static void handlePageSort(QueryParam request, Page<?> page, String defaultSort,
      String defaultOrder, boolean camelToUnderscore) {
    page.setCurrent(request.getPageNum());
    page.setSize(request.getPageSize());
    String sortField = request.getField();
    if (camelToUnderscore) {
      sortField = SystemUtil.camelToUnderscore(sortField);
      defaultSort = SystemUtil.camelToUnderscore(defaultSort);
    }
    if (StrUtil.isNotBlank(request.getField())
        && StrUtil.isNotBlank(request.getOrder())
        && !StrUtil.equalsIgnoreCase(request.getField(), "null")
        && !StrUtil.equalsIgnoreCase(request.getOrder(), "null")) {
      if (request.getOrder().equals(SystemConstant.ORDER_DESC)) {
        page.addOrder(OrderItem.desc(sortField));
      } else {
        page.addOrder(OrderItem.asc(sortField));
      }
    } else {
      if (StrUtil.isNotBlank(defaultSort)) {
        if (StrUtil.equals(defaultOrder, SystemConstant.ORDER_DESC)) {
          page.addOrder(OrderItem.desc(defaultSort));
        } else {
          page.addOrder(OrderItem.asc(defaultSort));
        }
      }
    }
  }

  /**
   * 处理排序 for mybatis-plus
   *
   * @param request QueryRequest
   * @param page    Page
   */
  public static void handlePageSort(QueryParam request, Page<?> page) {
    handlePageSort(request, page, null, null, false);
  }

  /**
   * 处理排序 for mybatis-plus
   *
   * @param request           QueryRequest
   * @param page              Page
   * @param camelToUnderscore 是否开启驼峰转下划线
   */
  public static void handlePageSort(QueryParam request, Page<?> page, boolean camelToUnderscore) {
    handlePageSort(request, page, null, null, camelToUnderscore);
  }

  /**
   * 处理排序 for mybatis-plus
   *
   * @param request           QueryRequest
   * @param wrapper           wrapper
   * @param defaultSort       默认排序的字段
   * @param defaultOrder      默认排序规则
   * @param camelToUnderscore 是否开启驼峰转下划线
   */
  public static void handleWrapperSort(QueryParam request, QueryWrapper<?> wrapper,
      String defaultSort, String defaultOrder, boolean camelToUnderscore) {
    String sortField = request.getField();
    if (camelToUnderscore) {
      sortField = SystemUtil.camelToUnderscore(sortField);
      defaultSort = SystemUtil.camelToUnderscore(defaultSort);
    }
    if (StrUtil.isNotBlank(request.getField())
        && StrUtil.isNotBlank(request.getOrder())
        && !StrUtil.equalsIgnoreCase(request.getField(), "null")
        && !StrUtil.equalsIgnoreCase(request.getOrder(), "null")) {
      if (StrUtil.equals(request.getOrder(), SystemConstant.ORDER_DESC)) {
        wrapper.orderByDesc(sortField);
      } else {
        wrapper.orderByAsc(sortField);
      }
    } else {
      if (StrUtil.isNotBlank(defaultSort)) {
        if (StrUtil.equals(defaultOrder, SystemConstant.ORDER_DESC)) {
          wrapper.orderByDesc(defaultSort);
        } else {
          wrapper.orderByAsc(defaultSort);
        }
      }
    }
  }

  /**
   * 处理排序 for mybatis-plus
   *
   * @param request QueryRequest
   * @param wrapper wrapper
   */
  public static void handleWrapperSort(QueryParam request, QueryWrapper<?> wrapper) {
    handleWrapperSort(request, wrapper, null, null, false);
  }

  /**
   * 处理排序 for mybatis-plus
   *
   * @param request           QueryRequest
   * @param wrapper           wrapper
   * @param camelToUnderscore 是否开启驼峰转下划线
   */
  public static void handleWrapperSort(QueryParam request, QueryWrapper<?> wrapper,
      boolean camelToUnderscore) {
    handleWrapperSort(request, wrapper, null, null, camelToUnderscore);
  }
}
