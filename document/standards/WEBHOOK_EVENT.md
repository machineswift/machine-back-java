# 🔗 Webhook事件推送规范

## 📋 概述

Webhook是一种HTTP回调机制，当系统中发生特定事件时，会自动向配置的URL发送HTTP POST请求，实现系统间的实时数据同步。

## 🚀 快速开始

### 📡 服务端开发

#### 请求方式
```http
POST
```

#### 请求地址
```text
{您的应用服务地址}/machine/web_hook/call_back
```

#### 请求参数

| 字段          | 名称   | 是否必须 | 类型     | 说明              |
|-------------|------|------|--------|-----------------|
| `eventType` | 事件编码 | ✅    | String | 事件类型，参考事件编码表    |
| `eventTime` | 事件时间 | ✅    | Long   | 事件发生时间（Unix时间戳） |
| `messageId` | 消息ID | ✅    | String | 消息唯一标识          |
| `traceId`   | 追踪ID | ✅    | String | 请求链路追踪ID        |
| `data`      | 消息体  | ✅    | Object | 事件相关数据          |

#### 请求示例

```json
{
    "eventType": "IAM_ORGANIZATION_CREATE",
    "eventTime": 1699783200000,
    "messageId": "0043e38nhf734a44b6ii3cee0ccctgfr",
    "traceId": "0043e38f96734a44b6ii3cee0cccf5a5",
    "data": {
        "id": "7ec2daf9dcd44115adfde63c78add018"
    }
}
```

#### 响应参数

| 字段          | 名称   | 是否必须 | 类型      | 说明            |
|-------------|------|------|---------|---------------|
| `status`    | 状态码  | ✅    | Integer | HTTP状态码       |
| `code`      | 编码   | ✅    | String  | 业务状态码         |
| `message`   | 信息   | ❌    | String  | 响应消息          |
| `timestamp` | 时间戳  | ✅    | Long    | 响应时间（Unix时间戳） |
| `traceId`   | 追踪ID | ✅    | String  | 请求追踪ID        |

#### 响应示例

```json
{
    "status": 200,
    "code": "SUCCESS",
    "message": "操作成功",
    "timestamp": 1733216514506,
    "traceId": "0043e38f96734a44b6ii3cee0cccf5a5"
}
```

## 🔧 技术实现

### 📦 Java实现示例

#### 控制器实现

```java
@Slf4j
@RestController
@RequestMapping("machine/web_hook")
public class WebHookController {

    private static final String SECRET = "enyAALFiSrgSH1odCXMS/XTJjbl2lRC1BWLSxFDYF0U=";

    @PostMapping("call_back")
    public WebHookResponseBody callBack(@RequestBody String encryptBody) {
        Long timestamp = System.currentTimeMillis();
        log.info("接收到WebHook回调数据, encryptBody={}", JSONUtil.toJsonStr(encryptBody));

        try {
            // 解密请求体
            String decrypt = AESUtil.decrypt(encryptBody, AESUtil.reconstructSecretKey(SECRET));
            WebHookEventRequestBody requestBody = JSONUtil.toBean(decrypt, WebHookEventRequestBody.class);

            // 处理业务逻辑
            processWebHookEvent(requestBody);

            // 构建响应
        WebHookResponseBody responseBody = new WebHookResponseBody();
        responseBody.setStatus(HttpStatus.OK.value());
        responseBody.setCode("SUCCESS");
        responseBody.setTimestamp(timestamp);
        responseBody.setTraceId(requestBody.getTraceId());

            log.info("成功处理WebHook回调数据, body={}", JSONUtil.toJsonStr(requestBody));
        return responseBody;

        } catch (Exception e) {
            log.error("处理WebHook回调失败", e);
            throw new BusinessException("WEBHOOK_PROCESS_ERROR", "处理WebHook回调失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void processWebHookEvent(WebHookEventRequestBody requestBody) {
        // 根据事件类型处理不同的业务逻辑
        switch (requestBody.getEventType()) {
            case IAM_ORGANIZATION_CREATE:
                handleOrganizationCreate(requestBody);
                break;
            case IAM_USER_CREATE:
                handleUserCreate(requestBody);
                break;
            // 其他事件处理...
            default:
                log.warn("未知的事件类型: {}", requestBody.getEventType());
        }
    }
}
```

#### 请求参数类

