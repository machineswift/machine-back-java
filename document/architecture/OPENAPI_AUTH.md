# 🔐 OAuth2开放平台认证规范

## 📋 概述

OAuth2是一种开放标准的授权协议，允许第三方应用在用户授权后访问用户资源，而无需获取用户的用户名和密码。本平台采用OAuth2 Client Credentials模式，适用于服务端到服务端的API调用。

## 🚀 快速开始

### 1️⃣ 注册客户端

#### 请求信息

**请求方式**: `POST`  
**请求地址**: `{{http_prefix}}/machine-iam-app/iam/auth2/create_client`

#### 请求参数

| 字段             | 名称    | 是否必须 | 类型     | 说明          |
|----------------|-------|------|--------|-------------|
| `clientId`     | 客户端ID | ✅    | String | 客户端唯一标识     |
| `clientName`   | 客户端名称 | ✅    | String | 客户端显示名称     |
| `password`     | 客户端密码 | ✅    | String | 客户端密钥       |
| `scopes`       | 授权范围  | ✅    | Array  | 允许访问的权限范围   |
| `redirectUrls` | 重定向地址 | ✅    | Array  | 授权成功后的重定向地址 |

#### 请求示例

```json
{
    "clientId": "APP2024120001",
    "clientName": "慧运营",
    "password": "hyynshdsbcjkcsbk&@#nbkhjksa",
    "scopes": [
        "iam",
        "data",
        "hrm"
    ],
    "redirectUrls": [
        "https://www.example.com/callback"
    ]
}
```

#### 响应示例

```json
{
    "status": 200,
    "code": "SUCCESS",
    "message": "操作成功",
    "timestamp": 1733732783127,
    "traceId": "1f6af7d1efd34246ba84741eb3ab092e",
    "data": {
        "id": "7ec2daf9dcd44115adfde63c78add018"
    }
}
```

### 2️⃣ 获取访问令牌

#### 请求信息

**请求方式**: `POST`  
**请求地址**: `{{http_prefix}}/machine-iam-app/iam/oauth2/authServer/token`

#### 请求参数

| 字段           | 名称   | 是否必须 | 类型     | 说明                       |
|--------------|------|------|--------|--------------------------|
| `grant_type` | 授权类型 | ✅    | String | 固定值：`client_credentials` |
| `scope`      | 作用域  | ✅    | String | 权限范围，多个用空格分隔             |

#### 认证方式

使用HTTP Basic认证，将客户端ID和密码进行Base64编码：

```bash
# 组合格式：clientId:password
# 示例：APP2024120001:hyynshdsbcjkcsbk&@#nbkhjksa
# Base64编码：QVBQMjAyNDEyMDAwMTpoeXluc2hkc2Jjamtjc2JrJkAjbmJraGprc2E=
```

#### 请求示例

```bash
curl --location --request POST 'http://localhost:8080/machine-iam-app/iam/oauth2/authServer/token' \
--header 'Authorization: Basic QVBQMjAyNDEyMDAwMTpoeXluc2hkc2Jjamtjc2JrJkAjbmJraGprc2E=' \
--data-urlencode 'grant_type=client_credentials' \
--data-urlencode 'scope=iam data hrm'
```

#### 响应示例

```json
{
    "access_token": "eyJraWQiOiJjMDJmMTk1Ni1jMDI5",
    "scope": "hrm iam data",
    "token_type": "Bearer",
    "expires_in": 312599
}
```

### 3️⃣ 调用API接口

#### 请求头设置

```http
Authorization: Bearer {access_token}
```

#### 请求示例

```bash
curl --location --request GET 'https://app-gateway-dev.machine888.com/machine-openapi-app/openapi/iam/user/detail' \
--header 'Authorization: Bearer eyJraWQiOiJjMDJmMTk1Ni1jMDI5'
```

## 🔧 技术实现

### 📦 Java客户端实现

#### 1. 令牌获取工具类

```java
@Component
@Slf4j
public class OAuth2TokenManager {

    @Value("${oauth2.client-id}")
    private String clientId;

    @Value("${oauth2.client-secret}")
    private String clientSecret;

    @Value("${oauth2.token-url}")
    private String tokenUrl;

    @Value("${oauth2.scope}")
    private String scope;

    private String accessToken;
    private long tokenExpireTime;

    /**
     * 获取访问令牌
     */
    public String getAccessToken() {
        if (isTokenExpired()) {
            refreshToken();
        }
        return accessToken;
    }

    /**
     * 刷新令牌
     */
    private void refreshToken() {
        try {
            String credentials = clientId + ":" + clientSecret;
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Basic " + encodedCredentials);
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "client_credentials");
            body.add("scope", scope);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<OAuth2TokenResponse> response = restTemplate.postForEntity(
                tokenUrl, request, OAuth2TokenResponse.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                OAuth2TokenResponse tokenResponse = response.getBody();
                this.accessToken = tokenResponse.getAccessToken();
                this.tokenExpireTime = System.currentTimeMillis() + (tokenResponse.getExpiresIn() * 1000);
                
                log.info("OAuth2令牌刷新成功，过期时间: {}", new Date(tokenExpireTime));
            } else {
                throw new RuntimeException("获取OAuth2令牌失败");
            }

        } catch (Exception e) {
            log.error("刷新OAuth2令牌失败", e);
            throw new RuntimeException("刷新OAuth2令牌失败", e);
        }
    }

    /**
     * 检查令牌是否过期
     */
    private boolean isTokenExpired() {
        return accessToken == null || System.currentTimeMillis() >= tokenExpireTime - 60000; // 提前1分钟刷新
    }
}
```

