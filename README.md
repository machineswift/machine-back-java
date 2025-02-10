## 文档目录

- [开发规范]()
    - [架构 规范文档](document/code/ARCHITECTURE.md)
    - [异常 规范文档](document/code/EXCEPTION.md)
    - [git 规范文档](document/code/GIT_STANDARD.md)
    - [webhook 对接](document/code/WEBHOOK_EVENT.md)
- [数据库设计文档]()
    - [schema](document/db/table/schema.sql)
    - [machine-nacos-table](document/db/table/machine-nacos-table.sql)
    - [machine-infra-table](document/db/table/machine-infra-table.sql)
    - [machine-camunda-table](document/db/table/machine-camunda-table.sql)
    - [machine-iam-table](document/db/table/machine-iam-table.sql)
    - [machine-data-table](document/db/table/machine-data-table.sql)
- [基础设施配置](document/infra/infra.md)

## 技术选型

### 核心技术栈

| 功能      | 名称                        | 版本         | 备注 |
|---------|---------------------------|------------|----|
| 微服务框架   | spring-cloud-alibaba      | 2023.0.1.2 | -  |
| 微服务框架   | spring-cloud              | 2023.0.2   | -  |
| 微服务框架   | spring-boot               | 3.2.8      | -  |
| 服务注册和发现 | nacos                     | 2.3.2      | -  |
| 配置中心    | nacos config              | 2.3.2      | -  |
| 网关      | spring-cloud-gateway      | -          | -  |
| 负载均衡    | spring-cloud-loadbalancer | -          | -  |
| 服务调用    | spring-cloud-openfeign    | -          | -  |
| 限流熔断    | 暂无                        | -          | -  |
| 分布式事务   | 暂无                        | -          | -  |
| 链路追踪    | skywalking                | 10.1.0     | -  |
| 认证授权    | SpringSecurity            | 10.1.0     | -  |
| 调度服务    | XXL-JOB                   | 3.0.0      | -  |
| ORM     | mybatisPlus               | 3.5.8      | -  |

### 基础设施

| 功能    | 名称            | 版本               | 备注 |
|-------|---------------|------------------|----|
| JDK   | openjdk       | 21               | -  |
| 消息服务  | RabbitMq      | 4.0.4-management | -  |
| 搜索引擎  | 暂无            | -                | -  |
| 文件上传  | 华为云           | -                | -  |
| 数据库   | mysql         | 8.0.22-30        | -  |
| 分布式缓存 | redis         | 7.4              | -  |
| 本地缓存  | caffeine      | 3.1.8            | -  |
| 日志搜索  | Elasticsearch | 8.17.0           | -  |
| ORM   | mybatisPlus   | 3.5.8            | -  |

### 开发支撑

| 功能    | 名称      | 版本     | 备注 |
|-------|---------|--------|----|
| IDE   | IDE     | 2024.1 | -  |
| 文档    | swagger | 2.2.22 | -  |
| Maven | Maven   | 3.9.6  | -  |
| 代码仓库  | 华为云     | -      | -  |