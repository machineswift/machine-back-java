## 网络

```bash
docker network create machine
```

## 离线安装

```bash
docker save -o mysql_9.7.2.tar mysql:9.7.2

docker load -i mysql_9.7.2.tar
```

## 创建数据目录

```bash
sudo mkdir -p /srv/data/mysql
sudo chmod -R 777 /srv/data/mysql
```

## 启动 mysql

```bash
docker run -d \
    --name mysql   \
    --hostname mysql \
    --network machine \
    -p 3306:3306 \
    -v /srv/data/mysql/data:/var/lib/mysql \
    -v /srv/data/mysql/backups:/backups \
    -v /srv/data/mysql/conf.d:/etc/mysql/conf.d \
    -e MYSQL_ROOT_PASSWORD=root \
    --cpus=2 \
    --memory=4g \
    --restart unless-stopped \
-d mysql:9.7.2 --character-set-server=utf8mb4 --collation-server=utf8mb4_general_ci
```