# 🏗️ 架构规范文档

## 📋 工程分层规范

### 🚀 APP 应用层规范

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

**示例：** `DataTagController` - 智能标签控制器
- 使用 `@RestController` 和 `@RequestMapping` 注解
- 使用 `@PreAuthorize` 进行权限控制
- 使用 `@Validated` 进行参数验证
- 通过 `Business` 层调用服务

### 🔗 CLIENT 客户端层规范

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

**示例：** `IIamUserClient` - 用户服务客户端
- 使用 `@FeignClient` 注解指定服务名称
- 使用 `@PostMapping`、`@GetMapping` 等注解
- 使用 `@SkipUserIdCheck` 跳过用户ID检查（可选）

### ⚙️ SERVICE 服务层规范

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

## 🎯 设计规范

### 📝 命名规范

| 层级 | 命名规范 | 示例 |
|------|---------|------|
| **Controller** | `{Business}Controller` | `DataTagController` |
| **Business** | `I{Business}Business` / `I{Business}BusinessImpl` | `IDataTagBusiness` |
| **Service** | `I{Business}Service` / `{Business}ServiceImpl` | `IDataTagService` |
| **DAO** | `I{Business}Dao` / `{Business}DaoImpl` | `IDataTagDao` |
| **Entity** | `{Business}Entity` | `DataTagEntity` |
| **VO** | `{Business}{Action}RequestVo` / `{Business}{Action}ResponseVo` | `DataTagCreateRequestVo` |
| **DTO** | `{Business}{Action}InputDto` / `{Business}{Action}OutputDto` | `DataTagCreateInputDto` |

### 🔧 技术规范

- **主键ID**: 使用 `String` 类型，32位UUID格式
- **时间字段**: 使用 `Long` 类型，Unix时间戳（毫秒）
- **状态字段**: 使用枚举类型，如 `StatusEnum`
- **权限控制**: 使用 `@PreAuthorize` 注解
- **参数验证**: 使用 `@Validated` 和 `@NotNull`、`@NotBlank` 等注解
- **事务管理**: 使用 `@Transactional` 注解
- **日志记录**: 使用 `@Slf4j` 注解，记录关键操作


### 🔐 权限规范

- **功能权限**: 使用 `SYSTEM:{MODULE}:{BUSINESS}:{ACTION}` 格式
- **数据权限**: 通过组织架构和角色进行控制
- **API权限**: 使用 `@PreAuthorize("hasAuthority('权限编码')")` 注解

**权限编码示例：**
- `SYSTEM:BASIC_DATA:TAG:CREATE` - 创建智能标签
- `SYSTEM:BASIC_DATA:TAG:UPDATE` - 修改智能标签
- `SYSTEM:BASIC_DATA:TAG:DELETE` - 删除智能标签
- `SYSTEM:BASIC_DATA:TAG:DETAIL` - 查看智能标签详情

### 🌐 上下文管理

- **用户上下文**: 通过 `AppContext.getContext().getUserId()` 获取当前用户ID
- **请求追踪**: 使用 `traceId` 进行请求链路追踪
- **Feign调用**: 自动传递用户ID和traceId到下游服务
- **跳过检查**: 使用 `@SkipUserIdCheck` 注解跳过用户ID验证