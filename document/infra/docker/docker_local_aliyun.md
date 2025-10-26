# 本地环境Docker部署（阿里云）

## 网络

```bash
docker network create machine
```

## MySQL

```bash
sudo mkdir -p /srv/data/mysql
sudo chmod -R 777 /srv/data/mysql

docker run -p 3306:3306 \
--name mysql   \
--hostname mysql \
--network machine \
-v /srv/data/mysql/data:/var/lib/mysql \
-v /srv/data/mysql/backups:/backups \
-v /srv/data/mysql/conf.d:/etc/mysql/conf.d \
-e MYSQL_ROOT_PASSWORD=root \
--cpus=1 \
--memory=1g --memory-swap=2g \
--restart unless-stopped \
-d mysql:9.4.0 --character-set-server=utf8mb4 --collation-server=utf8mb4_general_ci
```

## PostgreSQL

```bash
sudo mkdir -p /srv/data/postgresql
sudo chmod -R 777 /srv/data/postgresql

docker run -d \
--name postgres \
--hostname postgres \
--network machine \
-p 5432:5432 \
-v /srv/data/postgresql:/var/lib/postgresql \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=postgres \
-e POSTGRES_DB=postgres \
--cpus=1 \
--memory=1g --memory-swap=2g \
--restart unless-stopped \
postgres:18.0
```

## Redis

```bash
sudo mkdir -p /srv/data/redis
sudo chmod -R 777 /srv/data/redis

docker run -d --name redis \
-p 6379:6379 \
--hostname redis \
--network machine \
-v /srv/data/redis/data:/data \
-v /srv/data/redis/conf/redis.conf:/usr/local/etc/redis/redis.conf \
-v /srv/data/redis/logs:/logs \
--cpus=0.2 \
--memory=0.2g --memory-swap=2g \
--restart unless-stopped \
redis:8.2 --requirepass "redis"
```

## RabbitMQ

```bash
sudo mkdir -p /srv/data/rabbitmq
sudo chmod -R 777 /srv/data/rabbitmq

docker run -d \
--name rabbitmq \
--hostname rabbitmq \
--network machine \
-p 15672:15672 -p 5672:5672 -p 15692:15692 \
-e RABBITMQ_DEFAULT_USER=root \
-e RABBITMQ_DEFAULT_PASS=root \
-v /srv/data/rabbitmq/data:/var/lib/rabbitmq \
--cpus=0.2 \
--memory=0.2g --memory-swap=2g \
--restart unless-stopped \
rabbitmq:4.1.0-management
```

## ScyllaDB

```bash
sudo mkdir -p /srv/data/scylla/{data,backups}
sudo chmod -R 777 /srv/data/scylla

docker run -d -p 9042:9042 \
--name scylla \
--hostname scylla \
--network machine \
-v /srv/data/scylla/data:/var/lib/scylla \
-v /srv/data/scylla/backups:/backups \
-e SCYLLA_CLUSTER_NAME=scylla \
-e SCYLLA_DC=dc1 \
-e SCYLLA_RACK=rack1 \
-e SCYLLA_AUTHENTICATION=true \
-e SCYLLA_PASSWORD_HASHING=bcrypt \
-e SCYLLA_ADMIN_USER=scylla \
-e SCYLLA_ADMIN_PASSWORD=scylla \
--cpus=0.5 \
--memory=0.5g --memory-swap=2g \
--restart unless-stopped \
 scylladb/scylla:2025.3
```

## MinIO

```bash
sudo mkdir -p /srv/data/minio/{data,config,logs}
sudo mkdir -p /srv/data/minio/disk{1,2,3,4,5,6,7,8}
sudo chmod -R 777 /srv/data/minio

docker run -d -p 9000:9000 -p 9001:9001 \
--name minio \
-e "MINIO_ROOT_USER=root" \
-e "MINIO_ROOT_PASSWORD=root1234" \
-e "MINIO_AUDIT_LOGGER_CONSOLE_ENABLE=on" \
-e "MINIO_AUDIT_LOGGER_CONSOLE_FORMAT=json" \
-v /srv/data/minio/disk1:/data/disk1 \
-v /srv/data/minio/disk2:/data/disk2 \
-v /srv/data/minio/disk3:/data/disk3 \
-v /srv/data/minio/disk4:/data/disk4 \
-v /srv/data/minio/disk5:/data/disk5 \
-v /srv/data/minio/disk6:/data/disk6 \
-v /srv/data/minio/disk7:/data/disk7 \
-v /srv/data/minio/disk8:/data/disk8 \
-v /srv/data/minio/config:/root/.minio \
--cpus=0.2 \
--memory=0.4g --memory-swap=2g \
--restart unless-stopped \
minio/minio:RELEASE.2025-09-07T16-13-09Z server \
/data/disk1 /data/disk2 /data/disk3 /data/disk4 \
/data/disk5 /data/disk6 /data/disk7 /data/disk8 \
--console-address ":9001"
```

## Nacos（本地部署）

```bash
docker run -d --name nacos \
-p 8849:8080 -p 8848:8848 -p 9848:9848 \
--hostname nacos \
--network machine \
-e MODE=standalone \
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
--cpus=1 \
--memory=2g --memory-swap=2g \
nacos/nacos-server:v3.1.0
```