# ⚠️ 异常规范文档

## 📋 HTTP 状态码分类

| 分类      | 区间        | 分类描述                    | 使用场景      |
|---------|-----------|-------------------------|-----------|
| **1xx** | 100 ~ 199 | 信息，服务器收到请求，需要请求者继续执行操作  | 临时响应，很少使用 |
| **2xx** | 200 ~ 299 | 成功，操作被成功接收并处理           | 正常业务处理成功  |
| **3xx** | 300 ~ 399 | 重定向，需要进一步的操作以完成请求       | 资源重定向     |
| **4xx** | 400 ~ 499 | 客户端错误，请求包含语法错误或无法完成请求   | 客户端请求错误   |
| **5xx** | 500 ~ 599 | 服务器错误，服务器在处理请求的过程中发生了错误 | 服务器内部错误   |

## 🎯 常用状态码规范

### ✅ 成功状态码 (2xx)

| 状态码     | 含义         | 使用场景       |
|---------|------------|------------|
| **200** | OK         | 请求成功，返回数据  |
| **201** | Created    | 资源创建成功     |
| **204** | No Content | 请求成功，无返回内容 |

### ❌ 客户端错误 (4xx)

| 状态码     | 含义                   | 使用场景           |
|---------|----------------------|----------------|
| **400** | Bad Request          | 请求参数错误、格式不正确   |
| **401** | Unauthorized         | 未认证、token无效或过期 |
| **403** | Forbidden            | 已认证但无权限访问      |
| **404** | Not Found            | 资源不存在          |
| **409** | Conflict             | 资源冲突，如重复创建     |
| **422** | Unprocessable Entity | 请求格式正确，但语义错误   |
| **429** | Too Many Requests    | 请求频率超限         |

### 🔥 服务器错误 (5xx)

| 状态码     | 含义                    | 使用场景    |
|---------|-----------------------|---------|
| **500** | Internal Server Error | 服务器内部错误 |
| **502** | Bad Gateway           | 网关错误    |
| **503** | Service Unavailable   | 服务不可用   |
| **504** | Gateway Timeout       | 网关超时    |

## 🏗️ 异常体系架构

### 📦 异常类层次结构

```
BusinessException (基础业务异常)
├── IamBusinessException (IAM业务异常)
│   ├── IamPermissionBusinessException (权限业务异常)
│   └── AuthException (认证异常)
│       ├── AuthTokenParsingException (Token解析错误)
│       ├── AuthTokenUseException (Token使用错误)
│       ├── AuthInterceptorUserIdException (拦截器用户ID异常)
│       ├── AuthFeignUserIdException (Feign用户ID异常)
│       ├── JwtTokenBlackException (JWT黑名单)
│       ├── RefreshTokenUseException (刷新Token异常)
│       └── UserStatusDisableException (用户状态禁用)
├── DataBusinessException (数据业务异常)
│   └── DataLeafBusinessException (数据叶子业务异常)
├── CrmBusinessException (CRM业务异常)
├── DocBusinessException (文档业务异常)
└── SdkBusinessException (SDK业务异常)
```

### 🔧 异常处理机制

#### 1. 全局异常处理器

