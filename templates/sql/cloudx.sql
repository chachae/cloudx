/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : cloudx

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 29/04/2020 23:02:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`
(
    `client_id`               varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
    `resource_ids`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL,
    `client_secret`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
    `scope`                   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
    `authorized_grant_types`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
    `web_server_redirect_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL,
    `authorities`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL,
    `access_token_validity`   int(0)                                                         NOT NULL,
    `refresh_token_validity`  int(0)                                                         NULL DEFAULT NULL,
    `additional_information`  varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `autoapprove`             tinyint(0)                                                     NULL DEFAULT NULL,
    `origin_secret`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL,
    PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details`
VALUES ('app', '', '$2a$10$88IWBHS3PUn9.NSeA1cMbeotMCbc2tOposnW7efm4ed1T4ZCay2ei', 'all',
        'password,refresh_token', '', NULL, 86400, 8640000, NULL, 1, '123456');
INSERT INTO `oauth_client_details`
VALUES ('cloudx', NULL, '$2a$10$aSZTvMOtUAYUQ.75z2n3ceJd6dCIk9Vy3J/SKZUE4hBLd6sz7.6ge', 'all',
        'password,refresh_token', NULL, NULL, 86400, 8640000, NULL, 1, '123456');
INSERT INTO `oauth_client_details`
VALUES ('swagger', '', '$2a$10$HSXh7gb19pngr8gcvzkODOvhQ048m6mrlXtaHJmBUE2jca7RvjEBa', 'test',
        'password,refresh_token', '', NULL, 86400, 86400, '', 0, '123456');

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code`
(
    `create_time`    timestamp(0)                                            NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
    `code`           varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL,
    `authentication` blob                                                    NULL,
    INDEX `code_index` (`code`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_dept`;
CREATE TABLE `t_dept`
(
    `dept_id`     bigint(0)                                               NOT NULL AUTO_INCREMENT COMMENT '部门ID',
    `parent_id`   bigint(0)                                               NOT NULL COMMENT '上级部门ID',
    `dept_name`   varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门名称',
    `order_num`   int(0)                                                  NULL DEFAULT NULL COMMENT '排序',
    `create_time` datetime(0)                                             NULL DEFAULT NULL COMMENT '创建时间',
    `updae_time`  datetime(0)                                             NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '部门表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_dept
-- ----------------------------
INSERT INTO `t_dept`
VALUES (1, 0, '开发部', 1, '2018-01-04 15:42:26', '2019-01-05 21:08:27');
INSERT INTO `t_dept`
VALUES (2, 1, '开发一部', 1, '2018-01-04 15:42:34', '2019-01-18 00:59:37');
INSERT INTO `t_dept`
VALUES (3, 1, '开发二部', 2, '2018-01-04 15:42:29', '2019-01-05 14:09:39');
INSERT INTO `t_dept`
VALUES (4, 0, '市场部', 2, '2018-01-04 15:42:36', '2019-01-23 06:27:56');
INSERT INTO `t_dept`
VALUES (5, 0, '人事部', 3, '2018-01-04 15:42:32', '2019-01-23 06:27:59');
INSERT INTO `t_dept`
VALUES (6, 0, '测试部', 4, '2018-01-04 15:42:38', '2019-01-17 08:15:47');

-- ----------------------------
-- Table structure for t_login_log
-- ----------------------------
DROP TABLE IF EXISTS `t_login_log`;
CREATE TABLE `t_login_log`
(
    `id`         bigint(0)                                              NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id`    bigint(0)                                              NOT NULL COMMENT '用户名ID',
    `login_time` datetime(0)                                            NOT NULL COMMENT '登录时间',
    `location`   varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录地点',
    `ip`         varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
    `system`     varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作系统',
    `browser`    varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览器',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '登录日志表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu`
(
    `menu_id`     bigint(0)                                                 NOT NULL AUTO_INCREMENT COMMENT '菜单/按钮ID',
    `parent_id`   bigint(0)                                                 NOT NULL COMMENT '上级菜单ID',
    `menu_name`   varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci    NOT NULL COMMENT '菜单/按钮名称',
    `path`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL COMMENT '对应路由path',
    `expression`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci    NULL DEFAULT NULL COMMENT '权限标识',
    `icon`        varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci    NULL DEFAULT NULL COMMENT '图标',
    `type`        enum ('0','1') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型 0菜单 1按钮',
    `order_num`   int(0)                                                    NULL DEFAULT NULL COMMENT '排序',
    `create_time` datetime(0)                                               NOT NULL COMMENT '创建时间',
    `update_time` datetime(0)                                               NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 180
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '菜单表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu`
VALUES (1, 0, '系统管理', '/system', NULL, 'el-icon-set-up', '0', 1, '2017-12-27 16:39:07',
        '2019-07-20 16:19:04');
INSERT INTO `t_menu`
VALUES (2, 0, '系统监控', '/monitor', NULL, 'el-icon-data-line', '0', 2, '2017-12-27 16:45:51',
        '2019-01-23 06:27:12');
INSERT INTO `t_menu`
VALUES (3, 1, '用户管理', '/system/user', 'user:view', '', '0', 1, '2017-12-27 16:47:13',
        '2019-01-22 06:45:55');
INSERT INTO `t_menu`
VALUES (4, 1, '角色管理', '/system/role', 'role:view', '', '0', 2, '2017-12-27 16:48:09',
        '2018-04-25 09:01:12');
INSERT INTO `t_menu`
VALUES (5, 1, '菜单管理', '/system/menu', 'menu:view', '', '0', 3, '2017-12-27 16:48:57',
        '2018-04-25 09:01:30');
INSERT INTO `t_menu`
VALUES (6, 1, '部门管理', '/system/dept', 'dept:view', '', '0', 4, '2017-12-27 16:57:33',
        '2018-04-25 09:01:40');
INSERT INTO `t_menu`
VALUES (10, 2, '系统日志', '/monitor/systemlog', 'log:view', '', '0', 1, '2017-12-27 17:00:50',
        '2019-07-22 20:22:31');
INSERT INTO `t_menu`
VALUES (11, 3, '新增用户', '', 'user:add', NULL, '1', NULL, '2017-12-27 17:02:58', NULL);
INSERT INTO `t_menu`
VALUES (12, 3, '修改用户', '', 'user:update', NULL, '1', NULL, '2017-12-27 17:04:07', NULL);
INSERT INTO `t_menu`
VALUES (13, 3, '删除用户', '', 'user:delete', NULL, '1', NULL, '2017-12-27 17:04:58', NULL);
INSERT INTO `t_menu`
VALUES (14, 4, '新增角色', '', 'role:add', NULL, '1', NULL, '2017-12-27 17:06:38', NULL);
INSERT INTO `t_menu`
VALUES (15, 4, '修改角色', '', 'role:update', NULL, '1', NULL, '2017-12-27 17:06:38', NULL);
INSERT INTO `t_menu`
VALUES (16, 4, '删除角色', '', 'role:delete', NULL, '1', NULL, '2017-12-27 17:06:38', NULL);
INSERT INTO `t_menu`
VALUES (17, 5, '新增菜单', '', 'menu:add', NULL, '1', NULL, '2017-12-27 17:08:02', NULL);
INSERT INTO `t_menu`
VALUES (18, 5, '修改菜单', '', 'menu:update', NULL, '1', NULL, '2017-12-27 17:08:02', NULL);
INSERT INTO `t_menu`
VALUES (19, 5, '删除菜单', '', 'menu:delete', NULL, '1', NULL, '2017-12-27 17:08:02', NULL);
INSERT INTO `t_menu`
VALUES (20, 6, '新增部门', '', 'dept:add', NULL, '1', NULL, '2017-12-27 17:09:24', NULL);
INSERT INTO `t_menu`
VALUES (21, 6, '修改部门', '', 'dept:update', NULL, '1', NULL, '2017-12-27 17:09:24', NULL);
INSERT INTO `t_menu`
VALUES (22, 6, '删除部门', '', 'dept:delete', NULL, '1', NULL, '2017-12-27 17:09:24', NULL);
INSERT INTO `t_menu`
VALUES (24, 10, '删除日志', '', 'log:delete', NULL, '1', NULL, '2017-12-27 17:11:45', NULL);
INSERT INTO `t_menu`
VALUES (135, 3, '密码重置', NULL, 'user:reset', NULL, '1', NULL, '2019-01-23 06:37:00', NULL);
INSERT INTO `t_menu`
VALUES (150, 2, '登录日志', '/monitor/loginlog', 'monitor:loginlog', '', '0', 2, '2019-07-22 13:41:17',
        '2019-07-22 20:22:35');
INSERT INTO `t_menu`
VALUES (151, 150, '删除日志', NULL, 'loginlog:delete', NULL, '1', NULL, '2019-07-22 13:43:04', NULL);
INSERT INTO `t_menu`
VALUES (163, 1, '客户端管理', '/client', 'client:view', '', '0', 5, '2019-09-26 22:58:09', NULL);

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`
(
    `role_id`     bigint(0)                                               NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `rile_name`   varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '角色名称',
    `remark`      varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
    `create_time` datetime(0)                                             NOT NULL COMMENT '创建时间',
    `update_time` datetime(0)                                             NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '角色表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role`
VALUES (1, '管理员', '管理员', '2017-12-27 16:23:11', '2020-01-16 14:02:06');
INSERT INTO `t_role`
VALUES (2, '注册用户', '可查看，新增，导出', '2019-01-04 14:11:28', '2020-01-16 14:02:21');
INSERT INTO `t_role`
VALUES (3, '系统监控员', '负责系统监控模块', '2019-09-01 10:30:25', '2019-09-01 10:30:37');

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu`
(
    `role_id` bigint(0) NOT NULL,
    `menu_id` bigint(0) NOT NULL
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '角色菜单关联表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
INSERT INTO `t_role_menu`
VALUES (3, 2);
INSERT INTO `t_role_menu`
VALUES (3, 10);
INSERT INTO `t_role_menu`
VALUES (3, 24);
INSERT INTO `t_role_menu`
VALUES (3, 136);
INSERT INTO `t_role_menu`
VALUES (3, 150);
INSERT INTO `t_role_menu`
VALUES (3, 151);
INSERT INTO `t_role_menu`
VALUES (3, 152);
INSERT INTO `t_role_menu`
VALUES (3, 149);
INSERT INTO `t_role_menu`
VALUES (3, 148);
INSERT INTO `t_role_menu`
VALUES (3, 153);
INSERT INTO `t_role_menu`
VALUES (1, 1);
INSERT INTO `t_role_menu`
VALUES (1, 3);
INSERT INTO `t_role_menu`
VALUES (1, 11);
INSERT INTO `t_role_menu`
VALUES (1, 12);
INSERT INTO `t_role_menu`
VALUES (1, 13);
INSERT INTO `t_role_menu`
VALUES (1, 130);
INSERT INTO `t_role_menu`
VALUES (1, 135);
INSERT INTO `t_role_menu`
VALUES (1, 4);
INSERT INTO `t_role_menu`
VALUES (1, 14);
INSERT INTO `t_role_menu`
VALUES (1, 15);
INSERT INTO `t_role_menu`
VALUES (1, 16);
INSERT INTO `t_role_menu`
VALUES (1, 131);
INSERT INTO `t_role_menu`
VALUES (1, 5);
INSERT INTO `t_role_menu`
VALUES (1, 17);
INSERT INTO `t_role_menu`
VALUES (1, 18);
INSERT INTO `t_role_menu`
VALUES (1, 19);
INSERT INTO `t_role_menu`
VALUES (1, 132);
INSERT INTO `t_role_menu`
VALUES (1, 6);
INSERT INTO `t_role_menu`
VALUES (1, 20);
INSERT INTO `t_role_menu`
VALUES (1, 21);
INSERT INTO `t_role_menu`
VALUES (1, 22);
INSERT INTO `t_role_menu`
VALUES (1, 133);
INSERT INTO `t_role_menu`
VALUES (1, 163);
INSERT INTO `t_role_menu`
VALUES (1, 164);
INSERT INTO `t_role_menu`
VALUES (1, 165);
INSERT INTO `t_role_menu`
VALUES (1, 166);
INSERT INTO `t_role_menu`
VALUES (1, 167);
INSERT INTO `t_role_menu`
VALUES (1, 2);
INSERT INTO `t_role_menu`
VALUES (1, 10);
INSERT INTO `t_role_menu`
VALUES (1, 24);
INSERT INTO `t_role_menu`
VALUES (1, 136);
INSERT INTO `t_role_menu`
VALUES (1, 150);
INSERT INTO `t_role_menu`
VALUES (1, 151);
INSERT INTO `t_role_menu`
VALUES (1, 152);
INSERT INTO `t_role_menu`
VALUES (1, 149);
INSERT INTO `t_role_menu`
VALUES (1, 161);
INSERT INTO `t_role_menu`
VALUES (1, 162);
INSERT INTO `t_role_menu`
VALUES (1, 148);
INSERT INTO `t_role_menu`
VALUES (1, 153);
INSERT INTO `t_role_menu`
VALUES (1, 173);
INSERT INTO `t_role_menu`
VALUES (1, 174);
INSERT INTO `t_role_menu`
VALUES (1, 175);
INSERT INTO `t_role_menu`
VALUES (1, 176);
INSERT INTO `t_role_menu`
VALUES (1, 177);
INSERT INTO `t_role_menu`
VALUES (1, 178);
INSERT INTO `t_role_menu`
VALUES (1, 179);
INSERT INTO `t_role_menu`
VALUES (1, 156);
INSERT INTO `t_role_menu`
VALUES (1, 157);
INSERT INTO `t_role_menu`
VALUES (1, 159);
INSERT INTO `t_role_menu`
VALUES (1, 158);
INSERT INTO `t_role_menu`
VALUES (1, 160);
INSERT INTO `t_role_menu`
VALUES (1, 154);
INSERT INTO `t_role_menu`
VALUES (1, 155);
INSERT INTO `t_role_menu`
VALUES (1, 168);
INSERT INTO `t_role_menu`
VALUES (1, 169);
INSERT INTO `t_role_menu`
VALUES (1, 170);
INSERT INTO `t_role_menu`
VALUES (1, 171);
INSERT INTO `t_role_menu`
VALUES (1, 172);
INSERT INTO `t_role_menu`
VALUES (2, 1);
INSERT INTO `t_role_menu`
VALUES (2, 3);
INSERT INTO `t_role_menu`
VALUES (2, 4);
INSERT INTO `t_role_menu`
VALUES (2, 5);
INSERT INTO `t_role_menu`
VALUES (2, 6);
INSERT INTO `t_role_menu`
VALUES (2, 2);
INSERT INTO `t_role_menu`
VALUES (2, 10);
INSERT INTO `t_role_menu`
VALUES (2, 150);
INSERT INTO `t_role_menu`
VALUES (2, 149);
INSERT INTO `t_role_menu`
VALUES (2, 148);
INSERT INTO `t_role_menu`
VALUES (2, 153);
INSERT INTO `t_role_menu`
VALUES (2, 173);
INSERT INTO `t_role_menu`
VALUES (2, 174);
INSERT INTO `t_role_menu`
VALUES (2, 175);
INSERT INTO `t_role_menu`
VALUES (2, 176);
INSERT INTO `t_role_menu`
VALUES (2, 177);
INSERT INTO `t_role_menu`
VALUES (2, 178);
INSERT INTO `t_role_menu`
VALUES (2, 179);
INSERT INTO `t_role_menu`
VALUES (2, 156);
INSERT INTO `t_role_menu`
VALUES (2, 157);
INSERT INTO `t_role_menu`
VALUES (2, 158);
INSERT INTO `t_role_menu`
VALUES (2, 154);
INSERT INTO `t_role_menu`
VALUES (2, 155);
INSERT INTO `t_role_menu`
VALUES (2, 168);
INSERT INTO `t_role_menu`
VALUES (2, 169);
INSERT INTO `t_role_menu`
VALUES (2, 170);
INSERT INTO `t_role_menu`
VALUES (2, 171);
INSERT INTO `t_role_menu`
VALUES (2, 172);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`
(
    `user_id`         bigint(0)                                                       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `user_name`       varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci          NOT NULL COMMENT '用户名',
    `password`        varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci         NOT NULL COMMENT '密码',
    `dept_id`         bigint(0)                                                       NULL DEFAULT NULL COMMENT '部门ID',
    `email`           varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci         NULL DEFAULT NULL COMMENT '邮箱',
    `mobile`          varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci          NULL DEFAULT NULL COMMENT '联系电话',
    `status`          enum ('1','0') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态 0锁定 1有效',
    `create_time`     datetime(0)                                                     NOT NULL COMMENT '创建时间',
    `update_time`     datetime(0)                                                     NULL DEFAULT NULL COMMENT '修改时间',
    `last_login_time` datetime(0)                                                     NULL DEFAULT NULL COMMENT '最近访问时间',
    `gender`          enum ('0','1','2') CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL COMMENT '性别 0男 1女 2保密',
    `avatar`          varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci         NULL DEFAULT NULL COMMENT '头像',
    `description`     varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci         NULL DEFAULT NULL COMMENT '描述',
    PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 17
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '用户表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user`
VALUES (1, 'chachae', '$2a$10$/YDnX1OPBCRcXHQx.aR3tu8f3JfM2ABdWv1fE.PZ32ijAbvqnPz5a', 2,
        'chachae@foxmail.com', '18719202920', '1', '2019-06-14 20:39:22', '2019-07-19 10:18:36',
        '2020-01-16 13:58:28', '0', 'gaOngJwsRYRaVAuXXcmB.png', '我是作者。');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`
(
    `user_id` bigint(0) NOT NULL COMMENT '用户ID',
    `role_id` bigint(0) NOT NULL COMMENT '角色ID'
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '用户角色关联表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role`
VALUES (1, 1);

SET FOREIGN_KEY_CHECKS = 1;
