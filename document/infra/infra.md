# 基础设施配置文档

本文档描述了 Machine 微服务架构的完整基础设施配置，包括 Docker 部署、Nacos 配置管理、JVM 参数配置等。

## 🐳 Docker 部署配置

Docker 部署相关的配置文档和脚本，支持多种环境部署。

### 本地环境部署
- [🐳 本地环境Docker部署(Linux)](docker/docker_local_linux.md) - Linux 本地环境部署指南
- [🐳 本地环境Docker部署(Windows)](docker/docker_local_windows.md) - Windows 本地环境部署指南
- [🐳 本地环境Docker部署(阿里云)](docker/docker_local_aliyun.md) - 阿里云环境部署指南

### 测试环境部署
- [🐳 测试环境Docker部署](docker/docker_test_linux.md) - Linux 测试环境部署指南
- [🐳 测试环境运行配置](docker/docker_test_run.md) - 测试环境运行说明

---

## ⚙️ Nacos 配置管理

基于 Nacos 的配置中心管理，支持服务注册发现和动态配置管理。

### 🏗️ 主配置
- [machine.yaml](nacos/yml/machine.yaml) - 全局配置文件，包含公共配置项

### 🖥️ 服务器配置 (Servers)
基础设施服务器组件的配置管理：
- [machine-gateway-server.yaml](nacos/yml/servers/machine-gateway-server.yaml) - 网关服务器配置
- [machine-xxljob-server.yaml](nacos/yml/servers/machine-xxljob-server.yaml) - 定时任务服务器配置
- [machine-camunda-server.yaml](nacos/yml/servers/machine-camunda-server.yaml) - 工作流服务器配置

### 📱 应用配置 (Apps)
业务应用模块的配置管理：
- [machine-iam-app.yaml](nacos/yml/apps/machine-iam-app.yaml) - 身份认证应用配置
- [machine-manage-app.yaml](nacos/yml/apps/machine-manage-app.yaml) - 管理应用配置
- [machine-super-app.yaml](nacos/yml/apps/machine-super-app.yaml) - 超级管理员应用配置
- [machine-openapi-app.yaml](nacos/yml/apps/machine-openapi-app.yaml) - 开放API应用配置
- [machine-mq-app.yaml](nacos/yml/apps/machine-mq-app.yaml) - 消息队列应用配置
- [machine-xxljob-app.yaml](nacos/yml/apps/machine-xxljob-app.yaml) - 定时任务应用配置

### ⚙️ 服务配置 (Services)
微服务业务组件的配置管理：
- [machine-iam-service.yaml](nacos/yml/services/machine-iam-service.yaml) - 身份认证服务配置
- [machine-data-service.yaml](nacos/yml/services/machine-data-service.yaml) - 数据服务配置
- [machine-ai-service.yaml](nacos/yml/services/machine-ai-service.yaml) - AI服务配置
- [machine-hrm-service.yaml](nacos/yml/services/machine-hrm-service.yaml) - 人力资源服务配置
- [machine-crm-service.yaml](nacos/yml/services/machine-crm-service.yaml) - 客户关系管理服务配置
- [machine-scm-service.yaml](nacos/yml/services/machine-scm-service.yaml) - 供应链管理服务配置
- [machine-tpp-service.yaml](nacos/yml/services/machine-tpp-service.yaml) - 第三方平台服务配置
- [machine-doc-service.yaml](nacos/yml/services/machine-doc-service.yaml) - 文档服务配置
- [machine-plugin-service.yaml](nacos/yml/services/machine-plugin-service.yaml) - 插件服务配置

### 🧪 测试配置
- [machine-temp-test.yaml](nacos/yml/test/machine-temp-test.yaml) - 临时测试配置

---

## 🔧 JVM 参数配置

JVM 启动参数配置，针对不同环境和组件进行性能优化和内存调优。

### 🏠 本地环境 (Local)

#### 🖥️ 服务器 JVM 配置
基础设施服务器的JVM参数优化：
- [machine-gateway-server.properties](vm_options/local/servers/machine-gateway-server.properties) - 网关服务器JVM参数
- [machine-xxljob-server.properties](vm_options/local/servers/machine-xxljob-server.properties) - 定时任务服务器JVM参数
- [machine-camunda-server.properties](vm_options/local/servers/machine-camunda-server.properties) - 工作流服务器JVM参数

