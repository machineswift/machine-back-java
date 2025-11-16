## 创建网络
```bash
docker network create machine
```


## 启动 Nginx
```bash
sudo mkdir -p /srv/data/nginx/{conf,logs}
sudo chmod -R 777 /srv/data/nginx

--- 创建文件 
/srv/data/nginx/conf/jira.conf


# =========================
# Jira 反向代理
# =========================
server {
    listen 80;
    server_name jira.machinerust.cn;

    # 可选: 重定向到 HTTPS（如果你启用证书）
    # return 301 https://$server_name$request_uri;

    location / {
        proxy_pass http://jira:8080;  # jira 容器的名字 + 内部端口
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_read_timeout 300s;
        proxy_connect_timeout 300s;
    }
}


/srv/data/nginx/conf/confluence.conf

# =========================
# Confluence 反向代理
# =========================
server {
    listen 80;
    server_name confluence.machinerust.cn;

    location / {
        proxy_pass http://confluence:8090; # confluence 容器的名字 + 内部端口
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_read_timeout 300s;
        proxy_connect_timeout 300s;
    }
}



docker run -d \
--name nginx \
--hostname nginx \
--network machine \
-p 80:80 \
-v /srv/data/nginx/conf:/etc/nginx/conf.d \
-v /srv/data/nginx/logs:/var/log/nginx \
--cpus=0.5 \
--memory=1g --memory-swap=2g \
--restart unless-stopped \
nginx:1.29.3
```


## 启动 PostgreSQL
```bash
sudo mkdir -p /srv/data/postgresql
sudo chmod -R 777 /srv/data/postgresql

docker run -d \
--name postgres \
--hostname postgres \
--network machine \
-p 5432:5432 \
-v /srv/data/postgresql:/var/lib/postgresql \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=postgres \
-e POSTGRES_DB=postgres \
--cpus=1 \
--memory=2g --memory-swap=5g \
--restart unless-stopped \
postgres:18.0
```

## 运行Confluence容器
```bash
sudo mkdir -p /srv/data/confluence/{atlassian,data,logs}
sudo chown -R 2002:root /srv/data/confluence/{data,logs,atlassian}
sudo chmod -R 777 /srv/data/confluence

-- 创建数据库
CREATE DATABASE confluence WITH ENCODING 'UTF8' LC_COLLATE='C' LC_CTYPE='C' TEMPLATE=template0;


docker run -d \
--name confluence \
--network machine \
-p 8090:8090 \
-p 8091:8091 \
-v /srv/data/confluence/atlassian/atlassian-agent.jar:/opt/atlassian/confluence/atlassian-agent.jar:ro \
-v /srv/data/confluence/data:/var/atlassian/application-data/confluence \
-v /srv/data/confluence/logs:/opt/atlassian/confluence/logs \
-e ATL_JDBC_URL="jdbc:postgresql://postgres:5432/confluence?ssl=false" \
-e ATL_JDBC_USER="postgres" \
-e ATL_JDBC_PASSWORD="postgres" \
-e ATL_DB_TYPE="postgresql" \
-e TZ=Asia/Shanghai \
-e UMASK=0027 \
-e CONFLUENCE_UID=2002 \
-e CONFLUENCE_GID=0 \
-e CATALINA_OPTS="-Xms512m -Xmx3072m -XX:+UseG1GC -javaagent:/opt/atlassian/confluence/atlassian-agent.jar -Dconfluence.disable.peopledirectory.all=true  -Dplugin.auth.oauth.outgoing.enabled=true -Dplugin.auth.oauth.impersonation.enabled=true ${CATALINA_OPTS}" \
--cpus=2 \
--memory=4g --memory-swap=8g \
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


## 运行jira容器
```bash
sudo mkdir -p /srv/data/jira/{atlassian,data,logs}
sudo chown -R 2001:root /srv/data/jira/{data,logs,atlassian}
sudo chmod -R 777 /srv/data/jira


-- 创建数据库
CREATE DATABASE jira WITH ENCODING 'UTF8' LC_COLLATE='C' LC_CTYPE='C' TEMPLATE=template0;


docker run -d \
--name jira \
--network machine \
-p 8080:8080 \
-p 50000:50000 \
-v /srv/data/jira/atlassian/atlassian-agent.jar:/opt/atlassian/jira/atlassian-agent.jar:ro \
-v /srv/data/jira/data:/var/atlassian/application-data/jira \
-v /srv/data/jira/logs:/opt/atlassian/jira/logs \
-e ATL_JDBC_URL="jdbc:postgresql://postgres:5432/jira?ssl=false" \
-e ATL_JDBC_USER="postgres" \
-e ATL_JDBC_PASSWORD="postgres" \
-e ATL_DB_TYPE="postgresql" \
-e TZ=Asia/Shanghai \
-e UMASK=0027 \
-e JIRA_UID=2001 \
-e JIRA_GID=0 \
-e CATALINA_OPTS="-Xms512m -Xmx3072m -XX:+UseG1GC -javaagent:/opt/atlassian/jira/atlassian-agent.jar -Datlassian.mail.senddisabled=false -Datlassian.mail.fetchdisabled=false -Dplugin.auth.oauth.outgoing.enabled=true -Dplugin.auth.oauth.impersonation.enabled=true ${CATALINA_OPTS}" \
--cpus=2 \
--memory=4g --memory-swap=8g \
--restart unless-stopped \
atlassian/jira-software:10.7.4
```

### 破解jira
```bash
docker exec -it jira bash
cd /opt/atlassian/jira
java -jar atlassian-agent.jar -d -m machineswift@qq.com -n jira -p jira -o http://localhost -s BB5E-PMYV-C2TA-GY5Z
```

### 管理员密码
```bash
admin/admin
```
