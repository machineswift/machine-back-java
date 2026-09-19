## 网络

```bash
docker network create machine
```

## 离线安装

```bash
docker save -o nacos-server_v3.2.2.tar nacos/nacos-server:v3.2.2

docker load -i nacos-server_v3.2.2.tar
```

## 启动 redis

```bash
docker run -d \
    --name nacos \
    --hostname nacos \
    --network machine \
    -e MODE=standalone \
    -p 8849:8080 -p 8848:8848 -p 9848:9848 \
    -e NACOS_AUTH_ENABLE=true \
    -e NACOS_AUTH_TOKEN=SecretKey012345678901234567890123456789012345678901234567890123456789 \
    -e NACOS_AUTH_IDENTITY_KEY=nacos \
    -e NACOS_AUTH_IDENTITY_VALUE=nacos \
    -e SPRING_DATASOURCE_PLATFORM=mysql \
    -e MYSQL_SERVICE_HOST=mysql \
    -e MYSQL_SERVICE_PORT=3306 \
    -e MYSQL_SERVICE_DB_NAME=machine_nacos \
    -e MYSQL_SERVICE_USER=root \
    -e MYSQL_SERVICE_PASSWORD=root \
    --cpus=2 \
    --memory=4g \
nacos/nacos-server:v3.2.2
```