# 开发指南

## 开发 HTTP 推送服务端

### 请求方式

```plaintext
post
```

### 请求地址

```plaintext
对应应用方提供服务地址
```

### 请求参数

| 字段        | 名称      | 是否必须传 | 备注       |
|-----------|---------|-------|----------|
| eventType | 事件编码    | 是     | 参考下面的编码  |
| eventTime | 事件发生的时间 | 是     | UNIX 时间戳 |
| messageId | 消息Id    | 是     | 变更时生成    |
| traceId   | traceId | 是     | 用于跟踪数据   |
| data      | 消息体     | 是     | 参考下面的消息体 |

### demo:

```plaintext
{
    "eventType": "IAM_ORGANIZATION_CREATE",
    "eventTime": 1699783200000,
    "messageId": "0043e38nhf734a44b6ii3cee0ccctgfr",
    "traceId": "0043e38f96734a44b6ii3cee0cccf5a5",
    "data": {
        "roleId": "XXX"
    }
}
```

### 返回参数

| 字段        | 名称       | 是否必须返回 | 备注          |
|-----------|----------|--------|-------------|
| status    | 状态码      | 是      | HTTP 状态码    |
| code      | 编码       | 是      | 编码          |
| message   | 信息       | 否      | 消息内容        |
| timestamp | 接收到消息的时间 | 是      | UNIX 时间戳    |
| traceId   | traceId  | 是      | 取传入的traceId |

### 注意:

```plaintext
1.只有HTTP的状态码和返回参数里面的status都是200时才能说明本次请求成功。
2.请求失败时会重试，重试机制：5 10 30 300 1800 3600秒，还是不成功则不在重试。
```

### demo:

```plaintext
{
    "status": 200,
    "code": "SUCCESS",
    "message": "操作成功",
    "timestamp": 1733216514506,
    "traceId": "0043e38f96734a44b6ii3cee0cccf5a5"
}
```

## Java 代码

### 接口

```java
import cn.hutool.json.JSONUtil;
import com.machine.sdk.common.tool.AESUtil;
import com.machine.sdk.self.domain.WebHookEventRequestBody;
import com.machine.sdk.self.domain.WebHookResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("machine/web_hook")
public class CallBackController {

    private static final String secret = "enyAALFiSrgSH1odCXMS/XTJjbl2lRC1BWLSxFDYF0U=";

    @PostMapping("call_back")
    public <T> WebHookResponseBody callBack(@RequestBody String encryptBody) {
        Long timestamp = System.currentTimeMillis();
        log.info("应用接收到WebHook回调数据,encryptBody={}", JSONUtil.toJsonStr(encryptBody));

        String decrypt = AESUtil.decrypt(encryptBody, AESUtil.reconstructSecretKey(secret));
        var requestBody = JSONUtil.toBean(decrypt, WebHookEventRequestBody.class);

        /*
         * 处理业务逻辑 XXXX
         */

        WebHookResponseBody responseBody = new WebHookResponseBody();
        responseBody.setStatus(HttpStatus.OK.value());
        responseBody.setCode("SUCCESS");
        responseBody.setTimestamp(timestamp);
        responseBody.setTraceId(requestBody.getTraceId());

        log.info("应用成功处理WebHook回调数据,body={}", JSONUtil.toJsonStr(requestBody));
        return responseBody;
    }
}
```

### 请求参数

```java
import cn.hutool.core.util.StrUtil;
import com.machine.sdk.self.envm.EventTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;

import java.util.UUID;

@Data
@Schema
@NoArgsConstructor
public class WebHookEventRequestBody<T> {

    private String clientId;

    @NotNull(message = "事件类型不能为空")
    @Schema(description = "事件类型（EventTypeEnum）")
    private EventTypeEnum eventType;

    @NotNull(message = "事件发生的时间不能为空")
    @Schema(description = "事件发生的时间")
    private Long eventTime;

    @NotBlank(message = "消息Id不能为空")
    @Schema(description = "消息Id（变更时生成）")
    private String messageId;

    @NotBlank(message = "traceId不能为空")
    @Schema(description = "traceId")
    private String traceId;

    @NotNull(message = "数据不能为空")
    @Schema(description = "数据")
    private T data;
}
```

### 响应参数参数

```java
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WebHookResponseBody {

    @Schema(description = "状态码 (HttpStatus)")
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

### 加解密工具类

```java
import cn.hutool.core.codec.Base64;
import lombok.SneakyThrows;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

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

# 事件

## 【IAM】组织

| 事件名称   | 事件编码                              | 消息体           |
|--------|-----------------------------------|---------------|
| 创建组织   | IAM_ORGANIZATION_CREATE           | {"id":"XXXX"} |
| 删除组织   | IAM_ORGANIZATION_DELETE           | {"id":"XXXX"} |
| 修改组织   | IAM_ORGANIZATION_UPDATE           | {"id":"XXXX"} |
| 修改组织ID | IAM_ORGANIZATION_UPDATE_PARENT_ID | {"id":"XXXX"} |


