## 网络

```bash
docker network create machine
```

## 离线安装

```bash
docker save -o doris_all-in-one-4.1.3-full.tar apache/doris:all-in-one-4.1.3-full
docker load -i doris_all-in-one-4.1.3-full.tar
```

## 创建数据目录

```bash
mkdir -p /home/machine/doris/fe/doris-meta
mkdir -p /home/machine/doris/fe/log
mkdir -p /home/machine/doris/be/storage
mkdir -p /home/machine/doris/be/log
```

## 启动 doris

```bash
docker run -d \
  --name doris \
  --hostname doris \
  --network machine \
  -p 8030:8030 \
  -p 8040:8040 \
  -p 9030:9030 \
  -v /home/machine/doris/fe/doris-meta:/opt/apache-doris/fe/doris-meta \
  -v /home/machine/doris/fe/log:/opt/apache-doris/fe/log \
  -v /home/machine/doris/be/storage:/opt/apache-doris/be/storage \
  -v /home/machine/doris/be/log:/opt/apache-doris/be/log \
  -e FE_SERVERS=fe1:doris:9010 \
  -e FE_ID=1 \
  -e META_DIR=/opt/apache-doris/fe/doris-meta \
  -e BE_ADDR=doris:9050 \
  -e BE_FE_ADDR=doris:9010 \
  --cpus=8 \
  --memory=16g \
  --restart unless-stopped \
  apache/doris:all-in-one-4.1.3-full
```

## 修改密码

```bash
ALTER USER 'root'@'%' IDENTIFIED BY 'root';
```