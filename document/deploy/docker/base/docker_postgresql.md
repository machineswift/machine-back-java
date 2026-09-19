## 网络

```bash
docker network create machine
```

## 离线安装

```bash
docker save -o postgres_18.6.tar postgres:18.6

docker load -i postgres_18.6.tar
```

## 创建数据目录

```bash
sudo mkdir -p /srv/data/postgresql
sudo chmod -R 777 /srv/data/postgresql
```

## 启动 postgresql

```bash
docker run -d \
    --name postgres \
    --hostname postgres \
    --network machine \
    -p 5432:5432 \
    -v /srv/data/postgresql:/var/lib/postgresql \
    -e POSTGRES_USER=postgres \
    -e POSTGRES_PASSWORD=postgres \
    -e POSTGRES_DB=postgres \
    --cpus=2 \
    --memory=4g \
    --restart unless-stopped \
postgres:18.6
```