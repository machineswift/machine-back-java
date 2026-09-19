## 网络

```bash
docker network create machine
```

## 离线安装

```bash
docker save -o rocketmq_5.5.0.tar  apache/rocketmq:5.5.0
docker save -o rocketmq-dashboard_2.1.0.tar  apacherocketmq/rocketmq-dashboard:2.1.0

docker load -i rocketmq_5.5.0.tar
docker load -i rocketmq-dashboard_2.1.0.tar
```

## 创建数据目录

```bash
sudo mkdir -p /srv/data/rocketmq/{namesrv,broker,proxy}/logs
sudo mkdir -p /srv/data/rocketmq/broker/{conf,store}
sudo chmod -R 777 /srv/data/rocketmq
```

## 启动 NameServer
```bash
docker run -d \
  --name rocketmq-namesrv \
  --hostname rocketmq-namesrv \
  --network machine \
  -p 9876:9876 \
  -v /srv/data/rocketmq/namesrv/logs:/home/rocketmq/logs \
  --cpus=1 \
  --memory=2g \
  --restart unless-stopped \
  apache/rocketmq:5.5.0 \
  sh mqnamesrv
```

## 启动 broker
```bash
docker run -d \
  --name rocketmq-broker \
  --hostname rocketmq-broker \
  --network machine \
  -p 10909:10909 \
  -p 10911:10911 \
  -p 10912:10912 \
  -v /srv/data/rocketmq/broker/conf/broker.conf:/home/rocketmq/rocketmq-5.5.0/conf/broker.conf \
  -v /srv/data/rocketmq/broker/store:/home/rocketmq/store \
  -v /srv/data/rocketmq/broker/logs:/home/rocketmq/logs \
  -e "NAMESRV_ADDR=rocketmq-namesrv:9876" \
  --cpus=2 \
  --memory=4g \
  --restart unless-stopped \
  apache/rocketmq:5.5.0 \
  sh mqbroker \
  -c /home/rocketmq/rocketmq-5.5.0/conf/broker.conf
```

## 启动 Proxy
```bash
docker run -d \
  --name rocketmq-proxy \
  --hostname rocketmq-proxy \
  --network machine \
  -p 18080:8080 \
  -p 18081:8081 \
  -v /srv/data/rocketmq/proxy/logs:/home/rocketmq/logs \
  -e "NAMESRV_ADDR=rocketmq-namesrv:9876" \
  --cpus=1 \
  --memory=2g \
  --restart unless-stopped \
  apache/rocketmq:5.5.0 \
  sh mqproxy
```


## 启动 Dashboard
```bash
docker run -d \
  --name rocketmq-dashboard \
  --hostname rocketmq-dashboard \
  --network machine \
  -p 18082:8080 \
  -e "JAVA_OPTS=-Drocketmq.namesrv.addr=rocketmq-namesrv:9876 -Dserver.port=8080" \
  --cpus=1 \
  --memory=2g \
  --restart unless-stopped \
  apacherocketmq/rocketmq-dashboard:2.1.0
```