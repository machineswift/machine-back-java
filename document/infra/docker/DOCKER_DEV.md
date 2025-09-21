## 网络
docker network create machine

## mysql
sudo mkdir -p /Users/machine/data/mysql
sudo chmod -R 777 /Users/machine/data/mysql

docker run -p 3306:3306 \
--name mysql   \
--hostname mysql \
--network machine \
-v /Users/machine/data/mysql/data:/var/lib/mysql \
-v /Users/machine/data/mysql/backups:/backups \
-v /Users/machine/data/mysql/conf.d:/etc/mysql/conf.d \
-e MYSQL_ROOT_PASSWORD=root \
--cpus=2 \
--memory=4g --memory-swap=4g \
-d mysql:9.4.0 --character-set-server=utf8mb4 --collation-server=utf8mb4_general_ci

## redis
sudo mkdir -p /Users/machine/data/redis
sudo chmod -R 777 /Users/machine/data/redis

docker run -d --name redis \
-p 6379:6379 \
--hostname redis \
--network machine \
-v /Users/machine/data/redis/data:/data \
-v /Users/machine/data/redis/conf/redis.conf:/usr/local/etc/redis/redis.conf \
-v /Users/machine/data/redis/logs:/logs \
--cpus=2 \
--memory=4g --memory-swap=4g \
redis:8.2 --requirepass "redis"


## RabbitMq
sudo mkdir -p /Users/machine/data/rabbitmq
sudo chmod -R 777 /Users/machine/data/rabbitmq

docker run -d \
--name rabbitmq \
--hostname rabbitmq \
--network machine \
-p 15672:15672 -p 5672:5672 -p 15692:15692 \
-e RABBITMQ_DEFAULT_USER=root \
-e RABBITMQ_DEFAULT_PASS=root \
-v /Users/machine/data/rabbitmq/data:/var/lib/rabbitmq \
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
sudo mkdir -p /Users/machine/data/minio/{data,config,logs}
sudo mkdir -p /Users/machine/data/minio/disk{1,2,3,4,5,6,7,8}
sudo chmod -R 777 /Users/machine/data/minio

docker run -d -p 9000:9000 \
-p 9001:9001 \
--name minio \
-e "MINIO_ROOT_USER=root" \
-e "MINIO_ROOT_PASSWORD=root1234" \
-e "MINIO_AUDIT_LOGGER_CONSOLE_ENABLE=on" \
-e "MINIO_AUDIT_LOGGER_CONSOLE_FORMAT=json" \
-v /Users/machine/data/minio/disk1:/data/disk1 \
-v /Users/machine/data/minio/disk2:/data/disk2 \
-v /Users/machine/data/minio/disk3:/data/disk3 \
-v /Users/machine/data/minio/disk4:/data/disk4 \
-v /Users/machine/data/minio/disk5:/data/disk5 \
-v /Users/machine/data/minio/disk6:/data/disk6 \
-v /Users/machine/data/minio/disk7:/data/disk7 \
-v /Users/machine/data/minio/disk8:/data/disk8 \
-v /Users/machine/data/minio/config:/root/.minio \
--cpus=4 \
--memory=8g --memory-swap=8g \
--restart unless-stopped \
minio/minio:RELEASE.2025-04-22T22-12-26Z server \
/data/disk1 /data/disk2 /data/disk3 /data/disk4 \
/data/disk5 /data/disk6 /data/disk7 /data/disk8 \
--console-address ":9001"


## elastic
sudo mkdir -p /Users/machine/data/elasticsearch
sudo chmod -R 777 /Users/machine/data/elasticsearch

docker run -p 9200:9200 -d --name elasticsearch \
--hostname elasticsearch \
--network machine \
-e ELASTIC_PASSWORD=elastic@2 \
-e "discovery.type=single-node" \
-e "xpack.security.http.ssl.enabled=false" \
-e "xpack.license.self_generated.type=basic" \
-v /Users/machine/data/elasticsearch/data:/usr/share/elasticsearch/data \
-v /Users/machine/data/elasticsearch/logs:/usr/share/elasticsearch/logs \
-v /Users/machine/data/elasticsearch/backups:/usr/share/elasticsearch/backups \
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
sudo mkdir -p /Users/machine/data/kibana
sudo chmod -R 777 /Users/machine/data/kibana

