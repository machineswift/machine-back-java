# 🏗️ 架构规范文档

## 📋 架构概览

Machine微服务平台采用分层微服务架构设计，基于Spring Cloud Alibaba生态构建，支持高并发、高可用、可扩展的企业级应用场景。

### 架构设计原则

- **🔧 微服务治理**: Nacos 3.1.0 服务注册发现与配置中心 + Spring Cloud Gateway 4.3.0 统一网关 + OpenFeign 4.3.0 服务调用 + 链路追踪
- **🏗️ 分层架构**: APP应用层 + CLIENT客户端层 + SERVICE服务层，职责清晰，易于维护
- **⚡ 高性能数据架构**: MyBatis-Plus 3.5.14 + 多数据源动态切换，支持事务管理和读写分离
- **📊 多数据源架构**: MySQL(配置中心/Nacos/XXL-JOB/Camunda) + PostgreSQL(业务数据，支持JSON字段和复杂查询) + Redis(分布式缓存/会话存储/分布式锁) + ClickHouse(分析型数据库/OLAP查询) + Elasticsearch(全文搜索/日志分析)

---

## 📋 工程分层规范

### 🚀 APP 应用层规范

#### 技术栈
- **Spring Boot**: 3.5.7
- **Spring MVC**: Web框架
- **Spring Security**: 6.5.3

#### 职责说明
对外提供HTTP接口，处理请求参数验证、权限控制、业务逻辑编排

#### 设计模式
- RESTful API设计
- 统一异常处理
- 标准化响应格式

#### 安全机制
- `@PreAuthorize` 权限控制
- `@Validated` 参数验证
- JWT Token认证

#### 目录结构

APP层负责对外提供HTTP接口，处理请求参数验证、权限控制、业务逻辑编排等。

```
machine-apps/machine-{module}-app/
├── src/main/java/com/machine/app/{module}/
│   ├── {business}/
│   │   ├── controller/                    # 控制器层
│   │   │   ├── {Business}Controller.java  # 控制器实现
│   │   │   └── vo/                       # 视图对象
│   │   │       ├── request/              # 请求VO
│   │   │       │   └── {Business}CreateRequestVo.java
│   │   │       │   └── {Business}UpdateRequestVo.java
│   │   │       │   └── {Business}QueryPageRequestVo.java
│   │   │       └── response/             # 响应VO
│   │   │           └── {Business}DetailResponseVo.java
│   │   │           └── {Business}ListResponseVo.java
│   │   └── business/                     # 业务逻辑层
│   │       ├── I{Business}Business.java  # 业务接口
│   │       └── impl/
│   │           └── I{Business}BusinessImpl.java  # 业务实现
```

#### 代码示例

**`DataTagController`** - 智能标签控制器

- 使用 `@RestController` 和 `@RequestMapping` 注解
- 使用 `@PreAuthorize` 进行权限控制
- 使用 `@Validated` 进行参数验证
- 通过 `Business` 层调用服务

#### 服务模块

| 模块名称                    | 说明                                   |
|-------------------------|--------------------------------------|
| **machine-iam-app**     | 身份认证与授权应用，基于Spring Security + OAuth2 |
| **machine-manage-app**  | 管理后台应用，提供统一的管理界面                     |
| **machine-super-app**   | 超级管理员应用，系统级管理功能                      |
| **machine-openapi-app** | 开放API应用，提供第三方系统集成接口                  |
| **machine-mq-app**      | 消息队列应用，处理异步消息和事件                     |
| **machine-xxljob-app**  | 定时任务应用，集成XXL-JOB分布式调度                |

---

### 🔗 CLIENT 客户端层规范

#### 技术栈
- **OpenFeign**: 4.3.0
- **Spring Cloud LoadBalancer**: 负载均衡

#### 职责说明
服务间调用，实现微服务间的通信和数据传输

#### 设计模式
- 统一服务调用接口
- 自动传递用户上下文和链路追踪

#### 核心特性
- `@FeignClient` 注解
- `@SkipUserIdCheck` 跳过用户验证
- DTO数据传输

#### 目录结构

CLIENT层负责服务间调用，使用OpenFeign实现微服务间的通信。

```
machine-clients/machine-{module}-client/
├── src/main/java/com/machine/client/{module}/
│   ├── {business}/
│   │   ├── I{Business}Client.java        # Feign客户端接口
│   │   └── dto/                         # 数据传输对象
│   │       ├── input/                   # 输入DTO
│   │       │   └── {Business}CreateInputDto.java
│   │       │   └── {Business}UpdateInputDto.java
│   │       └── output/                  # 输出DTO
│   │           └── {Business}DetailOutputDto.java
│   │           └── {Business}ListOutputDto.java
```

#### 代码示例

**`IIamUserClient`** - 用户服务客户端

- 使用 `@FeignClient` 注解指定服务名称
- 使用 `@PostMapping`、`@GetMapping` 等注解
- 使用 `@SkipUserIdCheck` 跳过用户ID检查（可选）

#### 服务模块