```java
@Data
@Schema
@NoArgsConstructor
public class WebHookEventRequestBody<T> {

    private String clientId;

    @NotNull(message = "事件类型不能为空")
    @Schema(description = "事件类型")
    private EventTypeEnum eventType;

    @NotNull(message = "事件发生的时间不能为空")
    @Schema(description = "事件发生的时间")
    private Long eventTime;

    @NotBlank(message = "消息Id不能为空")
    @Schema(description = "消息Id")
    private String messageId;

    @NotBlank(message = "traceId不能为空")
    @Schema(description = "traceId")
    private String traceId;

    @NotNull(message = "数据不能为空")
    @Schema(description = "数据")
    private T data;
}
```

#### 响应参数类

```java
@Data
@NoArgsConstructor
public class WebHookResponseBody {

    @Schema(description = "状态码")
    private Integer status;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "消息内容")
    private String message;

    @Schema(description = "接收到消息的时间")
    private Long timestamp;

    @Schema(description = "traceId")
    private String traceId;
}
```

### 🔐 加解密工具类

```java
public class AESUtil {

    private static final String AES = "AES";
    private static final String CHARSET = "UTF-8";

    /**
     * 生成密钥
     */
    @SneakyThrows
    public static String generateSecretKey(String secret) {
        SecureRandom secureRandom = new SecureRandom(secret.getBytes(CHARSET));
        KeyGenerator kg = KeyGenerator.getInstance(AES);
        kg.init(256, secureRandom);
        return Base64.encode(kg.generateKey().getEncoded());
    }

    /**
     * 反向生成SecretKey
     */
    @SneakyThrows
    public static SecretKey reconstructSecretKey(String encodedBase64Key) {
        byte[] decodedKey = Base64.decode(encodedBase64Key);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, AES);
    }

    /**
     * 加密
     */
    @SneakyThrows
    public static String encrypt(String data, SecretKey key) {
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(data.getBytes(CHARSET));
        return Base64.encode(encryptedData);
    }

    /**
     * 解密
     */
    @SneakyThrows
    public static String decrypt(String encryptedData, SecretKey key) {
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedData = Base64.decode(encryptedData);
        byte[] decryptedData = cipher.doFinal(decodedData);
        return new String(decryptedData, CHARSET);
    }
}
```

## 📋 事件类型定义

### 🔐 IAM模块事件

#### 组织管理事件

| 事件名称    | 事件编码                                | 消息体             | 说明       |
|---------|-------------------------------------|-----------------|----------|
| 创建组织    | `IAM_ORGANIZATION_CREATE`           | `{"id":"组织ID"}` | 组织创建完成   |
| 删除组织    | `IAM_ORGANIZATION_DELETE`           | `{"id":"组织ID"}` | 组织删除完成   |
| 修改组织    | `IAM_ORGANIZATION_UPDATE`           | `{"id":"组织ID"}` | 组织信息修改   |
| 修改组织父ID | `IAM_ORGANIZATION_UPDATE_PARENT_ID` | `{"id":"组织ID"}` | 组织层级关系变更 |

#### 权限管理事件

| 事件名称   | 事件编码                           | 消息体                               | 说明     |
|--------|--------------------------------|-----------------------------------|--------|
| 创建权限   | `IAM_PERMISSION_CREATE`        | `{"id":"权限ID"}`                   | 权限创建完成 |
| 删除权限   | `IAM_PERMISSION_DELETE`        | `{"id":"权限ID"}`                   | 权限删除完成 |
| 修改权限   | `IAM_PERMISSION_UPDATE`        | `{"id":"权限ID"}`                   | 权限信息修改 |
| 修改权限状态 | `IAM_PERMISSION_UPDATE_STATUS` | `{"id":"权限ID","status":"ENABLE"}` | 权限状态变更 |

#### 角色管理事件

| 事件名称   | 事件编码                     | 消息体                               | 说明     |
|--------|--------------------------|-----------------------------------|--------|
| 创建角色   | `IAM_ROLE_CREATE`        | `{"id":"角色ID"}`                   | 角色创建完成 |
| 删除角色   | `IAM_ROLE_DELETE`        | `{"id":"角色ID"}`                   | 角色删除完成 |
| 修改角色   | `IAM_ROLE_UPDATE`        | `{"id":"角色ID"}`                   | 角色信息修改 |
| 修改角色状态 | `IAM_ROLE_UPDATE_STATUS` | `{"id":"角色ID","status":"ENABLE"}` | 角色状态变更 |

