<div align="center">

# 🚀 Machine 微服务平台

![Machine Logo](https://img.shields.io/badge/Machine-微服务平台-blue?style=for-the-badge&logo=spring)

[![Project](https://img.shields.io/badge/Project-2026.06.04--RELEASE-blue.svg)](pom.xml)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.6-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.1.1-blue.svg)](https://spring.io/projects/spring-cloud)
[![Spring Cloud Alibaba](https://img.shields.io/badge/Spring%20Cloud%20Alibaba-2025.1.0.0-orange.svg)](https://github.com/alibaba/spring-cloud-alibaba)
[![Java](https://img.shields.io/badge/Java-25-red.svg)](https://openjdk.java.net/)
[![Maven](https://img.shields.io/badge/Maven-3.14+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

**企业级智能化微服务平台 · 一站式数字化解决方案**

[功能特性](#-功能特性) • [在线演示](#-在线演示) • [快速开始](#-快速开始) • [项目结构](#-项目结构) • [文档](#-文档导航)

</div>

---

## 📖 项目简介

**Machine** 是一个面向企业的智能化微服务平台，致力于为企业提供一站式的数字化解决方案。平台涵盖人力资源管理（HRM）、客户关系管理（CRM）、供应链管理（SCM）、身份认证（IAM）、AI 智能等核心业务模块，帮助企业实现业务流程的数字化转型和智能化升级。

与 [Machine Monolith Java](https://gitee.com/machineswift/machine-monolith-java) 同源共建，单体版本与微服务版本业务能力保持一致。

---

### 🎮 在线演示

> **👉 [http://www.machinerust.cn](http://www.machinerust.cn)**

| 角色    | 账号      | 密码       |
|-------|---------|----------|
| 👤 访客 | `demo`  | `123456` |
| 👤 访客 | `guest` | `123456` |

---

## ✨ 功能特性

### 🔐 统一身份认证 (IAM)
- **多种登录方式**：用户名密码、手机验证码、验证码（Kaptcha）
- **OAuth2**：完整授权服务器与资源服务器
- **第三方登录**：Gitee、飞书等（JustAuth）
- **JWT**：无状态 Token 鉴权
- **RBAC**：角色与细粒度权限、数据权限
- **组织架构**：多级组织与用户管理

### 📊 数据管理 (Data)
- **基础数据**：区域、品牌、供应商、门店等
- **素材管理**：上传、分类、分页筛选
- **标签系统**：多级标签与智能标签选项
- **附件管理**：统一文件上传/下载、分类（支持 OBS/OSS/COS/MinIO）
- **下载中心**：导出与下载管理（基于 Fastexcel）
- **消息通知**：站内消息与模板

### 👥 客户关系管理 (CRM)
- **客户与会员**：客户信息、会员等级与权益

### 👨‍💼 人力资源管理 (HRM)
- **组织与员工**：部门、岗位、员工信息与入职离职管理

### 📦 供应链 (SCM)
- **商品与类目**：商品管理、类目枚举与扩展

### 🔌 第三方平台 (TPP)
- **微信生态**：小程序、公众号、企业微信、开放平台、支付（Weixin Java SDK）
- **飞书集成**：飞书开放平台 SDK（Lark OAPI）
- **华为云**：API Gateway SDK

### 🤖 AI 智能服务
- **智能对话**：基于 Spring AI + DashScope 的 AI 对话能力
- **文档智能**：文档内容分析与处理（Apache Tika + jodconverter）
- **知识库**：文档管理与智能检索

### 📄 文档管理 (Doc)
- **文档存储**：统一文档上传与管理
- **文档转换**：支持多格式文档转换与内容提取

### 🔌 开放平台 (OpenAPI)
- **开放 API**：对外提供标准 API 接口
- **第三方集成**：方便外部系统对接

### 🧩 插件体系 (Plugin)
- **可扩展**：插件化架构，支持动态加载

---

## 🔧 技术栈

当前项目版本：**2026.06.04-RELEASE**。

| 类型         | 技术 / 组件                            | 版本                               |
|------------|------------------------------------|----------------------------------|
| **运行框架**   | Spring Boot                        | 4.0.6                            |
| **微服务**    | Spring Cloud                       | 2025.1.1                         |
|            | Spring Cloud Alibaba               | 2025.1.0.0                       |
|            | Spring Cloud Gateway               | 随 Spring Cloud                   |
| **语言**     | Java                               | 25                               |
| **构建**     | Maven                              | 3.14+                            |
| **AI**     | Spring AI                          | 2.0.0-M8                         |
| **安全**     | Spring Security + OAuth2 + JWT     | 随 Spring Boot                    |
| **持久化**    | MyBatis-Plus                       | 3.5.16                           |
|            | dynamic-datasource (多数据源)          | 4.5.0                            |
|            | p6spy (SQL 日志)                     | 2.0.1                            |
| **缓存**     | Redisson                           | 4.4.0                            |
|            | Jedis                              | 7.5.2                            |
|            | Caffeine                           | 3.2.4                            |
| **数据库**    | PostgreSQL / MySQL                 | —                                |
| **服务发现**   | Nacos                              | 随 Spring Cloud Alibaba           |
| **文档**     | SpringDoc OpenAPI (Swagger UI)     | 3.0.2                            |
| **对象存储**   | x-file-storage (OBS/OSS/COS/MinIO) | 2.3.0                            |
| **文档转换**   | Apache Tika + jodconverter         | 3.3.1 / 4.4.11                   |
| **工作流**    | Camunda BPM                        | 7.24.0                           |
| **调度**     | XXL-Job                            | 3.4.0                            |
| **APM**    | Apache SkyWalking                  | 9.6.0                            |
| **流处理**    | Apache Flink                       | 2.2.1                            |
| **三方登录**   | JustAuth                           | 1.16.7                           |
| **微信 SDK** | 小程序 / 公众号 / 企业微信 / 支付              | 4.8.3-SNAPSHOT                   |
| **飞书 SDK** | Lark OAPI SDK                      | 2.0.2                            |
| **HTTP**   | OkHttp 5 + HttpClient 5            | 5.3.2 / 5.6.1                    |
| **工具库**    | Hutool / Guava / Gson / FastExcel  | 5.8.46 / 33.6.0 / 2.14.0 / 1.3.0 |
| **字节码**    | ByteBuddy + Javassist              | 1.18.9 / 3.31.0                  |

---

## 🚀 快速开始

### 环境要求

- **JDK 25**（若本地为 JDK 21，将根 `pom.xml` 中 `maven-compiler-plugin` 的 `source`/`target` 改为 `21`）
- **Maven 3.14+**
- **Docker**
- **PostgreSQL / MySQL**（业务数据库）
- **Redis**
- **Nacos**（服务注册与配置中心）

### 部署步骤

1. **环境准备**：安装 JDK 25、Maven 3.14+、Docker
2. **数据库初始化**：参考 [数据库设计文档](document/database/README.md) 执行数据库脚本
3. **基础设施启动**：参考 [本地 Docker 部署](document/deploy/docker/docker_local_linux.md)（Linux）或 [Windows 部署](document/deploy/docker/docker_local_windows.md) 启动 Nacos、Redis 等服务
4. **启动微服务**：按顺序依次启动各应用模块（Gateway → IAM → 业务服务）

---

## 📁 项目结构

```
machine-back-java/
├── machine-apps/                       # 应用层
│   ├── machine-iam-app/                # 身份认证服务
│   ├── machine-manage-app/             # 管理端 API
│   ├── machine-super-app/              # 超级管理端 API
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
│   └── machine-camunda-server/         # Camunda 工作流引擎
├── machine-starters/                   # Spring Boot Starter 自动配置
│   ├── machine-base-boot-starter/      # 基础自动配置
│   ├── machine-nacos-boot-starter/     # Nacos 自动配置
│   ├── machine-security-boot-starter/  # 安全认证自动配置
│   ├── machine-mybatis-boot-starter/   # MyBatis-Plus 自动配置
│   ├── machine-redis-boot-starter/     # Redis / Redisson 自动配置
│   ├── machine-obs-boot-starter/       # 对象存储自动配置
│   ├── machine-ai-boot-starter/        # AI 服务自动配置
│   ├── machine-mq-boot-starter/        # 消息队列自动配置
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

| 层级 | 目录 | 职责 |
|------|------|------|
| **Apps** | `machine-apps/` | 应用入口，对外暴露 HTTP API |
| **Clients** | `machine-clients/` | Feign 客户端接口 + DTO 定义，服务间 RPC 调用 |
| **Services** | `machine-services/` | 业务逻辑实现 + 数据访问（Mapper/DAO） |
| **Servers** | `machine-servers/` | 基础设施服务（网关、工作流引擎） |
| **Starters** | `machine-starters/` | Spring Boot 自动配置封装，按需引入 |
| **Generals** | `machine-generals/` | 通用 SDK，可供外部项目引用 |

---

## 📚 文档导航

| 文档                                          | 说明                                     |
|---------------------------------------------|----------------------------------------|
| [架构与规范](document/standards/ARCHITECTURE.md) | 技术架构、异常规范、Git 规范、OpenAPI 认证、Webhook 事件 |
| [部署与配置](document/deploy/README.md)          | Docker 部署、Nacos 配置、JVM 参数、Dockerfile   |
| [数据库设计](document/database/README.md)        | MySQL / PostgreSQL 表结构、初始化数据与脚本说明      |

---

## 📊 架构图

### 🏗️ 技术架构

> 详细的技术栈选型、基础设施和第三方集成信息，请参考 [架构规范文档](document/standards/ARCHITECTURE.md)

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
