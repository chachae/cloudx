package com.cloudx.server.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudx.common.core.constant.PageResultConstant;
import com.cloudx.common.core.constant.SystemConstant;
import com.cloudx.common.core.entity.DeptTree;
import com.cloudx.common.core.entity.QueryParam;
import com.cloudx.common.core.entity.Tree;
import com.cloudx.common.core.entity.system.Dept;
import com.cloudx.common.core.util.SortUtil;
import com.cloudx.common.core.util.TreeUtil;
import com.cloudx.server.system.mapper.DeptMapper;
import com.cloudx.server.system.service.IDeptService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 部门业务接口实现
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/27 22:09
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

  @Override
  public Map<String, Object> getDepts(QueryParam param, Dept dept) {
    Map<String, Object> result = new HashMap<>(2);
    try {
      List<Dept> depts = getDepts(dept, param);
      List<DeptTree> trees = new ArrayList<>();
      buildTrees(trees, depts);
      List<? extends Tree<?>> deptTree = TreeUtil.build(trees);

      result.put(PageResultConstant.ROWS, deptTree);
      result.put(PageResultConstant.TOTAL, depts.size());
    } catch (Exception e) {
      log.error("获取部门列表失败", e);
      result.put(PageResultConstant.ROWS, null);
      result.put(PageResultConstant.TOTAL, 0);
    }
    return result;
  }

  @Override
  public List<Dept> getDepts(Dept dept, QueryParam param) {
    QueryWrapper<Dept> qw = new QueryWrapper<>();

    if (StringUtils.isNotBlank(dept.getDeptName())) {
      qw.lambda().like(Dept::getDeptName, dept.getDeptName());
    }
    if (StringUtils.isNotBlank(dept.getCreateTimeFrom()) && StringUtils
        .isNotBlank(dept.getCreateTimeTo())) {
      qw.lambda()
          .ge(Dept::getCreateTime, dept.getCreateTimeFrom())
          .le(Dept::getCreateTime, dept.getCreateTimeTo());
    }
    SortUtil.handleWrapperSort(param, qw, "orderNum", SystemConstant.ORDER_ASC, true);
    return baseMapper.selectList(qw);
  }

  private void buildTrees(List<DeptTree> trees, List<Dept> depts) {
    depts.forEach(dept -> {
      DeptTree tree = new DeptTree();
      tree.setId(dept.getDeptId().toString());
      tree.setParentId(dept.getParentId().toString());
      tree.setLabel(dept.getDeptName());
      tree.setOrderNum(dept.getOrderNum());
      trees.add(tree);
    });
  }
}