#### 用户管理事件

| 事件名称     | 事件编码                       | 消息体                               | 说明       |
|----------|----------------------------|-----------------------------------|----------|
| 创建用户     | `IAM_USER_CREATE`          | `{"id":"用户ID"}`                   | 用户创建完成   |
| 删除用户     | `IAM_USER_DELETE`          | `{"id":"用户ID"}`                   | 用户删除完成   |
| 修改用户手机号  | `IAM_USER_UPDATE_PHONE`    | `{"id":"用户ID","phone":"手机号"}`     | 用户手机号变更  |
| 修改用户密码   | `IAM_USER_UPDATE_PASSWORD` | `{"id":"用户ID"}`                   | 用户密码修改   |
| 修改用户基础信息 | `IAM_USER_UPDATE_BASE`     | `{"id":"用户ID"}`                   | 用户基础信息修改 |
| 修改用户角色信息 | `IAM_USER_UPDATE_ROLE`     | `{"id":"用户ID"}`                   | 用户角色关系变更 |
| 用户状态变更   | `IAM_USER_UPDATE_STATUS`   | `{"id":"用户ID","status":"ENABLE"}` | 用户状态变更   |

### 📊 DATA模块事件

#### 公司员工事件

| 事件名称       | 事件编码                                  | 消息体                                          | 说明       |
|------------|---------------------------------------|----------------------------------------------|----------|
| 公司员工创建     | `DATA_COMPANY_EMPLOYEE_CREATE`        | `{"id":"员工ID"}`                              | 公司员工创建完成 |
| 公司员工删除     | `DATA_COMPANY_EMPLOYEE_DELETE`        | `{"id":"员工ID"}`                              | 公司员工删除完成 |
| 公司员工修改基本信息 | `DATA_COMPANY_EMPLOYEE_UPDATE_BASE`   | `{"id":"员工ID"}`                              | 员工基本信息修改 |
| 公司员工修改状态   | `DATA_COMPANY_EMPLOYEE_UPDATE_STATUS` | `{"id":"员工ID","employeeStatus":"FULL_TIME"}` | 员工状态变更   |

#### 门店员工事件

| 事件名称       | 事件编码                                           | 消息体                                          | 说明        |
|------------|------------------------------------------------|----------------------------------------------|-----------|
| 门店员工创建     | `DATA_SHOP_EMPLOYEE_CREATE`                    | `{"id":"员工ID"}`                              | 门店员工创建完成  |
| 门店员工删除     | `DATA_SHOP_EMPLOYEE_DELETE`                    | `{"id":"员工ID"}`                              | 门店员工删除完成  |
| 修改门店员工身份证  | `DATA_SHOP_EMPLOYEE_UPDATE_IDENTITY_CARD`      | `{"id":"员工ID"}`                              | 员工身份证信息修改 |
| 修改门店员工健康证  | `DATA_SHOP_EMPLOYEE_UPDATE_HEALTH_CERTIFICATE` | `{"id":"员工ID"}`                              | 员工健康证信息修改 |
| 门店员工修改基础信息 | `DATA_SHOP_EMPLOYEE_UPDATE_BASE`               | `{"id":"员工ID"}`                              | 员工基础信息修改  |
| 门店员工修改状态   | `DATA_SHOP_EMPLOYEE_UPDATE_STATUS`             | `{"id":"员工ID","employeeStatus":"FULL_TIME"}` | 员工状态变更    |

#### 加盟商事件

| 事件名称      | 事件编码                                   | 消息体                                | 说明         |
|-----------|----------------------------------------|------------------------------------|------------|
| 创建加盟商     | `DATA_FRANCHISEE_CREATE`               | `{"id":"加盟商ID"}`                   | 加盟商创建完成    |
| 加盟商删除     | `DATA_FRANCHISEE_DELETE`               | `{"id":"加盟商ID"}`                   | 加盟商删除完成    |
| 修改加盟商身份证  | `DATA_FRANCHISEE_UPDATE_IDENTITY_CARD` | `{"id":"加盟商ID"}`                   | 加盟商身份证信息修改 |
| 修改加盟商基础信息 | `DATA_FRANCHISEE_UPDATE_BASE`          | `{"id":"加盟商ID"}`                   | 加盟商基础信息修改  |
| 加盟商绑定门店   | `DATA_FRANCHISEE_BIND_SHOP`            | `{"id":"加盟商ID","shopCode":"门店编码"}` | 加盟商与门店绑定   |
| 加盟商解绑门店   | `DATA_FRANCHISEE_UNBIND_SHOP`          | `{"id":"加盟商ID","shopCode":"门店编码"}` | 加盟商与门店解绑   |
| 加盟商修改状态   | `DATA_FRANCHISEE_UPDATE_STATUS`        | `{"id":"加盟商ID","status":"ENABLE"}` | 加盟商状态变更    |

