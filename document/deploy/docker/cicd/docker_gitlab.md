## gitlab
```bash
sudo mkdir -p /srv/data/gitlab/{config,logs,data}
sudo chmod -R 777 /srv/data/gitlab

docker run -d -p 6080:80 -p 22:22 \
--hostname gitlab \
--name gitlab \
--network machine \
--volume /srv/data/gitLab/config:/etc/gitlab \
--volume /srv/data/gitLab/logs:/var/log/gitlab \
--volume /srv/data/gitLab/data:/var/opt/gitlab \
--shm-size=4096m \
-e EXTERNAL_URL="http://公网IP:6080" \
--env GITLAB_OMNIBUS_CONFIG="gitlab_rails['initial_root_password']='YT4kVFg+hbbtyUFzTePqR+faWMM9JrTWl963Y3T';" \
--cpus=24 \
--memory=48g --memory-swap=48g \
--restart unless-stopped \
gitlab/gitlab-ce:18.5.1-ce.0
```

## gitlab-runner
```bash
sudo mkdir -p /srv/data/gitlab-runner
sudo chmod -R 777 /srv/data/gitlab-runner

docker run -d \
--hostname gitlab-runner \
--name gitlab-runner \
--network machine \
-v /var/run/docker.sock:/var/run/docker.sock \
-v /srv/data/gitlab-runner/config:/etc/gitlab-runner \
--cpus=2 \
--memory=4g --memory-swap=4g \
--restart unless-stopped \
gitlab/gitlab-runner:ubuntu-v18.5.0


# 注册 Runner
docker exec -it gitlab-runner gitlab-runner register \
  --non-interactive \
  --url "http://gitlab/" \
  --registration-token "YOUR_TOKEN_HERE" \
  --executor "docker" \
  --docker-image "ubuntu:24.04" \
  --description "docker-runner-ubuntu" \
  --tag-list "docker,ubuntu" \
  --run-untagged="false" \
  --locked="false"
  
  
  

```