## 网络

```bash
docker network create machine
```

## 离线安装

```bash
docker save -o milvus_etcd_v3.5.25.tar quay.io/coreos/etcd:v3.5.25
docker save -o milvus_v3.0.1.tar milvusdb/milvus:v3.0.1
docker save -o attu_v3.0.0.tar zilliz/attu:v3.0.0

docker load -i milvus_etcd_v3.5.25.tar
docker load -i milvus_v3.0.1.tar
docker load -i attu_v3.0.0.tar
```

## 创建数据目录

```bash
sudo mkdir -p /srv/data/milvus/etcd
sudo mkdir -p /srv/data/milvus/milvus

sudo chmod -R 777 /srv/data/milvus
```

## 启动 milvus-etcd

```bash
docker run -d \
  --name milvus-etcd \
  --hostname milvus-etcd \
  --network machine \
  -e ETCD_AUTO_COMPACTION_MODE=revision \
  -e ETCD_AUTO_COMPACTION_RETENTION=1000 \
  -e ETCD_QUOTA_BACKEND_BYTES=4294967296 \
  -e ETCD_SNAPSHOT_COUNT=50000 \
  -v /srv/data/milvus/etcd:/etcd \
  --cpus=1 \
  --memory=2g \
  --restart unless-stopped \
  quay.io/coreos/etcd:v3.5.25 \
  etcd -advertise-client-urls=http://milvus-etcd:2379 \
  -listen-client-urls http://0.0.0.0:2379 \
  --data-dir /etcd
```

## 启动 milvus-standalone

```bash
docker run -d \
  --name milvus-standalone \
  --hostname milvus-standalone \
  --network machine \
  -p 19530:19530 -p 9091:9091 \
  --security-opt seccomp:unconfined \
  -e MINIO_REGION=us-east-1 \
  -e ETCD_ENDPOINTS=milvus-etcd:2379 \
  -e MINIO_ADDRESS=minio:9000 \
  -e MINIO_ACCESS_KEY=minioadmin \
  -e MINIO_SECRET_KEY=minioadmin \
  -e COMMON_SECURITY_AUTHORIZATIONENABLED=true \
  -v /srv/data/milvus/milvus:/var/lib/milvus \
  --cpus=4 \
  --memory=8g \
  --restart unless-stopped \
  milvusdb/milvus:v3.0.1 \
  milvus run standalone
```

## 启动 milvus-Attu

```bash
docker run -d \
  --name milvus-attu \
  --hostname milvus-attu \
  --network machine \
  -p 3000:3000 \
  -e MILVUS_URL=milvus-standalone:19530 \
  -e ATTU_SSRF_ALLOWLIST=milvus-standalone \
  --cpus=1 \
  --memory=2g \
  --restart unless-stopped \
  zilliz/attu:v3.0.0
```

## 账号/密码
```bash
http://127.0.0.1:3000
admin/Admin@123456

root/Milvus
```