#### 供应商事件

| 事件名称      | 事件编码                                    | 消息体                                | 说明        |
|-----------|-----------------------------------------|------------------------------------|-----------|
| 创建供应商     | `DATA_SUPPLIER_CREATE`                  | `{"id":"供应商ID"}`                   | 供应商创建完成   |
| 删除供应商     | `DATA_SUPPLIER_DELETE`                  | `{"id":"供应商ID"}`                   | 供应商删除完成   |
| 修改供应商基础信息 | `DATA_SUPPLIER_UPDATE_BASE`             | `{"id":"供应商ID"}`                   | 供应商基础信息修改 |
| 修改供应商关联公司 | `DATA_SUPPLIER_UPDATE_SUPPLIER_COMPANY` | `{"id":"供应商ID"}`                   | 供应商关联公司修改 |
| 修改供应商状态   | `DATA_SUPPLIER_UPDATE_STATUS`           | `{"id":"供应商ID","status":"ENABLE"}` | 供应商状态变更   |

#### 门店事件

| 事件名称     | 事件编码                                | 消息体                                      | 说明       |
|----------|-------------------------------------|------------------------------------------|----------|
| 创建门店     | `DATA_SHOP_CREATE`                  | `{"id":"门店ID"}`                          | 门店创建完成   |
| 删除门店     | `DATA_SHOP_DELETE`                  | `{"id":"门店ID"}`                          | 门店删除完成   |
| 修改门店     | `DATA_SHOP_UPDATE`                  | `{"id":"门店ID"}`                          | 门店信息修改   |
| 修改门店经营状态 | `DATA_SHOP_UPDATE_BUSINESS_STATUS`  | `{"id":"门店ID","businessStatus":"营业状态"}`  | 门店经营状态变更 |
| 修改门店运营状态 | `DATA_SHOP_UPDATE_OPERATION_STATUS` | `{"id":"门店ID","operationStatus":"运营状态"}` | 门店运营状态变更 |
| 修改门店物理状态 | `DATA_SHOP_UPDATE_PHYSICAL_STATUS`  | `{"id":"门店ID","physicalStatus":"物理状态"}`  | 门店物理状态变更 |

## ⚙️ 重试机制

### 🔄 重试策略

当Webhook回调失败时，系统会按照以下时间间隔进行重试：

| 重试次数 | 时间间隔 | 说明    |
|------|------|-------|
| 第1次  | 5秒   | 立即重试  |
| 第2次  | 10秒  | 短期重试  |
| 第3次  | 30秒  | 中期重试  |
| 第4次  | 5分钟  | 长期重试  |
| 第5次  | 30分钟 | 超长期重试 |
| 第6次  | 1小时  | 最终重试  |

### ✅ 成功判断标准

只有同时满足以下条件才认为回调成功：
1. HTTP状态码为200
2. 响应体中的status字段为200
3. 响应体中的code字段为"SUCCESS"

### ❌ 失败处理

如果所有重试都失败，系统会：
1. 记录失败日志
2. 停止重试
3. 可能需要人工介入处理

## 🚀 最佳实践

### ✅ 推荐做法

1. **幂等性处理**: 确保重复接收相同事件不会产生副作用
2. **快速响应**: 在5秒内返回响应，避免超时
3. **异常处理**: 妥善处理各种异常情况
4. **日志记录**: 记录关键操作和异常信息
5. **数据验证**: 验证接收到的数据完整性

### ❌ 避免做法

1. **长时间处理**: 避免在回调中执行耗时操作
2. **阻塞操作**: 避免同步等待外部服务响应
3. **忽略异常**: 不要忽略处理过程中的异常
4. **敏感信息**: 不要在日志中记录敏感信息
5. **硬编码**: 避免硬编码配置信息