# 测试环境 Docker 部署指南 (Linux)

> **环境说明**: 适用于 Linux 测试环境，包含完整的微服务基础设施组件

## 📋 部署清单

| 服务            | 端口                 | 用途     | 资源需求            |
|---------------|--------------------|--------|-----------------|
| MySQL         | 3306               | 主数据库   | 4 CPU, 8GB RAM  |
| Redis         | 6379               | 缓存数据库  | 4 CPU, 8GB RAM  |
| RabbitMQ      | 5672, 15672        | 消息队列   | 2 CPU, 4GB RAM  |
| MinIO         | 9000, 9001         | 对象存储   | 4 CPU, 8GB RAM  |
| ClickHouse    | 8123, 9000, 9009   | 分析数据库  | 4 CPU, 8GB RAM  |
| Elasticsearch | 9200               | 搜索引擎   | 4 CPU, 8GB RAM  |
| Kibana        | 5601               | 日志分析   | 1 CPU, 2GB RAM  |
| SkyWalking    | 11800, 12800, 9088 | 链路追踪   | 2 CPU, 4GB RAM  |
| Flink         | 8081, 6123         | 流处理    | 6 CPU, 12GB RAM |
| Nacos         | 8848, 8849, 9848   | 服务注册中心 | 2 CPU, 4GB RAM  |

## 🚀 快速开始

### 1. 创建网络
```bash
docker network create machine
```

### 2. MySQL 数据库
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
--cpus=4 \
--memory=8g --memory-swap=8g \
--restart unless-stopped \
-d mysql:9.6.0 --character-set-server=utf8mb4 --collation-server=utf8mb4_general_ci
```

### 3. Redis 缓存
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
--cpus=4 \
--memory=8g --memory-swap=8g \
--restart unless-stopped \
redis:8.6.2 --requirepass "redis"
```

### 4. RabbitMQ 消息队列
```bash
sudo mkdir -p /srv/data/rabbitmq
sudo chmod -R 777 /srv/data/rabbitmq

docker run -d \
--name rabbitmq \
--hostname rabbitmq \
--network machine \
-e RABBITMQ_DEFAULT_USER=root \
-e RABBITMQ_DEFAULT_PASS=root \
-p 15672:15672 -p 5672:5672  \
-v /srv/data/rabbitmq/data:/var/lib/rabbitmq \
--cpus=2 \
--memory=4g --memory-swap=4g \
--restart unless-stopped \
rabbitmq:4.2.5-management
```

### 5. MinIO 对象存储
```bash
sudo mkdir -p /srv/data/minio/{data,config,logs}
sudo mkdir -p /srv/data/minio/disk{1,2,3,4,5,6,7,8}
sudo chmod -R 777 /srv/data/minio

docker run -d -p 9000:9000 \
-p 9001:9001 \
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
--cpus=4 \
--memory=8g --memory-swap=8g \
--restart unless-stopped \
minio/minio:RELEASE.2025-04-22T22-12-26Z server \
/data/disk1 /data/disk2 /data/disk3 /data/disk4 \
/data/disk5 /data/disk6 /data/disk7 /data/disk8 \
--console-address ":9001"
```

### 6. ClickHouse 分析数据库
```bash
sudo mkdir -p /srv/data/clickhouse
sudo chmod -R 777 /srv/data/clickhouse

docker run -d -p 8123:8123 -p 9000:9000 -p 9009:9009 \
--name clickhouse \
--hostname clickhouse \
--network machine \
-v /srv/data/clickhouse:/var/lib/clickhouse \
-e CLICKHOUSE_USER=clickhouse \
-e CLICKHOUSE_PASSWORD=clickhouse \
-e CLICKHOUSE_DB=clickhouse \
--ulimit nofile=262144:262144 \
--cpus=4 \
--memory=8g --memory-swap=8g \
--restart unless-stopped \
clickhouse:25.9.3.48
```

### 7. Elasticsearch 搜索引擎
```bash
sudo mkdir -p /srv/data/elasticsearch
sudo chmod -R 777 /srv/data/elasticsearch

docker run -p 9200:9200 -d --name elasticsearch \
--hostname elasticsearch \
--network machine \
-e ELASTIC_PASSWORD=elastic@2 \
-e "discovery.type=single-node" \
-e "xpack.security.http.ssl.enabled=false" \
-e "xpack.license.self_generated.type=basic" \
-v /srv/data/elasticsearch/data:/usr/share/elasticsearch/data \
-v /srv/data/elasticsearch/logs:/usr/share/elasticsearch/logs \
-v /srv/data/elasticsearch/backups:/usr/share/elasticsearch/backups \
--cpus=4 \
--memory=8g --memory-swap=8g \
--restart unless-stopped \
elasticsearch:8.19.12
```

### 8. Kibana 日志分析
```bash
sudo mkdir -p /srv/data/kibana
sudo chmod -R 777 /srv/data/kibana

docker run -p 5601:5601 -d --name kibana \
--hostname kibana \
--network machine \
-e ELASTICSEARCH_URL=http://elasticsearch:9200 \
-e ELASTICSEARCH_HOSTS=http://elasticsearch:9200 \
-e ELASTICSEARCH_USERNAME=kibana_system \
-e ELASTICSEARCH_PASSWORD=kibana@2 \
-e "xpack.security.enabled=false" \
-e "xpack.license.self_generated.type=basic" \
-v /srv/data/kibana/plugins:/usr/share/kibana/plugins \
--cpus=1 \
--memory=2g --memory-swap=2g \
--restart unless-stopped \
kibana:8.19.12
```