docker run -p 5601:5601 -d --name kibana \
--hostname kibana \
--network machine \
-e ELASTICSEARCH_URL=http://elasticsearch:9200 \
-e ELASTICSEARCH_HOSTS=http://elasticsearch:9200 \
-e ELASTICSEARCH_USERNAME=kibana_system \
-e ELASTICSEARCH_PASSWORD=kibana@2 \
-e "xpack.security.enabled=false" \
-e "xpack.license.self_generated.type=basic" \
-v /Users/machine/data/kibana/plugins:/usr/share/kibana/plugins \
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


## gitlab
sudo chmod -R 777 /Users/machine/data/gitLab


docker run --detach \
--hostname 127.0.0.1 \
--publish --publish 6080:80 --publish 22:22 \
--name gitlab \
--volume /Users/machine/data/gitLab/config:/etc/gitlab \
--volume /Users/machine/data/gitLab/logs:/var/log/gitlab \
--volume /Users/machine/data/gitLab/data:/var/opt/gitlab \
--shm-size=512m \
-e EXTERNAL_URL="http://公网IP:6080" \
--cpus=4 \
--memory=8g --memory-swap=8g \
--env GITLAB_OMNIBUS_CONFIG="gitlab_rails['initial_root_password']='YT4kVFg+hbbtyUFzTePqR+faWMM9JrTWl963Y3T';" \
gitlab/gitlab-ce:16.11.5-ce.0

#### 查看root密码
docker exec -it gitlab grep 'Password:' /etc/gitlab/initial_root_password
root/YT4kVFg+hbbtyUFzTePqR+faWMM9JrTWl963Y3T

## confluence

#### 启动 PostgreSQL
sudo mkdir -p /Users/machine/data/postgres/{data,backups}
sudo chmod -R 777 /Users/machine/data/postgres/data

docker run -d \
--name postgres \
--network machine \
-p 5432:5432 \
-v /Users/machine/data/postgres/data:/var/lib/postgresql/data \
-v /Users/machine/data/postgres/backups:/backups \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=postgres \
-e POSTGRES_DB=confluence_db \
-e PGDATA=/var/lib/postgresql/data/pgdata \
--memory=4g --memory-swap=4g \
postgres:13.12

##### 用户赋予操作数据库的权限
GRANT ALL PRIVILEGES ON DATABASE confluence_db TO postgres;

#### 运行Confluence容器
sudo mkdir -p /Users/machine/data/confluence/{atlassian,data,logs}
sudo chown -R 2002:root /Users/machine/data/confluence/{data,logs,atlassian}
sudo chmod -R 777 /Users/machine/data/confluence

docker run -d \
--name confluence \
--network machine \
-p 8090:8090 \
-p 8091:8091 \
-v /Users/machine/data/confluence/atlassian/atlassian-agent.jar:/opt/atlassian/confluence/atlassian-agent.jar:ro \
-v /Users/machine/data/confluence/data:/var/atlassian/application-data/confluence \
-v /Users/machine/data/confluence/logs:/opt/atlassian/confluence/logs \
-e ATL_JDBC_URL="jdbc:postgresql://postgres:5432/confluence_db?ssl=false" \
-e ATL_JDBC_USER="postgres" \
-e ATL_JDBC_PASSWORD="postgres" \
-e ATL_DB_TYPE="postgresql" \
-e TZ=Asia/Shanghai \
-e UMASK=0027 \
-e CONFLUENCE_UID=2002 \
-e CONFLUENCE_GID=0 \
-e CATALINA_OPTS="-javaagent:/opt/atlassian/confluence/atlassian-agent.jar -Dconfluence.disable.peopledirectory.all=true ${CATALINA_OPTS}" \
--memory=8g --memory-swap=8g \
atlassian/confluence-server:8.5.2

##### 破解Confluence
docker exec -it confluence bash
cd /opt/atlassian/confluence
java -jar atlassian-agent.jar -d -m machineswift@qq.com -n confluence -p 'conf' -o http://localhost:8090 -s B6L9-4454-XKNM-8YEF

##### 管理员密码
admin/admin