## 【IAM】权限

| 事件名称   | 事件编码                         | 消息体                                                    |
|--------|------------------------------|--------------------------------------------------------|
| 创建权限   | IAM_PERMISSION_CREATE        | {"id":"XXXX"}                                          |
| 删除权限   | IAM_PERMISSION_DELETE        | {"id":"XXXX"}                                          |
| 修改权限   | IAM_PERMISSION_UPDATE        | {"id":"XXXX"}                                          |
| 修改权限状态 | IAM_PERMISSION_UPDATE_STATUS | {"id":"XXXX","status":"ENABLE"} <br/> (枚举类：StatusEnum) |

## 【IAM】角色

| 事件名称   | 事件编码                   | 消息体                                                    |
|--------|------------------------|--------------------------------------------------------|
| 创建角色   | IAM_ROLE_CREATE        | {"id":"XXXX"}                                          |
| 删除角色   | IAM_ROLE_DELETE        | {"id":"XXXX"}                                          |
| 修改角色   | IAM_ROLE_UPDATE        | {"id":"XXXX"}                                          |
| 修改角色状态 | IAM_ROLE_UPDATE_STATUS | {"id":"XXXX","status":"ENABLE"}<br/>  (枚举类：StatusEnum) |

## 【IAM】用户

| 事件名称     | 事件编码                     | 消息体                                                    |
|----------|--------------------------|--------------------------------------------------------|
| 创建用户     | IAM_USER_CREATE          | {"id":"XXXX"}                                          |
| 删除用户     | IAM_USER_DELETE          | {"id":"XXXX"}                                          |
| 修改用户手机号  | IAM_USER_UPDATE_PHONE    | {"id":"XXXX","phone":"XXX"}                            |
| 修改用户密码   | IAM_USER_UPDATE_PASSWORD | {"id":"XXXX"}                                          |
| 修改用户基础信息 | IAM_USER_UPDATE_BASE     | {"id":"XXXX"}                                          |
| 修改用户角色信息 | IAM_USER_UPDATE_ROLE     | {"id":"XXXX"}                                          |
| 用户修改状态   | IAM_USER_UPDATE_STATUS   | {"id":"XXXX","status":"ENABLE"} <br/> (枚举类：StatusEnum) |

## 【DATA】公司员工

| 事件名称       | 事件编码                                | 消息体                                                                              |
|------------|-------------------------------------|----------------------------------------------------------------------------------|
| 公司员工创建     | DATA_COMPANY_EMPLOYEE_CREATE        | {"id":"XXXX"}                                                                    |
| 公司员工删除     | DATA_COMPANY_EMPLOYEE_DELETE        | {"id":"XXXX"}                                                                    |
| 公司员工修改基本信息 | DATA_COMPANY_EMPLOYEE_UPDATE_BASE   | {"id":"XXXX"}                                                                    |
| 公司员工修改状态   | DATA_COMPANY_EMPLOYEE_UPDATE_STATUS | {"id":"XXXX","employeeStatus":"FULL_TIME"} <br/> (枚举类：CompanyEmployeeStatusEnum) |


## 【DATA】门店员工

| 事件名称       | 事件编码                                         | 消息体                                                                           |
|------------|----------------------------------------------|-------------------------------------------------------------------------------|
| 门店员工创建     | DATA_SHOP_EMPLOYEE_CREATE                    | {"id":"XXXX"}                                                                 |
| 门店员工删除     | DATA_SHOP_EMPLOYEE_DELETE                    | {"id":"XXXX"}                                                                 |
| 修改门店员身份证   | DATA_SHOP_EMPLOYEE_UPDATE_IDENTITY_CARD      | {"id":"XXXX"}                                                                 |
| 修改门店员工健康证  | DATA_SHOP_EMPLOYEE_UPDATE_HEALTH_CERTIFICATE | {"id":"XXXX"}                                                                 |
| 门店员工修改基础信息 | DATA_SHOP_EMPLOYEE_UPDATE_BASE               | {"id":"XXXX"}                                                                 |
| 门店员工修改状态   | DATA_SHOP_EMPLOYEE_UPDATE_STATUS             | {"id":"XXXX","employeeStatus":"FULL_TIME"} <br/> (枚举类：ShopEmployeeStatusEnum) |

## 【DATA】加盟商

| 事件名称      | 事件编码                                 | 消息体                                                    |
|-----------|--------------------------------------|--------------------------------------------------------|
| 创建加盟商     | DATA_FRANCHISEE_CREATE               | {"id":"XXXX"}                                          |
| 加盟商删除     | DATA_FRANCHISEE_DELETE               | {"id":"XXXX"}                                          |
| 修改加盟商身份证  | DATA_FRANCHISEE_UPDATE_IDENTITY_CARD | {"id":"XXXX"}                                          |
| 修改加盟商基础信息 | DATA_FRANCHISEE_UPDATE_BASE          | {"id":"XXXX"}                                          |
| 加盟商绑定门店   | DATA_FRANCHISEE_BIND_SHOP            | {"id":"XXXX","shopCode":"XXX"}                         |
| 加盟商解绑门店   | DATA_FRANCHISEE_UNBIND_SHOP          | {"id":"XXXX","shopCode":"XXX"}                         |
| 加盟商修改状态   | DATA_FRANCHISEE_UPDATE_STATUS        | {"id":"XXXX","status":"ENABLE"} <br/> (枚举类：StatusEnum) |