#### 📱 应用 JVM 配置
业务应用模块的JVM参数配置：
- [machine-iam-app.properties](vm_options/local/apps/machine-iam-app.properties) - 身份认证应用JVM参数
- [machine-manage-app.properties](vm_options/local/apps/machine-manage-app.properties) - 管理应用JVM参数
- [machine-super-app.properties](vm_options/local/apps/machine-super-app.properties) - 超级管理员应用JVM参数
- [machine-openapi-app.properties](vm_options/local/apps/machine-openapi-app.properties) - 开放API应用JVM参数
- [machine-mq-app.properties](vm_options/local/apps/machine-mq-app.properties) - 消息队列应用JVM参数
- [machine-xxljob-app.properties](vm_options/local/apps/machine-xxljob-app.properties) - 定时任务应用JVM参数

#### ⚙️ 服务 JVM 配置
微服务业务组件的JVM参数配置：
- [machine-iam-service.properties](vm_options/local/services/machine-iam-service.properties) - 身份认证服务JVM参数
- [machine-data-service.properties](vm_options/local/services/machine-data-service.properties) - 数据服务JVM参数
- [machine-ai-service.properties](vm_options/local/services/machine-ai-service.properties) - AI服务JVM参数
- [machine-hrm-service.properties](vm_options/local/services/machine-hrm-service.properties) - 人力资源服务JVM参数
- [machine-crm-service.properties](vm_options/local/services/machine-crm-service.properties) - 客户关系管理服务JVM参数
- [machine-scm-service.properties](vm_options/local/services/machine-scm-service.properties) - 供应链管理服务JVM参数
- [machine-tpp-service.properties](vm_options/local/services/machine-tpp-service.properties) - 第三方平台服务JVM参数
- [machine-doc-service.properties](vm_options/local/services/machine-doc-service.properties) - 文档服务JVM参数
- [machine-plugin-service.properties](vm_options/local/services/machine-plugin-service.properties) - 插件服务JVM参数

#### 🧪 测试 JVM 配置
- [machine-temp-test.properties](vm_options/local/tests/machine-temp-test.properties) - 临时测试JVM参数

### 🧪 测试环境 (Test)

#### 🖥️ 服务器 JVM 配置
测试环境基础设施服务器的JVM参数：
- [machine-gateway-server.properties](vm_options/test/servers/machine-gateway-server.properties) - 网关服务器JVM参数
- [machine-xxljob-server.properties](vm_options/test/servers/machine-xxljob-server.properties) - 定时任务服务器JVM参数
- [machine-camunda-server.properties](vm_options/test/servers/machine-camunda-server.properties) - 工作流服务器JVM参数

#### 📱 应用 JVM 配置
测试环境业务应用模块的JVM参数：
- [machine-iam-app.properties](vm_options/test/apps/machine-iam-app.properties) - 身份认证应用JVM参数
- [machine-manage-app.properties](vm_options/test/apps/machine-manage-app.properties) - 管理应用JVM参数
- [machine-super-app.properties](vm_options/test/apps/machine-super-app.properties) - 超级管理员应用JVM参数
- [machine-openapi-app.properties](vm_options/test/apps/machine-openapi-app.properties) - 开放API应用JVM参数
- [machine-mq-app.properties](vm_options/test/apps/machine-mq-app.properties) - 消息队列应用JVM参数
- [machine-xxljob-app.properties](vm_options/test/apps/machine-xxljob-app.properties) - 定时任务应用JVM参数

#### ⚙️ 服务 JVM 配置
测试环境微服务业务组件的JVM参数：
- [machine-iam-service.properties](vm_options/test/services/machine-iam-service.properties) - 身份认证服务JVM参数
- [machine-data-service.properties](vm_options/test/services/machine-data-service.properties) - 数据服务JVM参数
- [machine-ai-service.properties](vm_options/test/services/machine-ai-service.properties) - AI服务JVM参数
- [machine-hrm-service.properties](vm_options/test/services/machine-hrm-service.properties) - 人力资源服务JVM参数
- [machine-crm-service.properties](vm_options/test/services/machine-crm-service.properties) - 客户关系管理服务JVM参数
- [machine-scm-service.properties](vm_options/test/services/machine-scm-service.properties) - 供应链管理服务JVM参数
- [machine-tpp-service.properties](vm_options/test/services/machine-tpp-service.properties) - 第三方平台服务JVM参数
- [machine-doc-service.properties](vm_options/test/services/machine-doc-service.properties) - 文档服务JVM参数
- [machine-plugin-service.properties](vm_options/test/services/machine-plugin-service.properties) - 插件服务JVM参数

