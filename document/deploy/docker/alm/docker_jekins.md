## jenkins

### docker部署
```text
sudo mkdir -p /srv/data/jenkins
sudo chmod -R 777 /srv/data/jenkins

docker run -d \
  --name jenkins \
  -p 18080:8080 \
  -p 50000:50000 \
  -v /srv/data/jenkins:/var/jenkins_home \
  -v /var/run/docker.sock:/var/run/docker.sock \
  --cpus=4 \
  --memory=8g \
  --memory-swap=8g \
  --restart unless-stopped \
  jenkins/jenkins:2.533-jdk21
```
### 查看 admin 密码
```text
docker logs jenkins | grep "Please use the following password"
# 或
cat /srv/data/jenkins/secrets/initialAdminPassword

machine/machine
```



