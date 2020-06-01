/*
 Navicat Premium Data Transfer

 Source Server         : docker
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : docker.mysql
 Source Schema         : nacos

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 01/06/2020 16:15:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`
(
    `id`           bigint(20)                    NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id`     varchar(255) COLLATE utf8_bin          DEFAULT NULL,
    `content`      longtext COLLATE utf8_bin     NOT NULL COMMENT 'content',
    `md5`          varchar(32) COLLATE utf8_bin           DEFAULT NULL COMMENT 'md5',
    `gmt_create`   datetime                      NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
    `gmt_modified` datetime                      NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
    `src_user`     text COLLATE utf8_bin COMMENT 'source user',
    `src_ip`       varchar(20) COLLATE utf8_bin           DEFAULT NULL COMMENT 'source ip',
    `app_name`     varchar(128) COLLATE utf8_bin          DEFAULT NULL,
    `tenant_id`    varchar(128) COLLATE utf8_bin          DEFAULT '' COMMENT '租户字段',
    `c_desc`       varchar(256) COLLATE utf8_bin          DEFAULT NULL,
    `c_use`        varchar(64) COLLATE utf8_bin           DEFAULT NULL,
    `effect`       varchar(64) COLLATE utf8_bin           DEFAULT NULL,
    `type`         varchar(64) COLLATE utf8_bin           DEFAULT NULL,
    `c_schema`     text COLLATE utf8_bin,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 178
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC COMMENT ='config_info';

-- ----------------------------
-- Records of config_info
-- ----------------------------
BEGIN;
INSERT INTO `config_info`
VALUES (1, 'cloudx-admin.yaml', 'DEFAULT_GROUP',
        'server:\n  port: 8400\n\nspring:\n  security:\n    user:\n      name: cloudx\n      password: 123456\n      \n  boot:\n    admin:\n      ui:\n        title: ${spring.application.name}',
        '6e7a80a82c9bb556fa44ceb8219289bc', '2020-05-11 19:26:05', '2020-05-11 19:33:48', NULL,
        '106.15.202.13', '', '', 'null', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info`
VALUES (2, 'cloudx-auth.yaml', 'DEFAULT_GROUP',
        'server:\n  port: 9200\nspring:\n  thymeleaf:\n    cache: false\n  datasource:\n    hikari:\n      connection-timeout: 30000\n      max-lifetime: 1800000\n      max-pool-size: 15\n      min-idle: 5\n      connection-test-query: select 1\n      pool-name: CloudxHikariCP\n    username: root\n    password: 123456\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://${mysql.url}:3306/cloudx?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2b8\n\n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n    time-zone: GMT+8\n  boot:\n    admin:\n      client:\n        url: http://${cloudx-admin}:8400\n        username: cloudx\n        password: 123456\n        instance:\n          prefer-ip: true\n\n  redis:\n    database: 0\n    host: ${redis.url}\n    port: 6379\n    lettuce:\n      pool:\n        min-idle: 8\n        max-idle: 500\n        max-active: 2000\n        max-wait: 10000\n    timeout: 5000\n    \nmybatis-plus:\n  configuration:\n    jdbc-type-for-null: null\n    map-underscore-to-camel-case: true\n  global-config:\n    banner: false\n    db-config:\n      id-type: auto\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\n  endpoint:\n    health:\n      show-details: ALWAYS\n\njustauth:\n  enabled: true\n  type:\n    github:\n      client-id: caf4644ad6d3070dab52\n      client-secret: 4410ff1d4335e1183166c653a477ccca0b3e6583\n      redirect-uri: http://api.cloudx.cn:8301/auth/social/github/callback\n\n    gitee:\n      client-id: 1f7714460552ac1260bace3204a91b55596b3289fac8d072375a6f03f6995673\n      client-secret: a87a6d242e47a635f38636790e110c848cff2ef722ddc4b05391ff4be456913f\n      redirect-uri: http://api.cloudx.cn:8301/auth/social/gitee/callback\n    tencent_cloud:\n      client-id:\n      client-secret:\n      redirect-uri:\n    dingtalk:\n      client-id:\n      client-secret:\n      redirect-uri:\n    qq:\n      client-id:\n      client-secret:\n      redirect-uri:\n    microsoft:\n      client-id:\n      client-secret:\n      redirect-uri:\n  cache:\n    type: redis\n    prefix: \'CLOUDX::CLOUD::SOCIAL::STATE::\'\n    timeout: 1h\n\ncloudx:\n  frontUrl: \'http://localhost:9527\'',
        '20de07a530038cc5bc7829dc24e28446', '2020-05-11 19:51:55', '2020-05-31 18:34:24', NULL,
        '106.15.202.13', '', '', 'null', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info`
VALUES (3, 'cloudx-gateway.yaml', 'DEFAULT_GROUP',
        'server:\n  port: 8301\n  \nspring:\n  cloud:\n    gateway:\n      globalcors:\n        corsConfigurations:\n          \'[/**]\':\n            allowedOrigins: \"*\"\n            allowedMethods: \"*\"\n            allowedHeaders: \"*\"\n            allowCredentials: true\n      routes:\n        - id: cloudx-auth-social\n          uri: lb://cloudx-auth\n          predicates:\n            - Path=/auth/social/**\n          filters:\n            - name: Hystrix\n              args:\n                name: social-fallback\n                fallbackUri: forward:/fallback/cloudx-auth\n        - id: cloudx-auth\n          uri: lb://cloudx-auth\n          predicates:\n            - Path=/auth/**\n          filters:\n            - name: Hystrix\n              args:\n                name: auth-fallback\n                fallbackUri: forward:/fallback/cloudx-auth\n        - id: cloudx-server-system\n          uri: lb://cloudx-server-system\n          predicates:\n            - Path=/system/**\n          filters:\n            - name: Hystrix\n              args:\n                name: server-system-fallback\n                fallbackUri: forward:/fallback/cloudx-server-system\n        - id: cloudx-server-demo\n          uri: lb://cloudx-server-demo\n          predicates:\n            - Path=/demo/**\n          filters:\n            - name: Hystrix\n              args:\n                name: server-demo-fallback\n                fallbackUri: forward:/fallback/cloudx-server-demo\n      loadbalancer:\n        use404: true\n      # 转发前去掉一层访问路径\n      default-filters:\n        - StripPrefix=1\n    sentinel:\n      transport:\n        dashboard: ${sentinel.url}:8080 #sentinel控制台访问路径\n        port: 8080\n      eager: true #心跳启动\n      enabled: ${cloudx.gateway.enableFlow} # 开启sentinel\n\n  redis:\n    database: 3\n    host: ${redis.url}\n    port: 6379\n    lettuce:\n      pool:\n        min-idle: 8\n        max-idle: 500\n        max-active: 2000\n        max-wait: 10000\n    timeout: 5000\n\n  jackson:\n    date-format: yyyy-mm-dd HH:mm:ss\n\n  boot:\n    admin:\n      client:\n        url: http://${cloudx-admin}:8400\n        username: cloudx\n        password: 123456\n        instance:\n          prefer-ip: true\n\nhystrix:\n  command:\n    default:\n      execution:\n        isolation:\n          thread:\n            timeoutInMilliseconds: 5000\n    socialfallback:\n      execution:\n        isolation:\n          thread:\n            timeoutInMilliseconds: 30000\n\nribbon:\n  eager-load:\n    enabled: true\n\nmanagement:\n  endpoint:\n    health:\n      show-details: ALWAYS\n  endpoints:\n    web:\n      exposure:\n        include: health,info,gateway\n    sentinel:\n      enabled: false\n',
        'd88473eb6dc1e157369b920455e2dc1d', '2020-05-11 20:59:19', '2020-06-01 15:20:37', NULL,
        '106.15.202.13', '', '', 'null', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info`
VALUES (4, 'cloudx-server-demo.yaml', 'DEFAULT_GROUP',
        'server:\n  port: 9501\nspring:\n  datasource:\n    dynamic:\n      p6spy: true\n      hikari:\n        connection-timeout: 30000\n        max-lifetime: 1800000\n        max-pool-size: 15\n        min-idle: 5\n        connection-test-query: select 1\n        pool-name: CloudxHikariCP\n      primary: base\n      datasource:\n        base:\n          username: root\n          password: 123456\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://${mysql.url}:3306/cloudx?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2b8\n  boot:\n    admin:\n      client:\n        url: http://${cloudx-admin}:8401\n        username: cloudx\n        password: 123456\n        instance:\n          prefer-ip: true\n\nfeign:\n  hystrix:\n    enabled: true\n\nhystrix:\n  shareSecurityContext: true\n  command:\n    default:\n      execution:\n        isolation:\n          thread:\n            timeoutInMilliseconds: 3000\n\nmybatis-plus:\n  configuration:\n    jdbc-type-for-null: null\n    map-underscore-to-camel-case: true\n  global-config:\n    banner: false\n\nsecurity:\n  oauth2:\n    resource:\n      id: ${spring.application.name}\n      user-info-uri: http://${cloudx-gateway}:8301/auth/user\n\ntx-lcn:\n  client:\n    manager-address: ${cloudx-tx-manager}:8888\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\n  endpoint:\n    health:\n      show-details: ALWAYS\ncloudx:\n  cloud:\n    security:\n      enable: true\n      anon-uris: /actuator/**',
        'fbd8930111e8a268e5bf24b5bc67e3cd', '2020-05-11 21:06:26', '2020-06-01 15:12:40', NULL,
        '106.15.202.13', '', '', 'null', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info`
VALUES (5, 'cloudx-server-system.yaml', 'DEFAULT_GROUP',
        'server:\n  port: 9500\n\nspring:\n  aop:\n    proxy-target-class: true\n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n    time-zone: GMT+8\n  boot:\n    admin:\n      client:\n        url: http://${cloudx-admin}:8400\n        username: cloudx\n        password: 123456\n        instance:\n          prefer-ip: true\n  freemarker:\n    check-template-location: false\n\n  datasource:\n    dynamic:\n      p6spy: true\n      hikari:\n        connection-timeout: 30000\n        max-lifetime: 1800000\n        max-pool-size: 15\n        min-idle: 5\n        connection-test-query: select 1\n        pool-name: CloudxHikariCP\n      primary: base\n      datasource:\n        base:\n          username: root\n          password: 123456\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://${mysql.url}:3306/cloudx?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2b8\n\nmybatis-plus:\n  mapper-locations: classpath:mapper/*.xml\n  configuration:\n    jdbc-type-for-null: null\n    map-underscore-to-camel-case: true\n  global-config:\n    banner: false\nsecurity:\n  oauth2:\n    resource:\n      id: ${spring.application.name}\n      user-info-uri: http://${cloudx-gateway}:8301/auth/user\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\n  endpoint:\n    health:\n      show-details: ALWAYS\ncloudx:\n  cloud:\n    security:\n      enable: true\n      anon-uris: /actuator/**\n      \ntx-lcn:\n  client:\n    manager-address: ${cloudx-tx-manager}:8888',
        '970c619db917d6440c53c8e39e72083e', '2020-05-11 21:08:56', '2020-06-01 15:38:21', NULL,
        '106.15.202.13', '', '', 'null', 'null', 'null', 'yaml', 'null');
INSERT INTO `config_info`
VALUES (6, 'cloudx-tx-manager.yaml', 'DEFAULT_GROUP',
        'server:\n  port: 8501\nspring:\n  boot:\n    admin:\n      client:\n        url: http://${cloudx-admin}:8400\n        username: cloudx\n        password: 123456\n        instance:\n          prefer-ip: true\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://${mysql.url}:3306/cloudx?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2b8\n    username: root\n    password: 123456\n  redis:\n    database: 0\n    host: ${redis.url}\n    port: 6379\n    lettuce:\n      pool:\n        min-idle: 8\n        max-idle: 500\n        max-active: 2000\n        max-wait: 10000\n    timeout: 5000\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\n  endpoint:\n    health:\n      show-details: ALWAYS\n\ntx-lcn:\n  manager:\n    host: 0.0.0.0\n    # TM监听Socket端口.\n    port: 8888\n    # TM控制台密码\n    admin-key: 123456\n  logger:\n    # 开启日志记录\n    enabled: true\n    driver-class-name: ${spring.datasource.driver-class-name}\n    jdbc-url: ${spring.datasource.url}\n    username: ${spring.datasource.username}\n    password: ${spring.datasource.password}',
        'f4e147c12eb955d54a99dd19998635bc', '2020-06-01 00:29:23', '2020-06-01 00:29:23', NULL,
        '120.239.18.255', '', '', NULL, NULL, NULL, 'yaml', NULL);
COMMIT;

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`
(
    `id`           bigint(20)                    NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id`     varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
    `datum_id`     varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
    `content`      longtext COLLATE utf8_bin     NOT NULL COMMENT '内容',
    `gmt_modified` datetime                      NOT NULL COMMENT '修改时间',
    `app_name`     varchar(128) COLLATE utf8_bin DEFAULT NULL,
    `tenant_id`    varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`, `group_id`, `tenant_id`, `datum_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC COMMENT ='增加租户字段';

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`
(
    `id`           bigint(20)                    NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id`     varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
    `app_name`     varchar(128) COLLATE utf8_bin          DEFAULT NULL COMMENT 'app_name',
    `content`      longtext COLLATE utf8_bin     NOT NULL COMMENT 'content',
    `beta_ips`     varchar(1024) COLLATE utf8_bin         DEFAULT NULL COMMENT 'betaIps',
    `md5`          varchar(32) COLLATE utf8_bin           DEFAULT NULL COMMENT 'md5',
    `gmt_create`   datetime                      NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
    `gmt_modified` datetime                      NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
    `src_user`     text COLLATE utf8_bin COMMENT 'source user',
    `src_ip`       varchar(20) COLLATE utf8_bin           DEFAULT NULL COMMENT 'source ip',
    `tenant_id`    varchar(128) COLLATE utf8_bin          DEFAULT '' COMMENT '租户字段',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC COMMENT ='config_info_beta';

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`
(
    `id`           bigint(20)                    NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id`     varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
    `tenant_id`    varchar(128) COLLATE utf8_bin          DEFAULT '' COMMENT 'tenant_id',
    `tag_id`       varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
    `app_name`     varchar(128) COLLATE utf8_bin          DEFAULT NULL COMMENT 'app_name',
    `content`      longtext COLLATE utf8_bin     NOT NULL COMMENT 'content',
    `md5`          varchar(32) COLLATE utf8_bin           DEFAULT NULL COMMENT 'md5',
    `gmt_create`   datetime                      NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
    `gmt_modified` datetime                      NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
    `src_user`     text COLLATE utf8_bin COMMENT 'source user',
    `src_ip`       varchar(20) COLLATE utf8_bin           DEFAULT NULL COMMENT 'source ip',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`, `group_id`, `tenant_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC COMMENT ='config_info_tag';

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`
(
    `id`        bigint(20)                    NOT NULL COMMENT 'id',
    `tag_name`  varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
    `tag_type`  varchar(64) COLLATE utf8_bin  DEFAULT NULL COMMENT 'tag_type',
    `data_id`   varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id`  varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
    `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
    `nid`       bigint(20)                    NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`nid`) USING BTREE,
    UNIQUE KEY `uk_configtagrelation_configidtag` (`id`, `tag_name`, `tag_type`) USING BTREE,
    KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC COMMENT ='config_tag_relation';

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`
(
    `id`                bigint(20) unsigned           NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `group_id`          varchar(128) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
    `quota`             int(10) unsigned              NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
    `usage`             int(10) unsigned              NOT NULL DEFAULT '0' COMMENT '使用量',
    `max_size`          int(10) unsigned              NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
    `max_aggr_count`    int(10) unsigned              NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
    `max_aggr_size`     int(10) unsigned              NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
    `max_history_count` int(10) unsigned              NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
    `gmt_create`        datetime                      NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
    `gmt_modified`      datetime                      NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_group_id` (`group_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC COMMENT ='集群、各Group容量信息表';

-- ----------------------------
-- Records of group_capacity
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`
(
    `id`           bigint(64) unsigned           NOT NULL,
    `nid`          bigint(20) unsigned           NOT NULL AUTO_INCREMENT,
    `data_id`      varchar(255) COLLATE utf8_bin NOT NULL,
    `group_id`     varchar(128) COLLATE utf8_bin NOT NULL,
    `app_name`     varchar(128) COLLATE utf8_bin          DEFAULT NULL COMMENT 'app_name',
    `content`      longtext COLLATE utf8_bin     NOT NULL,
    `md5`          varchar(32) COLLATE utf8_bin           DEFAULT NULL,
    `gmt_create`   datetime                      NOT NULL DEFAULT '2010-05-05 00:00:00',
    `gmt_modified` datetime                      NOT NULL DEFAULT '2010-05-05 00:00:00',
    `src_user`     text COLLATE utf8_bin,
    `src_ip`       varchar(20) COLLATE utf8_bin           DEFAULT NULL,
    `op_type`      char(10) COLLATE utf8_bin              DEFAULT NULL,
    `tenant_id`    varchar(128) COLLATE utf8_bin          DEFAULT '' COMMENT '租户字段',
    PRIMARY KEY (`nid`) USING BTREE,
    KEY `idx_gmt_create` (`gmt_create`) USING BTREE,
    KEY `idx_gmt_modified` (`gmt_modified`) USING BTREE,
    KEY `idx_did` (`data_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 189
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC COMMENT ='多租户改造';

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`
(
    `role`     varchar(50)  NOT NULL,
    `resource` varchar(512) NOT NULL,
    `action`   varchar(8)   NOT NULL,
    UNIQUE KEY `uk_role_permission` (`role`, `resource`, `action`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- ----------------------------
-- Records of permissions
-- ----------------------------
BEGIN;
INSERT INTO `permissions`
VALUES ('ROLE_GUEST', ':*:*', 'r');
COMMIT;

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`
(
    `username` varchar(50) NOT NULL,
    `role`     varchar(50) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of roles
-- ----------------------------
BEGIN;
INSERT INTO `roles`
VALUES ('nacos', 'ROLE_ADMIN');
COMMIT;

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`
(
    `id`                bigint(20) unsigned           NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id`         varchar(128) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
    `quota`             int(10) unsigned              NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
    `usage`             int(10) unsigned              NOT NULL DEFAULT '0' COMMENT '使用量',
    `max_size`          int(10) unsigned              NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
    `max_aggr_count`    int(10) unsigned              NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
    `max_aggr_size`     int(10) unsigned              NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
    `max_history_count` int(10) unsigned              NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
    `gmt_create`        datetime                      NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
    `gmt_modified`      datetime                      NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_tenant_id` (`tenant_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC COMMENT ='租户容量信息表';

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`
(
    `id`            bigint(20)                    NOT NULL AUTO_INCREMENT COMMENT 'id',
    `kp`            varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'kp',
    `tenant_id`     varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
    `tenant_name`   varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_name',
    `tenant_desc`   varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT 'tenant_desc',
    `create_source` varchar(32) COLLATE utf8_bin  DEFAULT NULL COMMENT 'create_source',
    `gmt_create`    bigint(20)                    NOT NULL COMMENT '创建时间',
    `gmt_modified`  bigint(20)                    NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`, `tenant_id`) USING BTREE,
    KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin
  ROW_FORMAT = DYNAMIC COMMENT ='tenant_info';

-- ----------------------------
-- Records of tenant_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`
(
    `username` varchar(50)  NOT NULL,
    `password` varchar(500) NOT NULL,
    `enabled`  tinyint(1)   NOT NULL,
    PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
INSERT INTO `users`
VALUES ('cloudx', '$2a$10$1GRYceIFqRdkjo0Ltxv0iegWI/DD7PF.j/9Ruc5ixlznDn3bSiXLC', 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