## 【DATA】供应商

| 事件名称       | 事件编码                                  | 消息体                                                    |
|------------|---------------------------------------|--------------------------------------------------------|
| 创建供应商      | DATA_SUPPLIER_CREATE                  | {"id":"XXXX"}                                          |
| 删除供应商      | DATA_SUPPLIER_DELETE                  | {"id":"XXXX"}                                          |
| 修改供应商基础信息  | DATA_SUPPLIER_UPDATE_BASE             | {"id":"XXXX"}                                          |
| 修改供应商关联的公司 | DATA_SUPPLIER_UPDATE_SUPPLIER_COMPANY | {"id":"XXXX"}                                          |
| 修改供应商状态    | DATA_SUPPLIER_UPDATE_STATUS           | {"id":"XXXX","status":"ENABLE"} <br/> (枚举类：StatusEnum) |

## 【DATA】供应商公司

| 事件名称        | 事件编码                                | 消息体                                                    |
|-------------|-------------------------------------|--------------------------------------------------------|
| 创建供应商公司     | DATA_SUPPLIER_COMPANY_CREATE        | {"id":"XXXX"}                                          |
| 删除供应商公司     | DATA_SUPPLIER_COMPANY_DELETE        | {"id":"XXXX"}                                          |
| 修改供应商公司基础信息 | DATA_SUPPLIER_COMPANY_UPDATE_BASE   | {"id":"XXXX"}                                          |
| 修改供应商状态     | DATA_SUPPLIER_COMPANY_UPDATE_STATUS | {"id":"XXXX","status":"ENABLE"} <br/> (枚举类：StatusEnum) |

## 【DATA】门店标签

| 事件名称     | 事件编码                          | 消息体                                                    |
|----------|-------------------------------|--------------------------------------------------------|
| 创建门店标签   | DATA_SHOP_LABEL_CREATE        | {"id":"XXXX"}                                          |
| 删除门店标签   | DATA_SHOP_LABEL_DELETE        | {"id":"XXXX"}                                          |
| 修改门店标签   | DATA_SHOP_LABEL_UPDATE        | {"id":"XXXX"}                                          |
| 修改门店标签状态 | DATA_SHOP_LABEL_UPDATE_STATUS | {"id":"XXXX","status":"ENABLE"} <br/> (枚举类：StatusEnum) |

## 【DATA】门店

| 事件名称      | 事件编码                                      | 消息体                                                                           |
|-----------|-------------------------------------------|-------------------------------------------------------------------------------|
| 创建门店      | DATA_SHOP_CREATE                          | {"id":"XXXX"}                                                                 |
| 删除门店      | DATA_SHOP_DELETE                          | {"id":"XXXX"}                                                                 |
| 修改门店      | DATA_SHOP_UPDATE                          | {"id":"XXXX"}                                                                 |
| 修改门店经营状态  | DATA_SHOP_UPDATE_BUSINESS_STATUS          | {"id":"XXXX","businessStatus":"XXX"} <br/> (枚举类：DataShopBusinessStatusEnum)   |
| 修改门店运营状态  | DATA_SHOP_UPDATE_OPERATION_STATUS         | {"id":"XXXX","operationStatus":"XXX"} <br/> (枚举类：DataShopOperationStatusEnum) |
| 修改门店物理状态  | DATA_SHOP_UPDATE_PHYSICAL_STATUS          | {"id":"XXXX","physicalStatus":"XXX"} <br/> (枚举类：DataShopPhysicalStatusEnum)   |
| 修改门店营业执照  | DATA_SHOP_UPDATE_SHOP_BUSINESS_LICENSE    | {"id":"XXXX"}                                                                 |
| 修改食品经营许可证 | DATA_SHOP_UPDATE_FOOD_BUSINESS_LICENSE    | {"id":"XXXX"}                                                                 |
| 修改消杀合同    | DATA_SHOP_UPDATE_DISINFECTING_CONTRACT    | {"id":"XXXX"}                                                                 |
| 修改门头照     | DATA_SHOP_UPDATE_SHOP_FRONT_PHOTO         | {"id":"XXXX"}                                                                 |
| 修改门店关联标签  | DATA_SHOP_UPDATE_UPDATE_SHOP_LABEL        | {"id":"XXXX"}                                                                 |
| 修改门店关联组织  | DATA_SHOP_UPDATE_UPDATE_SHOP_ORGANIZATION | {"id":"XXXX"}                                                                 |
| 门店解绑组织    | DATA_SHOP_UPDATE_UNBIND_SHOP_ORGANIZATION | {"id":"XXXX","organizationId":"XXXX"}                                         |