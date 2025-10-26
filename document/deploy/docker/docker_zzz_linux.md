## 常用命令
```bash
docker save -o XXX.tar XXX:XXX
docker load -i XXX.tar
docker logs -f --tail 500 XXX
```

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
--memory=4g --memory-swap=4g \
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
--memory=4g --memory-swap=4g \
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
--memory=1g --memory-swap=1g \
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
--memory=4g --memory-swap=4g \
--restart unless-stopped \
apache/kafka:4.1.0


docker run -d \
--name kafka-ui \
--network machine \
-p 9082:8080 \
-e KAFKA_CLUSTERS_0_NAME=local \
-e KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS=kafka:9092 \
--cpus=1 \
--memory=1g --memory-swap=1g \
--restart unless-stopped \
provectuslabs/kafka-ui:v0.7.2
```