## 网络

```bash
docker network create machine
```

## 离线安装

```bash
docker save -o xxl-job-admin_3.4.2.tar xxl-job-admin:3.4.2

docker load -i xxl-job-admin_3.4.2.tar
```

## 启动 xxl-job

```bash
docker run -d \
    --name xxl-job-admin \
    --hostname xxl-job-admin \
    --network machine \
    -p 8083:8080 \
    -e PARAMS="--spring.datasource.url=jdbc:mysql://127.0.0.1:3306/machine_xxljob?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=Asia/Shanghai \
    --spring.datasource.username=root \
    --spring.datasource.password=root" \
    --cpus=2 \
    --memory=4g \
    --restart unless-stopped \
xuxueli/xxl-job-admin:3.4.2
```

## 账号/密码
```bash
admin/123456
```