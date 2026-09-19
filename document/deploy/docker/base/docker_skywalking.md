## 网络

```bash
docker network create machine
```

## 离线安装

```bash
docker save -o skywalking-oap-server_11.0.0.tar  apache/skywalking-oap-server:11.0.0
docker save -o skywalking-ui_horizon-1.0.0.tar  apache/skywalking-ui:horizon-1.0.0

docker load -i skywalking-oap-server_11.0.0.tar
docker load -i skywalking-ui_horizon-1.0.0.tar
```


## 启动 skywalking-oap-server
```bash
docker run -d \
    --name skywalking-server \
    --hostname skywalking-server \
    --network machine \
    -p 11800:11800 \
    -p 12800:12800 \
    -p 17128:17128 \
    -e SW_STORAGE=elasticsearch \
    -e SW_ES_USER=elastic \
    -e SW_ES_PASSWORD='elastic@2' \
    -e SW_STORAGE_ES_CLUSTER_NODES=elasticsearch:9200 \
    -e SW_CORE_RECORD_DATA_TTL=90 \
    -e SW_CORE_METRICS_DATA_TTL=270 \
    --cpus=2 \
    --memory=4g \
    --restart unless-stopped \
    apache/skywalking-oap-server:11.0.0
```


### 启动 skywalking-ui
```bash
docker run -d \
    --name skywalking-ui \
    --hostname skywalking-ui \
    --network machine \
    -e HORIZON_OAP_QUERY_URL=http://skywalking-server:12800 \
    -e HORIZON_OAP_ADMIN_URL=http://skywalking-server:17128 \
    -e 'HORIZON_AUTH_LOCAL_USERS=[{"username":"admin","passwordHash":"$argon2id$v=19$m=65536,t=3,p=4$eemqy1r72oSXR58y8VpRqw$Bn/dULrmJTHEi3263KfgWDEwQmUsqNLi3xwyv/DekHM","roles":["admin"]}]' \
    -p 8081:8081 \
    --cpus=1 \
    --memory=2g \
    --restart unless-stopped \
    apache/skywalking-ui:horizon-1.0.0
```

## 账号/密码
```bash
admin/admin
```