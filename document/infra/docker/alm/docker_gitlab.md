## gitlab
```bash
sudo mkdir -p /srv/data/gitlab/{config,logs,data}
sudo chmod -R 777 /srv/data/gitlab

docker run -d -p 6080:80 -p 22:22 \
--hostname 公网IP \
--name gitlab \
--volume /srv/data/gitLab/config:/etc/gitlab \
--volume /srv/data/gitLab/logs:/var/log/gitlab \
--volume /srv/data/gitLab/data:/var/opt/gitlab \
--shm-size=4096m \
-e EXTERNAL_URL="http://公网IP:6080" \
--env GITLAB_OMNIBUS_CONFIG="gitlab_rails['initial_root_password']='YT4kVFg+hbbtyUFzTePqR+faWMM9JrTWl963Y3T';" \
--cpus=24 \
--memory=48g --memory-swap=48g \
--restart unless-stopped \
gitlab/gitlab-ce:18.4.2-ce.0
```