```java
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常处理
     */
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = BusinessException.class)
    public AppResult<Objects> errorHandler(BusinessException exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail(exception.getCode(), exception.getMessage());
    }

    /**
     * 参数验证异常处理
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = MethodArgumentNotValidException.class)
    public AppResult<Objects> errorHandler(MethodArgumentNotValidException exception) {
        log.error(exception.getMessage(), exception);

        List<String> errorMessages = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        String combinedMessage = String.join(", ", errorMessages);

        return AppResult.fail("param.valid.exception", combinedMessage);
    }

    /**
     * 认证异常处理
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = AuthenticationException.class)
    public AppResult<Objects> errorHandler(AuthenticationException exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail("iam.auth.authentication", exception.getMessage());
    }

    /**
     * 权限异常处理
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = AccessDeniedException.class)
    public AppResult<Objects> errorHandler(AccessDeniedException exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail("iam.auth.accessDenied", "您没有权限执行此操作");
    }

    /**
     * 服务不可用异常处理
     */
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = FeignException.ServiceUnavailable.class)
    public AppResult<Objects> errorHandler(FeignException.ServiceUnavailable exception) {
        log.error(exception.getMessage(), exception);

        String serviceName = extractServiceName(exception.getMessage());
        String errorMessage = String.format("服务[%s]不可用", serviceName);
        return AppResult.fail("SERVICE_UNAVAILABLE", errorMessage);
    }

    /**
     * 通用异常处理
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public AppResult<Objects> errorHandler(Exception exception) {
        log.error(exception.getMessage(), exception);
        return AppResult.fail("exception", exception.getMessage());
    }
}
```

#### 2. 自定义异常类

```java
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {

    private String code;

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public BusinessException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
```

#### 3. 统一响应格式

```java
@Data
@Schema
@NoArgsConstructor
public class AppResult<T> {

    @Schema(description = "编码")
    private String code;

    @Schema(description = "消息内容")
    private String message;

    @Schema(description = "当前时间（UNIX 时间戳）")
    private Long timestamp;

    @Schema(description = "链路追踪Id")
    private String traceId;

    private T data;

    public static <T> AppResult<T> success(String message) {
        return success(message, null);
    }

    public static <T> AppResult<T> success(T data) {
        return success("操作成功", data);
    }

    public static <T> AppResult<T> fail(String code, String message) {
        return fail(code, message, null);
    }
}
```

## 📝 异常使用规范

### 🎯 异常抛出规范

1. **业务异常**: 使用具体的业务异常类
   ```java
   if (user == null) {
       throw new IamBusinessException("iam.user.service.create.phoneAlreadyExists", "手机号已经存在");
   }
   ```

2. **参数验证异常**: 使用 `@Valid` 和 `@Validated` 注解
   ```java
   @PostMapping("create")
   public IdResponse<String> create(@RequestBody @Validated UserCreateRequestVo request) {
       // 业务逻辑
   }
   ```

3. **权限异常**: 使用权限相关异常
   ```java
   if (!hasPermission(userId, permission)) {
       throw new IamPermissionBusinessException("iam.permission.data.permissionCodeWrong", "权限编码错误");
   }
   ```

4. **认证异常**: 使用认证相关异常
   ```java
       throw new AuthTokenParsingException("Token认证失败");
   ```

5. **服务调用异常**: 使用Feign异常
   ```java
   try {
       return userClient.getUserById(userId);
   } catch (FeignException.ServiceUnavailable e) {
       throw new BusinessException("SERVICE_UNAVAILABLE", "用户服务不可用");
   }
   ```

### 🔍 异常日志规范

1. **错误级别**: 使用 `log.error()` 记录异常
2. **日志内容**: 包含异常信息、用户ID、请求参数等关键信息
3. **敏感信息**: 避免记录密码等敏感信息


### 📊 异常响应格式

#### 业务异常响应
```json
{
    "code": "iam.user.service.create.phoneAlreadyExists",
    "message": "手机号已经存在",
    "timestamp": 1733216514506,
    "traceId": "0043e38f96734a44b6ii3cee0cccf5a5",
    "data": null
}
```

#### 参数验证异常响应
```json
{
    "code": "param.valid.exception",
    "message": "用户名不能为空, 密码长度不能少于6位",
    "timestamp": 1733216514506,
    "traceId": "0043e38f96734a44b6ii3cee0cccf5a5",
    "data": null
}
```

#### 认证异常响应
```json
{
    "code": "iam.auth.authentication.authTokenExpire",
    "message": "Token已过期",
    "timestamp": 1733216514506,
    "traceId": "0043e38f96734a44b6ii3cee0cccf5a5",
    "data": null
}
```

