## 网络
docker network create machine

## mysql


## redis
docker run -d \
--name redis \
-p 6379:6379 \
--cpus=2 \
--memory=4g --memory-swap=8g \
--restart unless-stopped \  
-v /xj-data/redis/redis.conf:/etc/redis/redis.conf \
-v /xj-data/redis/data:/data \
redis:7.4 --requirepass "XXXX"


## RabbitMq
docker run -d \
--name rabbitmq \
-e RABBITMQ_DEFAULT_USER=root \
-e RABBITMQ_DEFAULT_PASS=XXXX \
-p 15672:15672 -p 5672:5672  \
--cpus=2 \
--memory=4g --memory-swap=4g \
--restart unless-stopped \  
v /xj-data/rabbitmq/data:/var/lib/rabbitmq \
rabbitmq:4.0.4-management


## nacos（mysql）
docker run -d --name nacos \
-p 8848:8848 \
-p 9848:9848 \
-e MODE=standalone \
-e NACOS_AUTH_ENABLE=true \
-e NACOS_AUTH_TOKEN=SecretKey012345678901234567890123456789012345678901234567890123456789 \
-e NACOS_AUTH_IDENTITY_KEY=nacos \
-e NACOS_AUTH_IDENTITY_VALUE=XXXX \
-e SPRING_DATASOURCE_PLATFORM=mysql \
-e MYSQL_SERVICE_HOST=10.4.2.120 \
-e MYSQL_SERVICE_PORT=3306 \
-e MYSQL_SERVICE_DB_NAME=machine_nacos \
-e MYSQL_SERVICE_USER=root \
-e MYSQL_SERVICE_PASSWORD=XXXX \
--cpus=1 \
--memory=2g --memory-swap=2g \
--restart unless-stopped \
nacos/nacos-server:v2.3.2


## xxljob
admin/XXXX


## elastic
docker run -p 9200:9200 -d --name elasticsearch --network machine \
-e ELASTIC_PASSWORD=elastic@2 \
-e "discovery.type=single-node" \
-e "xpack.security.http.ssl.enabled=false" \
-e "xpack.license.self_generated.type=trial" \
--cpus=4 \
--memory=8g --memory-swap=8g \
--restart unless-stopped \
docker.elastic.co/elasticsearch/elasticsearch:8.17.0


### 执行命令
curl -u elastic:elastic@2 \
-X POST \
http://127.0.0.1:9200/_security/user/kibana_system/_password \
-d '{"password":"kibana@2"}' \
-H 'Content-Type: application/json'

## kibana
docker run -p 5601:5601 -d --name kibana --network machine \
-e ELASTICSEARCH_URL=http://elasticsearch:9200 \
-e ELASTICSEARCH_HOSTS=http://elasticsearch:9200 \
-e ELASTICSEARCH_USERNAME=kibana_system \
-e ELASTICSEARCH_PASSWORD=kibana@2 \
-e "xpack.security.enabled=false" \
-e "xpack.license.self_generated.type=trial" \
--cpus=0.5 \
--memory=1g --memory-swap=1g \
--restart unless-stopped \
docker.elastic.co/kibana/kibana:8.17.0


## skywalking
### server
docker run --name skywalking --network machine  -d \
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
apache/skywalking-oap-server:10.1.0

### ui
docker run --name skywalking-ui  -d \
--network=machine \
-e SW_OAP_SERVER=skywalking:12800 \
-e SW_OAP_ADDRESS=http://skywalking:12800 \
-p 9088:8080 \
--cpus=0.5 \
--memory=1g --memory-swap=1g \
--restart unless-stopped \
apache/skywalking-ui:10.1.0


