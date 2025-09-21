# Machine 微服务平台

## 项目简介
Machine 是一个基于 Spring Cloud Alibaba 的微服务平台，采用现代化的技术栈和规范化的工程结构，提供完整的微服务解决方案。平台包含认证授权、网关、任务调度、工作流、消息队列等核心功能模块。

## 文档目录

### 开发规范
- [架构规范文档](document/code/ARCHITECTURE.md) - 工程分层规范和设计原则
- [异常规范文档](document/code/EXCEPTION.md) - HTTP状态码使用规范
- [Git规范文档](document/code/GIT_STANDARD.md) - 分支管理和提交规范
- [Webhook对接](document/code/WEBHOOK_EVENT.md) - 事件推送接口规范
- [OAuth2认证](document/code/OPENAPI_AUTH.md) - 开放平台认证流程

### 数据库设计
- [Schema设计](document/db/table/schema.sql)
- 各模块表结构：
  - [Nacos相关表](document/db/table/machine-nacos-table.sql)
  - [基础设施表](document/db/table/machine-infra-table.sql)
  - [Camunda工作流表](document/db/table/machine-camunda-table.sql)
  - [IAM权限表](document/db/table/machine-iam-table.sql)
  - [数据服务表](document/db/table/machine-data-table.sql)
  - [供应链表](document/db/table/machine-scm-table.sql)

### 基础设施配置
- [基础设施部署指南](document/infra/infra.md)
- [开发环境Docker部署](document/infra/docker/DOCKER_DEV.md)
- [生产环境Docker部署](document/infra/docker/DOCKER_PROD.md)
- [服务运行配置](document/infra/docker/DOCKER_RUN_DEV.md)
- [Nacos配置](document/infra/nacos/yml/machine.yaml)

## 技术选型

### 核心技术栈
| 功能     | 技术组件                 | 版本         |
|--------|----------------------|------------|
| 微服务框架  | Spring Cloud Alibaba | 2025.0.0.0 |
| 微服务框架  | Spring Cloud         | 2025.0.0   |
| Web框架  | Spring Boot          | 3.5.5      |
| 服务注册发现 | Nacos                | 3.0.3      |
| 配置中心   | Nacos Config         | 3.0.3      |
| API网关  | Spring Cloud Gateway | 4.3.0      |
| 服务调用   | OpenFeign            | 4.3.0      |
| 认证授权   | Spring Security      | 6.5.3      |
| 调度服务   | XXL-JOB              | 3.2.0      |
| ORM框架  | MyBatis-Plus         | 3.5.14     |
| 链路追踪   | SkyWalking           | 10.2.0     |

### 基础设施
| 功能    | 技术组件          | 版本                           |
|-------|---------------|------------------------------|
| 数据库   | MySQL         | 9.4.0                        |
| 缓存    | Redis         | 8.2                          |
| 消息队列  | RabbitMQ      | 4.0.4                        |
| 对象存储  | MinIO         | RELEASE.2025-04-22T22-12-26Z |
| 搜索引擎  | Elasticsearch | 8.19.0                       |
| 日志可视化 | Kibana        | 8.19.0                       |

### 开发支撑
| 工具    | 版本              |
|-------|-----------------|
| JDK   | OpenJDK 21      |
| Maven | 3.14.0          |
| IDE   | IntelliJ 2024.3 |
| 文档工具  | Swagger  2.2.36 |   

## 架构分析
![部署架构](https://foruda.gitee.com/images/1752487404755927519/2b218446_1743170.jpeg)

## 技术架构
![部署架构](https://foruda.gitee.com/images/1752487175170367124/6654ebcb_1743170.jpeg)

## 技术栈
![技术栈](https://foruda.gitee.com/images/1752487843086550351/ad18634e_1743170.jpeg)

## 部署架构
![部署架构](https://foruda.gitee.com/images/1752486981421917964/325c5625_1743170.jpeg)
