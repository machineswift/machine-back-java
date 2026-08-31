<div align="center">

# 🚀 Machine 企业级智能管理平台

![Machine Logo](https://img.shields.io/badge/Machine-微服务平台-blue?style=for-the-badge&logo=spring)

[![Project](https://img.shields.io/badge/Project-2026.09.01--RELEASE-blue.svg)](pom.xml)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.1.2-blue.svg)](https://spring.io/projects/spring-cloud)
[![Spring Cloud Alibaba](https://img.shields.io/badge/Spring%20Cloud%20Alibaba-2025.1.0.0-orange.svg)](https://github.com/alibaba/spring-cloud-alibaba)
[![Java](https://img.shields.io/badge/Java-25-red.svg)](https://openjdk.java.net/)
[![Maven](https://img.shields.io/badge/Maven-3.14+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

**企业级智能化微服务平台 · 一站式数字化解决方案**

[在线演示](#-在线演示) • [项目结构](#-项目结构) • [文档](#-文档导航)

</div>

---

## 📖 项目简介

**Machine** 是一个面向企业的智能化微服务平台，致力于为企业提供一站式的数字化解决方案。平台涵盖人力资源管理（HRM）、客户关系管理（CRM）、供应链管理（SCM）、身份认证（IAM）、AI 智能等核心业务模块，帮助企业实现业务流程的数字化转型和智能化升级。

与 [Machine Monolith Java](https://gitee.com/machineswift/machine-monolith-java) 同源共建，单体版本与微服务版本业务能力保持一致。

### ✨ 核心特性

- 🏗️ **微服务架构**：基于 Spring Cloud + Spring Cloud Alibaba，服务注册发现、配置中心、网关路由、Feign 远程调用一应俱全。
- 🔐 **统一认证授权**：网关 + Spring Security + OAuth2 + JWT 统一鉴权，支持三方登录（JustAuth）与图形验证码（Kaptcha）。
- 🗄️ **多数据源架构**：MyBatis-Plus + dynamic-datasource，MySQL 承载基础设施库，PostgreSQL 承载业务与工作流数据。
- ⚙️ **工作流引擎**：集成 Cibseven BPM（Camunda 分支），支持业务流程建模与执行。
- ☁️ **对象存储**：基于 x-file-storage，一处接入 MinIO、华为云 OBS、阿里云 OSS、腾讯云 COS。
- 🤖 **AI 能力**：集成 Spring AI 与 Spring AI Alibaba，赋能智能化业务场景。
- 📡 **消息与调度**：消息队列消费者（`machine-mq-app`）与 XXL-Job 分布式任务调度。
- 📄 **文档处理**：Apache Tika + jodconverter，支持文档解析与格式转换。
- 📊 **可观测性**：SkyWalking APM、p6spy SQL 日志、SpringDoc OpenAPI 接口文档。
- 📋 **认证中心-客户端管理**：OAuth2 客户端（Registered Client）全生命周期管理（增删改查/启停/密钥重置），内置 Caffeine + Redis 本地缓存，配套授权码模式授权确认页（Consent）。
- 🏠 **首页程序坞配置**：基于用户配置（UserConfig）的首页程序坞（Dock）个性化配置能力。
- 🧾 **审计日志中心**：登录 / 访问 / 操作三类日志统一管理，异步落库、全链路可追溯。

---

### 🎮 在线演示

> **👉 [http://www.machinerust.cn](http://www.machinerust.cn)**

| 角色    | 账号      | 密码       |
|-------|---------|----------|
| 👤 访客 | `demo`  | `123456` |
| 👤 访客 | `guest` | `123456` |

---

## 🔧 技术栈

当前项目版本：**2026.09.01-RELEASE**。

| 类型          | 技术 / 组件                        | 版本                     |
|-------------|--------------------------------|------------------------|
| **运行框架**    | Spring Boot                    | 4.0.7                  |
| **微服务**     | Spring Cloud                   | 2025.1.2               |
|             | Spring Cloud Alibaba           | 2025.1.0.0             |
|             | Spring Cloud Gateway           | 随 Spring Cloud         |
| **语言**      | Java                           | 25                     |
| **构建**      | Maven                          | 3.14+                  |
|             | Lombok                         | 1.18.46                |
| **AI**      | Spring AI                      | 2.0.0                  |
|             | Spring AI Alibaba              | 2.0.0-M1.1             |
| **安全**      | Spring Security + OAuth2 + JWT | 随 Spring Boot          |
|             | JustAuth（三方登录）                 | 1.16.7                 |
|             | Kaptcha（验证码）                   | 2.3.2                  |
|             | Nimbus JOSE JWT                | 10.9                   |
| **持久化**     | MyBatis-Plus                   | 3.5.16                 |
|             | MyBatis                        | 3.5.19                 |
|             | dynamic-datasource（多数据源）       | 4.5.0                  |
|             | p6spy（SQL 日志）                  | 2.0.1                  |
| **缓存**      | Redisson                       | 4.4.0                  |
|             | Jedis                          | 7.5.2                  |
|             | Caffeine                       | 3.2.4                  |
| **数据库**     | PostgreSQL / MySQL             | —                      |
| **服务发现**    | Nacos                          | 随 Spring Cloud Alibaba |
| **API 文档**  | SpringDoc OpenAPI (Swagger UI) | 3.0.2                  |
|             | Swagger Annotations Jakarta    | 2.2.46                 |
| **对象存储**    | x-file-storage                 | 2.3.0                  |
|             | MinIO SDK                      | 8.5.2                  |
|             | 华为云 OBS SDK                    | 3.22.12                |
|             | 阿里云 OSS SDK                    | 3.16.1                 |
|             | 腾讯云 COS SDK                    | 5.6.260                |
| **文档转换**    | Apache Tika                    | 3.3.1                  |
|             | jodconverter                   | 4.4.11                 |
| **工作流**     | Cibseven BPM（Camunda 分支）       | 2.2.0                  |
|             | GraalVM JS（脚本引擎）               | 23.0.6                 |
| **调度**      | XXL-Job                        | 3.4.0                  |
| **APM**     | Apache SkyWalking              | 9.6.0                  |
| **流处理**     | Apache Flink                   | 2.2.1                  |
|             | Flink Connector Kafka          | 4.0.1-2.0              |
| **微信 SDK**  | 小程序 / 公众号 / 企业微信 / 开放平台 / 支付   | 4.8.3                  |
| **飞书 SDK**  | Lark OAPI SDK                  | 2.0.2                  |
| **华为云**     | API Gateway SDK                | 3.2.4                  |
| **HTTP**    | OkHttp 5                       | 5.3.2                  |
|             | HttpClient 5                   | 5.6.1                  |
|             | HttpCore 5                     | 5.4.2                  |
| **字节码**     | ByteBuddy + ByteBuddy Agent    | 1.18.9                 |
|             | Javassist                      | 3.31.0-GA              |
| **AOP**     | AspectJ Weaver + Runtime       | 1.9.25.1               |
| **工具库**     | Hutool                         | 5.8.47                 |
|             | Guava                          | 33.7.0-jre             |
|             | Gson                           | 2.14.0                 |
|             | FastExcel                      | 1.3.0                  |
|             | jsoup（HTML 解析）                 | 1.23.1                 |
|             | commons-compress               | 1.26.2                 |
|             | TransmittableThreadLocal       | 2.14.5                 |
|             | Bouncy Castle                  | 1.84                   |
| **对象变更对比**  | JaVers                         | 7.5.0                  |
| **Jakarta** | Jakarta EE Platform            | 11.0.0                 |

---

## 🚀 快速开始

> 完整的环境搭建、数据库初始化与部署流程请参考 [部署与配置文档](document/deploy/README.md) 与 [数据库设计文档](document/database/README.md)。

### 环境要求

| 依赖         | 说明                                 |
|------------|------------------------------------|
| JDK        | 25                                 |
| Maven      | 3.14+                              |
| MySQL      | 基础设施库（Nacos、XXL-Job）               |
| PostgreSQL | 业务与工作流库（与 `machine-services` 对应）   |
| Redis      | 缓存 / Redisson 分布式锁                 |
| Nacos      | 注册中心 + 配置中心                        |
| XXL-Job    | 分布式任务调度中心（`machine-xxljob-app` 需要） |

### 启动步骤

1. **初始化数据库**：按 [数据库设计文档](document/database/README.md) 执行 MySQL 与 PostgreSQL 建库脚本，并导入对应初始化数据。
2. **部署中间件**：参考 [Docker 部署指南](document/deploy/README.md) 启动 Nacos、Redis、MySQL、PostgreSQL、XXL-Job 等基础设施。
3. **导入 Nacos 配置**：将 [nacos/yml](document/deploy/nacos/yml/) 下的 YAML 配置按模块（Apps / Services / Servers）导入 Nacos 配置中心。
4. **启动服务**：依次启动 `machine-servers`（网关、工作流引擎）→ `machine-apps`（各应用入口），JVM 参数可参考 [本地 JVM 配置](document/vm_options/local/)。

> 💡 想快速体验业务能力？可先运行同源的 [Machine Monolith Java](https://gitee.com/machineswift/machine-monolith-java) 单体版本。

---

## 📁 项目结构

```
machine-back-java/
├── machine-apps/                       # 应用层
│   ├── machine-iam-app/                # 身份认证服务
│   ├── machine-admin-app/             # 管理端 API
│   ├── machine-partner-app/              # 超级管理端 API
│   ├── machine-openapi-app/            # 开放 API
│   ├── machine-mq-app/                 # 消息队列消费者
│   └── machine-xxljob-app/             # XXL-Job 执行器
├── machine-clients/                    # Feign 客户端接口层
│   ├── machine-iam-client/             # 身份认证接口
│   ├── machine-data-client/            # 数据管理接口
│   ├── machine-ai-client/              # AI 服务接口
│   ├── machine-crm-client/             # CRM 接口
│   ├── machine-hrm-client/             # HRM 接口
│   ├── machine-scm-client/             # SCM 接口
│   ├── machine-tpp-client/             # 第三方平台接口
│   ├── machine-doc-client/             # 文档管理接口
│   └── machine-plugin-client/          # 插件接口
├── machine-services/                   # 服务实现层
│   ├── machine-iam-service/            # 身份认证实现
│   ├── machine-data-service/           # 数据管理实现
│   ├── machine-ai-service/             # AI 服务实现
│   ├── machine-crm-service/            # CRM 实现
│   ├── machine-hrm-service/            # HRM 实现
│   ├── machine-scm-service/            # SCM 实现
│   ├── machine-tpp-service/            # 第三方平台实现
│   ├── machine-doc-service/            # 文档管理实现
│   └── machine-plugin-service/         # 插件实现
├── machine-servers/                    # 基础设施服务
│   ├── machine-gateway-server/         # Spring Cloud Gateway 网关
│   └── machine-camunda-server/         # Cibseven BPM 工作流引擎
├── machine-starters/                   # Spring Boot Starter 自动配置
│   ├── machine-base-boot-starter/      # 基础自动配置
│   ├── machine-nacos-boot-starter/     # Nacos 自动配置
│   ├── machine-security-boot-starter/  # 安全认证自动配置
│   ├── machine-mybatis-boot-starter/   # MyBatis-Plus 自动配置
│   ├── machine-redis-boot-starter/     # Redis / Redisson 自动配置
│   ├── machine-obs-boot-starter/       # 对象存储自动配置
│   ├── machine-ai-boot-starter/        # AI 服务自动配置
│   ├── machine-mq-boot-starter/        # 消息队列自动配置
│   ├── machine-web-boot-starter/       # Web 自动配置（访问/操作日志、线程池）
│   ├── machine-wechat-boot-starter/    # 微信 SDK 自动配置
│   └── machine-sdk-boot-starter/       # 三方 SDK 自动配置
├── machine-generals/                   # 通用共享库
│   ├── machine-base-sdk/               # 通用 SDK（枚举、异常、工具类）
│   ├── machine-self-sdk/               # 自研 SDK
│   ├── machine-feishu-sdk/             # 飞书 SDK
│   ├── machine-huawei-sdk/             # 华为云 SDK
│   └── machine-beisen-sdk/             # 北森 SDK
├── machine-tests/                      # 测试与示例
│   ├── machine-flink-test/             # Flink 流处理示例
│   └── machine-temp-test/              # 临时测试
├── pom.xml                             # 父 POM
└── README.md
```

### 模块说明

| 层级           | 目录                  | 职责                              |
|--------------|---------------------|---------------------------------|
| **Apps**     | `machine-apps/`     | 应用入口，对外暴露 HTTP API              |
| **Clients**  | `machine-clients/`  | Feign 客户端接口 + DTO 定义，服务间 RPC 调用 |
| **Services** | `machine-services/` | 业务逻辑实现 + 数据访问（Mapper/DAO）       |
| **Servers**  | `machine-servers/`  | 基础设施服务（网关、工作流引擎）                |
| **Starters** | `machine-starters/` | Spring Boot 自动配置封装，按需引入         |
| **Generals** | `machine-generals/` | 通用 SDK，可供外部项目引用                 |

---

## 📚 文档导航

| 文档                                                                     | 说明                                     |
|------------------------------------------------------------------------|----------------------------------------|
| [架构与规范](document/architecture/ARCHITECTURE.md)                         | 技术架构、异常规范、Git 规范、OpenAPI 认证、Webhook 事件 |
| [异常处理规范](document/architecture/EXCEPTION.md)                           | 统一异常码定义与全局异常处理约定                       |
| [Git 规范](document/architecture/GIT_STANDARD.md)                        | 分支策略、Commit 约定、Merge Request 流程        |
| [OpenAPI 认证](document/architecture/OPENAPI_AUTH.md)                    | 开放接口的鉴权与签名机制                           |
| [Webhook 事件](document/architecture/WEBHOOK_EVENT.md)                   | 事件驱动架构中的 Webhook 事件定义与路由               |
| [部署与配置](document/deploy/README.md)                                     | Docker 部署、Nacos 配置中心、JVM 参数总览          |
| ├─ [Docker 本地部署 (Linux)](document/deploy/docker/docker_local_linux.md) | Linux 本地环境部署指南                         |
| ├─ [Docker 本地部署 (阿里云)](document/deploy/docker/docker_local_aliyun.md)  | 阿里云环境部署指南                              |
| ├─ [Docker 测试环境部署](document/deploy/docker/docker_test_linux.md)        | 测试环境部署指南                               |
| ├─ [Nacos 配置中心](document/deploy/nacos/yml/)                            | Apps / Services / Servers 的 YAML 配置示例  |
| └─ [JVM 参数](document/vm_options/)                                      | 本地与测试环境 JVM 启动参数配置                     |
| [数据库设计](document/database/README.md)                                   | MySQL / PostgreSQL 表结构、初始化数据与脚本说明      |
| ├─ [MySQL 建库脚本](document/database/mysql/table/schema.sql)              | 基础设施库（Nacos、XXL-Job）                   |
| └─ [PostgreSQL 建库脚本](document/database/postgresql/table/schema.sql)    | 业务与工作流库（按服务模块划分）                       |

---

## 📊 架构图

### 🏗️ 技术架构

> 详细的技术栈选型、基础设施和第三方集成信息，请参考 [架构规范文档](document/architecture/ARCHITECTURE.md)

<div align="center">
  <img src="https://foruda.gitee.com/images/1752487175170367124/6654ebcb_1743170.jpeg" alt="技术架构" width="80%"/>
</div>

### 🐳 部署架构

<div align="center">
  <img src="https://foruda.gitee.com/images/1752486981421917964/325c5625_1743170.jpeg" alt="部署架构" width="80%"/>
</div>

---

## 📄 许可证

本项目基于 [MIT License](LICENSE) 开源。

---

## 📞 联系我们

📧 **邮箱**: machineswift@qq.com

---

<div align="center">

**如果本项目对您有帮助，欢迎 ⭐ Star**

Made with ❤️ by Machine Team

</div>
