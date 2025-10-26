# 数据库设计文档

本文档描述了 Machine 微服务架构的完整数据库设计，包括 MySQL 和 PostgreSQL 数据库的表结构定义、初始化数据等。数据库采用多数据源架构，MySQL 负责基础设施组件，PostgreSQL 负责业务数据存储。

## 🗄️ MySQL 数据库设计

MySQL 数据库主要用于基础设施组件的数据存储，包括配置中心、任务调度等核心组件。

### 📊 数据库架构

MySQL 数据库采用分库设计，每个基础设施组件独立数据库：

- **machine_nacos** - Nacos 配置中心数据库，存储配置信息和服务注册信息
- **machine_xxljob** - XXL-Job 定时任务调度中心数据库，存储任务配置和执行记录

### 📋 表结构设计 (Table)

数据库表结构定义文件，包含各基础设施组件的完整表结构：

- [📊 Schema设计](mysql/table/schema.sql) - 数据库创建脚本，定义所有 MySQL 数据库
- [🔧 machine-nacos表结构](mysql/table/machine-nacos-table.sql) - 服务注册与配置管理
- [⏰ machine-xxljob表结构](mysql/table/machine-xxljob-table.sql) - 分布式任务调度

### 📊 初始化数据 (Data)

数据库初始化数据文件，包含系统运行所需的基础数据：

- [📊 machine-xxljob初始化数据](mysql/data/machine-xxljob-data.sql) - XXL-Job 定时任务初始化数据

---

## 🐘 PostgreSQL 数据库设计

PostgreSQL 数据库主要用于工作流引擎、业务服务的核心数据存储，提供更强大的数据管理能力和高性能特性。

### 📊 数据库架构

PostgreSQL 数据库采用分库设计，每个业务服务独立数据库：

- **machine_camunda** - 工作流引擎数据库
- **machine_iam** - 身份认证与授权服务数据库
- **machine_data** - 数据管理服务数据库  
- **machine_ai** - AI 智能服务数据库
- **machine_hrm** - 人力资源服务数据库
- **machine_crm** - 客户关系管理服务数据库
- **machine_scm** - 供应链管理服务数据库
- **machine_tpp** - 第三方平台服务数据库
- **machine_plugin** - 插件管理服务数据库

### 📋 表结构设计 (Table)

业务服务数据库表结构定义文件：

- [📊 Schema设计](postgresql/table/schema.sql) - 数据库创建脚本，定义所有 PostgreSQL 数据库
- [⚙️ Camunda工作流表](postgresql/table/machine-camunda-table.sql) - 业务流程管理
- [🔐 machine-iam表结构](postgresql/table/machine-iam-table.sql) - 用户、角色、权限、组织管理
- [📊 machine-data表结构](postgresql/table/machine-data-table.sql) - 门店、标签、素材、附件管理
- [🤖 machine-ai表结构](postgresql/table/machine-ai-table.sql) - AI模型、对话、分析数据
- [👥 machine-hrm表结构](postgresql/table/machine-hrm-table.sql) - 员工、部门、组织架构
- [🤝 machine-crm表结构](postgresql/table/machine-crm-table.sql) - 客户、会员管理
- [🔗 machine-scm表结构](postgresql/table/machine-scm-table.sql) - 供应商、采购管理
- [🌐 machine-tpp表结构](postgresql/table/machine-tpp-table.sql) - 第三方平台集成
- [🔌 machine-plugin表结构](postgresql/table/machine-plugin-table.sql) - 插件系统管理

### 📊 初始化数据 (Data)

业务服务初始化数据文件：

- [📊 machine-iam初始化数据](postgresql/data/machine-iam-data.sql) - 身份认证服务初始化数据
- [📊 machine-data初始化数据](postgresql/data/machine-data-data.sql) - 数据服务初始化数据


## 📊 文件结构总览

```
document/db/
├── README.md                           # 数据库设计文档
├── mysql/                              # MySQL 数据库配置
│   ├── table/                          # 表结构定义
│   │   ├── schema.sql                  # 数据库创建脚本
│   │   ├── machine-nacos-table.sql     # Nacos 配置中心表结构
│   │   └── machine-xxljob-table.sql    # XXL-Job 任务调度表结构
│   └── data/                           # 初始化数据
│       └── machine-xxljob-data.sql     # XXL-Job 初始化数据
└── postgresql/                         # PostgreSQL 数据库配置
    ├── table/                          # 表结构定义
    │   ├── schema.sql                  # 数据库创建脚本
    │   ├── machine-camunda-table.sql   # Camunda 工作流表结构
    │   ├── machine-iam-table.sql       # 身份认证服务表结构
    │   ├── machine-data-table.sql      # 数据管理服务表结构
    │   ├── machine-ai-table.sql        # AI 智能服务表结构
    │   ├── machine-hrm-table.sql       # 人力资源服务表结构
    │   ├── machine-crm-table.sql       # 客户关系管理表结构
    │   ├── machine-scm-table.sql       # 供应链管理表结构
    │   ├── machine-tpp-table.sql       # 第三方平台表结构
    │   └── machine-plugin-table.sql    # 插件管理表结构
    └── data/                           # 初始化数据
        ├── machine-iam-data.sql        # 身份认证服务初始化数据
        └── machine-data-data.sql       # 数据管理服务初始化数据
```