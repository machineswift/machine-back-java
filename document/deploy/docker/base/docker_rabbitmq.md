## 网络

```bash
docker network create machine
```

## 离线安装

```bash
docker save -o rabbitmq_4.3.5-management.tar rabbitmq:4.3.5-management

docker load -i rabbitmq_4.3.5-management.tar
```

## 创建数据目录

```bash
sudo mkdir -p /srv/data/rabbitmq
sudo chmod -R 777 /srv/data/rabbitmq
```

## 启动 RabbitMQ

```bash
docker run -d \
    --name rabbitmq \
    --hostname rabbitmq \
    --network machine \
    -p 15672:15672 -p 5672:5672 -p 15692:15692 \
    -e RABBITMQ_DEFAULT_USER=root \
    -e RABBITMQ_DEFAULT_PASS=root \
    -v /srv/data/rabbitmq/data:/var/lib/rabbitmq \
    --cpus=2 \
    --memory=4g \
   --restart unless-stopped \
rabbitmq:4.3.5-management
```