### 9. SkyWalking 链路追踪
```bash
# SkyWalking Server
docker run --name skywalking-server  -d \
--hostname skywalking-server \
--network machine \
-e SW_STORAGE=elasticsearch \
-e SW_ES_USER=elastic \
-e SW_ES_PASSWORD=elastic@2 \
-e SW_STORAGE_ES_CLUSTER_NODES=elasticsearch:9200 \
-e SW_CORE_RECORD_DATA_TTL=90 \
-e SW_CORE_METRICS_DATA_TTL=270 \
-p 11800:11800 \
-p 12800:12800 \
--cpus=2 \
--memory=4g --memory-swap=4g \
--restart unless-stopped \
apache/skywalking-oap-server:10.3.0

# SkyWalking UI
docker run --name skywalking-ui  -d \
--hostname skywalking-ui \
--network machine \
-e SW_OAP_SERVER=skywalking-server:12800 \
-e SW_OAP_ADDRESS=http://skywalking-server:12800 \
-p 9088:8080 \
--cpus=1 \
--memory=2g --memory-swap=2g \
--restart unless-stopped \
apache/skywalking-ui:10.3.0
```

### 10. Apache Flink 流处理
```bash
sudo mkdir -p /srv/data/flink
sudo chmod -R 777 /srv/data/flink

# JobManager
docker run -d -p 8081:8081 -p 6123:6123 \
--name flink-jobmanager \
--hostname flink-jobmanager \
--network machine \
-v /srv/data/flink/flink-state/jobmanager:/opt/flink/state \
-v /srv/data/flink/flink-logs/jobmanager:/opt/flink/log \
--cpus=2 \
--memory=4g --memory-swap=4g \
--restart unless-stopped \
flink:2.1.0-java21 jobmanager

# TaskManager 01
docker run -d \
--name flink-taskmanager-01 \
--hostname flink-taskmanager-01 \
--network machine \
-e JOB_MANAGER_RPC_ADDRESS=flink-jobmanager \
-e TASK_MANAGER_NUMBER_OF_TASK_SLOTS=2 \
-e BLINK_TASKMANAGER_MEMORY_SIZE=4g \
-v /srv/data/flink/flink-state/taskmanager-01:/opt/flink/state \
-v /srv/data/flink/flink-logs/taskmanager-01:/opt/flink/log \
--cpus=2 \
--memory=4g --memory-swap=4g \
--restart unless-stopped \
flink:2.1.0-java21 taskmanager

# TaskManager 02
docker run -d \
--name flink-taskmanager-02 \
--hostname flink-taskmanager-02 \
--network machine \
-e JOB_MANAGER_RPC_ADDRESS=flink-jobmanager \
-e TASK_MANAGER_NUMBER_OF_TASK_SLOTS=2 \
-e BLINK_TASKMANAGER_MEMORY_SIZE=4g \
-v /srv/data/flink/flink-state/taskmanager-02:/opt/flink/state \
-v /srv/data/flink/flink-logs/taskmanager-02:/opt/flink/log \
--cpus=2 \
--memory=4g --memory-swap=4g \
--restart unless-stopped \
flink:2.1.0-java21 taskmanager
```

### 11. Nacos 服务注册中心
```bash
docker run -d --name nacos \
-p 8848:8848 -p 9848:9848\
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
--cpus=2 \
--memory=4g --memory-swap=4g \
--restart unless-stopped \
nacos/nacos-server:v3.2.0
```

### 12. XXL-JOB 任务调度中心
```bash
docker run -d \
--name xxl-job-admin \
-p 8083:8080 \
--hostname xxl-job-admin \
--network machine \
-e PARAMS="--spring.datasource.url=jdbc:mysql://127.0.0.1:3306/machine_xxljob?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=Asia/Shanghai \
--spring.datasource.username=root \
--spring.datasource.password=root" \
--cpus=2 \
--memory=4g --memory-swap=4g \
xuxueli/xxl-job-admin:3.3.2
```


## 🌐 服务访问地址

| 服务            | 访问地址                                | 用户名           | 密码         | 说明     |
|---------------|-------------------------------------|---------------|------------|--------|
| MySQL         | localhost:3306                      | root          | root       | 数据库连接  |
| Redis         | localhost:6379                      | -             | redis      | 缓存连接   |
| RabbitMQ      | http://localhost:15672              | root          | root       | 管理界面   |
| MinIO         | http://localhost:9001               | root          | root1234   | 控制台    |
| ClickHouse    | http://localhost:8123               | clickhouse    | clickhouse | 查询界面   |
| Elasticsearch | http://localhost:9200               | elastic       | elastic@2  | API接口  |
| Kibana        | http://localhost:5601               | kibana_system | kibana@2   | 日志分析   |
| SkyWalking    | http://localhost:9088               | -             | -          | 链路追踪   |
| Flink         | http://localhost:8081               | -             | -          | 流处理管理  |
| Nacos         | http://localhost:8848/nacos         | nacos         | nacos      | 服务注册中心 |
| XXL_JOB       | http://127.0.0.1:8083/xxl-job-admin | admin         | 123456     | 任务调度中心 |