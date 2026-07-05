# 本地环境Docker部署（阿里云）

## 网络

```bash
docker network create machine
```

## 离线安装

```bash
docker save -o doris_fe-4.1.0.tar apache/doris:fe-4.1.0
docker save -o doris_be-4.1.0.tar apache/doris:be-4.1.0
 
docker load -i doris_fe-4.1.0.tar
docker load -i doris_be-4.1.0.tar
```

## 创建数据目录

```bash
mkdir -p /home/machine/doris/fe/data /home/machine/doris/fe/log
mkdir -p /home/machine/doris/be/data /home/machine/doris/be/log
```

## 创建自定义网络

```bash
docker network create --driver bridge --subnet=172.20.80.0/24 doris-net
```

## 启动 FE

```bash
docker run -d \
  --name doris-fe \
  --hostname doris-fe \
  --network doris-net \
  --ip 172.20.80.2 \
  -p 8030:8030 \
  -p 9030:9030 \
  -p 9010:9010 \
  -e FE_SERVERS=fe1:172.20.80.2:9010 \
  -e FE_ID=1 \
  -v /home/machine/doris/fe/data:/opt/apache-doris/fe/doris-meta \
  -v /home/machine/doris/fe/log:/opt/apache-doris/fe/log \
  --cpus=8 \
  --memory=16g --memory-swap=16g \
  --restart unless-stopped \
  apache/doris:fe-4.1.0
```

## 启动 BE

```bash
docker run -d \
  --name doris-be \
  --hostname doris-be \
  --network doris-net \
  --ip 172.20.80.3 \
  -p 8040:8040 \
  -p 9050:9050 \
  -e FE_SERVERS=fe1:172.20.80.2:9010 \
  -e BE_ADDR=172.20.80.3:9050 \
  -v /home/machine/doris/be/data:/opt/apache-doris/be/storage \
  -v /home/machine/doris/be/log:/opt/apache-doris/be/log \
  --cpus=16 \
  --memory=32g --memory-swap=32g \
  --restart unless-stopped \
  apache/doris:be-4.1.0
```

## 修改密码

```bash
ALTER USER 'root'@'%' IDENTIFIED BY 'root';
```