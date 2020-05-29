package com.cloudx.server.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudx.common.core.constant.PageResultConstant;
import com.cloudx.common.core.constant.SystemConstant;
import com.cloudx.common.core.entity.DeptTree;
import com.cloudx.common.core.entity.QueryParam;
import com.cloudx.common.core.entity.Tree;
import com.cloudx.common.core.entity.system.Dept;
import com.cloudx.common.core.exception.ApiException;
import com.cloudx.common.core.util.SortUtil;
import com.cloudx.common.core.util.TreeUtil;
import com.cloudx.server.system.mapper.DeptMapper;
import com.cloudx.server.system.service.IDeptService;
import com.cloudx.server.system.service.IUserDataPermissionService;
import com.cloudx.server.system.service.IUserService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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

  private final IUserService userService;
  private final IUserDataPermissionService userDataPermissionService;

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

    if (StrUtil.isNotBlank(dept.getDeptName())) {
      qw.lambda().like(Dept::getDeptName, dept.getDeptName());
    }
    if (StrUtil.isNotBlank(dept.getCreateTimeFrom()) && StrUtil
        .isNotBlank(dept.getCreateTimeTo())) {
      qw.lambda()
          .ge(Dept::getCreateTime, dept.getCreateTimeFrom())
          .le(Dept::getCreateTime, dept.getCreateTimeTo());
    }
    SortUtil.handleWrapperSort(param, qw, "order_num", SystemConstant.ORDER_ASC, false);
    return baseMapper.selectList(qw);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createDept(Dept dept) {
    if (dept.getParentId() == null) {
      dept.setParentId(Dept.TOP_DEPT_ID);
    }
    dept.setCreateTime(new Date());
    this.save(dept);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateDept(Dept dept) {
    if (dept.getParentId() == null) {
      dept.setParentId(Dept.TOP_DEPT_ID);
    }
    dept.setUpdateTime(new Date());
    this.baseMapper.updateById(dept);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteDepts(String[] deptIds) {
    // 查询时候存在子级部门
    if (CollUtil.isNotEmpty(getDeptIdByParentId(deptIds))) {
      throw new ApiException("部门存在二级部门关联，请删除相关部门后重试");
    }
    // 查询是否有用户关联
    List<String> result = this.userService.getUserIdByDeptIds(deptIds);
    if (CollUtil.isNotEmpty(result)) {
      throw new ApiException("部门存在用户关联，请删除相关用户后重试");
    }
    this.delete(Arrays.asList(deptIds));
  }


  private void delete(List<String> deptIds) {
    removeByIds(deptIds);
    userDataPermissionService.deleteByDeptIds(deptIds);

    LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.in(Dept::getParentId, deptIds);
    List<Dept> depts = baseMapper.selectList(queryWrapper);
    if (CollUtil.isNotEmpty(depts)) {
      List<String> deptIdList = new ArrayList<>();
      depts.forEach(d -> deptIdList.add(String.valueOf(d.getDeptId())));
      this.delete(deptIdList);
    }
  }

  private List<String> getDeptIdByParentId(String[] deptIds) {
    return baseMapper
        .selectList(new LambdaQueryWrapper<Dept>().in(Dept::getParentId, String.join(",", deptIds)))
        .stream().map(dept -> String.valueOf(dept.getDeptId())).collect(
            Collectors.toList());
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
