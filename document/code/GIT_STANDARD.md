## 分支命名规范

在软件开发过程中，良好的分支命名规范有助于团队成员更好地理解和协作。以下是一些常用的分支前缀及其含义，这些前缀在 Vue 和 Element Plus 等项目中广泛使用。

### 通用前缀

- **feat**: 新功能
    - 示例：`feat/user-authentication` 表示添加用户认证功能

- **fix**: 问题修复
    - 示例：`fix/login-bug` 表示修复登录功能的bug

- **refactor**: 涉及代码重构，一般和新功能、问题修复无关
    - 示例：`refactor/module-structure` 表示重构模块结构

- **perf**: 和性能相关的改造
    - 示例：`perf/table` 用来优化表格展示性能

- **chore**: 构建或辅助工具调整
    - 示例：`chore/upgrade-vite` 表示升级 Vite 构建工具

### 其他前缀

- **docs**: 文档更新
    - 示例：`docs/readme-update` 表示更新 README 文件

- **style**: 代码风格调整，不影响功能
    - 示例：`style/eslint-config` 表示调整 ESLint 配置

- **test**: 添加或修改测试用例
    - 示例：`test/unit-tests` 表示添加单元测试

- **ci**: 持续集成相关配置
    - 示例：`ci/github-actions` 表示配置 GitHub Actions

- **revert**: 回滚之前的提交
    - 示例：`revert/feat/user-authentication` 表示回滚 `feat/user-authentication` 分支的更改

### 示例

#### 新功能
```plaintext
feat/user-authentication
```





## 代码提交规范
良好的代码提交规范有助于团队成员更好地理解和协作，同时也便于版本管理和代码审查。大多数开源框架都采用 Angular.js 指定的提交格式。以下是常见的提交类型及其含义：

### 提交类型

- **feat**: 新功能
    - 示例：`feat(user-authentication): 添加用户认证功能`

- **fix**: 问题修复
    - 示例：`fix(login): 修复登录功能的bug`

- **docs**: 文档更新
    - 示例：`docs(readme): 更新 README 文件`

- **style**: 代码风格修改，例如空格处理、格式化、添加分号等
    - 示例：`style(eslint): 调整 ESLint 配置`

- **refactor**: 新功能和问题修复除外的，代码结构、逻辑调整
    - 示例：`refactor(module-structure): 重构模块结构`

- **perf**: 性能优化
    - 示例：`perf(table): 优化表格展示性能`

- **test**: 增加或修复测试用例
    - 示例：`test(unit-tests): 添加单元测试`

- **chore**: 像构建脚本、辅助工具、文档生成等工程相关代码提交
    - 示例：`chore(webpack): 升级 Webpack 版本`

### 提交信息格式

一个标准的提交信息应该包含以下几个部分：

1. **类型 (Type)**: 提交类型，如 `feat`, `fix`, `docs` 等。
2. **范围 (Scope)**: 可选，描述提交影响的范围，如 `login`, `user-authentication` 等。
3. **描述 (Description)**: 简短描述提交的内容。

### 示例

#### 新功能
```plaintext
feat(user-authentication): 添加用户认证功能
```


## 发布手动操作命令集

```
-- 群里通知当前版本封版，不允许提交

-- GitLab 获取最新代码
git checkout dev
git pull

-- Maven 变更版本号为 Current RELEASE
mvn versions:set -DnewVersion=1.0.0-RELEASE -DgenerateBackupPoms=false

-- GitLab 提交改动
git commit -a -m "NONE New Branch 1.0.0-RELEASE"

-- GitLab 创建 Current Branch
git branch 1.0.0-RELEASE
git push -u origin 1.0.0-RELEASE

-- GitLab 创建 Current Tag(现在没有tag)
git tag 1.0.0
git push origin 1.0.0
git tag --delete 1.0.0

-- Maven 变更版本号为 Next SNAPSHOT
git branch dev
mvn versions:set -DnewVersion=1.1.0-SNAPSHOT -DgenerateBackupPoms=false

-- GitLab 提交改动
git commit -a -m "NONE New VERSION 1.1.0-SNAPSHOT"
git push
```