| 模块名称                      | 说明                   |
|---------------------------|----------------------|
| **machine-iam-client**    | IAM服务客户端，提供用户认证相关接口  |
| **machine-data-client**   | 数据服务客户端，提供数据管理相关接口   |
| **machine-ai-client**     | AI服务客户端，提供AI能力相关接口   |
| **machine-hrm-client**    | HRM服务客户端，提供人力资源相关接口  |
| **machine-crm-client**    | CRM服务客户端，提供客户关系相关接口  |
| **machine-scm-client**    | SCM服务客户端，提供供应链相关接口   |
| **machine-tpp-client**    | TPP服务客户端，提供第三方平台相关接口 |
| **machine-doc-client**    | 文档服务客户端，提供文档管理相关接口   |
| **machine-plugin-client** | 插件服务客户端，提供插件管理相关接口   |

---

### ⚙️ SERVICE 服务层规范

#### 技术栈
- **Spring Boot**: 应用框架
- **MyBatis-Plus**: 3.5.14
- **多数据源动态切换**: 支持事务管理和读写分离

#### 职责说明
核心业务逻辑实现，包含服务接口、数据访问层、事务管理

#### 设计模式
- 分层清晰，职责单一
- 支持 `@Transactional` 事务管理

#### 数据访问
DAO层 + Mapper层 + Entity层，支持复杂查询和批量操作

#### 目录结构

SERVICE层负责核心业务逻辑实现，包含服务接口、数据访问层等。

```
machine-services/machine-{module}-service/
├── src/main/java/com/machine/service/{module}/
│   ├── {business}/
│   │   ├── service/                     # 服务层
│   │   │   ├── I{Business}Service.java  # 服务接口
│   │   │   └── impl/
│   │   │       └── {Business}ServiceImpl.java  # 服务实现
│   │   ├── dao/                         # 数据访问层
│   │   │   ├── I{Business}Dao.java      # DAO接口
│   │   │   ├── impl/
│   │   │   │   └── {Business}DaoImpl.java  # DAO实现
│   │   │   └── mapper/                  # MyBatis映射
│   │   │       ├── entity/
│   │   │       │   └── {Business}Entity.java  # 实体类
│   │   │       └── I{Business}Mapper.java  # Mapper接口
│   │   └── server/                      # 服务端接口（可选）
│   │       └── {Business}Server.java    # 服务端控制器
```

#### 服务模块

| 模块名称                       | 说明                            |
|----------------------------|-------------------------------|
| **machine-iam-service**    | 身份认证服务，用户、角色、权限、组织管理          |
| **machine-data-service**   | 数据管理服务，智能标签、门店、素材、附件管理        |
| **machine-ai-service**     | AI智能服务，集成Spring AI框架，提供智能分析能力 |
| **machine-hrm-service**    | 人力资源服务，员工、部门、组织架构管理           |
| **machine-crm-service**    | 客户关系管理服务，客户、会员管理              |
| **machine-scm-service**    | 供应链管理服务，供应商、采购流程管理            |
| **machine-tpp-service**    | 第三方平台服务，微信、飞书、华为云等集成          |
| **machine-doc-service**    | 文档服务，文档管理和转换                  |
| **machine-plugin-service** | 插件服务，支持插件化扩展                  |

---

### 🖥️ SERVER 服务器层规范

#### 职责说明
提供基础设施服务和管理控制台，支持系统运维和监控

#### 服务模块

| 模块名称                       | 说明                                               |
|----------------------------|--------------------------------------------------|
| **machine-gateway-server** | API网关服务器，基于Spring Cloud Gateway，提供统一入口、路由转发、限流熔断 |
| **machine-xxljob-server**  | 任务调度服务器，XXL-JOB管理控制台，提供任务管理、执行监控、调度配置            |
| **machine-camunda-server** | 工作流服务器，Camunda BPM管理控制台，提供流程设计、流程监控、流程管理         |

---

### 🗄️ 数据层架构

#### 架构说明

多数据源架构设计，支持不同业务场景的数据存储需求。

#### 数据库选型

| 数据库               | 版本        | 用途说明                                   |
|-------------------|-----------|----------------------------------------|
| **MySQL**         | 9.4.0     | 配置中心(Nacos)、任务调度(XXL-JOB)、工作流(Camunda) |
| **PostgreSQL**    | 18.0      | 业务数据存储，支持JSON字段和复杂查询                   |
| **Redis**         | 8.2       | 分布式缓存，会话存储，分布式锁(Redisson)              |
| **ClickHouse**    | 25.9.3.48 | 分析型数据库，支持大数据量OLAP查询                    |
| **Elasticsearch** | 8.19.0    | 全文搜索引擎，日志分析和数据检索                       |

---

## 🛠️ 技术栈选型

### 🚀 核心技术栈