#### 2. 令牌响应类

```java
@Data
public class OAuth2TokenResponse {
    private String accessToken;
    private String scope;
    private String tokenType;
    private Long expiresIn;
}
```

#### 3. API调用拦截器

```java
@Component
public class OAuth2RequestInterceptor implements RequestInterceptor {

    @Autowired
    private OAuth2TokenManager tokenManager;

    @Override
    public void apply(RequestTemplate template) {
        String accessToken = tokenManager.getAccessToken();
        template.header("Authorization", "Bearer " + accessToken);
    }
}
```

#### 4. Feign客户端配置

```java
@Configuration
public class OAuth2FeignConfig {

    @Bean
    public RequestInterceptor oauth2RequestInterceptor() {
        return new OAuth2RequestInterceptor();
    }
}
```

#### 5. API客户端使用

```java
@FeignClient(
    name = "machine-openapi",
    url = "https://app-gateway-dev.machine888.com/machine-openapi-app",
    configuration = OAuth2FeignConfig.class
)
public interface MachineOpenApiClient {

    @GetMapping("/openapi/iam/user/detail")
    UserDetailResponse getUserDetail(@RequestParam("userId") String userId);

    @PostMapping("/openapi/data/shop/list")
    PageResponse<ShopListResponse> getShopList(@RequestBody ShopQueryRequest request);
}
```

### 🔧 配置示例

#### application.yml配置

```yaml
# OAuth2配置
oauth2:
  client-id: APP2024120001
  client-secret: hyynshdsbcjkcsbk&@#nbkhjksa
  token-url: http://localhost:8080/machine-iam-app/iam/oauth2/authServer/token
  scope: iam data hrm
  # 令牌刷新提前时间（秒）
  refresh-ahead-time: 60

# API网关配置
api:
  gateway:
    base-url: https://app-gateway-dev.machine888.com
    timeout: 30000
    retry-times: 3
```

## 📋 权限范围说明

### 🔐 权限范围定义

| 权限范围   | 说明      | 可访问的API          |
|--------|---------|------------------|
| `iam`  | 身份认证与授权 | 用户、角色、权限、组织相关API |
| `data` | 数据管理    | 门店、标签、素材、附件相关API |
| `hrm`  | 人力资源    | 员工、部门相关API       |
| `crm`  | 客户关系管理  | 客户、会员相关API       |
| `scm`  | 供应链管理   | 供应商、采购相关API      |

### 🎯 权限验证

系统会根据客户端注册时指定的权限范围，验证API调用的权限：

```java
@PreAuthorize("hasAuthority('SCOPE_iam')")
@GetMapping("/openapi/iam/user/detail")
public UserDetailResponse getUserDetail(@RequestParam("userId") String userId) {
    // 需要iam权限范围
}
```

## 🔄 令牌管理

### ⏰ 令牌生命周期

- **有效期**: 默认8小时（28800秒）
- **刷新策略**: 提前1分钟自动刷新
- **存储方式**: 内存缓存（生产环境建议使用Redis）

### 🔄 自动刷新机制

```java
@Scheduled(fixedDelay = 300000) // 每5分钟检查一次
public void checkTokenExpiry() {
    if (isTokenExpired()) {
        refreshToken();
    }
}
```

## 🚀 最佳实践

### ✅ 推荐做法

1. **安全存储**: 客户端密钥应安全存储，不要硬编码
2. **令牌缓存**: 合理缓存访问令牌，避免频繁请求
3. **错误处理**: 妥善处理令牌获取和API调用异常
4. **日志记录**: 记录关键操作，便于问题排查
5. **权限最小化**: 只申请必要的权限范围

### ❌ 避免做法

1. **明文传输**: 不要在日志中记录敏感信息
2. **频繁刷新**: 避免不必要的令牌刷新
3. **忽略异常**: 不要忽略令牌获取失败的情况
4. **硬编码配置**: 避免在代码中硬编码配置信息
5. **权限过度**: 不要申请超出实际需要的权限

## 🔧 故障排查

### 常见问题

#### 1. 令牌获取失败

**错误信息**: `401 Unauthorized`

**可能原因**:
- 客户端ID或密码错误
- 客户端未注册或已禁用
- 权限范围不匹配

**解决方案**:
```bash
# 检查客户端信息
curl -X POST "http://localhost:8080/machine-iam-app/iam/auth2/create_client" \
-H "Content-Type: application/json" \
-d '{
    "clientId": "APP2024120001",
    "clientName": "测试客户端",
    "password": "your_password",
    "scopes": ["iam", "data"],
    "redirectUrls": ["https://example.com"]
}'
```

#### 2. API调用失败

**错误信息**: `403 Forbidden`

**可能原因**:
- 访问令牌过期
- 权限范围不足
- API路径错误

**解决方案**:
```bash
# 检查令牌有效性
curl -X GET "https://app-gateway-dev.machine888.com/machine-openapi-app/openapi/iam/user/detail?userId=123" \
-H "Authorization: Bearer your_access_token"
```

#### 3. 网络连接问题

**错误信息**: `Connection timeout`

**可能原因**:
- 网络连接不稳定
- 服务器不可达
- 防火墙阻拦

**解决方案**:
```bash
# 检查网络连通性
ping app-gateway-dev.machine888.com

# 检查端口连通性
telnet app-gateway-dev.machine888.com 443
```