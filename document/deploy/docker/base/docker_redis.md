## 网络

```bash
docker network create machine
```

## 离线安装

```bash
docker save -o redis_8.8.tar redis:8.8

docker load -i redis_8.8.tar
```

## 创建数据目录

```bash
sudo mkdir -p /srv/data/redis
sudo chmod -R 777 /srv/data/redis
```

## 启动 redis

```bash
docker run -d \
    --name redis \
    --hostname redis \
    --network machine \
    -p 6379:6379 \
    -v /srv/data/redis/data:/data \
    -v /srv/data/redis/conf/redis.conf:/usr/local/etc/redis/redis.conf \
    -v /srv/data/redis/logs:/logs \
    --cpus=2 \
    --memory=4g \
    --restart unless-stopped \
redis:8.8 --requirepass "redis"
```