## activemq
```bash
sudo mkdir -p /srv/data/activemq/{data,config}
sudo chmod -R 777 /srv/data/activemq

docker run -d \
--name activemq \
--hostname activemq \
--network machine \
-p 8161:8161 \
-p 61616:61616 \
-e ARTEMIS_USER=admin \
-e ARTEMIS_PASSWORD=admin \
-v /srv/data/activemq/data:/var/lib/artemis-instance/data \
-v /srv/data/activemq/config:/var/lib/artemis-instance/etc \
--cpus=2 \
--memory=4g \
--restart unless-stopped \
apache/activemq-artemis:2.42.0
```

## mongodb
```bash
sudo mkdir -p /srv/data/mongo/{data,config,logs}
sudo chmod -R 777 /srv/data/mongo


docker run -d \
--name mongo \
--hostname mongo \
--network machine \
-p 27017:27017 \
-v /srv/data/mongo/data:/data/db \
-v /srv/data/mongo/config:/etc/mongo \
-v /srv/data/mongo/logs:/var/log/mongodb \
-e MONGO_INITDB_ROOT_USERNAME=root \
-e MONGO_INITDB_ROOT_PASSWORD=root \
--cpus=2 \
--memory=4g \
--restart unless-stopped \
mongo:8.0.15


docker run -d \
--name mongo-express \
--network machine \
-p 9084:8081 \
-e ME_CONFIG_MONGODB_SERVER=mongo \
-e ME_CONFIG_MONGODB_ADMINUSERNAME=root \
-e ME_CONFIG_MONGODB_ADMINPASSWORD=root \
--cpus=1 \
--memory=1g \
--restart unless-stopped \
mongo-express:1.0.2

admin/pass
```

## kafka
```bash
sudo mkdir -p /srv/data/kafka/{data,config,logs}
sudo chmod -R 777 /srv/data/kafka

docker run -d \
--name kafka \
--hostname kafka \
--network machine \
-p 9092:9092 \
-p 9093:9093 \
-e KAFKA_PROCESS_ROLES=broker,controller \
-e KAFKA_NODE_ID=1 \
-e KAFKA_LISTENERS=PLAINTEXT://:9092,CONTROLLER://:9093 \
-e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafka:9092 \
-e KAFKA_CONTROLLER_LISTENER_NAMES=CONTROLLER \
-e KAFKA_CONTROLLER_QUORUM_VOTERS=1@kafka:9093 \
-e KAFKA_LISTENER_SECURITY_PROTOCOL_MAP=PLAINTEXT:PLAINTEXT,CONTROLLER:PLAINTEXT \
-e KAFKA_LOG_DIRS=/var/lib/kafka/data \
-v /srv/data/kafka/data:/var/lib/kafka/data \
-v /srv/data/kafka/config:/opt/kafka/config \
-v /srv/data/kafka/logs:/opt/kafka/logs \
--cpus=2 \
--memory=4g \
--restart unless-stopped \
apache/kafka:4.1.0


docker run -d \
--name kafka-ui \
--network machine \
-p 9082:8080 \
-e KAFKA_CLUSTERS_0_NAME=local \
-e KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS=kafka:9092 \
--cpus=1 \
--memory=1g \
--restart unless-stopped \
provectuslabs/kafka-ui:v0.7.2
```


### 6. ClickHouse 分析数据库
```bash
sudo mkdir -p /srv/data/clickhouse
sudo chmod -R 777 /srv/data/clickhouse

docker run -d -p 8123:8123 -p 9000:9000 \
--name clickhouse \
--hostname clickhouse \
--network machine \
-v /srv/data/clickhouse:/var/lib/clickhouse \
-e CLICKHOUSE_USER=clickhouse \
-e CLICKHOUSE_PASSWORD=clickhouse \
-e CLICKHOUSE_DB=clickhouse \
-e CLICKHOUSE_DEFAULT_ACCESS_MANAGEMENT=1 \
--ulimit nofile=262144:262144 \
--cpus=2 \
--memory=4g \
clickhouse:26.3.3.20
```

### 10. Apache Flink 流处理
```bash
sudo mkdir -p /srv/data/flink
sudo chmod -R 777 /srv/data/flink
```

### JobManager
```bash
docker run -d -p 8081:8081 -p 6123:6123 \
--name flink-jobmanager \
--hostname flink-jobmanager \
--network machine \
-v /srv/data/flink/flink-state/jobmanager:/opt/flink/state \
-v /srv/data/flink/flink-logs/jobmanager:/opt/flink/log \
--cpus=2 \
--memory=4g \
flink:2.1.0-java21 jobmanager
```

### TaskManager
```bash
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
--memory=4g \
flink:2.1.0-java21 taskmanager


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
--memory=4g \
flink:2.1.0-java21 taskmanager
```