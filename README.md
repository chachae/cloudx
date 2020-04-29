# cloudx

💦 Spring Cloud Hoxton &amp; alibaba 快速构建分布式微服务工程的种子项目

[![https://img.shields.io/badge/SpringCloud-Hoxton.SR3-yellow.svg?longCache=true&style=flat-square](https://img.shields.io/badge/SpringCloud-Hoxton.SR3-yellow.svg?longCache=true&style=flat-square)](https://img.shields.io/badge/SpringCloud-Hoxton.SR3-yellow.svg?longCache=true&style=flat-square) [![https://img.shields.io/badge/SpringCloudAlibaba-2.2.0.RELEASE-blueviolet.svg?style=flat-square](https://img.shields.io/badge/SpringCloudAlibaba-2.2.0.RELEASE-blueviolet.svg?style=flat-square)](https://img.shields.io/badge/SpringCloudAlibaba-2.2.0.RELEASE-blueviolet.svg?style=flat-square) [![https://img.shields.io/badge/SpringBoot-2.2.6.RELEASE-brightgreen.svg?style=flat-square](https://img.shields.io/badge/SpringBoot-2.2.6.RELEASE-brightgreen.svg?style=flat-square)](https://img.shields.io/badge/SpringBoot-2.2.6.RELEASE-brightgreen.svg?style=flat-square) 

cloudx 是一个构建于 Spring Cloud Hoxton.SR3 & Spring Cloud OAuth 2.0 & Spring Cloud Alibaba 之上的认证服务授权服务分离、网关统一鉴权、微服务统一防护、可以作为微服务开发的种子项目，架构清晰，开箱即用。核心技术使用了 Nacos、Open-Fegin、Ribbon、Gateway、OAUTH 2、MyBatis、SpringBoot、Sentinel 等主流框架和中间件。

本项目只从系统架构出发，努力实现一套基础能力健壮的分布式微服务工程，不涉及任何具体的业务。

| index |                     feature                      |
| :---: | :----------------------------------------------: |
|   1   |   前后端分离架构，客户端和服务端纯 Token 交互    |
|   2   | 认证服务与资源服务分离，便于直接接入自己的微服务 |
|   3   |    阿里系三方模块，Nacos、Sentinel 等组件加持    |
|   4   |          Gateway 网关统一微服务行为控制          |
|   5   |                  服务外部化配置                  |

### 系统架构

| **cloudx**                                                   |
| ------------------------------------------------------------ |
| [![img](https://gitee.com/chachae/imgs/raw/master/cloudx/cloudx.png)](https://gitee.com/chachae/imgs/raw/master/cloudx/cloudx.png) |

### 服务模块

cloudx 模块：

| 服务名称       | 端口 | 描述             |
| -------------- | ---- | ---------------- |
| cloudx-auth    | 9200 | 微服务认证服务器 |
| cloudx-gateway | 80   | 微服务网关       |
| cloudx-admin | 8400   | 微服务监控子系统       |

三方模块：

| 服务名称 | 端口 | 描述                |
| -------- | ---- | ------------------- |
| Nacos    | 8848 | 注册中心 / 配置中心 |
| MySQL    | 3306 | MySQL 数据库        |
| Redis    | 6379 | K-V 缓存数据库      |
| Sentinel | 8401 | 微服务流量防卫兵    |
