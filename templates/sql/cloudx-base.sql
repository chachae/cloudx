/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : cloudx

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 01/06/2020 15:59:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `resource_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `client_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `scope` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `authorized_grant_types` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `web_server_redirect_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `authorities` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `access_token_validity` int NOT NULL,
  `refresh_token_validity` int DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `autoapprove` tinyint DEFAULT NULL,
  `origin_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
BEGIN;
INSERT INTO `oauth_client_details` VALUES ('app', '', '$2a$10$8Qk/efslEpO1Af1kyw/rp.DdJGsdnET8UCp1vGDzpQEa.1qBklvua', 'all', 'password,refresh_token', '', NULL, 86400, 8640000, NULL, 1, '123456');
INSERT INTO `oauth_client_details` VALUES ('cloudx', NULL, '$2a$10$aSZTvMOtUAYUQ.75z2n3ceJd6dCIk9Vy3J/SKZUE4hBLd6sz7.6ge', 'all', 'password,refresh_token', NULL, NULL, 86400, 8640000, NULL, 1, '123456');
COMMIT;

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code` (
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `authentication` blob,
  KEY `code_index` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of oauth_code
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_dept`;
CREATE TABLE `t_dept` (
  `dept_id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `parent_id` bigint NOT NULL COMMENT '上级部门ID',
  `dept_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门名称',
  `order_num` int DEFAULT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='部门表';

-- ----------------------------
-- Records of t_dept
-- ----------------------------
BEGIN;
INSERT INTO `t_dept` VALUES (1, 0, '开发部', 1, '2018-01-04 15:42:26', '2019-01-05 21:08:27');
INSERT INTO `t_dept` VALUES (2, 1, '开发一部', 1, '2018-01-04 15:42:34', '2019-01-18 00:59:37');
INSERT INTO `t_dept` VALUES (3, 1, '开发二部', 2, '2018-01-04 15:42:29', '2020-05-30 17:57:11');
INSERT INTO `t_dept` VALUES (4, 0, '市场部', 2, '2018-01-04 15:42:36', '2019-01-23 06:27:56');
INSERT INTO `t_dept` VALUES (5, 0, '人事部', 3, '2018-01-04 15:42:32', '2019-01-23 06:27:59');
INSERT INTO `t_dept` VALUES (6, 0, '测试部', 4, '2018-01-04 15:42:38', '2019-01-17 08:15:47');
INSERT INTO `t_dept` VALUES (8, 3, '开发二部（南方分部）', 0, '2020-05-30 17:58:46', NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_log
-- ----------------------------
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '操作用户',
  `operation` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '操作内容',
  `time` decimal(11,0) DEFAULT NULL COMMENT '耗时',
  `method` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '操作方法',
  `params` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '方法参数',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '操作者IP',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `location` varchar(50) DEFAULT NULL COMMENT '操作地点',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `t_log_create_time` (`create_time`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户操作日志表';

-- ----------------------------
-- Records of t_log
-- ----------------------------
BEGIN;
INSERT INTO `t_log` VALUES (1, 'chachae', '新增部门', 79, 'com.cloudx.server.system.controller.DeptController.addDept()', ' dept: \"Dept(deptId=7, parentId=5, deptName=111212, orderNum=0, createTime=Sat May 30 17:04:10 CST 2020, updateTime=null, createTimeFrom=null, createTimeTo=null)\"', '169.254.202.65', '2020-05-30 17:04:11', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `t_log` VALUES (2, 'chachae', '删除部门', 165, 'com.cloudx.server.system.controller.DeptController.deleteDepts()', ' deptIds: \"7\"', '169.254.202.65', '2020-05-30 17:04:18', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `t_log` VALUES (3, 'chachae', '更新部门', 62, 'com.cloudx.server.system.controller.DeptController.updateDept()', ' dept: \"Dept(deptId=3, parentId=2, deptName=开发二部, orderNum=2, createTime=null, updateTime=Sat May 30 17:56:53 CST 2020, createTimeFrom=null, createTimeTo=null)\"', '169.254.202.65', '2020-05-30 17:56:54', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `t_log` VALUES (4, 'chachae', '更新部门', 38, 'com.cloudx.server.system.controller.DeptController.updateDept()', ' dept: \"Dept(deptId=3, parentId=1, deptName=开发二部, orderNum=2, createTime=null, updateTime=Sat May 30 17:57:11 CST 2020, createTimeFrom=null, createTimeTo=null)\"', '169.254.202.65', '2020-05-30 17:57:11', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `t_log` VALUES (5, 'chachae', '新增部门', 52, 'com.cloudx.server.system.controller.DeptController.addDept()', ' dept: \"Dept(deptId=8, parentId=3, deptName=开发二部（南方分部）, orderNum=0, createTime=Sat May 30 17:58:46 CST 2020, updateTime=null, createTimeFrom=null, createTimeTo=null)\"', '169.254.202.65', '2020-05-30 17:58:46', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `t_log` VALUES (6, 'chachae', '新增角色', 130, 'com.cloudx.server.system.controller.RoleController.addRole()', ' role: \"Role(roleId=5, roleName=辅导员, remark=辅导员, createTime=Sun May 31 22:48:15 CST 2020, updateTime=null, menuIds=3)\"', '169.254.113.159', '2020-05-31 22:48:16', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `t_log` VALUES (7, 'chachae', '新增用户', 206, 'com.cloudx.server.system.controller.UserController.addUser()', ' user: \"SystemUser(userId=19, username=fudaoyuan, password=$2a$10$WJqH8KEzpQ4y7pW5NM.KAeCnE217w8Rqh9/R94HDczqmfjPFP/am., deptId=4, email=fhu@qq.com, mobile=, status=1, createTime=Sun May 31 22:49:41 CST 2020, updateTime=null, lastLoginTime=null, sex=0, avatar=default.jpg, description=null, deptName=null, createTimeFrom=null, createTimeTo=null, roleId=5, roleName=null, deptIds=)\"', '169.254.113.159', '2020-05-31 22:49:42', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `t_log` VALUES (8, 'chachae', '删除用户', 102, 'com.cloudx.server.system.controller.UserController.deleteUsers()', ' userIds: \"19\"', '169.254.113.159', '2020-05-31 22:50:36', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `t_log` VALUES (9, 'chachae', '删除角色', 143, 'com.cloudx.server.system.controller.RoleController.deleteRoles()', ' roleIds: \"5\"', '169.254.113.159', '2020-05-31 22:50:42', '内网IP|0|0|内网IP|内网IP');
INSERT INTO `t_log` VALUES (10, 'chachae', '更新角色', 393, 'com.cloudx.server.system.controller.RoleController.updateRole()', ' role: \"Role(roleId=1, roleName=null, remark=管理员, createTime=null, updateTime=Mon Jun 01 15:42:38 CST 2020, menuIds=1,3,135,130,13,12,11,4,14,15,16,131,5,132,17,18,19,6,133,20,21,22,163,167,164,165,166,2,180,10,136,24,150,151,152,173,174,175,176,177,178,179,156,157,159,158,160,154,155,168,169,170,171,172)\"', '169.254.97.217', '2020-06-01 15:42:39', '内网IP|0|0|内网IP|内网IP');
COMMIT;

-- ----------------------------
-- Table structure for t_logger
-- ----------------------------
DROP TABLE IF EXISTS `t_logger`;
CREATE TABLE `t_logger` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `group_id` varchar(64) COLLATE utf8mb4_general_ci NOT NULL,
  `unit_id` varchar(32) COLLATE utf8mb4_general_ci NOT NULL,
  `tag` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `content` varchar(1024) COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `app_name` varchar(128) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of t_logger
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_login_log
-- ----------------------------
DROP TABLE IF EXISTS `t_login_log`;
CREATE TABLE `t_login_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `login_time` datetime NOT NULL COMMENT '登录时间',
  `location` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '登录地点',
  `ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'IP地址',
  `system` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '浏览器',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `t_login_log_login_time` (`login_time`)
) ENGINE=MyISAM AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='登录日志表';

-- ----------------------------
-- Records of t_login_log
-- ----------------------------
BEGIN;
INSERT INTO `t_login_log` VALUES (1, 'chachae', '2020-05-27 18:42:49', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'OSX', 'MSEdge');
INSERT INTO `t_login_log` VALUES (2, 'chachae', '2020-05-27 20:29:12', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'OSX', 'MSEdge');
INSERT INTO `t_login_log` VALUES (3, 'chachae', '2020-05-27 23:06:37', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'OSX', 'MSEdge');
INSERT INTO `t_login_log` VALUES (4, 'chachae', '2020-05-28 12:28:39', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'OSX', 'MSEdge');
INSERT INTO `t_login_log` VALUES (5, 'chachae', '2020-05-28 17:07:21', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'OSX', 'MSEdge');
INSERT INTO `t_login_log` VALUES (6, 'chachae', '2020-05-28 17:10:55', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'OSX', 'MSEdge');
INSERT INTO `t_login_log` VALUES (7, 'chachae', '2020-05-28 17:30:41', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'OSX', 'MSEdge');
INSERT INTO `t_login_log` VALUES (8, 'chachae', '2020-05-28 17:45:51', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'OSX', 'MSEdge');
INSERT INTO `t_login_log` VALUES (9, 'chachae', '2020-05-28 17:48:23', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'OSX', 'MSEdge');
INSERT INTO `t_login_log` VALUES (10, 'chachae', '2020-05-28 17:51:24', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'OSX', 'MSEdge');
INSERT INTO `t_login_log` VALUES (11, 'chachae', '2020-05-28 18:07:52', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'OSX', 'MSEdge');
INSERT INTO `t_login_log` VALUES (12, 'scott', '2020-05-28 18:21:43', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'OSX', 'MSEdge');
INSERT INTO `t_login_log` VALUES (40, 'chachae', '2020-06-01 00:36:12', '内网IP|0|0|内网IP|内网IP', '169.254.113.159', 'Mac OS X 10.15.4', 'Microsoft Edge 83');
INSERT INTO `t_login_log` VALUES (39, 'chachae', '2020-05-31 22:50:12', '内网IP|0|0|内网IP|内网IP', '169.254.113.159', 'Mac OS X 10.15.4', 'Microsoft Edge 83');
INSERT INTO `t_login_log` VALUES (33, 'chachae', '2020-05-30 18:59:28', '内网IP|0|0|内网IP|内网IP', '169.254.202.65', 'Mac OS X 10.15.4', 'Microsoft Edge 83');
INSERT INTO `t_login_log` VALUES (34, 'chachae', '2020-05-30 21:10:10', '内网IP|0|0|内网IP|内网IP', '169.254.202.65', 'Mac OS X 10.15.4', 'Microsoft Edge 83');
INSERT INTO `t_login_log` VALUES (35, 'chachae', '2020-05-31 17:53:53', '内网IP|0|0|内网IP|内网IP', '169.254.113.159', 'Mac OS X 10.15.4', 'Microsoft Edge 83');
INSERT INTO `t_login_log` VALUES (36, 'chachae', '2020-05-31 18:46:00', '内网IP|0|0|内网IP|内网IP', '169.254.113.159', 'Mac OS X 10.15.4', 'Microsoft Edge 83');
INSERT INTO `t_login_log` VALUES (37, 'chachae', '2020-05-31 18:55:23', '内网IP|0|0|内网IP|内网IP', '169.254.113.159', 'Mac OS X 10.15.4', 'Microsoft Edge 83');
INSERT INTO `t_login_log` VALUES (38, 'fudaoyuan', '2020-05-31 22:49:57', '内网IP|0|0|内网IP|内网IP', '169.254.113.159', 'Mac OS X 10.15.4', 'Microsoft Edge 83');
COMMIT;

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单/按钮ID',
  `parent_id` bigint NOT NULL COMMENT '上级菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单/按钮名称',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '对应路由path',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '对应路由组件component',
  `expression` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限表达式',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图标',
  `type` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型 0菜单 1按钮',
  `order_num` double(20,0) DEFAULT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`menu_id`) USING BTREE,
  KEY `t_menu_parent_id` (`parent_id`),
  KEY `t_menu_menu_id` (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=195 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='菜单表';

-- ----------------------------
-- Records of t_menu
-- ----------------------------
BEGIN;
INSERT INTO `t_menu` VALUES (1, 0, '系统管理', '/system', 'Layout', NULL, 'el-icon-set-up', '0', 1, '2017-12-27 16:39:07', '2019-07-20 16:19:04');
INSERT INTO `t_menu` VALUES (2, 0, '系统监控', '/monitor', 'Layout', NULL, 'el-icon-data-line', '0', 2, '2017-12-27 16:45:51', '2019-01-23 06:27:12');
INSERT INTO `t_menu` VALUES (3, 1, '用户管理', '/system/user', 'febs/system/user/Index', 'user:view', '', '0', 1, '2017-12-27 16:47:13', '2019-01-22 06:45:55');
INSERT INTO `t_menu` VALUES (4, 1, '角色管理', '/system/role', 'febs/system/role/Index', 'role:view', '', '0', 2, '2017-12-27 16:48:09', '2018-04-25 09:01:12');
INSERT INTO `t_menu` VALUES (5, 1, '菜单管理', '/system/menu', 'febs/system/menu/Index', 'menu:view', '', '0', 3, '2017-12-27 16:48:57', '2018-04-25 09:01:30');
INSERT INTO `t_menu` VALUES (6, 1, '部门管理', '/system/dept', 'febs/system/dept/Index', 'dept:view', '', '0', 4, '2017-12-27 16:57:33', '2018-04-25 09:01:40');
INSERT INTO `t_menu` VALUES (10, 2, '系统日志', '/monitor/systemlog', 'febs/monitor/systemlog/Index', 'log:view', '', '0', 2, '2017-12-27 17:00:50', '2020-04-13 11:38:04');
INSERT INTO `t_menu` VALUES (11, 3, '新增用户', '', '', 'user:add', NULL, '1', NULL, '2017-12-27 17:02:58', NULL);
INSERT INTO `t_menu` VALUES (12, 3, '修改用户', '', '', 'user:update', NULL, '1', NULL, '2017-12-27 17:04:07', NULL);
INSERT INTO `t_menu` VALUES (13, 3, '删除用户', '', '', 'user:delete', NULL, '1', NULL, '2017-12-27 17:04:58', NULL);
INSERT INTO `t_menu` VALUES (14, 4, '新增角色', '', '', 'role:add', NULL, '1', NULL, '2017-12-27 17:06:38', NULL);
INSERT INTO `t_menu` VALUES (15, 4, '修改角色', '', '', 'role:update', NULL, '1', NULL, '2017-12-27 17:06:38', NULL);
INSERT INTO `t_menu` VALUES (16, 4, '删除角色', '', '', 'role:delete', NULL, '1', NULL, '2017-12-27 17:06:38', NULL);
INSERT INTO `t_menu` VALUES (17, 5, '新增菜单', '', '', 'menu:add', NULL, '1', NULL, '2017-12-27 17:08:02', NULL);
INSERT INTO `t_menu` VALUES (18, 5, '修改菜单', '', '', 'menu:update', NULL, '1', NULL, '2017-12-27 17:08:02', NULL);
INSERT INTO `t_menu` VALUES (19, 5, '删除菜单', '', '', 'menu:delete', NULL, '1', NULL, '2017-12-27 17:08:02', NULL);
INSERT INTO `t_menu` VALUES (20, 6, '新增部门', '', '', 'dept:add', NULL, '1', NULL, '2017-12-27 17:09:24', NULL);
INSERT INTO `t_menu` VALUES (21, 6, '修改部门', '', '', 'dept:update', NULL, '1', NULL, '2017-12-27 17:09:24', NULL);
INSERT INTO `t_menu` VALUES (22, 6, '删除部门', '', '', 'dept:delete', NULL, '1', NULL, '2017-12-27 17:09:24', NULL);
INSERT INTO `t_menu` VALUES (24, 10, '删除日志', '', '', 'log:delete', NULL, '1', NULL, '2017-12-27 17:11:45', NULL);
INSERT INTO `t_menu` VALUES (130, 3, '导出Excel', NULL, NULL, 'user:export', NULL, '1', NULL, '2019-01-23 06:35:16', NULL);
INSERT INTO `t_menu` VALUES (131, 4, '导出Excel', NULL, NULL, 'role:export', NULL, '1', NULL, '2019-01-23 06:35:36', NULL);
INSERT INTO `t_menu` VALUES (132, 5, '导出Excel', NULL, NULL, 'menu:export', NULL, '1', NULL, '2019-01-23 06:36:05', NULL);
INSERT INTO `t_menu` VALUES (133, 6, '导出Excel', NULL, NULL, 'dept:export', NULL, '1', NULL, '2019-01-23 06:36:25', NULL);
INSERT INTO `t_menu` VALUES (135, 3, '密码重置', NULL, NULL, 'user:reset', NULL, '1', NULL, '2019-01-23 06:37:00', NULL);
INSERT INTO `t_menu` VALUES (136, 10, '导出Excel', NULL, NULL, 'log:export', NULL, '1', NULL, '2019-01-23 06:37:27', NULL);
INSERT INTO `t_menu` VALUES (150, 2, '登录日志', '/monitor/loginlog', 'febs/monitor/loginlog/Index', 'monitor:loginlog', '', '0', 3, '2019-07-22 13:41:17', '2020-04-13 11:38:08');
INSERT INTO `t_menu` VALUES (151, 150, '删除日志', NULL, NULL, 'loginlog:delete', NULL, '1', NULL, '2019-07-22 13:43:04', NULL);
INSERT INTO `t_menu` VALUES (152, 150, '导出Excel', NULL, NULL, 'loginlog:export', NULL, '1', NULL, '2019-07-22 13:43:30', NULL);
INSERT INTO `t_menu` VALUES (154, 0, '其他模块', '/others', 'Layout', '', 'el-icon-shopping-bag-1', '0', 6, '2019-07-25 10:16:16', '2020-04-14 18:38:20');
INSERT INTO `t_menu` VALUES (155, 154, '导入导出', '/others/eximport', 'febs/others/eximport/Index', 'others:eximport', '', '0', 1, '2019-07-25 10:19:31', NULL);
INSERT INTO `t_menu` VALUES (156, 0, '代码生成', '/gen', 'Layout', '', 'el-icon-printer', '0', 4, '2019-07-25 10:24:03', '2020-01-16 13:59:49');
INSERT INTO `t_menu` VALUES (157, 156, '基础配置', '/gen/config', 'febs/gen/config/Index', 'gen:config', '', '0', 1, '2019-07-25 10:24:55', '2020-04-09 14:21:54');
INSERT INTO `t_menu` VALUES (158, 156, '生成代码', '/gen/generate', 'febs/gen/generate/Index', 'gen:generate', '', '0', 2, '2019-07-25 10:25:26', '2019-07-25 11:13:20');
INSERT INTO `t_menu` VALUES (159, 157, '修改配置', NULL, NULL, 'gen:config:update', NULL, '1', NULL, '2019-07-26 16:22:56', NULL);
INSERT INTO `t_menu` VALUES (160, 158, '打包生成', NULL, NULL, 'gen:generate:gen', NULL, '1', NULL, '2019-07-26 16:23:38', '2019-07-26 16:23:53');
INSERT INTO `t_menu` VALUES (163, 1, '客户端管理', '/client', 'febs/system/client/Index', 'client:view', '', '0', 5, '2019-09-26 22:58:09', NULL);
INSERT INTO `t_menu` VALUES (164, 163, '新增', NULL, NULL, 'client:add', NULL, '1', NULL, '2019-09-26 22:58:21', NULL);
INSERT INTO `t_menu` VALUES (165, 163, '修改', NULL, NULL, 'client:update', NULL, '1', NULL, '2019-09-26 22:58:43', NULL);
INSERT INTO `t_menu` VALUES (166, 163, '删除', NULL, NULL, 'client:delete', NULL, '1', NULL, '2019-09-26 22:58:55', NULL);
INSERT INTO `t_menu` VALUES (167, 163, '解密', NULL, NULL, 'client:decrypt', NULL, '1', NULL, '2019-09-26 22:59:08', NULL);
INSERT INTO `t_menu` VALUES (168, 0, '静态组件', '/components', 'Layout', '', 'el-icon-present', '0', 7, '2019-12-02 16:41:28', '2020-04-14 18:38:23');
INSERT INTO `t_menu` VALUES (169, 168, '二级菜单', '/two', 'demos/two/Index', '', '', '0', 1, '2019-12-02 16:41:51', NULL);
INSERT INTO `t_menu` VALUES (170, 169, '三级菜单', '/three', 'demos/two/three/Index', '', '', '0', 1, '2019-12-02 16:42:09', NULL);
INSERT INTO `t_menu` VALUES (171, 168, 'MarkDown', '/components/markdown', 'demos/markdown', '', '', '0', 2, '2019-12-02 16:42:34', NULL);
INSERT INTO `t_menu` VALUES (172, 168, '富文本编辑器', '/components/tinymce', 'demos/tinymce', '', '', '0', 3, '2019-12-02 16:42:50', NULL);
INSERT INTO `t_menu` VALUES (173, 0, '网关管理', '/route', 'Layout', '', 'el-icon-odometer', '0', 3, '2020-01-16 14:00:15', NULL);
INSERT INTO `t_menu` VALUES (174, 173, '网关用户', '/route/user', 'febs/route/routeuser/Index', '', '', '0', 1, '2020-01-16 14:00:32', NULL);
INSERT INTO `t_menu` VALUES (175, 173, '网关日志', '/route/log', 'febs/route/routelog/Index', '', '', '0', 2, '2020-01-16 14:00:47', NULL);
INSERT INTO `t_menu` VALUES (176, 173, '限流规则', '/route/ratelimitrule', 'febs/route/ratelimitrule/Index', '', '', '0', 3, '2020-01-16 14:01:01', NULL);
INSERT INTO `t_menu` VALUES (177, 173, '限流日志', '/route/ratelimitlog', 'febs/route/ratelimitlog/Index', '', '', '0', 4, '2020-01-16 14:01:17', NULL);
INSERT INTO `t_menu` VALUES (178, 173, '黑名单管理', '/route/blacklist', 'febs/route/blacklist/Index', '', '', '0', 5, '2020-01-16 14:01:32', NULL);
INSERT INTO `t_menu` VALUES (179, 173, '黑名单日志', '/route/blocklog', 'febs/route/blocklog/Index', '', '', '0', 6, '2020-01-16 14:01:49', NULL);
INSERT INTO `t_menu` VALUES (180, 2, '监控面板', '/monitor/dashboard', 'febs/monitor/dashboard/Index', 'monitor:dashboard', '', '0', 1, '2020-04-13 09:44:09', '2020-04-13 11:38:00');
INSERT INTO `t_menu` VALUES (181, 154, '个人博客', '/others/blog', 'febs/others/blog/Index', '', '', '0', 2, '2020-04-13 16:11:48', '2020-04-13 16:12:26');
INSERT INTO `t_menu` VALUES (182, 154, '数据权限', '/others/datapermission', 'febs/others/datapermission/Index', 'others:datapermission', '', '0', 3, '2020-04-14 14:51:35', '2020-04-14 15:37:19');
INSERT INTO `t_menu` VALUES (183, 0, '任务调度', '/job', 'Layout', '', 'el-icon-alarm-clock', '0', 5, '2020-04-14 18:39:35', '2020-04-14 18:39:53');
INSERT INTO `t_menu` VALUES (184, 183, '任务列表', '/job/list', 'febs/job/job/Index', 'job:view', '', '0', 1, '2020-04-14 18:40:37', '2020-04-14 18:41:36');
INSERT INTO `t_menu` VALUES (185, 183, '调度日志', '/job/log', 'febs/job/log/Index', 'job:log:view', '', '0', 2, '2020-04-14 18:42:25', NULL);
INSERT INTO `t_menu` VALUES (186, 184, '新增任务', NULL, NULL, 'job:add', NULL, '1', NULL, '2020-04-14 18:59:55', '2020-04-15 08:56:03');
INSERT INTO `t_menu` VALUES (187, 184, '修改任务', NULL, NULL, 'job:update', NULL, '1', NULL, '2020-04-14 19:00:13', NULL);
INSERT INTO `t_menu` VALUES (188, 184, '删除任务', NULL, NULL, 'job:delete', NULL, '1', NULL, '2020-04-14 19:00:26', NULL);
INSERT INTO `t_menu` VALUES (189, 184, '暂停任务', NULL, NULL, 'job:pause', NULL, '1', NULL, '2020-04-14 19:00:42', NULL);
INSERT INTO `t_menu` VALUES (190, 184, '恢复任务', NULL, NULL, 'job:resume', NULL, '1', NULL, '2020-04-14 19:00:56', NULL);
INSERT INTO `t_menu` VALUES (191, 184, '立即执行一次', NULL, NULL, 'job:run', NULL, '1', NULL, '2020-04-14 19:01:42', NULL);
INSERT INTO `t_menu` VALUES (192, 184, '导出Excel', NULL, NULL, 'job:export', NULL, '1', NULL, '2020-04-14 19:01:59', NULL);
INSERT INTO `t_menu` VALUES (193, 185, '删除', NULL, NULL, 'job:log:delete', NULL, '1', NULL, '2020-04-15 14:01:33', NULL);
INSERT INTO `t_menu` VALUES (194, 185, '导出', NULL, NULL, 'job:log:export', NULL, '1', NULL, '2020-04-15 14:01:45', NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色表';

-- ----------------------------
-- Records of t_role
-- ----------------------------
BEGIN;
INSERT INTO `t_role` VALUES (1, '管理员', '管理员', '2017-12-27 16:23:11', '2020-06-01 15:42:38');
INSERT INTO `t_role` VALUES (2, '注册用户', '可查看，新增，导出', '2019-01-04 14:11:28', '2020-01-16 14:02:21');
INSERT INTO `t_role` VALUES (3, '系统监控员', '负责系统监控模块', '2019-09-01 10:30:25', '2019-09-01 10:30:37');
COMMIT;

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu` (
  `role_id` bigint NOT NULL,
  `menu_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色菜单关联表';

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `t_role_menu` VALUES (3, 2);
INSERT INTO `t_role_menu` VALUES (3, 10);
INSERT INTO `t_role_menu` VALUES (3, 24);
INSERT INTO `t_role_menu` VALUES (3, 136);
INSERT INTO `t_role_menu` VALUES (3, 150);
INSERT INTO `t_role_menu` VALUES (3, 151);
INSERT INTO `t_role_menu` VALUES (3, 152);
INSERT INTO `t_role_menu` VALUES (3, 149);
INSERT INTO `t_role_menu` VALUES (3, 148);
INSERT INTO `t_role_menu` VALUES (3, 153);
INSERT INTO `t_role_menu` VALUES (2, 1);
INSERT INTO `t_role_menu` VALUES (2, 3);
INSERT INTO `t_role_menu` VALUES (2, 4);
INSERT INTO `t_role_menu` VALUES (2, 5);
INSERT INTO `t_role_menu` VALUES (2, 6);
INSERT INTO `t_role_menu` VALUES (2, 2);
INSERT INTO `t_role_menu` VALUES (2, 10);
INSERT INTO `t_role_menu` VALUES (2, 150);
INSERT INTO `t_role_menu` VALUES (2, 149);
INSERT INTO `t_role_menu` VALUES (2, 148);
INSERT INTO `t_role_menu` VALUES (2, 153);
INSERT INTO `t_role_menu` VALUES (2, 173);
INSERT INTO `t_role_menu` VALUES (2, 174);
INSERT INTO `t_role_menu` VALUES (2, 175);
INSERT INTO `t_role_menu` VALUES (2, 176);
INSERT INTO `t_role_menu` VALUES (2, 177);
INSERT INTO `t_role_menu` VALUES (2, 178);
INSERT INTO `t_role_menu` VALUES (2, 179);
INSERT INTO `t_role_menu` VALUES (2, 156);
INSERT INTO `t_role_menu` VALUES (2, 157);
INSERT INTO `t_role_menu` VALUES (2, 158);
INSERT INTO `t_role_menu` VALUES (2, 154);
INSERT INTO `t_role_menu` VALUES (2, 155);
INSERT INTO `t_role_menu` VALUES (2, 168);
INSERT INTO `t_role_menu` VALUES (2, 169);
INSERT INTO `t_role_menu` VALUES (2, 170);
INSERT INTO `t_role_menu` VALUES (2, 171);
INSERT INTO `t_role_menu` VALUES (2, 172);
INSERT INTO `t_role_menu` VALUES (1, 1);
INSERT INTO `t_role_menu` VALUES (1, 3);
INSERT INTO `t_role_menu` VALUES (1, 135);
INSERT INTO `t_role_menu` VALUES (1, 130);
INSERT INTO `t_role_menu` VALUES (1, 13);
INSERT INTO `t_role_menu` VALUES (1, 12);
INSERT INTO `t_role_menu` VALUES (1, 11);
INSERT INTO `t_role_menu` VALUES (1, 4);
INSERT INTO `t_role_menu` VALUES (1, 14);
INSERT INTO `t_role_menu` VALUES (1, 15);
INSERT INTO `t_role_menu` VALUES (1, 16);
INSERT INTO `t_role_menu` VALUES (1, 131);
INSERT INTO `t_role_menu` VALUES (1, 5);
INSERT INTO `t_role_menu` VALUES (1, 132);
INSERT INTO `t_role_menu` VALUES (1, 17);
INSERT INTO `t_role_menu` VALUES (1, 18);
INSERT INTO `t_role_menu` VALUES (1, 19);
INSERT INTO `t_role_menu` VALUES (1, 6);
INSERT INTO `t_role_menu` VALUES (1, 133);
INSERT INTO `t_role_menu` VALUES (1, 20);
INSERT INTO `t_role_menu` VALUES (1, 21);
INSERT INTO `t_role_menu` VALUES (1, 22);
INSERT INTO `t_role_menu` VALUES (1, 163);
INSERT INTO `t_role_menu` VALUES (1, 167);
INSERT INTO `t_role_menu` VALUES (1, 164);
INSERT INTO `t_role_menu` VALUES (1, 165);
INSERT INTO `t_role_menu` VALUES (1, 166);
INSERT INTO `t_role_menu` VALUES (1, 2);
INSERT INTO `t_role_menu` VALUES (1, 180);
INSERT INTO `t_role_menu` VALUES (1, 10);
INSERT INTO `t_role_menu` VALUES (1, 136);
INSERT INTO `t_role_menu` VALUES (1, 24);
INSERT INTO `t_role_menu` VALUES (1, 150);
INSERT INTO `t_role_menu` VALUES (1, 151);
INSERT INTO `t_role_menu` VALUES (1, 152);
INSERT INTO `t_role_menu` VALUES (1, 173);
INSERT INTO `t_role_menu` VALUES (1, 174);
INSERT INTO `t_role_menu` VALUES (1, 175);
INSERT INTO `t_role_menu` VALUES (1, 176);
INSERT INTO `t_role_menu` VALUES (1, 177);
INSERT INTO `t_role_menu` VALUES (1, 178);
INSERT INTO `t_role_menu` VALUES (1, 179);
INSERT INTO `t_role_menu` VALUES (1, 156);
INSERT INTO `t_role_menu` VALUES (1, 157);
INSERT INTO `t_role_menu` VALUES (1, 159);
INSERT INTO `t_role_menu` VALUES (1, 158);
INSERT INTO `t_role_menu` VALUES (1, 160);
INSERT INTO `t_role_menu` VALUES (1, 154);
INSERT INTO `t_role_menu` VALUES (1, 155);
INSERT INTO `t_role_menu` VALUES (1, 168);
INSERT INTO `t_role_menu` VALUES (1, 169);
INSERT INTO `t_role_menu` VALUES (1, 170);
INSERT INTO `t_role_menu` VALUES (1, 171);
INSERT INTO `t_role_menu` VALUES (1, 172);
COMMIT;

-- ----------------------------
-- Table structure for t_tx_exception
-- ----------------------------
DROP TABLE IF EXISTS `t_tx_exception`;
CREATE TABLE `t_tx_exception` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `group_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `unit_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mod_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `transaction_state` tinyint DEFAULT NULL,
  `registrar` tinyint DEFAULT NULL,
  `ex_state` tinyint DEFAULT NULL COMMENT '0 待处理 1已处理',
  `remark` varchar(10240) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_tx_exception
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `dept_id` bigint DEFAULT NULL COMMENT '部门ID',
  `email` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系电话',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态 0锁定 1有效',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最近访问时间',
  `ssex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '性别 0男 1女 2保密',
  `is_tab` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否开启tab，0关闭 1开启',
  `theme` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '主题',
  `avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '头像',
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`user_id`) USING BTREE,
  KEY `t_user_username` (`username`),
  KEY `t_user_mobile` (`mobile`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
BEGIN;
INSERT INTO `t_user` VALUES (1, 'chachae', '$2a$10$eG9uK3ujGwqWZCIXRCZgPOv0Rmh9KiDxNCf/rzz.MvATAh9uhZZ6e', 2, 'chachae@qq.com', '13670459539', '1', '2019-06-14 20:39:22', '2020-04-15 16:00:32', '2020-06-01 00:36:12', '0', '1', 'white', 'c7c4ee7be3eb4e73a19887dc713505145.jpg', '我是作者。');
INSERT INTO `t_user` VALUES (2, 'scott', '$2a$10$/YDnX1OPBCRcXHQx.aR3tu8f3JfM2ABdWv1fE.PZ32ijAbvqnPz5a', 5, 'scott@hotmail.com', '17720888888', '1', '2019-07-20 19:00:32', '2020-04-15 16:00:42', '2020-05-29 17:48:47', '2', NULL, NULL, 'BiazfanxmamNRoxxVxka.png', NULL);
INSERT INTO `t_user` VALUES (3, 'Jane', '$2a$10$/YDnX1OPBCRcXHQx.aR3tu8f3JfM2ABdWv1fE.PZ32ijAbvqnPz5a', 4, 'Jane@hotmail.com', '13679554032', '1', '2019-09-01 10:31:21', '2020-05-28 19:03:13', '2020-05-29 17:48:25', '1', NULL, NULL, '2dd7a2d09fa94bf8b5c52e5318868b4d9.jpg', NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_user_connection
-- ----------------------------
DROP TABLE IF EXISTS `t_user_connection`;
CREATE TABLE `t_user_connection` (
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'cloudx系统用户名',
  `provider_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '第三方平台名称',
  `provider_user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '第三方平台账户ID',
  `provider_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '第三方平台用户名',
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '第三方平台昵称',
  `image_url` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '第三方平台头像',
  `location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '地址',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_name`,`provider_name`,`provider_user_id`) USING BTREE,
  UNIQUE KEY `UserConnectionRank` (`user_name`,`provider_name`,`provider_user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统用户社交账户关联表';

-- ----------------------------
-- Records of t_user_connection
-- ----------------------------
BEGIN;
INSERT INTO `t_user_connection` VALUES ('chachae', 'GITEE', '2172962', 'chachae', 'chachae', 'https://portrait.gitee.com/uploads/avatars/user/724/2172962_chachae_1578967843.png', NULL, '');
INSERT INTO `t_user_connection` VALUES ('chachae', 'GITHUB', '25251252', 'chachae', 'yue.xin', 'https://avatars3.githubusercontent.com/u/25251252?v=4', NULL, '一天撸码25小时。');
COMMIT;

-- ----------------------------
-- Table structure for t_user_data_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_user_data_permission`;
CREATE TABLE `t_user_data_permission` (
  `user_id` bigint NOT NULL,
  `dept_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`dept_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户数据权限关联表';

-- ----------------------------
-- Records of t_user_data_permission
-- ----------------------------
BEGIN;
INSERT INTO `t_user_data_permission` VALUES (1, 1);
INSERT INTO `t_user_data_permission` VALUES (1, 2);
INSERT INTO `t_user_data_permission` VALUES (1, 3);
INSERT INTO `t_user_data_permission` VALUES (1, 4);
INSERT INTO `t_user_data_permission` VALUES (1, 5);
INSERT INTO `t_user_data_permission` VALUES (1, 6);
INSERT INTO `t_user_data_permission` VALUES (3, 1);
INSERT INTO `t_user_data_permission` VALUES (15, 1);
INSERT INTO `t_user_data_permission` VALUES (15, 2);
INSERT INTO `t_user_data_permission` VALUES (16, 4);
INSERT INTO `t_user_data_permission` VALUES (16, 5);
COMMIT;

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户角色关联表';

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
BEGIN;
INSERT INTO `t_user_role` VALUES (1, 1);
INSERT INTO `t_user_role` VALUES (2, 2);
INSERT INTO `t_user_role` VALUES (3, 3);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
