<div style="text-align: center;">

![Machine Logo](https://img.shields.io/badge/Machine-微服务平台-blue?style=for-the-badge&logo=spring)

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.0.0-blue.svg)](https://spring.io/projects/spring-cloud)
[![Spring Cloud Alibaba](https://img.shields.io/badge/Spring%20Cloud%20Alibaba-2025.0.0.0-orange.svg)](https://github.com/alibaba/spring-cloud-alibaba)
[![Java](https://img.shields.io/badge/Java-21-red.svg)](https://openjdk.java.net/)
[![Maven](https://img.shields.io/badge/Maven-3.14.0-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

</div>


## 📖 项目简介

**Machine** 是一个基于 **Spring Cloud Alibaba 2025.0.0** 生态构建的现代化微服务架构平台，采用 **Java 21** + **Spring Boot 3.5.5** 最新技术栈。平台集成了 **Spring AI 1.1.0-M2** 框架，提供原生AI能力支持，并采用多数据源架构（MySQL + PostgreSQL + Redis + ClickHouse + Elasticsearch），支持高并发、高可用的企业级应用场景。

### 🏗️ 技术架构特色

- **🔧 微服务治理**: 基于 Nacos 3.1.0 的服务注册发现与配置中心，Spring Cloud Gateway 4.3.0 统一网关
- **🤖 AI原生集成**: Spring AI + Spring Alibaba AI 1.0.0.4，提供智能对话、数据分析等AI能力
- **⚡ 高性能数据层**: MyBatis-Plus 3.5.14 + 多数据源动态切换，支持事务管理和读写分离
- **🔐 企业级安全**: Spring Security 6.5.3 + OAuth2 + JWT，支持多租户权限隔离
- **⚙️ 工作流引擎**: Camunda 7.24.0-alpha2 业务流程管理，支持复杂业务自动化
- **⏰ 分布式调度**: XXL-JOB 3.2.0 任务调度，支持分布式任务执行和监控
- **🔍 可观测性**: SkyWalking 10.2.0 链路追踪 + ELK 日志分析 + Prometheus 监控
- **🌊 流处理**: Apache Flink 2.1.0 实时流处理，支持大数据实时计算


### 🚀 核心技术亮点

- **🔥 前沿技术栈**: Java 21 + Spring Boot 3.5.5 + Spring Cloud 2025.0.0，采用最新LTS版本
- **🤖 AI原生架构**: Spring AI 1.1.0-M2 + Spring Alibaba AI，提供完整的AI能力集成
- **🏗️ 分层架构设计**: APP应用层 + CLIENT客户端层 + SERVICE服务层，职责清晰，易于维护
- **⚡ 高性能数据架构**: 多数据源支持（MySQL/PostgreSQL/Redis/ClickHouse/Elasticsearch）
- **🔐 企业级安全体系**: Spring Security + OAuth2 + JWT + 多租户权限隔离
- **🌐 微服务治理**: Nacos服务发现 + Gateway网关 + OpenFeign服务调用 + 链路追踪
- **⚙️ 工作流自动化**: Camunda BPM + XXL-JOB分布式调度，支持复杂业务流程
- **🔍 全链路可观测**: SkyWalking + ELK + Prometheus，完整的监控告警体系

### 🎯 技术价值

- **🏗️ 微服务架构**: 基于Spring Cloud Alibaba，服务拆分合理，支持独立部署和弹性扩缩容
- **🔐 企业级安全**: Spring Security + OAuth2 + JWT，支持功能权限和数据权限的细粒度控制
- **📊 多数据源架构**: MySQL(配置中心) + PostgreSQL(业务数据) + Redis(缓存) + ClickHouse(分析) + Elasticsearch(搜索)
- **🤖 AI原生能力**: Spring AI框架集成，提供智能对话、数据分析、预测等AI服务
- **⚙️ 工作流引擎**: Camunda BPM 7.24.0-alpha2，支持复杂业务流程的自动化管理
- **⏰ 分布式调度**: XXL-JOB 3.2.0，支持分布式任务调度和异步处理
- **🌍 多租户支持**: 完整的多租户架构，支持数据隔离和权限隔离
- **🔌 开放API**: 基于OpenAPI 3.0规范的完整API体系，支持第三方系统集成

## 🏗️ 微服务架构

### 📱 应用层 (Apps)
- **machine-iam-app**: 身份认证与授权应用，基于Spring Security + OAuth2
- **machine-manage-app**: 管理后台应用，提供统一的管理界面
- **machine-super-app**: 超级管理员应用，系统级管理功能
- **machine-openapi-app**: 开放API应用，提供第三方系统集成接口
- **machine-mq-app**: 消息队列应用，处理异步消息和事件
- **machine-xxljob-app**: 定时任务应用，集成XXL-JOB分布式调度

### ⚙️ 服务层 (Services)
- **machine-iam-service**: 身份认证服务，用户、角色、权限、组织管理
- **machine-data-service**: 数据管理服务，智能标签、门店、素材、附件管理
- **machine-ai-service**: AI智能服务，集成Spring AI框架，提供智能分析能力
- **machine-hrm-service**: 人力资源服务，员工、部门、组织架构管理
- **machine-crm-service**: 客户关系管理服务，客户、会员管理
- **machine-scm-service**: 供应链管理服务，供应商、采购流程管理
- **machine-tpp-service**: 第三方平台服务，微信、飞书、华为云等集成
- **machine-doc-service**: 文档服务，文档管理和转换
- **machine-plugin-service**: 插件服务，支持插件化扩展

### 🔗 客户端层 (Clients)
- **machine-iam-client**: IAM服务客户端，提供用户认证相关接口
- **machine-data-client**: 数据服务客户端，提供数据管理相关接口
- **machine-ai-client**: AI服务客户端，提供AI能力相关接口
- **machine-hrm-client**: HRM服务客户端，提供人力资源相关接口
- **machine-crm-client**: CRM服务客户端，提供客户关系相关接口
- **machine-scm-client**: SCM服务客户端，提供供应链相关接口
- **machine-tpp-client**: TPP服务客户端，提供第三方平台相关接口
- **machine-doc-client**: 文档服务客户端，提供文档管理相关接口
- **machine-plugin-client**: 插件服务客户端，提供插件管理相关接口

### 🖥️ 服务器层 (Servers)
- **machine-gateway-server**: API网关服务器，基于Spring Cloud Gateway
- **machine-xxljob-server**: 任务调度服务器，XXL-JOB管理控制台
- **machine-camunda-server**: 工作流服务器，Camunda BPM管理控制台

## 🛠️ 技术栈

### 🚀 核心技术栈

| 分类            | 技术组件                 | 版本            | 说明            |
|---------------|----------------------|---------------|---------------|
| **🏗️ 微服务框架** | Spring Cloud         | 2025.0.0      | 微服务基础框架       |
|               | Spring Cloud Alibaba | 2025.0.0.0    | 阿里云微服务生态      |
| **🌐 Web框架**  | Spring Boot          | 3.5.5         | Web应用框架       |
| **🤖 AI框架**   | Spring AI            | 1.1.0-M2      | Spring AI集成框架 |
|               | Spring Alibaba AI    | 1.0.0.4       | 阿里云AI服务集成     |
| **🔧 服务治理**   | Nacos                | 3.1.0         | 服务注册发现与配置中心   |
|               | Spring Cloud Gateway | 4.3.0         | API网关         |
|               | OpenFeign            | 4.3.0         | 服务间调用         |
| **🔐 安全认证**   | Spring Security      | 6.5.3         | 认证授权框架        |
| **⏰ 任务调度**    | XXL-JOB              | 3.2.0         | 分布式任务调度       |
| **💾 数据访问**   | MyBatis-Plus         | 3.5.14        | ORM框架         |
| **⚙️ 工作流引擎**  | Camunda              | 7.24.0-alpha2 | 业务流程管理        |
| **🔍 链路追踪**   | SkyWalking           | 10.2.0        | 分布式链路追踪       |
| **📚 API文档**  | Swagger              | 2.2.36        | API文档生成工具     |

### 🏗️ 基础设施

| 分类             | 技术组件                 | 版本                           | 状态     | 用途说明     |
|----------------|----------------------|------------------------------|--------|----------|
| **🗄️ 关系型数据库** | MySQL                | 9.4.0                        | ✅ 已集成  | 主数据库     |
|                | PostgreSQL           | 18.0                         | ✅ 已集成  | 业务数据库    |
| **⚡ 缓存**       | Redis                | 8.2                          | ✅ 已集成  | 分布式缓存    |
| **📨 消息队列**    | RabbitMQ             | 4.0.4                        | ✅ 已集成  | 消息中间件    |
|                | Pulsar               | -                            | 🔄 待集成 | 分布式消息队列  |
| **📊 列式数据库**   | ClickHouse           | 25.9.3.48                    | ✅ 已集成  | 分析型数据库   |
|                | Apache Doris         | -                            | 🔄 待集成 | 实时分析数据库  |
| **🗃️ NoSQL**  | ScyllaDB             | 2025.3                       | ✅ 已集成  | 分布式NoSQL |
| **🌊 流处理**     | Apache Flink         | 2.1.0                        | ✅ 已集成  | 实时流处理    |
|                | Apache Spark         | -                            | 🔄 待集成 | 大数据批处理   |
| **🔍 搜索引擎**    | Elasticsearch        | 8.19.0                       | ✅ 已集成  | 全文搜索引擎   |
|                | Kibana               | 8.19.0                       | ✅ 已集成  | 日志分析可视化  |
| **📦 对象存储**    | MinIO                | RELEASE.2025-04-22T22-12-26Z | ✅ 已集成  | 对象存储服务   |
| **📈 监控可视化**   | Grafana + Prometheus | -                            | 🔄 待集成 | 监控告警系统   |

### 🔧 第三方集成

| 分类          | 技术组件     | 版本      | 状态    | 用途说明           |
|-------------|----------|---------|-------|----------------|
| **💬 即时通讯** | 微信生态     | 4.7.8.B | ✅ 已集成 | 微信小程序、公众号、企业微信 |
| **☁️ 云服务**  | 华为云      | 3.2.4   | ✅ 已集成 | 华为云API网关       |
| **📋 办公协作** | 飞书       | 2.0.2   | ✅ 已集成 | 飞书开放平台         |
| **🏢 企业服务** | 北森       | -       | ✅ 已集成 | 北森HR系统集成       |
| **🔐 身份认证** | JustAuth | 1.16.7  | ✅ 已集成 | 第三方登录集成        |

## 🏗️ 系统架构

### 📊 架构概览

Machine微服务平台采用现代化的微服务架构设计，基于Spring Cloud Alibaba生态构建，支持高并发、高可用、可扩展的企业级应用场景。

### 🏗️ 技术架构
<div style="text-align: center;">
  <img src="https://foruda.gitee.com/images/1752487175170367124/6654ebcb_1743170.jpeg" alt="技术架构" width="80%"/>
</div>

### 📊 业务架构
<div style="text-align: center;">
  <img src="https://foruda.gitee.com/images/1752487404755927519/2b218446_1743170.jpeg" alt="架构分析" width="80%"/>
</div>

### 🚀 技术栈概览
<div style="text-align: center;">
  <img src="https://foruda.gitee.com/images/1752487843086550351/ad18634e_1743170.jpeg" alt="技术栈" width="80%"/>
</div>

### 🐳 部署架构
<div style="text-align: center;">
  <img src="https://foruda.gitee.com/images/1752486981421917964/325c5625_1743170.jpeg" alt="部署架构" width="80%"/>
</div>

### 🏗️ 架构特点

- **🔧 微服务架构**: 基于Spring Cloud Alibaba，服务拆分合理，职责清晰
- **🌐 API网关**: Spring Cloud Gateway统一入口，支持路由、限流、熔断
- **🔐 统一认证**: Spring Security + OAuth2，支持多租户权限管理
- **📊 数据管理**: 多数据源支持，MySQL + PostgreSQL + Redis + ClickHouse
- **⚡ 高性能**: 分布式缓存、消息队列、异步处理
- **🔍 可观测性**: SkyWalking链路追踪、ELK日志分析、Prometheus监控
- **🤖 AI集成**: Spring AI框架，提供智能分析和对话能力
- **⚙️ 工作流**: Camunda工作流引擎，支持复杂业务流程自动化

### 🏗️ 分层架构设计

#### 📱 APP应用层 (machine-apps)
- **技术栈**: Spring Boot 3.5.5 + Spring MVC + Spring Security 6.5.3
- **职责**: 对外提供HTTP接口，处理请求参数验证、权限控制、业务逻辑编排
- **设计模式**: RESTful API设计，统一异常处理，标准化响应格式
- **安全机制**: @PreAuthorize权限控制，@Validated参数验证，JWT Token认证

#### 🔗 CLIENT客户端层 (machine-clients)
- **技术栈**: OpenFeign 4.3.0 + Spring Cloud LoadBalancer
- **职责**: 服务间调用，实现微服务间的通信和数据传输
- **设计模式**: 统一服务调用接口，自动传递用户上下文和链路追踪
- **特性**: @FeignClient注解，@SkipUserIdCheck跳过用户验证，DTO数据传输

#### ⚙️ SERVICE服务层 (machine-services)
- **技术栈**: Spring Boot + MyBatis-Plus 3.5.14 + 多数据源动态切换
- **职责**: 核心业务逻辑实现，包含服务接口、数据访问层、事务管理
- **设计模式**: 分层清晰，职责单一，支持@Transactional事务管理
- **数据访问**: DAO层 + Mapper层 + Entity层，支持复杂查询和批量操作

#### 🗄️ 数据层架构
- **MySQL 9.4.0**: 配置中心(Nacos)、任务调度(XXL-JOB)、工作流(Camunda)
- **PostgreSQL 18.0**: 业务数据存储，支持JSON字段和复杂查询
- **Redis 8.2**: 分布式缓存，会话存储，分布式锁(Redisson)
- **ClickHouse 25.9.3.48**: 分析型数据库，支持大数据量OLAP查询
- **Elasticsearch 8.19.0**: 全文搜索引擎，日志分析和数据检索

## 🔧 开发支撑

### 🛠️ 开发工具

| 类型             | 工具         | 版本         | 说明            |
|----------------|------------|------------|---------------|
| 开发工具包          | JDK        | OpenJDK 21 | 基于项目pom.xml配置 |
| 项目管理和构建工具      | Maven      | 3.14.0     | 基于项目pom.xml配置 |
| 集成开发环境         | VSCode     | 1.90.0+    | 推荐版本          |
| 集成开发环境         | IntelliJ   | 2024.3     | 已指定版本         |
| 接口文档与调试工具      | Apifox     | 2.5.0+     | 推荐版本          |
| 数据库管理工具        | Navicat    | 17.0+      | 推荐版本          |
| 通用数据库管理工具      | DBeaver    | 24.1.0+    | 推荐版本          |
| 浏览器            | Chrome     | 131.0+     | 推荐版本          |
| AI 本地模型管理与运行工具 | Ollama     | 0.1.50+    | 推荐版本          |
| 容器化平台          | Docker     | 27.0+      | 基于项目Docker配置  |
| 容器编排平台         | Kubernetes | 1.31+      | 推荐版本          |
| 代码托管平台         | GitLab     | 17.0+      | 推荐版本          |
| 分布式版本控制系统      | Git        | 2.45+      | 推荐版本          |
| 项目管理与问题跟踪工具    | Jira       | 9.14+      | 推荐版本          |
| 企业级知识管理和文档协作工具 | Confluence | 8.8+       | 推荐版本          |

### 🔧 开发与测试工具

| 类型       | 工具             | 版本     | 说明                  |
|----------|----------------|--------|---------------------|
| 代码质量检查工具 | SonarQube      | 10.6+  | 代码质量分析              |
| 单元测试框架   | JUnit          | 5.10+  | 基于Spring Boot 3.5.5 |
| 集成测试工具   | TestContainers | 1.19+  | 容器化测试               |
| 性能测试工具   | JMeter         | 5.6+   | 性能压测                |
| API测试工具  | Postman        | 10.25+ | API调试和测试            |

### 🚀 部署与运维工具

| 类型          | 工具         | 版本     | 说明                  |
|-------------|------------|--------|---------------------|
| 持续集成/持续部署工具 | Jenkins    | 2.450+ | CI/CD流水线            |
| 容器镜像仓库      | Harbor     | 2.10+  | 私有镜像仓库              |
| 监控告警工具      | Prometheus | 2.50+  | 基于项目技术栈             |

## 📖 文档

### 🛠️ 开发规范

- [🏗️ 架构规范文档](document/code/ARCHITECTURE.md) - 工程分层规范和设计原则
- [⚠️ 异常规范文档](document/code/EXCEPTION.md) - HTTP状态码使用规范和异常处理机制
- [🌿 Git规范文档](document/code/GIT_STANDARD.md) - 分支管理和提交规范
- [🔗 Webhook对接规范](document/code/WEBHOOK_EVENT.md) - 事件推送接口规范
- [🔐 OAuth2认证规范](document/code/OPENAPI_AUTH.md) - 开放平台认证流程

### 🚀 部署指南

- [🏗️ 基础设施部署指南](document/infra/infra.md) - **完整的基础设施部署说明，包含所有配置详情**

> 💡 **提示**：所有详细的部署配置、Docker部署、Nacos配置管理、JVM参数配置等详细信息，请参考 [基础设施部署指南](document/infra/infra.md)

### 🗄️ 数据库设计

#### MySQL数据库
- [📊 Schema设计](document/db/mysql/table/schema.sql) - 数据库结构设计
  - `machine_nacos` - Nacos配置中心数据库
  - `machine_xxljob` - XXL-JOB任务调度数据库
  - `machine_camunda` - Camunda工作流数据库
- [🔧 machine-nacos表结构](document/db/mysql/table/machine-nacos-table.sql) - 服务注册与配置管理
- [⏰ machine-xxljob表结构](document/db/mysql/table/machine-xxljob-table.sql) - 分布式任务调度
- [📊 测试数据](document/db/mysql/data) - 测试环境初始化数据

#### PostgreSQL数据库
- [📊 Schema设计](document/db/postgresql/table/schema.sql) - 业务数据库结构设计
  - `machine_iam` - 身份认证与授权
  - `machine_data` - 数据管理服务
  - `machine_ai` - AI智能服务
  - `machine_hrm` - 人力资源
  - `machine_crm` - 客户关系管理
  - `machine_scm` - 供应链管理
  - `machine_tpp` - 第三方平台
  - `machine_plugin` - 插件管理
- [⚙️ Camunda工作流表](document/db/postgresql/table/machine-camunda-table.sql) - 业务流程管理
- [🔐 machine-iam表结构](document/db/postgresql/table/machine-iam-table.sql) - 用户、角色、权限、组织管理
- [📊 machine-data表结构](document/db/postgresql/table/machine-data-table.sql) - 门店、标签、素材、附件管理
- [🤖 machine-ai表结构](document/db/postgresql/table/machine-ai-table.sql) - AI模型、对话、分析数据
- [👥 machine-hrm表结构](document/db/postgresql/table/machine-hrm-table.sql) - 员工、部门、组织架构
- [🤝 machine-crm表结构](document/db/postgresql/table/machine-crm-table.sql) - 客户、会员管理
- [🔗 machine-scm表结构](document/db/postgresql/table/machine-scm-table.sql) - 供应商、采购管理
- [🌐 machine-tpp表结构](document/db/postgresql/table/machine-tpp-table.sql) - 第三方平台集成
- [🔌 machine-plugin表结构](document/db/postgresql/table/machine-plugin-table.sql) - 插件系统管理
- [📊 测试数据](document/db/postgresql/data) - 测试环境初始化数据

## 🤝 贡献指南

### 📝 代码规范

- 遵循 [Java代码规范](document/code/ARCHITECTURE.md)
- 使用 [Git提交规范](document/code/GIT_STANDARD.md)
- 确保代码通过所有测试
- 添加必要的文档和注释

## 📄 许可证

本项目基于 [MIT 许可证](LICENSE) 开源。

## 📞 联系我们

- 📧 **邮箱**: machineswift@qq.com
---

<div style="text-align: center;">

**如果这个项目对您有帮助，请给我们一个 ⭐ Star！**

</div>



