# 开发指南

## oauth2注册客户端

### 请求方式

```plaintext
post
```

### 请求地址

```plaintext
{{http_prefix}}/machine-iam-app/iam/auth2/create_client
```

### 请求参数

| 字段           | 名称    | 是否必须传 | 备注 |
|--------------|-------|-------|----|
| clientId     | 客户端ID | 是     |    |
| clientName   | 客户端名称 | 是     |    |
| password     | 密码    | 是     |    |
| scopes       | 范围    | 是     |    |
| redirectUrls | 重定向地址 | 是     |    |


### demo:

```plaintext
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
        "www.baidu.com"
    ]
}
```

### 返回参数

| 字段        | 名称       | 是否必须返回 | 备注          |
|-----------|----------|--------|-------------|
| id        | id       | 是      | 主键Id        |

```

### demo:

```plaintext
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

## oauth2获得token接口

### 请求方式

```plaintext
post
```

### 请求地址

```plaintext
{{http_prefix}}/machine-iam-app/iam/oauth2/authServer/token
```

### 请求参数

| 字段         | 名称     | 是否必须传 | 备注                               |
|------------|--------|-------|----------------------------------|
| Username   | 客户端id  | 是     | APP2024120001                    |
| Password   | 客户端密码  | 是     | 1f6af7d1efd34246ba84741eb3ab092e |
| grant_type | 授权授予类型 | 是     | client_credentials               |
| scope      | 作用域    | 是     | iam data hrm                     |

### demo:

```plaintext
客户端构造认证信息：
客户端获取到服务器的认证要求后，需要将用户名和密码进行组合并编码。
具体的做法是将用户名和密码用冒号 : 连接起来
（例如，用户名是 user，密码是 password，则组合成 user:password），
然后使用 Base64 编码算法对这个字符串进行编码，
得到一个 Base64 编码后的字符串（如上述示例编码后可能是 dXNlcjpwYXNzd29yZA==）。


curl --location --request POST 'http://localhost:8080/machine-iam-app/iam/oauth2/authServer/token' \
--header 'Authorization: Basic QVBQMjAyNDEyMDAwMTpoeXluc2hkc2Jjamtjc2JrJkAjbmJraGprc2E=' \
--data-urlencode 'grant_type=client_credentials' \
--data-urlencode 'scope=iam data hrm'
```

### 返回参数

| 字段        | 名称       | 是否必须返回 | 备注          |
|-----------|----------|--------|-------------|
| status    | 状态码      | 是      | HTTP 状态码    |
| code      | 编码       | 是      | 编码          |
| message   | 信息       | 否      | 消息内容        |
| timestamp | 接收到消息的时间 | 是      | UNIX 时间戳    |
| traceId   | traceId  | 是      | 取传入的traceId |


### demo:

```plaintext
{
    "access_token": "eyJraWQiOiJjMDJmMTk1Ni1jMDI5",
    "scope": "hrm iam data",
    "token_type": "Bearer",
    "expires_in": 312599
}
```

## oauth2获取token图片

![oauth2生成token](.\images\iam\auth2-login.png)
![oauth2生成token](.\images\iam\auth2-login-param.png)

## oauth2请求openapi接口



## oauth2接口

### 请求方式

```plaintext
post
```

### 请求地址

```plaintext
https://app-gateway-dev.machine888.com/machine-openapi-app/openapi/iam/oauth2/authToken
```

### 请求参数
无



### 返回参数

| 字段        | 名称       | 是否必须返回 | 备注          |
|-----------|----------|--------|-------------|
| status    | 状态码      | 是      | HTTP 状态码    |
| code      | 编码       | 是      | 编码          |
| message   | 信息       | 否      | 消息内容        |
| timestamp | 接收到消息的时间 | 是      | UNIX 时间戳    |
| traceId   | traceId  | 是      | 取传入的traceId |


### demo:

```plaintext
{
    "status": 200,
    "code": "SUCCESS",
    "message": "操作成功",
    "timestamp": 1733478870764,
    "traceId": "",
    "data": {
        "success": "success"
    }
}
```

## oauth2调用openapi接口

![oauth2获取授权码](.\images\iam\auth2-openapi.png)
