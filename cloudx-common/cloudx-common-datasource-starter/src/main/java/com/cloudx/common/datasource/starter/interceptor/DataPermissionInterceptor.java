package com.cloudx.common.datasource.starter.interceptor;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.handlers.AbstractSqlParserHandler;
import com.cloudx.common.core.entity.auth.CurrentUser;
import com.cloudx.common.core.util.SecurityUtil;
import com.cloudx.common.datasource.starter.announcation.DataPermission;
import java.io.StringReader;
import java.sql.Connection;
import java.util.Properties;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

/**
 * 数据权限拦截器
 *
 * @author chachae
 * @version v1.0
 * @date 2020/5/29 16:39
 */
@Slf4j
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class,
    Integer.class})})
public class DataPermissionInterceptor extends AbstractSqlParserHandler implements Interceptor {

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
    MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
    this.sqlParser(metaObject);
    MappedStatement mappedStatement = (MappedStatement) metaObject
        .getValue("delegate.mappedStatement");

    BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
    // 数据权限只针对查询语句
    if (SqlCommandType.SELECT == mappedStatement.getSqlCommandType()) {
      DataPermission dataPermission = getDataPermission(mappedStatement);
      if (dataPermission != null && shouldFilter(mappedStatement, dataPermission)) {
        String originSql = boundSql.getSql();
        String dataPermissionSql = dataPermissionSql(originSql, dataPermission);
        metaObject.setValue("delegate.boundSql.sql", dataPermissionSql);
      }
    }
    return invocation.proceed();
  }

  @Override
  public Object plugin(Object target) {
    if (target instanceof StatementHandler) {
      return Plugin.wrap(target, this);
    }
    return target;
  }

  @Override
  public void setProperties(Properties properties) {
    // to programming......
  }

  private String dataPermissionSql(String originSql, DataPermission dataPermission) {
    try {
      // 字段为空或者用户为空，均返回默认 sql
      CurrentUser user = SecurityUtil.getCurrentUser();
      if (StrUtil.isBlank(dataPermission.field()) || user == null) {
        return originSql;
      }

      CCJSqlParserManager parserManager = new CCJSqlParserManager();
      Select select = (Select) parserManager.parse(new StringReader(originSql));
      PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
      Table fromItem = (Table) plainSelect.getFromItem();

      String selectTableName =
          fromItem.getAlias() == null ? fromItem.getName() : fromItem.getAlias().getName();
      String dataPermissionSql = String
          .format("%s.%s in (%s)", selectTableName, dataPermission.field(),
              StrUtil.blankToDefault(user.getDeptIds(), "'WARNING: WITHOUT PERMISSION!'"));

      if (plainSelect.getWhere() == null) {
        plainSelect.setWhere(CCJSqlParserUtil.parseCondExpression(dataPermissionSql));
      } else {
        plainSelect.setWhere(new AndExpression(plainSelect.getWhere(),
            CCJSqlParserUtil.parseCondExpression(dataPermissionSql)));
      }
      return select.toString();
    } catch (Exception e) {
      log.warn("get data permission sql fail: {}", e.getMessage());
      return originSql;
    }
  }

  private DataPermission getDataPermission(MappedStatement mappedStatement) {
    String mappedStatementId = mappedStatement.getId();
    DataPermission dataPermission = null;
    try {
      String className = mappedStatementId.substring(0, mappedStatementId.lastIndexOf('.'));
      // throws ClassNotFoundException......（than return null）
      final Class<?> clazz = Class.forName(className);
      if (clazz.isAnnotationPresent(DataPermission.class)) {
        dataPermission = clazz.getAnnotation(DataPermission.class);
      }
    } catch (ClassNotFoundException e) {
      return null;
    }
    return dataPermission;
  }

  private Boolean shouldFilter(@NotNull MappedStatement mappedStatement,
      @NotNull DataPermission dataPermission) {
    String methodName = StrUtil.subAfter(mappedStatement.getId(), '.', true);
    String methodPrefix = dataPermission.methodPrefix();
    // 方法名前缀过滤
    if (StrUtil.isNotBlank(methodPrefix) && methodName.startsWith(methodPrefix)) {
      return true;
    }
    // 方法全名过滤
    String[] methods = dataPermission.methods();
    for (String method : methods) {
      if (method.equals(methodName)) {
        return true;
      }
    }
    return false;
  }
}

