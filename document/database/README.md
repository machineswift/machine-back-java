# 数据库设计文档

本文档描述 Machine 微服务架构的完整数据库设计，包括 MySQL 与 PostgreSQL 的表结构定义、初始化数据及脚本说明。采用多数据源架构：**MySQL** 用于基础设施（Nacos、XXL-Job），**PostgreSQL** 用于工作流与业务数据。

📌 [返回项目首页](../../README.md) · [部署与配置](../deploy/README.md)

---

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

PostgreSQL 由 [schema.sql](postgresql/table/schema.sql) 建库，当前定义库与 `machine-services` 对应：

- **machine_camunda** - 工作流（表可由 Camunda 引擎首次启动时创建）
- **machine_iam**、**machine_data**、**machine_ai**、**machine_hrm**、**machine_crm**、**machine_scm**、**machine_tpp** - 业务库

### 📋 表结构设计 (Table) — 仓库内已提供脚本

以下为仓库中已有的表结构脚本，与代码模块一致：

| 服务 | 脚本位置 | 说明 |
|------|----------|------|
| 建库 | [schema.sql](postgresql/table/schema.sql) | 创建上述所有 PostgreSQL 数据库 |
| IAM | [machine-iam-table.sql](postgresql/table/machine-iam-table.sql) | 用户、角色、权限、组织 |
| HRM | [machine-hrm-table.sql](postgresql/table/machine-hrm-table.sql) | 员工、部门、组织架构 |
| Data | [table/data/](postgresql/table/data/) | 门店、标签、素材、附件：`machine-data-base-table.sql`、`machine-data-tag-table.sql`、`machine-data-file-table.sql`、`machine-scm-product-table.sql` |
| CRM | [table/crm/](postgresql/table/crm/) | [身份标识](postgresql/table/crm/machine-crm-identity-table.sql)、[业务数据](postgresql/table/crm/machine-crm-business-table.sql) |

**其他库**（machine_camunda、machine_ai、machine_scm、machine_tpp 等）：建库已在 schema 中完成，表结构可由框架自动创建或后续在 `postgresql/table/` 下补充脚本。

### 📊 初始化数据 (Data)

业务服务初始化数据文件：

- [📊 machine-iam 初始化数据](postgresql/data/machine-iam-data.sql) - 身份认证服务初始化数据
- [📊 machine-data 初始化数据](postgresql/data/machine-data-data.sql) - 数据服务初始化数据

### ⚡ 脚本执行顺序

建议按以下顺序执行，避免依赖错误：

1. **schema.sql** — 创建数据库
2. **table/*.sql** — 执行各服务表结构脚本
3. **data/*.sql** — 执行初始化数据脚本

## 📊 文件结构总览

以下为 `document/database/` 目录的完整结构，与仓库实际文件一一对应：

```
document/database/
├── README.md                                    # 本说明文档
├── mysql/                                       # MySQL（基础设施）
│   ├── table/                                   # 表结构定义
│   │   ├── schema.sql                           # 建库脚本（machine_nacos、machine_xxljob）
│   │   ├── machine-nacos-table.sql              # Nacos 配置中心表结构
│   │   └── machine-xxljob-table.sql             # XXL-Job 调度中心表结构
│   └── data/                                    # 初始化数据
│       └── machine-xxljob-data.sql              # XXL-Job 初始化数据
└── postgresql/                                  # PostgreSQL（业务数据）
    ├── table/                                   # 表结构定义
    │   ├── schema.sql                           # 建库脚本（各业务库）
    │   ├── machine-iam-table.sql                # IAM 用户、角色、权限、组织
    │   ├── machine-hrm-table.sql                # HRM 员工、部门、组织架构
    │   ├── data/                                # 数据管理服务（门店/标签/素材/附件）
    │   │   ├── machine-data-base-table.sql           # 主表
    │   │   ├── machine-data-tag-table.sql       # 标签
    │   │   ├── machine-data-file-table.sql      # 文件
    │   │   └── machine-scm-product-table.sql      # 条目
    │   └── crm/                                 # 客户关系管理服务
    │       ├── machine-crm-identity-table.sql    # 客户身份标识
    │       └── machine-crm-business-table.sql   # 客户业务数据
    └── data/                                    # 初始化数据
        ├── machine-iam-data.sql                 # IAM 初始化数据
        └── machine-data-data.sql                # Data 服务初始化数据
```

---
📌 [返回项目首页](../../README.md)