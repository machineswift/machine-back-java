<div align="center">

# 🚀 Machine 微服务平台

![Machine Logo](https://img.shields.io/badge/Machine-微服务平台-blue?style=for-the-badge&logo=spring)

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.1.0-blue.svg)](https://spring.io/projects/spring-cloud)
[![Spring Cloud Alibaba](https://img.shields.io/badge/Spring%20Cloud%20Alibaba-2025.1.0.0-orange.svg)](https://github.com/alibaba/spring-cloud-alibaba)
[![Java](https://img.shields.io/badge/Java-25-red.svg)](https://openjdk.java.net/)
[![Maven](https://img.shields.io/badge/Maven-3.14.0-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

</div>

## 📖 项目简介

**Machine** 是一个面向企业的智能化微服务平台，致力于为企业提供一站式的数字化解决方案。平台涵盖人力资源管理（HRM）、客户关系管理（CRM）、供应链管理（SCM）等核心业务模块，并集成
AI 智能分析能力，帮助企业实现业务流程的数字化转型和智能化升级。

### 🎯 核心能力

- **👥 人力资源管理（HRM）**: 支持员工、部门、组织架构的全面管理，提供灵活的组织体系和权限控制
- **🤝 客户关系管理（CRM）**: 完善的客户和会员管理体系，助力企业提升客户服务和营销效率
- **📦 供应链管理（SCM）**: 覆盖供应商管理和采购流程，实现供应链全流程数字化管理
- **🔐 统一身份认证（IAM）**: 企业级权限管理，支持多租户、细粒度权限控制，保障数据安全
- **🤖 AI 智能服务**: 集成 AI 能力，提供智能对话、数据分析、业务预测等智能化功能
- **📊 数据管理**: 智能标签、门店管理、素材和附件管理，构建企业数据资产
- **🔌 开放生态**: 支持与微信、飞书、华为云等第三方平台集成，提供开放 API 体系

## 🚀 快速开始

1. **环境准备**：安装 [JDK 25](https://openjdk.java.net/)、[Maven 3.14+](https://maven.apache.org/)、[Docker](https://www.docker.com/)
2. **数据库初始化**：参考 [数据库设计文档](document/database/README.md) 执行 MySQL / PostgreSQL 脚本
3. **本地部署**：参考 [基础设施配置与部署](document/deploy/README.md)，按 [本地 Docker 部署](document/deploy/docker/docker_local_linux.md)（Linux）或 [Windows 部署](document/deploy/docker/docker_local_windows.md) 启动服务

## ✨ 核心特性

- **🔥 前沿技术栈**: Java 25 + Spring Boot 4.0 + Spring Cloud 2025.1.0（与根 [pom.xml](pom.xml) 一致）
- **🤖 AI 原生集成**: Spring AI 2.0.0-M2 + Spring Alibaba AI 1.1.2.0，提供智能对话、数据分析、预测等完整 AI 能力
- **🔐 企业级安全**: Spring Security（随 Spring Boot 4.0）+ OAuth2 + JWT，支持多租户权限隔离，功能权限与数据权限细粒度控制
- **⚙️ 工作流引擎**: Camunda BPM 7.24.0 业务流程管理，支持复杂业务流程自动化
- **⏰ 分布式调度**: XXL-JOB 3.3.2 任务调度，支持分布式任务执行、监控与异步处理
- **🔍 可观测性**: SkyWalking 9.5.0 链路追踪 + ELK 日志分析 + Prometheus 监控，提供完整监控告警体系
- **🌊 流处理**: Apache Flink 2.1.0 实时流处理，支持大数据实时计算
- **🔌 开放 API**: 基于 OpenAPI 3.0 规范的完整 API 体系，支持第三方系统集成

## 📦 工程模块结构

与 [根 pom.xml](pom.xml) 一致，采用多模块分层：

| 层级           | 目录                  | 模块说明                                                     |
|--------------|---------------------|----------------------------------------------------------|
| **Generals** | `machine-generals/` | 通用 SDK（self、common、feishu、huawei、beisen）                 |
| **Starters** | `machine-starters/` | 各 Boot Starter（nacos、security、mybatis、redis、ai、mq 等）     |
| **Servers**  | `machine-servers/`  | 网关 `machine-gateway-server`、工作流 `machine-camunda-server` |
| **Apps**     | `machine-apps/`     | 应用入口：iam、manage、super、openapi、mq、xxljob                  |
| **Clients**  | `machine-clients/`  | Feign 客户端：iam、data、ai、hrm、crm、scm、tpp、doc、plugin         |
| **Services** | `machine-services/` | 业务服务：iam、data、ai、hrm、crm、scm、tpp、doc、plugin              |
| **Tests**    | `machine-tests/`    | 测试/示例：`machine-flink-test`、`machine-temp-test`           |

## 📚 文档导航

| 文档                                          | 说明                                      |
|---------------------------------------------|-----------------------------------------|
| [架构与规范](document/standards/ARCHITECTURE.md) | 技术架构、异常规范、Git 规范、OpenAPI 认证、Webhook 事件等 |
| [部署与配置](document/deploy/README.md)          | Docker 部署、Nacos 配置、JVM 参数、Dockerfile    |
| [数据库设计](document/database/README.md)        | MySQL / PostgreSQL 表结构、初始化数据与脚本说明       |


## 🔧 开发支撑

### 🛠️ 开发工具

| 类型             | 工具                         | 版本         | 
|----------------|----------------------------|------------|
| 开发工具包          | JDK                        | OpenJDK 25 | 
| 项目管理和构建工具      | Maven                      | 3.14.0     | 
| 代码质量检查工具       | SonarQube                  | 10.6+      |
| 单元测试框架         | JUnit                      | 5.10+      | 
| 性能测试工具         | JMeter                     | 5.6+       |
| 集成开发环境         | VSCode / IntelliJ / Cursor | 最新版本       |
| 接口文档与调试工具      | Apifox                     | 最新版本       | 
| 数据库管理工具        | Navicat                    | 最新版本       | 
| 通用数据库管理工具      | DBeaver                    | 最新版本       | 
| 浏览器            | Chrome                     | 最新版本       |
| AI 本地模型管理与运行工具 | Ollama                     | 最新版本       |
| 容器化平台          | Docker                     | 最新版本       | 
| 容器镜像仓库         | Harbor                     | 最新版本       |
| 持续集成           | Jenkins/Gitlab             | 最新版本       |  
| 容器编排平台         | Kubernetes                 | 最新版本       |
| 代码托管平台         | GitLab                     | 最新版本       |
| 分布式版本控制系统      | Git                        | 最新版本       | 
| 监控告警工具         | Prometheus                 | 最新版本       | 
| 项目管理与问题跟踪工具    | Jira                       | 9.14+      |
| 企业级知识管理和文档协作工具 | Confluence                 | 8.8+       | 

## 📊 架构图

### 🏗️ 技术架构

> 💡 **提示**：详细的技术栈选型、基础设施和第三方集成信息，请参考 [架构规范文档](document/standards/ARCHITECTURE.md)
<div align="center">
  <img src="https://foruda.gitee.com/images/1752487175170367124/6654ebcb_1743170.jpeg" alt="技术架构" width="80%"/>
</div>

### 🐳 部署架构

<div align="center">
  <img src="https://foruda.gitee.com/images/1752486981421917964/325c5625_1743170.jpeg" alt="部署架构" width="80%"/>
</div>

## 📄 许可证

本项目基于 [MIT 许可证](LICENSE) 开源。

---

## 📞 联系我们

📧 **邮箱**: machineswift@qq.com

---

<div align="center">

**如果这个项目对您有帮助，请给我们一个 ⭐ Star！**

Made with ❤️ by Machine Team

</div>
