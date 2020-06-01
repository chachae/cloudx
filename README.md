# cloudx

Spring Cloud Hoxton &amp; alibaba 快速构建分布式微服务工程的种子项目

[![https://img.shields.io/badge/SpringCloud-Hoxton.SR3-yellow.svg?longCache=true&style=flat-square](https://img.shields.io/badge/SpringCloud-Hoxton.SR3-yellow.svg?longCache=true&style=flat-square)](https://img.shields.io/badge/SpringCloud-Hoxton.SR3-yellow.svg?longCache=true&style=flat-square) [![https://img.shields.io/badge/SpringCloudAlibaba-2.2.1.RELEASE-blueviolet.svg?style=flat-square](https://img.shields.io/badge/SpringCloudAlibaba-2.2.1.RELEASE-blueviolet.svg?style=flat-square)](https://img.shields.io/badge/SpringCloudAlibaba-2.2.1.RELEASE-blueviolet.svg?style=flat-square) [![https://img.shields.io/badge/SpringBoot-2.2.6.RELEASE-brightgreen.svg?style=flat-square](https://img.shields.io/badge/SpringBoot-2.2.6.RELEASE-brightgreen.svg?style=flat-square)](https://img.shields.io/badge/SpringBoot-2.2.6.RELEASE-brightgreen.svg?style=flat-square) 

cloudx 是一个构建于 Spring Cloud Hoxton.SR3 & Spring Cloud OAuth 2 & Spring Cloud Alibaba 之上的认证服务授权服务分离、可插拔的网关请求控制、微服务统一防护、可以作为微服务开发的种子项目，架构清晰，开箱即用。核心技术使用了 Gateway、OAuth 2、Spring Boot、Spring Security、MyBatis、Sentinel、Nacos、Open Fegin 等主流框架和中间件。

本项目只从系统架构自身出发，围绕现阶段主流的分布式微服务，努力实现一套基础能力健壮的分布式微服务工程，不涉及任何具体的业务。

## 系统特点

| index |                      feature                       |
| :---: | :------------------------------------------------: |
|   1   |    前后端分离架构，客户端和服务端纯 Token 交互     |
|   2   | 认证服务与资源服务分离，便于快速地接入自己的微服务 |
|   3   |     阿里系三方模块，Nacos、Sentinel 等组件加持     |
|   4   |             可插拔的网关微服务行为控制             |
|   5   |                   服务外部化配置                   |
|   6   |              分布式架构，系统耦合度低              |

## 架构

| **cloudx**                                                   |
| ------------------------------------------------------------ |
| [![img](https://gitee.com/chachae/imgs/raw/master/cloudx/cloudx.png)](https://gitee.com/chachae/imgs/raw/master/cloudx/cloudx.png) |

## 现有服务模块

### 系统模块：

| server name | port | description  |
| -------------- | ---- | ---------------- |
| cloudx-auth    | 9200 | 微服务认证授权系统 |
| cloudx-gateway | 8301   | 微服务网关 |
| cloudx-apm-admin | 8400   | 微服务 Spring-Admin 监控子系统 |
| cloudx-server-system | 9500 | 微服务基础信息服务子系统 |
| cloudx-server-demo | 9501 | 微服务远程调用示例子系统 |
| cloudx-common-core | 无 | 微服务通用 starter |
| cloudx-common-datasource | 无 | 微服务数据源 starter |
| cloudx-common-redis | 无 | 微服务 Redis starter |
| cloudx-common-security | 无 | 微服务资源保护 starter |
| cloudx-tx-manager | 8501 | 微服务分布式事务控制系统 |
### 三方模块：

| 服务名称 | 端口 | 描述                            |
| -------- | ---- | ------------------------------- |
| Nacos    | 80   | 集群化部署的注册中心 / 配置中心 |
| MySQL    | 3306 | MySQL 数据库                    |
| Redis    | 6379 | K-V 缓存数据库                  |
| Sentinel | 8401 | 微服务流量防卫兵                |
| ELK | 4560 | 日志收集与分析系统             |
