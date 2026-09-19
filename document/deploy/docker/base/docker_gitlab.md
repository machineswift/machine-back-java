## 网络

```bash
docker network create machine
```

## 离线安装

```bash
docker save -o gitlab-ce_19.3.0-ce.0.tar gitlab/gitlab-ce:19.3.0-ce.0

docker load -i gitlab-ce_19.3.0-ce.0.tar
```

## 创建数据目录

```bash
sudo mkdir -p /srv/data/gitlab/{config,logs,data}
sudo chmod -R 777 /srv/data/gitlab
```

##   启动 gitlab
```bash
docker run -d \
    --name gitlab \
    --hostname gitlab \
    --network machine \
    -p 6080:80 -p 22:22
    --volume /srv/data/gitLab/config:/etc/gitlab \
    --volume /srv/data/gitLab/logs:/var/log/gitlab \
    --volume /srv/data/gitLab/data:/var/opt/gitlab \
    --shm-size=4096m \
    -e EXTERNAL_URL="http://公网IP:6080" \
    --env GITLAB_OMNIBUS_CONFIG="gitlab_rails['initial_root_password']='YT4kVFg+hbbtyUFzTePqR+faWMM9JrTWl963Y3T';" \
    --cpus=16 \
    --memory=48g \
    --restart unless-stopped \
gitlab/gitlab-ce:19.3.0-ce.0
```