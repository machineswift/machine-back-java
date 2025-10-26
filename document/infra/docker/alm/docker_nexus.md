## nexus

### docker部署
```text
sudo mkdir -p /srv/data/nexus
sudo chmod -R 777 /srv/data/nexus

docker run -d --name nexus\
-p 18081:8081 \
-v /srv/data/nexus:/nexus-data \
--cpus=4 \
--memory=8g --memory-swap=8g \
--restart unless-stopped \
sonatype/nexus3:3.85.0-java17-alpine
```

### 查看 admin 密码
```text
docker exec nexus cat /nexus-data/admin.password
16e83f51-282f-44ef-94ae-60590722a6ee
```

### nexus 配置
```text
1.添加代理
    name:maven-aliyun
    format:maven2
    type:proxy
    URL:https://maven.aliyun.com/repository/public/

2.配置group
  将maven-aliyun仓库添加到maven-public组里，结果如下：
      maven-releases
      maven-snapshots
      maven-aliyun
      maven-central
      
3.新建角色并赋予权限

    权限1：全局搜索权限
        Name: Search Access
        Privileges:
          - nx-search-read

    权限2：阿里云代理仓库只读权限
        Name: Developer Aliyun Read Access
        Privileges:
          - nx-repository-view-maven2-maven-aliyun-read
          - nx-repository-view-maven2-maven-aliyun-browse
        Repository: maven-aliyun

    权限3：中央仓库只读权限
        Name: Developer Central Read Access
        Privileges:
          - nx-repository-view-maven2-maven-central-read
          - nx-repository-view-maven2-maven-central-browse
        Repository: maven-central

    权限4：开发人员Release仓库权限
        Name: Developer Releases RW Access
        Privileges: 
          - nx-repository-view-maven2-maven-releases-read
          - nx-repository-view-maven2-maven-releases-browse
          - nx-repository-view-maven2-maven-releases-add
          - nx-repository-view-maven2-maven-releases-edit
        Repository: maven-releases

    权限5：开发人员Snapshot仓库权限
        Name: Developer Snapshots RW Access
        Privileges:
          - nx-repository-view-maven2-maven-snapshots-read
          - nx-repository-view-maven2-maven-snapshots-browse
          - nx-repository-view-maven2-maven-snapshots-add
          - nx-repository-view-maven2-maven-snapshots-edit
        Repository: maven-snapshots 

4.新建用户并赋予上面的角色 （注意:权限5根据情况赋予）
    用户:machine
    密码:XXX
```

### setting.xml 配置
```text
1.复制当前目录下面的 setting.xml
2.修改 mirrors-->mirror-->url 里面的ip和端口
```

### pom.xml 配置
```text
1.修改 distributionManagement-->repository-->url 里面的ip和端口
```