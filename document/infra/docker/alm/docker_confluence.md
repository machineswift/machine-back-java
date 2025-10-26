## 启动 PostgreSQL
```bash
sudo mkdir -p /srv/data/postgres/{data,backups}
sudo chmod -R 777 /srv/data/postgres

docker run -d -p 5432:5432\
--name postgres \
--network machine \
-v /srv/data/postgres/data:/var/lib/postgresql/data \
-v /srv/data/postgres/backups:/backups \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=postgres \
-e POSTGRES_DB=postgres \
-e PGDATA=/var/lib/postgresql/data/pgdata \
--cpus=4 \
--memory=8g --memory-swap=8g \
--restart unless-stopped \
postgres:15.14
```

### 用户赋予操作数据库的权限
```bash
GRANT ALL PRIVILEGES ON DATABASE postgres TO postgres;
```

## 运行Confluence容器
```bash
sudo mkdir -p /srv/data/confluence/{atlassian,data,logs}
sudo chown -R 2002:root /srv/data/confluence/{data,logs,atlassian}
sudo chmod -R 777 /srv/data/confluence

docker run -d \
--name confluence \
--network machine \
-p 8090:8090 \
-p 8091:8091 \
-v /srv/data/confluence/atlassian/atlassian-agent.jar:/opt/atlassian/confluence/atlassian-agent.jar:ro \
-v /srv/data/confluence/data:/var/atlassian/application-data/confluence \
-v /srv/data/confluence/logs:/opt/atlassian/confluence/logs \
-e ATL_JDBC_URL="jdbc:postgresql://postgres:5432/postgres?ssl=false" \
-e ATL_JDBC_USER="postgres" \
-e ATL_JDBC_PASSWORD="postgres" \
-e ATL_DB_TYPE="postgresql" \
-e TZ=Asia/Shanghai \
-e UMASK=0027 \
-e CONFLUENCE_UID=2002 \
-e CONFLUENCE_GID=0 \
-e CATALINA_OPTS="-javaagent:/opt/atlassian/confluence/atlassian-agent.jar -Dconfluence.disable.peopledirectory.all=true ${CATALINA_OPTS}" \
--cpus=8 \
--memory=16g --memory-swap=16g \
--restart unless-stopped \
atlassian/confluence-server:8.8-jdk17
```

### 破解Confluence
```bash
docker exec -it confluence bash
cd /opt/atlassian/confluence
java -jar atlassian-agent.jar -d -m machineswift@qq.com -n confluence -p 'conf' -o http://localhost:8090 -s B6L9-4454-XKNM-8YEF
```

### 管理员密码
```bash
admin/admin
```