#### 权限异常响应
```json
{
    "code": "iam.auth.accessDenied",
    "message": "您没有权限执行此操作",
    "timestamp": 1733216514506,
    "traceId": "0043e38f96734a44b6ii3cee0cccf5a5",
    "data": null
}
```

#### 服务不可用异常响应
```json
{
    "code": "SERVICE_UNAVAILABLE",
    "message": "服务[machine-iam-service]不可用",
    "timestamp": 1733216514506,
    "traceId": "0043e38f96734a44b6ii3cee0cccf5a5",
    "data": null
}
```

## 📋 错误码规范

### 🎯 错误码命名规范

错误码采用分层命名规范，格式为：`{模块}.{子模块}.{功能}.{错误类型}`

| 层级 | 说明 | 示例 |
|------|------|------|
| **模块** | 业务模块标识 | `iam`、`data`、`hrm`、`crm`、`scm` |
| **子模块** | 子业务模块 | `user`、`role`、`permission`、`organization` |
| **功能** | 具体功能 | `create`、`update`、`delete`、`query` |
| **错误类型** | 错误类型 | `notFound`、`alreadyExists`、`invalidParam` |

### 📝 错误码示例

#### IAM模块错误码
```java
// 用户相关
"iam.user.service.create.phoneAlreadyExists"     // 手机号已存在
"iam.user.service.create.usernameAlreadyExists"  // 用户名已存在
"iam.user.service.query.userNotFound"            // 用户不存在
"iam.user.service.update.invalidStatus"          // 无效状态

// 角色相关
"iam.role.service.create.roleNameAlreadyExists"  // 角色名已存在
"iam.role.service.query.roleNotFound"            // 角色不存在
"iam.role.service.delete.roleInUse"              // 角色正在使用中

// 权限相关
"iam.permission.data.permissionCodeWrong"        // 权限编码错误
"iam.permission.service.create.permissionExists" // 权限已存在

// 认证相关
"iam.auth.authentication.authTokenExpire"        // Token过期
"iam.auth.authentication.authTokenParsingFailed" // Token解析失败
"iam.auth.authentication.authTokenUseWrong"      // Token使用错误
"iam.auth.authentication.refreshTokenUseWrong"   // 刷新Token错误
"iam.auth.authentication.jwtTokenBlack"          // JWT黑名单
"iam.auth.authentication.userStatusDisable"      // 用户状态禁用
"iam.auth.accessDenied"                          // 权限不足
"iam.auth.accessDenied.openApiResourceBlack"     // OpenAPI资源黑名单
"iam.auth.accessDenied.wrongClientId"            // 错误的客户端ID
```

#### 通用错误码
```java
"param.valid.exception"                          // 参数验证异常
"SERVICE_UNAVAILABLE"                            // 服务不可用
"exception"                                      // 通用异常
```

### 🔧 错误码使用规范

1. **错误码唯一性**: 每个错误码在系统中必须唯一
2. **错误码稳定性**: 错误码一旦确定，不应随意修改
3. **错误码可读性**: 错误码应能清晰表达错误含义
4. **错误码层次性**: 按照模块、子模块、功能、错误类型分层组织

## 🚀 最佳实践

### ✅ 推荐做法

1. **使用具体异常类**: 根据业务模块使用对应的异常类
2. **提供有意义的错误码**: 使用清晰的错误码便于前端处理
3. **记录详细日志**: 包含足够的上下文信息用于问题排查
4. **统一异常格式**: 保持异常响应格式的一致性
5. **异常链传递**: 保留原始异常信息用于调试

### ❌ 避免做法

1. **不要捕获所有异常**: 避免使用 `catch (Exception e)`
2. **不要忽略异常**: 不要使用空的 catch 块
3. **不要暴露敏感信息**: 避免在异常信息中暴露系统内部信息
4. **不要过度使用异常**: 异常应该用于异常情况，不是正常业务流程
5. **不要使用通用异常**: 避免过度使用 `RuntimeException`
