## 网络

```bash
docker network create machine
```

## 离线安装

```bash
docker save -o minio_RELEASE.2025-04-22T22-12-26Z.tar minio/minio:RELEASE.2025-04-22T22-12-26Z

docker load -i minio_RELEASE.2025-04-22T22-12-26Z.tar
```

## 创建数据目录

```bash
sudo mkdir -p /srv/data/minio/{data,config,logs}
sudo mkdir -p /srv/data/minio/disk{1,2,3,4,5,6,7,8}
sudo chmod -R 777 /srv/data/minio
```

## 启动 minio

```bash
docker run -d \
    --name minio \
    --hostname minio \
    --network machine \
    -p 9001:9001 -p 9000:9000 \
    -e "MINIO_ROOT_USER=minioadmin" \
    -e "MINIO_ROOT_PASSWORD=minioadmin" \
    -e "MINIO_AUDIT_LOGGER_CONSOLE_ENABLE=on" \
    -e "MINIO_AUDIT_LOGGER_CONSOLE_FORMAT=json" \
    -v /srv/data/minio/disk1:/data/disk1 \
    -v /srv/data/minio/disk2:/data/disk2 \
    -v /srv/data/minio/disk3:/data/disk3 \
    -v /srv/data/minio/disk4:/data/disk4 \
    -v /srv/data/minio/disk5:/data/disk5 \
    -v /srv/data/minio/disk6:/data/disk6 \
    -v /srv/data/minio/disk7:/data/disk7 \
    -v /srv/data/minio/disk8:/data/disk8 \
    -v /srv/data/minio/config:/root/.minio \
    --cpus=2 \
    --memory=4g \
minio/minio:RELEASE.2025-04-22T22-12-26Z server \
/data/disk1 /data/disk2 /data/disk3 /data/disk4 \
/data/disk5 /data/disk6 /data/disk7 /data/disk8 \
--console-address ":9001"
```