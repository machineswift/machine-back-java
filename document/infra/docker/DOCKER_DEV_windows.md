## 网络
docker network create machine

## mysql
sudo mkdir -p D:/data/mysql
sudo chmod -R 777 D:/data/mysql

docker run -p 3306:3306 \
--name mysql   \
--hostname mysql \
--network machine \
-v D:/data/mysql/data:/var/lib/mysql \
-v D:/data/mysql/backups:/backups \
-v D:/data/mysql/conf.d:/etc/mysql/conf.d \
-e MYSQL_ROOT_PASSWORD=root \
--cpus=2 \
--memory=4g --memory-swap=4g \
-d mysql:9.4.0 --character-set-server=utf8mb4 --collation-server=utf8mb4_general_ci

## redis
sudo mkdir -p D:/data/redis
sudo chmod -R 777 D:/data/redis

docker run -d --name redis \
-p 6379:6379 \
--hostname redis \
--network machine \
-v D:/data/redis/data:/data \
-v D:/data/redis/conf/redis.conf:/usr/local/etc/redis/redis.conf \
-v D:/data/redis/logs:/logs \
--cpus=2 \
--memory=4g --memory-swap=4g \
redis:8.2 --requirepass "redis"


## RabbitMq
sudo mkdir -p D:/data/rabbitmq
sudo chmod -R 777 D:/data/rabbitmq

docker run -d \
--name rabbitmq \
--hostname rabbitmq \
--network machine \
-p 15672:15672 -p 5672:5672 -p 15692:15692 \
-e RABBITMQ_DEFAULT_USER=root \
-e RABBITMQ_DEFAULT_PASS=root \
-v D:/data/rabbitmq/data:/var/lib/rabbitmq \
--cpus=1 \
--memory=2g --memory-swap=2g \
rabbitmq:4.1.0-management


## nacos
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
nacos/nacos-server:v3.0.3

## minio
sudo mkdir -p D:/data/minio
sudo chmod -R 777 D:/data/minio

docker run -d  -p 9000:9000 \
-p 9001:9001 \
--name minio \
-e "MINIO_ROOT_USER=root" \
-e "MINIO_ROOT_PASSWORD=root1234" \
-e "MINIO_AUDIT_LOGGER_CONSOLE_ENABLE=on" \
-e "MINIO_AUDIT_LOGGER_CONSOLE_FORMAT=json" \
-v D:/data/minio/data:/data \
-v D:/data/minio/config:/root/.minio \
-v D:/data/minio/logs:/root/.minio/logs \
--cpus=1 \
--memory=2g --memory-swap=2g \
minio/minio:RELEASE.2025-04-22T22-12-26Z server /data --console-address ":9001"


## elastic
sudo mkdir -p D:/data/elasticsearch
sudo chmod -R 777 D:/data/elasticsearch

docker run -p 9200:9200 -d --name elasticsearch \
--hostname elasticsearch \
--network machine \
-e ELASTIC_PASSWORD=elastic@2 \
-e "discovery.type=single-node" \
-e "xpack.security.http.ssl.enabled=false" \
-e "xpack.license.self_generated.type=basic" \
-v D:/data/elasticsearch/data:/usr/share/elasticsearch/data \
-v D:/data/elasticsearch/logs:/usr/share/elasticsearch/logs \
-v D:/data/elasticsearch/backups:/usr/share/elasticsearch/backups \
--cpus=2 \
--memory=4g --memory-swap=4g \
elasticsearch:8.19.0


### 执行命令
curl -u elastic:elastic@2 \
-X POST \
http://127.0.0.1:9200/_security/user/kibana_system/_password \
-d '{"password":"kibana@2"}' \
-H 'Content-Type: application/json'

## kibana
sudo mkdir -p D:/data/kibana
sudo chmod -R 777 D:/data/kibana

docker run -p 5601:5601 -d --name kibana \
--hostname kibana \
--network machine \
-e ELASTICSEARCH_URL=http://elasticsearch:9200 \
-e ELASTICSEARCH_HOSTS=http://elasticsearch:9200 \
-e ELASTICSEARCH_USERNAME=kibana_system \
-e ELASTICSEARCH_PASSWORD=kibana@2 \
-e "xpack.security.enabled=false" \
-e "xpack.license.self_generated.type=basic" \
-v D:/data/kibana/plugins:/usr/share/kibana/plugins \
--cpus=1 \
--memory=2g --memory-swap=2g \
kibana:8.19.0


## skywalking
### server
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
--cpus=1 \
--memory=2g --memory-swap=2g \
apache/skywalking-oap-server:10.2.0

### ui
docker run --name skywalking-ui  -d \
--hostname skywalking-ui \
--network machine \
-e SW_OAP_SERVER=skywalking-server:12800 \
-e SW_OAP_ADDRESS=http://skywalking-server:12800 \
-p 9088:8080 \
--cpus=1 \
--memory=2g --memory-swap=2g \
apache/skywalking-ui:10.2.0