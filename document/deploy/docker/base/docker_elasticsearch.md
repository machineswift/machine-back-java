## 网络

```bash
docker network create machine
```

## 离线安装

```bash
docker save -o elasticsearch_9.5.3.tar  elasticsearch:9.5.3
docker save -o kibana_9.5.3.tar  kibana:9.5.3

docker load -i elasticsearch_9.5.3.tar
docker load -i kibana_9.5.3.tar
```

## 创建数据目录

```bash
sudo mkdir -p /srv/data/elasticsearch
sudo chmod -R 777 /srv/data/elasticsearch

sudo mkdir -p /srv/data/kibana
sudo chmod -R 777 /srv/data/kibana
```

## 启动 elasticsearch
```bash
docker run -d \
    --name elasticsearch \
    --hostname elasticsearch \
    --network machine \
    -p 9200:9200 \
    -e ELASTIC_PASSWORD=elastic@2 \
    -e "discovery.type=single-node" \
    -e "xpack.security.http.ssl.enabled=false" \
    -e "xpack.license.self_generated.type=basic" \
    -v /srv/data/elasticsearch/data:/usr/share/elasticsearch/data \
    -v /srv/data/elasticsearch/logs:/usr/share/elasticsearch/logs \
    -v /srv/data/elasticsearch/backups:/usr/share/elasticsearch/backups \
    --cpus=4 \
    --memory=8g \
    --restart unless-stopped \
elasticsearch:9.5.3
```

### 执行命令
```bash
curl -u elastic:elastic@2 \
-X POST \
http://127.0.0.1:9200/_security/user/kibana_system/_password \
-d '{"password":"kibana@2"}' \
-H 'Content-Type: application/json'
```

## 启动 elasticsearch
```bash
docker run -d \
    --name kibana \
    --hostname kibana \
    --network machine \
    -p 5601:5601 \
    -e ELASTICSEARCH_URL=http://elasticsearch:9200 \
    -e ELASTICSEARCH_HOSTS=http://elasticsearch:9200 \
    -e ELASTICSEARCH_USERNAME=kibana_system \
    -e ELASTICSEARCH_PASSWORD=kibana@2 \
    -e "xpack.security.enabled=false" \
    -e "xpack.license.self_generated.type=basic" \
    -v /srv/data/kibana/plugins:/usr/share/kibana/plugins \
    --cpus=1 \
    --memory=2g \
    --restart unless-stopped \
kibana:9.5.3
```