#### 🧪 测试 JVM 配置
- [machine-temp-test.properties](vm_options/test/tests/machine-temp-test.properties) - 临时测试JVM参数

---

## 📦 Dockerfile 配置

各服务的 Docker 镜像构建配置文件，支持多阶段构建和优化。

### 🖥️ 服务器 Dockerfile
基础设施服务器的镜像构建配置：
- [machine-gateway-server.Dockerfile](../../machine-servers/machine-gateway-server/Dockerfile) - 网关服务器镜像构建
- [machine-xxljob-server.Dockerfile](../../machine-servers/machine-xxljob-server/Dockerfile) - 定时任务服务器镜像构建
- [machine-camunda-server.Dockerfile](../../machine-servers/machine-camunda-server/Dockerfile) - 工作流服务器镜像构建

### 📱 应用 Dockerfile
业务应用模块的镜像构建配置：
- [machine-iam-app.Dockerfile](../../machine-apps/machine-iam-app/Dockerfile) - 身份认证应用镜像构建
- [machine-manage-app.Dockerfile](../../machine-apps/machine-manage-app/Dockerfile) - 管理应用镜像构建
- [machine-super-app.Dockerfile](../../machine-apps/machine-super-app/Dockerfile) - 超级管理员应用镜像构建
- [machine-openapi-app.Dockerfile](../../machine-apps/machine-openapi-app/Dockerfile) - 开放API应用镜像构建
- [machine-mq-app.Dockerfile](../../machine-apps/machine-mq-app/Dockerfile) - 消息队列应用镜像构建
- [machine-xxljob-app.Dockerfile](../../machine-apps/machine-xxljob-app/Dockerfile) - 定时任务应用镜像构建

### ⚙️ 服务 Dockerfile
微服务业务组件的镜像构建配置：
- [machine-iam-service.Dockerfile](../../machine-services/machine-iam-service/Dockerfile) - 身份认证服务镜像构建
- [machine-data-service.Dockerfile](../../machine-services/machine-data-service/Dockerfile) - 数据服务镜像构建
- [machine-ai-service.Dockerfile](../../machine-services/machine-ai-service/Dockerfile) - AI服务镜像构建
- [machine-hrm-service.Dockerfile](../../machine-services/machine-hrm-service/Dockerfile) - 人力资源服务镜像构建
- [machine-crm-service.Dockerfile](../../machine-services/machine-crm-service/Dockerfile) - 客户关系管理服务镜像构建
- [machine-scm-service.Dockerfile](../../machine-services/machine-scm-service/Dockerfile) - 供应链管理服务镜像构建
- [machine-tpp-service.Dockerfile](../../machine-services/machine-tpp-service/Dockerfile) - 第三方平台服务镜像构建
- [machine-doc-service.Dockerfile](../../machine-services/machine-doc-service/Dockerfile) - 文档服务镜像构建
- [machine-plugin-service.Dockerfile](../../machine-services/machine-plugin-service/Dockerfile) - 插件服务镜像构建

### 🧪 测试 Dockerfile
- [machine-temp-test.Dockerfile](../../machine-tests/machine-temp-test/Dockerfile) - 临时测试镜像构建

---

## 📝 使用说明

### 🚀 快速开始

1. **环境准备**：确保已安装 Docker 和 Docker Compose
2. **配置检查**：根据部署环境选择合适的配置文件
3. **服务启动**：按照部署文档逐步启动各个服务

### 🔧 配置管理

1. **Docker 部署**：根据环境选择对应的部署文档，按照步骤进行容器化部署
2. **Nacos 配置**：通过 Nacos 控制台管理各服务的配置，支持动态更新
3. **JVM 调优**：根据服务特点和环境选择合适的 JVM 参数配置
4. **镜像构建**：使用对应的 Dockerfile 构建各服务的 Docker 镜像