| 分类            | 技术组件                 | 版本            | 说明            |
|---------------|----------------------|---------------|---------------|
| **🏗️ 微服务框架** | Spring Cloud         | 2025.0.0      | 微服务基础框架       |
|               | Spring Cloud Alibaba | 2025.0.0.0    | 阿里云微服务生态      |
| **🌐 Web框架**  | Spring Boot          | 3.5.7         | Web应用框架       |
| **🤖 AI框架**   | Spring AI            | 1.1.0-M4      | Spring AI集成框架 |
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

---

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

---

### 🔧 第三方集成

| 分类          | 技术组件     | 版本      | 状态    | 用途说明           |
|-------------|----------|---------|-------|----------------|
| **💬 即时通讯** | 微信生态     | 4.7.8.B | ✅ 已集成 | 微信小程序、公众号、企业微信 |
| **☁️ 云服务**  | 华为云      | 3.2.4   | ✅ 已集成 | 华为云API网关       |
| **📋 办公协作** | 飞书       | 2.0.2   | ✅ 已集成 | 飞书开放平台         |
| **🏢 企业服务** | 北森       | -       | ✅ 已集成 | 北森HR系统集成       |
| **🔐 身份认证** | JustAuth | 1.16.7  | ✅ 已集成 | 第三方登录集成        |

---

## 🎯 设计规范

### 📝 命名规范

| 层级             | 命名规范                                                           | 示例                       |
|----------------|----------------------------------------------------------------|--------------------------|
| **Controller** | `{Business}Controller`                                         | `DataTagController`      |
| **Business**   | `I{Business}Business` / `I{Business}BusinessImpl`              | `IDataTagBusiness`       |
| **Service**    | `I{Business}Service` / `{Business}ServiceImpl`                 | `IDataTagService`        |
| **DAO**        | `I{Business}Dao` / `{Business}DaoImpl`                         | `IDataTagDao`            |
| **Entity**     | `{Business}Entity`                                             | `DataTagEntity`          |
| **VO**         | `{Business}{Action}RequestVo` / `{Business}{Action}ResponseVo` | `DataTagCreateRequestVo` |
| **DTO**        | `{Business}{Action}InputDto` / `{Business}{Action}OutputDto`   | `DataTagCreateInputDto`  |

---

### 🔧 技术规范

#### 数据类型规范
- **主键ID**: 使用 `String` 类型，32位UUID格式
- **时间字段**: 使用 `Long` 类型，Unix时间戳（毫秒）
- **状态字段**: 使用枚举类型，如 `StatusEnum`

#### 注解使用规范
- **权限控制**: 使用 `@PreAuthorize` 注解
- **参数验证**: 使用 `@Validated` 和 `@NotNull`、`@NotBlank` 等注解
- **事务管理**: 使用 `@Transactional` 注解
- **日志记录**: 使用 `@Slf4j` 注解，记录关键操作

---

### 🔐 权限规范

#### 权限编码格式
- **功能权限**: 使用 `SYSTEM:{MODULE}:{BUSINESS}:{ACTION}` 格式
- **数据权限**: 通过组织架构和角色进行控制
- **API权限**: 使用 `@PreAuthorize("hasAuthority('权限编码')")` 注解

#### 权限编码示例

| 权限编码                           | 说明       |
|--------------------------------|----------|
| `SYSTEM:BASIC_DATA:TAG:CREATE` | 创建智能标签   |
| `SYSTEM:BASIC_DATA:TAG:UPDATE` | 修改智能标签   |
| `SYSTEM:BASIC_DATA:TAG:DELETE` | 删除智能标签   |
| `SYSTEM:BASIC_DATA:TAG:DETAIL` | 查看智能标签详情 |

---

### 🌐 上下文管理

#### 用户上下文
- **获取方式**: 通过 `AppContext.getContext().getUserId()` 获取当前用户ID

#### 请求追踪
- **链路追踪**: 使用 `traceId` 进行请求链路追踪

#### Feign调用
- **自动传递**: 自动传递用户ID和traceId到下游服务

#### 跳过检查
- **注解使用**: 使用 `@SkipUserIdCheck` 注解跳过用户ID验证

---

## 📖 相关文档

### 🛠️ 开发规范

- [⚠️ 异常规范文档](EXCEPTION.md) - HTTP 状态码使用规范和异常处理机制
- [🌿 Git 规范文档](GIT_STANDARD.md) - 分支管理和提交规范
- [🔗 Webhook 对接规范](WEBHOOK_EVENT.md) - 事件推送接口规范
- [🔐 OAuth2 认证规范](OPENAPI_AUTH.md) - 开放平台认证流程

### 🚀 部署指南

- [🏗️ 基础设施部署指南](../deploy/README.md) - **完整的基础设施部署说明，包含所有配置详情**

> 💡 **提示**：所有详细的部署配置、Docker 部署、Nacos 配置管理、JVM 参数配置等详细信息，请参考 [基础设施部署指南](../deploy/README.md)

### 🗄️ 数据库设计

- [📊 完整数据库设计文档](../database/README.md) - **完整的数据库设计说明，包含所有表结构和数据详情**

> 💡 **提示**：所有详细的数据库设计、表结构定义、初始化数据等详细信息，请参考 [数据库设计文档](../database/README.md)
