## 网络

```bash
docker network create machine
```

## 离线安装

```bash
docker save -o scylla_2026.3.tar scylladb/scylla:2026.3

docker load -i scylla_2026.3.tar
```

## 创建数据目录

```bash
sudo mkdir -p /srv/data/scylla/{data,backups}
sudo chmod -R 777 /srv/data/scylla
```

## 启动 ScyllaDB

```bash
docker run -d \
    --name scylla \
    --hostname scylla \
    --network machine \
    -p 9042:9042 \
    -v /srv/data/scylla/data:/var/lib/scylla \
    -v /srv/data/scylla/backups:/backups \
    -e SCYLLA_CLUSTER_NAME=scylla \
    -e SCYLLA_DC=dc1 \
    -e SCYLLA_RACK=rack1 \
    -e SCYLLA_AUTHENTICATION=true \
    -e SCYLLA_PASSWORD_HASHING=bcrypt \
    -e SCYLLA_ADMIN_USER=scylla \
    -e SCYLLA_ADMIN_PASSWORD=scylla \
    --cpus=4 \
    --memory=16g \
    --restart unless-stopped \
scylladb/scylla:2026.3
```